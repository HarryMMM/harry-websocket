package cn.harryai.harrywebsocket.socket.server;

import cn.harryai.harrywebsocket.socket.encoder.CommonEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Harry
 * @since 2019/6/30 10:38
 */
@ServerEndpoint(value = "/socket/user", encoders = {CommonEncoder.class})
public class UserSocketServer {
    private static final Set<UserSocketServer> onLine = new CopyOnWriteArraySet<>();
    private static final AtomicInteger onLineCount = new AtomicInteger();
    private static final String PING = "ping";
    private static final String PONG = "pong";
    private static final long SESSION_TIMEOUT = 0;
    private static final String TREE_ID_KEY = "treeId";
    private static final String USER_KEY = "user";
    private static final Logger LOGGER = LoggerFactory.getLogger(UserSocketServer.class);
    private String treeId;
    private Session session;
    private String user;

    public String getUser() {
        return user;
    }

    public static Set<UserSocketServer> getOnline() {
        return onLine;
    }

    public String getTreeId() {
        return treeId;
    }

    public Session getSession() {
        return session;
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        session.setMaxIdleTimeout(SESSION_TIMEOUT);
        onLine.add(this);
        int online = onLineCount.incrementAndGet();
        Map<String, List<String>> requestParameterMap = session.getRequestParameterMap();
        if (Objects.nonNull(requestParameterMap) && !requestParameterMap.isEmpty()) {
            List<String> treeIds = requestParameterMap.get(TREE_ID_KEY);
            List<String> users = requestParameterMap.get(USER_KEY);
            if (Objects.nonNull(treeIds) && !treeIds.isEmpty()) {
                treeId = treeIds.get(0);
            }
            if (Objects.nonNull(users) && !users.isEmpty()) {
                user = users.get(0);

            }
        }
        LOGGER.info("User[{}]-treeId[{}]加入连接,当前在线人数为{}", user, treeId, online);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        LOGGER.error("User[{}]-treeId[{}] has an exception:{},", user, treeId, error);
//        throw ServiceException(error);

    }

    /**
     * 此时可以删除树
     */
    @OnClose
    public void onClose(Session session) {
        int online = onLineCount.decrementAndGet();
        // 删除树
//        deleteShadowTree(treeId);
        // 删除断连的socketServer
        onLine.remove(this);
        LOGGER.info("User[{}]-treeId[{}]断开连接,当前在线人数为:{}", user, treeId, online);

    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        LOGGER.info("Receive heartbeat '{}' and send message '{}'.", message, PONG);
        session.getBasicRemote().sendText(PONG);
    }


}
