package scotty.app;

import scotty.server.UndertowServer;
import scotty.servlet.WozRequestServlet;

import javax.servlet.http.HttpServlet;
import java.util.HashMap;
import java.util.Map;

import static scotty.common.Config.CHATBOT_PORT;

public class Mirror {

    public static void main(String[] args) {

        Map<String, Class<? extends HttpServlet>> pathServletMap = new HashMap<>();

        pathServletMap.put("woz", WozRequestServlet.class);

        UndertowServer mirrorServer = new UndertowServer(pathServletMap);
        mirrorServer.start(CHATBOT_PORT);
    }
}
