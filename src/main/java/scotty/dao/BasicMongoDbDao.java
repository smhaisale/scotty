package scotty.dao;

import scotty.util.MongoDb;
import scotty.util.SystemUtils;

import java.util.Map;

import static scotty.common.Config.DATABASE;

public abstract class BasicMongoDbDao {

    private static final boolean log = true;

    private static final String table = "table";

    private static MongoDb database = DATABASE;

    public static Map<String, Object> get(String key) {

        if (log) {
            SystemUtils.log("BasicMongoDbDao", "Get value for " + key);
        }

        Map<String, Object> map = database.findOne(table, key);

        return map;
    }

    public static void put(String key, String field, String value) {

        if (log) {
            SystemUtils.log("BasicMongoDbDao", "Put value " + value + " for " + key);
        }

        Map<String, Object> map = get(key);
        map.put(field, value);

        database.insert(table, key, map);
    }

    public static void delete(String key) {

        if (log) {
            SystemUtils.log("BasicMongoDbDao", "Delete for " + key);
        }

        database.remove(table, key);
    }
}
