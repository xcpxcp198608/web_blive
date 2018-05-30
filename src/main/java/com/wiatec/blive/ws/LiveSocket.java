package com.wiatec.blive.ws;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wiatec.blive.common.result.EnumResult;
import com.wiatec.blive.common.result.XException;
import com.wiatec.blive.common.utils.ApplicationContextHelper;
import com.wiatec.blive.common.utils.TextUtil;
import com.wiatec.blive.dto.ChannelCommentInfo;
import com.wiatec.blive.orm.dao.*;
import com.wiatec.blive.orm.pojo.*;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author patrick
 */
@Component
@ServerEndpoint("/live/{groupId}/{userId}")
public class LiveSocket {

    private final Logger logger = LoggerFactory.getLogger(LiveSocket.class);
    public static final String [] KEYS = {"arse", "ass", "asshole", "bastard", "bitch", "bollocks",
            "child-fucker", "Christ on a bike", "Christ on a cracker", "cunt", "fuck", "Fuck",
            "FUCK", "fucking", "Fucking", "FUCKING", "fucker", "Fucker", "FUCKER", "Fuckers",
            "FUCKERS", "fuckers", "goddamn", "godsdamn", "holy shit", "motherfucker",
            "Motherfuckers", "nigga", "nigger", "shit", "shit ass", "shitass", "son of a bitch",
            "son of a motherless goat", "son of a whore", "twat"};


    private static AuthRegisterUserDao authRegisterUserDao;
    private static LiveChannelDao liveChannelDao;
    private static LogLiveCommentDao logLiveCommentDao;
    private static LiveViewDao liveViewDao;
    private static LdIllegalWordDao ldIllegalWordDao;

    private static List<String> illegalWords;

    static {
        SqlSession sqlSession = (SqlSession) ApplicationContextHelper.getApplicationContext().getBean("sqlSessionTemplate");
        liveViewDao = sqlSession.getMapper(LiveViewDao.class);
        authRegisterUserDao = sqlSession.getMapper(AuthRegisterUserDao.class);
        liveChannelDao = sqlSession.getMapper(LiveChannelDao.class);
        logLiveCommentDao = sqlSession.getMapper(LogLiveCommentDao.class);
        ldIllegalWordDao = sqlSession.getMapper(LdIllegalWordDao.class);
        illegalWords = ldIllegalWordDao.selectAll();
    }


    static Map<Integer, LiveSocket> clientMap = new ConcurrentHashMap<>();
    private Session session;
    private int groupId;
    private int userId;
    private String username = "guest";
    private LiveViewInfo liveViewInfo = new LiveViewInfo();

    private boolean insertSuccess = false;

