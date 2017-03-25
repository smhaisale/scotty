package scotty.app;

import scotty.servlet.WozRequestServlet;

import javax.servlet.http.HttpServlet;
import java.util.HashMap;
import java.util.Map;

public class Woz {

    public static final Integer WOZ_PORT = 3452;

    public static void main(String[] args) {

        Map<String, Class<? extends HttpServlet>> pathServletMap = new HashMap<>();

        pathServletMap.put("woz", WozRequestServlet.class);

        UndertowServer wozServer = new UndertowServer(pathServletMap);
        wozServer.start(WOZ_PORT);
    }
}
