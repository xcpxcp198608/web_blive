package com.wiatec.blive.service;

import com.wiatec.blive.common.result.ResultInfo;
import com.wiatec.blive.common.result.ResultMaster;
import com.wiatec.blive.common.result.XException;
import com.wiatec.blive.common.utils.TextUtil;
import com.wiatec.blive.common.utils.TimeUtil;
import com.wiatec.blive.instance.Application;
import com.wiatec.blive.orm.dao.PayResultDao;
import com.wiatec.blive.orm.pojo.PayResultInfo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * @author patrick
 */
@Service
public class PayService {

    private static final String PAYPAL_VERIFY_URL_SANDBOX = "https://api.sandbox.paypal.com/v1/payments/payment/";
    private static final String PAYPAL_VERIFY_URL = "https://api.paypal.com/v1/payments/payment/";
    private static final long EXPIRES = 86400000;

    @Resource
    private PayResultDao payResultDao;

    /**
     * 验证观看者是否已付款给播放者
     * 1. 若paymentId为空，则数据库中查询之前支付结果中是否有观看者付给播放者的支付信息
     * 2. 若之前有支付信息获取最后一条支付信息，检查支付结果是否approved并在观看有效期内
     * 3. 若paymentId不为空，则根据paymentId在数据库中查询交易记录是否存在并检查支付结果是否approved并在观看有效期内
     * 4. 若数据中不存在paymentId的交易记录，则去paypal服务器验证交易并将验证结果存入本地数据库
     * @param payerName    观看者username
     * @param publisherId  播放者id
     * @param paymentId    payment id
     * @return ResultInfo
     */
    public ResultInfo<PayResultInfo> verify(String payerName, int publisherId, String paymentId){
        if(TextUtil.isEmpty(paymentId)){
            List<PayResultInfo> payResultInfoList = payResultDao
                    .selectOneByPayer(new PayResultInfo(payerName, publisherId));
            if(payResultInfoList == null || payResultInfoList.size() <= 0) {
                throw new XException("This is a pay program, please follow the instructions to make payment.");
            }
            PayResultInfo payResultInfo = payResultInfoList.get(0);
            return handleResult(payResultInfo);
        }
        if (payResultDao.countOne(paymentId) == 1) {
            PayResultInfo payResultInfo = payResultDao.selectOne(paymentId);
            return handleResult(payResultInfo);
        }
        return realVerify(PAYPAL_VERIFY_URL + paymentId, payerName,  publisherId);
    }

    /**
     * 通过paymentId去paypal服务器接口验证交易结果
     * @param url paypal服务器接口 + paymentId
     * @param payerName payerName
     * @param publisherId publisherId
     * @return ResultInfo
     */
    private ResultInfo<PayResultInfo> realVerify(String url, String payerName, int publisherId){
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder builder = new StringBuilder();
        try {
            URL url1 = new URL(url);
            URLConnection urlConnection = url1.openConnection();
            urlConnection.setRequestProperty("Content-type","application/json");
            urlConnection.setRequestProperty("Authorization","Bearer "+Application.getInstance().getPayAccessToken());
            inputStream = urlConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while((line = bufferedReader.readLine()) != null){
                builder.append(line);
            }
            String result =  builder.toString();
            JSONObject jsonObject = new JSONObject(result);
            System.out.println(jsonObject);
            PayResultInfo payResultInfo = new PayResultInfo();
            payResultInfo.setPayerName(payerName);
            payResultInfo.setPublisherId(publisherId);
            payResultInfo.setChannelName("");
            payResultInfo.setPaymentId(jsonObject.getString("id"));
            payResultInfo.setState(jsonObject.getString("state"));
            payResultInfo.setCart(jsonObject.getString("cart"));
            JSONObject payer = jsonObject.getJSONObject("payer");
            payResultInfo.setPaymentMethod(payer.getString("payment_method"));
            payResultInfo.setPaymentStatus(payer.getString("status"));
            JSONObject payer_info = payer.getJSONObject("payer_info");
            payResultInfo.setEmail(payer_info.getString("email"));
            payResultInfo.setFirstName(payer_info.getString("first_name"));
            payResultInfo.setLastName(payer_info.getString("last_name"));
            payResultInfo.setPayPalPayerId(payer_info.getString("payer_id"));
            payResultInfo.setPhone(payer_info.getString("phone"));
            payResultInfo.setCountryCode(payer_info.getString("country_code"));
            JSONArray transactions = jsonObject.getJSONArray("transactions");
            JSONObject jsonObject1 = transactions.getJSONObject(0);
            payResultInfo.setDescription(jsonObject1.getString("description"));
            JSONArray related_resources = jsonObject1.getJSONArray("related_resources");
            JSONObject jsonObject2 = related_resources.getJSONObject(0);
            JSONObject sale = jsonObject2.getJSONObject("sale");
            JSONObject amount = sale.getJSONObject("amount");
            payResultInfo.setPrice(Float.parseFloat(amount.getString("total")));
            payResultInfo.setCurrency(amount.getString("currency"));
            JSONObject transaction_fee = sale.getJSONObject("transaction_fee");
            payResultInfo.setTransactionFee(Float.parseFloat(transaction_fee.getString("value")));
            String createTime = sale.getString("create_time");
            createTime = createTime.replace("T", " ");
            createTime = createTime.replace("Z", "");
            payResultInfo.setCreateTime(createTime);
            String updateTime = sale.getString("update_time");
            updateTime = updateTime.replace("T", " ");
            updateTime = updateTime.replace("Z", "");
            payResultInfo.setModifyTime(updateTime);
            if(!"approved".equals(payResultInfo.getState())) {
                throw new XException("pay state no approved");
            }
            if(payResultDao.countOne(payResultInfo.getPaymentId()) == 1){
                payResultDao.updateOne(payResultInfo);
            }else{
                payResultDao.insertOne(payResultInfo);
            }
            return ResultMaster.success(payResultInfo);
        } catch (IOException e) {
            throw new XException("pay result validate error");
        }finally {
            try {
                if(bufferedReader != null) {
                    bufferedReader.close();
                }
                if(inputStream != null){
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 检查支付结果是否通过并且是否在有效期内
     * @param payResultInfo PayResultInfo
     * @return ResultInfo
     */
    private ResultInfo<PayResultInfo> handleResult(PayResultInfo payResultInfo){
        if(!"approved".equals(payResultInfo.getState())){
            throw new XException("pay state no approved");
        }
        long time = TimeUtil.getUnixFromStr(payResultInfo.getTime());
        if(time + EXPIRES < System.currentTimeMillis()){
            throw new XException("pay state expires");
        }
        return ResultMaster.success(payResultInfo);
    }
}