    /**
     * web socket connect
     * @param session socket session
     * @param groupId pusher id
     * @param userId viewer id
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("groupId")int groupId, @PathParam("userId")int userId) {
        this.session = session;
        this.userId = userId;
        this.groupId = groupId;

        AuthRegisterUserInfo userInfo = authRegisterUserDao.selectOneById(userId);
        if(userInfo != null){
            username = userInfo.getUsername();
        }

        clientMap.put(userId, this);
        logger.debug("ws -> new client connected, group id: " + groupId +
                ", user id: " + userId + ", total client is: {}", getOnlineCount());
        logger.debug("ws -> new client connected, group id: " + groupId +
                ", user id: " + userId + ", this group client is: {}", getCountByGroupId(groupId));

        ChannelCommentInfo channelCommentInfo = new ChannelCommentInfo();
        channelCommentInfo.setPusherId(groupId);
        channelCommentInfo.setScope(ChannelCommentInfo.SCOPE_GROUP);
        channelCommentInfo.setType(ChannelCommentInfo.TYPE_LIVE_VIEWERS);
        channelCommentInfo.setViewers(getCountByGroupId(groupId));
        sendMessage(new Gson().toJson(channelCommentInfo));

        //记录观看开始
        if(groupId != userId) {
            liveViewInfo.setPlayerId(groupId);
            liveViewInfo.setViewerId(userId);
            insertSuccess = liveViewDao.insertOne(liveViewInfo) == 1;
        }
    }


    /** received message
     * @param session socket Session
     * @param message message (json string) -> {@link com.wiatec.blive.dto.ChannelCommentInfo}
     */
    @OnMessage
    public void onMessage(Session session, String message) {
        try {
            logger.info("wss:"+ message);
            ChannelCommentInfo commentInfo = new Gson().fromJson(message, new TypeToken<ChannelCommentInfo>(){}.getType());
            if(commentInfo == null){
                return;
            }
            if(!TextUtil.isEmpty(commentInfo.getComment())){
                if(illegalWords != null && illegalWords.size() >0){
                    for(String key: illegalWords){
                        commentInfo.setComment(commentInfo.getComment().replace(key, "**"));
                    }
                }
            }
            commentInfo.setViewerUsername(username);
            if(commentInfo.getScope() == ChannelCommentInfo.SCOPE_ALL){
                for(Map.Entry<Integer, LiveSocket> entry: clientMap.entrySet()){
                    entry.getValue().sendMessage(new Gson().toJson(commentInfo));
                }
            }else if(commentInfo.getScope() == ChannelCommentInfo.SCOPE_GROUP){
                for(Map.Entry<Integer, LiveSocket> entry: clientMap.entrySet()){
                    LiveSocket liveSocket = entry.getValue();
                    if(liveSocket.getGroupId() == commentInfo.getPusherId()){
                        liveSocket.sendMessage(new Gson().toJson(commentInfo));
                    }
                }
            }

            // log comment
            ChannelInfo channelInfo = liveChannelDao.selectOneByUserId(commentInfo.getPusherId());
            LogLiveCommentInfo logLiveCommentInfo = new LogLiveCommentInfo();
            logLiveCommentInfo.setChannelId(channelInfo.getId());
            logLiveCommentInfo.setGroupId(commentInfo.getPusherId());
            logLiveCommentInfo.setWatchUserId(userId);
            logLiveCommentInfo.setComment(message);
            logLiveCommentDao.insertOne(logLiveCommentInfo);
        }catch (Exception e){
            logger.error("ws error", e);
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        if(groupId == userId){
            for(Map.Entry<Integer, LiveSocket> entry: clientMap.entrySet()) {
                LiveSocket liveSocket = entry.getValue();
                if (liveSocket.getGroupId() == groupId) {
                    ChannelCommentInfo channelCommentInfo = new ChannelCommentInfo();
                    channelCommentInfo.setPusherId(groupId);
                    channelCommentInfo.setScope(ChannelCommentInfo.SCOPE_GROUP);
                    channelCommentInfo.setType(ChannelCommentInfo.TYPE_LIVE_END);
                    liveSocket.sendMessage(new Gson().toJson(channelCommentInfo));
                }
            }
            liveChannelDao.updateUnavailableByUserId(userId);
        }
        clientMap.remove(userId);
        liveChannelDao.updateUnavailableByUserId(userId);
        logger.debug("ws -> client " + userId +" disconnect, current client is: {}", getOnlineCount());

        //记录观看结束
        if(groupId != userId && insertSuccess) {
            liveViewDao.updateOne(liveViewInfo.getId());
        }
    }

    @OnError
    public void onError(Session session, Throwable t) {
        clientMap.remove(userId);
        logger.error("ws -> Exception: ", t);
    }

    /**
     * real send message by session
     * @param message message body
     * @throws IOException IOException
     */
    public void sendMessage(String message){
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.error("system exception", e);
        }
    }

    /**
     * get all client count
     * @return int
     */
    public static synchronized int getOnlineCount() {
        return clientMap.size();
    }

    /**
     * get special group client count
     * @param groupId group id
     * @return int
     */
    public static synchronized int getCountByGroupId(int groupId){
        int count = 0;
        for(Map.Entry<Integer, LiveSocket> entry: clientMap.entrySet()){
            LiveSocket liveSocket = entry.getValue();
            if(liveSocket.getGroupId() == groupId && liveSocket.getGroupId() != liveSocket.getUserId()){
                count ++;
            }
        }
        return count;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


}
