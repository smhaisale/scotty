package scotty.servlet;

import org.json.simple.JSONArray;
import scotty.common.DialogEntry;
import scotty.common.DialogReview;
import scotty.dao.DialogEntryDao;
import scotty.dao.WozReviewDao;
import scotty.manager.DialogHistoryManager;
import scotty.manager.UserInformationManager;
import scotty.manager.DialogReviewManager;
import scotty.util.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static scotty.util.FacebookUtils.SEND_CLIENT;

public class WozRequestServlet extends HttpServlet {

    private static Map toMap(DialogReview review) {
        Map map = WozReviewDao.toMap(review);

        JSONArray array = new JSONArray();

        for (DialogEntry entry : review.getDialogInformation().getDialogHistory()) {
            array.add(DialogEntryDao.toMap(entry));
        }
        map.put("history", array);

        return map;
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST");

        String userId = request.getParameter("userId");
        String query = request.getParameter("query");
        String reply = request.getParameter("response");

        if (userId == null || userId.equals("")) {

            List<DialogReview> list = DialogReviewManager.getAll();

            JSONArray array = new JSONArray();

            for (DialogReview review : list) {
                array.add(toMap(review));
            }

            array.writeJSONString(response.getWriter());

            HttpUtils.writeJSON(response, null);

        } else {

            DialogReview review = DialogReviewManager.get(userId);

            if (review != null && !review.getReviewed()) {
                DialogReviewManager.put(userId, query, reply);

                String facebookId = UserInformationManager.getFacebookId(userId);
                try {
                    SEND_CLIENT.sendTextMessage(facebookId, reply);
                    DialogHistoryManager.addEntry(userId, "system", reply);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST");

        String userId = request.getParameter("userId");
        String query = request.getParameter("query");
        String reply = request.getParameter("response");

        DialogReviewManager.put(userId, query, reply);

        //TODO: Somehow wake up waiting user thread.
    }
}
