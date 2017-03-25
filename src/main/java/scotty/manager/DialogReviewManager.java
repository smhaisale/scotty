package scotty.manager;

import scotty.common.DialogInformation;
import scotty.common.DialogReview;
import scotty.dao.WozReviewDao;

import java.util.List;

public class DialogReviewManager {

    //TODO: Move to config
    private static final Integer DEFAULT_TIMEOUT = 15000;
    private static final Integer DEFAULT_TURN_COUNT = 10;

    public static DialogReview review(String userId, String query, List<String> responses, Integer timeout) {

        if (timeout == null) {
            timeout = DEFAULT_TIMEOUT;
        }

        //TODO: Move this to DAO
        DialogReview review = new DialogReview();

        review.setUserId(userId);
        review.setQuery(query);
        review.setResponses(responses);
        review.setSelectedResponse(responses.get(0));
        review.setReviewed(false);

        WozReviewDao.put(review);

        try {
            Thread.sleep(timeout);
        } catch (Exception e) {
            e.printStackTrace();
        }

        review = WozReviewDao.get(userId);
        //WozReviewDao.delete(userId);

        return review;
    }

    public static DialogReview get(String userId) {
        return WozReviewDao.get(userId);
    }

    public static List<DialogReview> getAll() {
        List<DialogReview> reviews = WozReviewDao.getAll();
        for(DialogReview review : reviews) {
            review.setDialogInformation(
                    new DialogInformation(DialogHistoryManager.getHistory(review.getUserId(), DEFAULT_TURN_COUNT)));
        }
        return reviews;
    }

    public static void put(String userId, String query, String response) {

        DialogReview review = WozReviewDao.get(userId);

        review.setSelectedResponse(response);
        review.setReviewed(true);

        WozReviewDao.put(review);
    }
}
