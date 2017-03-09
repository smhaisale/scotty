package scotty.manager;

import scotty.common.WozReview;
import scotty.dao.WozReviewDao;

import java.util.List;

public class WozReviewManager {

    private static final Integer TIMEOUT = 5000;

    public static String review(String userId, String query, String response) {

        //WozReviewDao.put(userId, query, response);

        try {
            Thread.sleep(TIMEOUT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String reply = WozReviewDao.get(userId).getSelectedResponse();
        WozReviewDao.delete(userId);

        return reply;
    }

    public static List<WozReview> getAll() {
        return WozReviewDao.getAll();
    }

    public static void put(String userId, String query, String response) {
        //WozReviewDao.put(userId, query, response);
    }
}
