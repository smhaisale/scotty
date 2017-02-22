package scotty.app;

import scotty.servlet.MirrorServlet;
import scotty.servlet.UserRequestServlet;
import scotty.servlet.WebhookServlet;
import scotty.servlet.WozRequestServlet;
import scotty.server.UndertowServer;

import javax.servlet.http.HttpServlet;
import java.util.HashMap;
import java.util.Map;

import static scotty.common.Config.CHATBOT_PORT;

public class Scotty {

    public static final Integer USER_PORT = 80;
    public static final Integer WOZ_PORT = 3452;

    public static void main(String[] args) throws Exception {

        Map <String, Class <? extends HttpServlet>> pathServletMap = new HashMap<>();
        pathServletMap.put("user", UserRequestServlet.class);
        pathServletMap.put("webhook", WebhookServlet.class);

        UndertowServer userServer = new UndertowServer(pathServletMap);
        userServer.start(USER_PORT);
    }
}
