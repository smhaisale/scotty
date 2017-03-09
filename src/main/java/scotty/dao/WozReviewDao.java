package scotty.dao;

import com.google.common.collect.ImmutableMap;
import scotty.common.WozReview;
import scotty.util.MongoDbUtils;
import scotty.util.SystemUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WozReviewDao {

    private static final boolean log = true;

    private static final String TABLE = "woz_review";

    private static final String USER_ID_FIELD = "user_id";
    private static final String QUERY_FIELD = "query";
    private static final String RESPONSES_FIELD = "responses";
    private static final String SELECTED_RESPONSE_FIELD = "selected_response";
    private static final String IS_REVIEWED_FIELD = "is_reviewed";

    private static MongoDbUtils database = new MongoDbUtils("localhost", 27017, "scotty");

    private static WozReview fromMap(Map map) {
        WozReview review = new WozReview();

        review.setUserId((String) map.get(USER_ID_FIELD));
        review.setQuery((String) map.get(QUERY_FIELD));
        review.setResponses((Map) map.get(RESPONSES_FIELD));
        review.setSelectedResponse((String) map.get(SELECTED_RESPONSE_FIELD));
        review.setReviewed((Boolean) map.get(IS_REVIEWED_FIELD));

        return review;
    }

    private static Map toMap(WozReview review) {
        Map<String, Object> map = new HashMap<>();

        map.put(USER_ID_FIELD, review.getUserId());
        map.put(QUERY_FIELD, review.getQuery());
        map.put(RESPONSES_FIELD, review.getResponses());
        map.put(SELECTED_RESPONSE_FIELD, review.getSelectedResponse());
        map.put(IS_REVIEWED_FIELD, review.getReviewed());

        return map;
    }

    public static WozReview get(String userId) {

        if (log) {
            SystemUtils.log(WozReviewDao.class, "Get woz review for " + userId);
        }

        Map<String, String> map = database.findOne(TABLE, userId);

        return fromMap(map);
    }

    public static List<WozReview> getAll() {

        if (log) {
            SystemUtils.log(WozReviewDao.class, "Get all woz reviews");
        }

        List<Map> maps = database.findAll(TABLE);
        List<WozReview> results = new ArrayList<>();

        for(Map map : maps) {
            results.add(fromMap(map));
        }
        return results;
    }

    public static void put(WozReview review) {

        if (log) {
            SystemUtils.log(WozReviewDao.class, "Put woz review " + review);
        }

        database.insert(TABLE, review.getUserId(), toMap(review));
    }

    public static void delete(String userId) {

        if (log) {
            SystemUtils.log(WozReviewDao.class, "Delete woz review for " + userId);
        }

        database.remove(TABLE, userId);
    }

    public static void main(String[] args) {

        WozReview review = new WozReview();

        String response1 = "This is response option 1";
        String response2 = "This is response option 2";
        String response3 = "This is response option 3";

        Map<String, Integer> responses = ImmutableMap.of(response3, 3, response2, 2, response1, 1);

        review.setUserId("user0123");
        review.setQuery("Is this the query?");
        review.setSelectedResponse("This is the selected response");
        review.setResponses(responses);
        review.setReviewed(true);

        System.out.println(review);

        WozReviewDao.put(review);

        System.out.println(WozReviewDao.getAll());

        System.out.println(WozReviewDao.get(review.getUserId()));
    }
}
