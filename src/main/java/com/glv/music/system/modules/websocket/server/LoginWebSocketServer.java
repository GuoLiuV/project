package com.glv.music.system.modules.websocket.server;

import com.glv.music.system.utils.ObjectUtils;
import com.glv.music.system.utils.StringUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.types.CopyOnWriteArrayMap;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * 用于扫码登录的websocket推送服务
 *
 * @author ZHOUXIANG
 */
@Slf4j
@Data
@Component
@ServerEndpoint("/websocket/login/{sid}")
public class LoginWebSocketServer {

    private static CopyOnWriteArrayMap<String, LoginWebSocketServer>
            webSocketServers = new CopyOnWriteArrayMap<>();

    private Session session;

    private String sid;

    /**
     * 客户端连接回调
     *
     * @param session 会话
     * @param sid     会话标识，唯一标志客户端
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        this.sid = sid;
        webSocketServers.put(sid, this);
        log.debug("websocket连接成功:{}", sid);
    }

    /**
     * 客户端关闭回调
     */
    @OnClose
    public void onClose() {
        log.debug("websocket连接关闭:{}", sid);
        webSocketServers.remove(sid);
    }

    /**
     * 收到客户端消息回调
     *
     * @param message 消息
     * @param session 会话
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.debug("收到客户端：{}, 消息：{}", this.sid, message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("weksocket异常：{}", this.sid, error);
    }

    /**
     * 服务器主动推送消息
     *
     * @param message 消息
     */
    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
            log.debug("websocket消息发送成功: {}-{}", this.sid, message);
        } catch (IOException e) {
            log.error("websocket消息发送失败: {}", sid, e);
        }
    }

    public static void sendMessage(String message, String sid) {
        if (StringUtils.isBlank(sid)) {
            webSocketServers.forEach((key, value) -> value.sendMessage(message));
        } else {
            LoginWebSocketServer server = webSocketServers.get(sid);
            if (ObjectUtils.notNull(server)) {
                server.sendMessage(message);
            }
        }
    }
}
