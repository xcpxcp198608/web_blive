package com.wiatec.blive.txcloud;

import com.wiatec.blive.common.http.HttpMaster;
import com.wiatec.blive.common.utils.TextUtil;
import com.wiatec.blive.orm.dao.LiveChannelDao;
import com.wiatec.blive.orm.dao.VodChannelDao;
import com.wiatec.blive.orm.pojo.ChannelInfo;
import com.wiatec.blive.orm.pojo.LiveChannelInfo;
import com.wiatec.blive.orm.pojo.VodChannelInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * @author patrick
 */
@Service
public class EventCallbackService {

    private final Logger logger = LoggerFactory.getLogger(EventCallbackService.class);

    @Resource private LiveChannelDao liveChannelDao;
    @Resource private VodChannelDao vodChannelDao;

    /**
     * tx 事件回调处理
     * @param txEventInfo
     * @return
     */
    public boolean handleEvent(TXEventInfo txEventInfo){
        logger.info(txEventInfo.toString());
        int userId = liveChannelDao.selectUserIdByStreamId(txEventInfo.getStream_id());
        if(userId < 0){
            return false;
        }
        switch (txEventInfo.getEvent_type()){
            case 0:
                streamStop(userId, txEventInfo);
                break;
            case 1:
                streamStart(userId, txEventInfo);
                break;
            case 100:
                vodCreate(userId, txEventInfo);
                break;
            case 200:
                screenshotCreate(userId, txEventInfo);
                break;
            default:
                break;
        }
        return true;
    }

    private void streamStop(int userId, TXEventInfo txEventInfo){
        liveChannelDao.updateUnavailableByUserId(userId);
    }

    private void streamStart(int userId, TXEventInfo txEventInfo){

    }


    private void vodCreate(int userId, TXEventInfo txEventInfo){
        ChannelInfo liveChannelInfo = liveChannelDao.selectOneByUserId(userId);
        ChannelInfo channelInfo = ChannelInfo.createFrom(txEventInfo);
        channelInfo.setUserId(userId);
        channelInfo.setRating(liveChannelInfo.getRating());
        channelInfo.setTitle(liveChannelInfo.getTitle());
        channelInfo.setMessage(liveChannelInfo.getMessage());
        channelInfo.setPreview(liveChannelInfo.getPreview());
        channelInfo.setLink(liveChannelInfo.getLink());
        channelInfo.setCategory(liveChannelInfo.getCategory());
        channelInfo.setType(liveChannelInfo.getType());
        vodChannelDao.insertChannel(channelInfo);
    }

    private void screenshotCreate(int userId, TXEventInfo txEventInfo){

    }


    /**
     * 请求tx进行截图
     * @param channelInfo
     */
    private void requestSnapshot(ChannelInfo channelInfo){
        String nonce = new Random().nextInt(1000)+ "";
        String params = new StringBuilder()
                .append("Action=CreateSnapshotByTimeOffset").append("&")
                .append("Nonce=").append(nonce).append("&")
                .append("Region=szjr").append("&")
                .append("SecretId=AKIDcDj5GHhmJpESbZsyOdhZWak2cdUcBTOC").append("&")
                .append("SignatureMethod=HmacSHA256").append("&")
                .append("Timestamp=").append(System.currentTimeMillis() / 1000).append("&")
                .append("definition=10").append("&")
                .append("fileId=").append(channelInfo.getFileId()).append("&")
                .append("timeOffset=100")
                .toString();
        String url = TXKeyMaster.getSignatureUrl(params);
        HttpMaster.post(url)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                    }
                });
    }

    /**
     * tx 截图回调处理
     * @param txEventInfo
     * @return
     */
    public boolean handleScreenshotEvent(TXScreenshotEventInfo txEventInfo){
        logger.info(txEventInfo.toString());
        TXScreenshotEventInfo.EventData data = txEventInfo.getData();
        String fileId = data.getFileId();
        if(TextUtil.isEmpty(fileId)){
            return false;
        }
        if(vodChannelDao.countByFileId(fileId) !=1){
            return false;
        }
        List<TXScreenshotEventInfo.PicInfo> picInfoList = data.getPicInfo();
        if(picInfoList == null || picInfoList.size() <= 0){
            return false;
        }
        System.out.println(picInfoList);
        TXScreenshotEventInfo.PicInfo picInfo = picInfoList.get(0);
        if(picInfo.getStatus() != 0){
            return false;
        }
        if(vodChannelDao.updatePreviewByFileId(fileId, picInfo.getUrl()) != 1){
            return false;
        }
        return true;
    }
}
