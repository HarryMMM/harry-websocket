package cn.harryai.harrywebsocket.controller;

import cn.harryai.harrywebsocket.model.User;
import cn.harryai.harrywebsocket.socket.server.UserSocketServer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.Set;

/**
 * @author Harry
 * @since 2019/6/30 12:27
 */
public class SocketController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Set<UserSocketServer> online = UserSocketServer.getOnline();

        online.forEach(onLineUser -> {
            try {
                User user = new User(onLineUser.getUser(), onLineUser.getTreeId());
                user.setTreeId(onLineUser.getTreeId());
                onLineUser.getSession().getBasicRemote().sendObject(user);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (EncodeException e) {
                e.printStackTrace();
            }
        });

    }
}
