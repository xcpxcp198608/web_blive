package com.wiatec.blive.service;

import com.wiatec.blive.entity.ResultInfo;
import com.wiatec.blive.instance.Application;
import com.wiatec.blive.orm.dao.PayResultDao;
import com.wiatec.blive.orm.pojo.PayResultInfo;
import com.wiatec.blive.xutils.LoggerUtil;
import com.wiatec.blive.xutils.TextUtil;
import com.wiatec.blive.xutils.TimeUtil;
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

@Service
public class PayService {

//    private static final String PAYPAL_VERIFY_URL = "https://api.sandbox.paypal.com/v1/payments/payment/";
    private static final String PAYPAL_VERIFY_URL = "https://api.paypal.com/v1/payments/payment/";
    private static final long EXPIRES = 86400000;

    @Resource
    private PayResultDao payResultDao;

    public ResultInfo<PayResultInfo> verify(String payerName, int publisherId, String paymentId){
        ResultInfo<PayResultInfo> resultInfo = new ResultInfo<>();
        if(TextUtil.isEmpty(paymentId)){
            List<PayResultInfo> payResultInfos = payResultDao.selectOneByPayer(new PayResultInfo(payerName, publisherId));
            if(payResultInfos == null || payResultInfos.size() <= 0) {
                resultInfo.setCode(ResultInfo.CODE_UNAUTHORIZED);
                resultInfo.setStatus(ResultInfo.STATUS_UNAUTHORIZED);
                resultInfo.setMessage("This is a pay program, please follow the instructions to make payment.");
                return resultInfo;
            }
            PayResultInfo payResultInfo = payResultInfos.get(0);
            LoggerUtil.d(payResultInfo);
            return handleResult(payResultInfo);
        }
        if (payResultDao.countOne(paymentId) == 1) {
            PayResultInfo payResultInfo = payResultDao.selectOne(paymentId);
            return handleResult(payResultInfo);
        }
        return realVerify(PAYPAL_VERIFY_URL + paymentId, payerName,  publisherId);
    }

    private ResultInfo<PayResultInfo> realVerify(String url, String payerName, int publisherId){
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder builder = new StringBuilder();
        ResultInfo<PayResultInfo> resultInfo = new ResultInfo<>();
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
            payResultInfo.setUpdateTime(updateTime);
            if("approved".equals(payResultInfo.getState())){
                if(payResultDao.countOne(payResultInfo.getPaymentId()) == 1){
                    payResultDao.updateOne(payResultInfo);
                }else{
                    payResultDao.insertOne(payResultInfo);
                }
                resultInfo.setCode(ResultInfo.CODE_OK);
                resultInfo.setStatus(ResultInfo.STATUS_OK);
                resultInfo.setMessage("pay successfully");
                resultInfo.setT(payResultInfo);
                return resultInfo;
            }else{
                resultInfo.setCode(ResultInfo.CODE_INVALID);
                resultInfo.setStatus(ResultInfo.STATUS_INVALID);
                resultInfo.setMessage("pay state no approved");
                return resultInfo;
            }
        } catch (IOException e) {
            e.printStackTrace();
            resultInfo.setCode(ResultInfo.CODE_INVALID);
            resultInfo.setStatus(ResultInfo.STATUS_INVALID);
            resultInfo.setMessage("payment id error");
            return resultInfo;
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

    private ResultInfo<PayResultInfo> handleResult(PayResultInfo payResultInfo){
        ResultInfo<PayResultInfo> resultInfo = new ResultInfo<>();
        if(!"approved".equals(payResultInfo.getState())){
            resultInfo.setCode(ResultInfo.CODE_INVALID);
            resultInfo.setStatus(ResultInfo.STATUS_INVALID);
            resultInfo.setMessage("pay state no approved");
            return resultInfo;
        }
        long time = TimeUtil.getUnixFromStr(payResultInfo.getTime());
        LoggerUtil.d(time);
        if(time + EXPIRES < System.currentTimeMillis()){
            resultInfo.setCode(ResultInfo.CODE_INVALID);
            resultInfo.setStatus(ResultInfo.STATUS_INVALID);
            resultInfo.setMessage("pay state out expires");
            return resultInfo;
        }
        resultInfo.setCode(ResultInfo.CODE_OK);
        resultInfo.setStatus(ResultInfo.STATUS_OK);
        resultInfo.setMessage("pay successfully");
        resultInfo.setT(payResultInfo);
        return resultInfo;
    }
}
