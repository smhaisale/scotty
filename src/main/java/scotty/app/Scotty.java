package scotty.app;

import scotty.server.UserRequestServlet;
import scotty.server.FacebookMessengerServlet;
import scotty.server.UndertowServer;
import scotty.server.WozRequestServlet;

import javax.servlet.http.HttpServlet;
import java.util.HashMap;
import java.util.Map;

public class Scotty {

    public static final Integer USER_PORT = 80;
    public static final Integer WOZ_PORT = 3452;

    public static void main(String[] args) throws Exception {

        Map <String, Class <? extends HttpServlet>> pathServletMap = new HashMap<>();
        pathServletMap.put("user", UserRequestServlet.class);
        pathServletMap.put("webhook", FacebookMessengerServlet.class);
        pathServletMap.put("woz", WozRequestServlet.class);

        UndertowServer userServer = new UndertowServer(pathServletMap);
        userServer.start(USER_PORT);
    }
}
