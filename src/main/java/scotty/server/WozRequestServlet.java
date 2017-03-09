package scotty.server;

import org.json.simple.JSONArray;
import scotty.dao.WozReviewDao;
import scotty.manager.WozReviewManager;
import scotty.util.HttpUtils;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WozRequestServlet extends HttpServlet {

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST");

        List list = WozReviewManager.getAll();

        //JSONObject json = new JSONObject(list);

        //HttpUtils.writeJSON(response, json);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userId = request.getParameter("userId");
        String query = request.getParameter("query");
        String answer = request.getParameter("answer");

        //WozReviewDao.put(userId, query, answer);

        //TODO: Somehow wake up waiting user thread.
    }
}
