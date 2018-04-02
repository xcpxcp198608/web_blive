package com.wiatec.blive.ws;

import com.wiatec.blive.common.utils.ApplicationContextHelper;
import com.wiatec.blive.orm.dao.AuthRegisterUserDao;
import com.wiatec.blive.orm.dao.ChannelDao;
import com.wiatec.blive.orm.dao.LogLiveCommentDao;
import com.wiatec.blive.orm.dao.UserDao;
import com.wiatec.blive.orm.pojo.AuthRegisterUserInfo;
import com.wiatec.blive.orm.pojo.ChannelInfo;
import com.wiatec.blive.orm.pojo.LogLiveCommentInfo;
import com.wiatec.blive.orm.pojo.UserInfo;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author patrick
 */
@Component
@ServerEndpoint("/live/{groupId}/{userId}")
public class LiveSocket {

    private final Logger logger = LoggerFactory.getLogger(LiveSocket.class);

    private static final int MSG_TYPE_FULL = 0;
    private static final int MSG_TYPE_GROUP = 1;

    protected static SqlSession sqlSession;

    static {
        sqlSession = (SqlSession) ApplicationContextHelper.getApplicationContext().getBean("sqlSessionTemplate");
    }

    private AuthRegisterUserDao authRegisterUserDao;
    private ChannelDao channelDao;
    private LogLiveCommentDao logLiveCommentDao;
    public static Map<Integer, LiveSocket> clientMap = new ConcurrentHashMap<>();
    private Session session;
    private int groupId;
    private int userId;
    private String username = "guest";

    @OnOpen
    public void onOpen(Session session, @PathParam("groupId")int groupId, @PathParam("userId")int userId) {
        this.session = session;
        this.userId = userId;
        this.groupId = groupId;
        authRegisterUserDao = sqlSession.getMapper(AuthRegisterUserDao.class);
        channelDao = sqlSession.getMapper(ChannelDao.class);
        logLiveCommentDao = sqlSession.getMapper(LogLiveCommentDao.class);
        AuthRegisterUserInfo userInfo = authRegisterUserDao.selectOneById(userId);
        if(userInfo != null){
            username = userInfo.getUsername();
        }
        clientMap.put(userId, this);
        logger.debug("ws -> new client connected, group id: " + groupId +
                ", user id: " + userId + ", total client is: {}", getOnlineCount());
        logger.debug("ws -> new client connected, group id: " + groupId +
                ", user id: " + userId + ", this group client is: {}", getCountByGroupId(groupId));
        sendMessage("blive group count:" + getCountByGroupId(groupId));
    }

    /**
     * 3 parts of message body  0/1/i'm patrick/group count
     * 1: type: 0->全发所有客户端全部收到， 1->发送给属于指定group id的客户端
     * 2: group id:
     * 3: comment:
     * 4: the current view count in group
     * @param session Session
     * @param message message
     * @throws IOException IOException
     */
    @OnMessage
    public void onMessage(Session session, String message) {
        String[] msg = message.split("/");
        int type = Integer.parseInt(msg[0]);
        int group = Integer.parseInt(msg[1]);
        String comment = username + ": " + msg[2];
        logger.debug("ws -> type: {}", type);
        logger.debug("ws -> group: {}", group);
        logger.debug("ws -> comment: {}", comment);
        if(type == MSG_TYPE_FULL){
            for(Map.Entry<Integer, LiveSocket> entry: clientMap.entrySet()){
                entry.getValue().sendMessage(comment);
            }
        }else if(type == MSG_TYPE_GROUP){
            for(Map.Entry<Integer, LiveSocket> entry: clientMap.entrySet()){
                LiveSocket liveSocket = entry.getValue();
                if(liveSocket.getGroupId() == group){
                    liveSocket.sendMessage(comment);
                }
            }
        }
        ChannelInfo channelInfo = channelDao.selectOneByUserId(group);
        LogLiveCommentInfo logLiveCommentInfo = new LogLiveCommentInfo();
        logLiveCommentInfo.setChannelId(channelInfo.getId());
        logLiveCommentInfo.setGroupId(group);
        logLiveCommentInfo.setWatchUserId(userId);
        logLiveCommentInfo.setComment(msg[2]);
        logLiveCommentDao.insertOne(logLiveCommentInfo);
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        clientMap.remove(userId);
        channelDao.updateUnavailableByUserId(userId);
        logger.debug("ws -> client " + userId +" disconnect, current client is: {}", getOnlineCount());
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
