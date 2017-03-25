package scotty.app;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.api.ServletInfo;

import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;

import javax.servlet.http.HttpServlet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.undertow.servlet.Servlets.defaultContainer;
import static io.undertow.servlet.Servlets.deployment;
import static io.undertow.servlet.Servlets.servlet;

public class UndertowServer {

    public static final String APP_PATH = "/";

    private DeploymentInfo deploymentInfo;

    public UndertowServer(Map<String, Class<? extends HttpServlet>> servlets) {

        List<ServletInfo> servletInfo = new ArrayList<>();

        for (String path : servlets.keySet()) {
            servletInfo.add(
                    servlet(path, servlets.get(path)).addMapping("/" + path)
            );
        }

        this.deploymentInfo = deployment()
                .setClassLoader(UndertowServer.class.getClassLoader())
                .setContextPath(APP_PATH)
                .setDeploymentName("")
                .addServlets(servletInfo);
    }

    public void start(Integer port) {

        try {
            DeploymentManager manager = defaultContainer().addDeployment(this.deploymentInfo);
            manager.deploy();

            HttpHandler servletHandler = manager.start();
            PathHandler path = Handlers.path(Handlers.redirect(APP_PATH))
                    .addPrefixPath(APP_PATH, servletHandler);

            Undertow server = Undertow.builder()
                    .addHttpListener(port, "0.0.0.0")
                    .setHandler(path)
                    .build();

            server.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
