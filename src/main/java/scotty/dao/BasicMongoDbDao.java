package scotty.dao;

import scotty.util.MongoDbUtils;
import scotty.util.SystemUtils;

import java.util.Map;

public abstract class BasicMongoDbDao {

    private static final boolean log = true;

    private static final String table = "table";

    private static MongoDbUtils database = new MongoDbUtils("localhost", 27017, "scotty");

    public static Map<String, Object> get(String key) {

        if (log) {
            SystemUtils.log(BasicMongoDbDao.class, "Get value for " + key);
        }

        Map<String, Object> map = database.findOne(table, key);

        return map;
    }

    public static void put(String key, String field, String value) {

        if (log) {
            SystemUtils.log(BasicMongoDbDao.class, "Put value " + value + " for " + key);
        }

        Map<String, Object> map = get(key);
        map.put(field, value);

        database.insert(table, key, map);
    }

    public static void delete(String key) {

        if (log) {
            SystemUtils.log(BasicMongoDbDao.class, "Delete for " + key);
        }

        database.remove(table, key);
    }
}
