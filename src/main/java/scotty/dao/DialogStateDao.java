package scotty.dao;

import scotty.util.MongoDbUtils;
import scotty.util.SystemUtils;

import java.util.HashMap;
import java.util.Map;

public class DialogStateDao {

    private static final boolean log = true;

    private static final String table = "bot_context";
    private static final String field = "dialog_state";

    private static MongoDbUtils contextDB = new MongoDbUtils("localhost", 27017, "scotty");

    public static String get(String userId) {

        if (log) {
            SystemUtils.log(DialogStateDao.class, "Get dialog state for " + userId);
        }

        Map<String, String> map = contextDB.findOne(table, userId);

        String state = map.get(field);

        return (state == null) ? "" : state;
    }

    public static void put(String userId, String state) {

        if (log) {
            SystemUtils.log(DialogStateDao.class, "Put dialog state " + state + " for " + userId);
        }

        Map<String, String> map = new HashMap<>();
        map.put(field, state);

        contextDB.insert(table, userId, map);
    }

    public static void main(String[] args) {
        put("user1", "state");
        System.out.println(get("user1"));
    }
}
