package com.wiatec.blive.txcloud;

import com.wiatec.blive.common.security.MD5Utils;
import com.wiatec.blive.common.utils.TimeUtil;
import com.wiatec.blive.orm.pojo.LiveChannelInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author patrick
 */
public class LiveChannelMaster {

    private static Logger logger = LoggerFactory.getLogger(LiveChannelMaster.class);

    private static final String KEY = "077d6c1603ce4634a78ebd37a35bb655";
    private static final String BIZID = "24467";



    public static LiveChannelInfo create(int userId){
        LiveChannelInfo liveChannelInfo = new LiveChannelInfo();
        liveChannelInfo.setUserId(userId);
        String streamId = BIZID + "_" + MD5Utils.create16(userId+"");
        long time = TimeUtil.getExpiresByDays(7).getTime() / 1000L;
        String auth = getSafeUrl(streamId, time);
        String pushUrl = new StringBuilder()
                .append("rtmp://")
                .append(BIZID)
                .append(".livepush.myqcloud.com/live/")
                .append(streamId)
                .append("?bizid=")
                .append(BIZID)
                .append("&")
                .append(auth).toString();
        String playUrl = new StringBuilder()
                .append("http://")
                .append(BIZID)
                .append(".liveplay.myqcloud.com/live/")
                .append(streamId)
                .append(".m3u8").toString();
        String rtmpUrl = new StringBuilder()
                .append("rtmp://")
                .append(BIZID)
                .append(".livepush.myqcloud.com/live/")
                .toString();
        String rtmpKey = new StringBuilder()
                .append(streamId)
                .append("?bizid=")
                .append(BIZID)
                .append("&")
                .append(auth)
                .toString();
        liveChannelInfo.setUrl(pushUrl);
        liveChannelInfo.setPlayUrl(playUrl);
        liveChannelInfo.setRtmpUrl(rtmpUrl);
        liveChannelInfo.setRtmpKey(rtmpKey);
        return liveChannelInfo;
    }


    private static String getSafeUrl(String streamId, long txTime) {
        String input = new StringBuilder().
                append(KEY).
                append(streamId).
                append(Long.toHexString(txTime).toUpperCase())
                .toString();
        return new StringBuilder().
                        append("txSecret=").
                        append(MD5Utils.create32(input)).
                        append("&").
                        append("txTime=").
                        append(Long.toHexString(txTime).toUpperCase())
                        .toString();
    }

    public static void main (String [] args){
        System.out.println(create(41));
    }

}
