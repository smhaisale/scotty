package scotty.util;

import com.mongodb.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MongoDb {

    private static final String PRIMARY_INDEX_FIELD = "_id";

    private static boolean log = true;

    private String database;
    private String host;
    private Integer port;

    private MongoClient client = null;
    private DB db = null;

    public MongoDb(String host, Integer port, String database) {
        this.database = database;
        this.host = host;
        this.port = port;

        client = this.getMongoClient();
        this.authenticate("", "");
    }

    public MongoClient getMongoClient() {
        MongoClient client = null;
        try {
            client = new MongoClient(host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }

    public MongoClient authenticate(String username, String password) {

        this.db = client.getDB(database);

        //boolean auth = db.authenticate(username, password.toCharArray());
        //return auth ? client : null;

        return client;
    }

    public void insert(String table, String key, Map<String, Object> fields) {

        if (log) {
            SystemUtils.log(this.getClass().getName(), "Trying to insert record " + key + " in table " + table);
        }

        DBCollection collection = db.getCollection(table);

        BasicDBObject object = new BasicDBObject();
        object.put(PRIMARY_INDEX_FIELD, key);
        collection.remove(object);

        for(String field : fields.keySet()) {
            object.put(field, fields.get(field));
        }

        collection.insert(object);
    }

    public Map findOne(String table, String key) {

        if (log) {
            SystemUtils.log(this.getClass().getName(), "Trying to findOne record " + key + " in table " + table);
        }

        DBCollection collection = db.getCollection(table);

        BasicDBObject query = new BasicDBObject();
        BasicDBObject field = new BasicDBObject();
        query.put(PRIMARY_INDEX_FIELD, key);

        DBCursor cursor = collection.find(query,field);
        if (cursor.hasNext()) {
            BasicDBObject obj = (BasicDBObject) cursor.next();
            return obj.toMap();
        }
        return new HashMap();
    }

    public Map findOne(String table, String field, String value) {

        if (log) {
            SystemUtils.log(this.getClass().getName(), "Trying to find record with " + field + ":" + value + " in table " + table);
        }

        DBCollection collection = db.getCollection(table);

        BasicDBObject query = new BasicDBObject();
        BasicDBObject fieldObject = new BasicDBObject();
        query.put(field, value);

        DBCursor cursor = collection.find(query,fieldObject);
        if (cursor.hasNext()) {
            BasicDBObject obj = (BasicDBObject) cursor.next();
            return obj.toMap();
        }
        return new HashMap();
    }

    public List<Map> findAll(String table) {

        if (log) {
            SystemUtils.log(this.getClass().getName(), "Trying to find all records in table " + table);
        }

        DBCollection collection = db.getCollection(table);

        BasicDBObject query = new BasicDBObject();
        BasicDBObject field = new BasicDBObject();

        List<Map> results = new ArrayList<>();
        DBCursor cursor = collection.find(query,field);
        while (cursor.hasNext()) {
            BasicDBObject obj = (BasicDBObject) cursor.next();
            results.add(obj.toMap());
        }
        return results;
    }

    public void remove(String table, String key) {

        if (log) {
            SystemUtils.log(this.getClass().getName(), "Trying to delete record " + key + " in table " + table);
        }

        DBCollection collection = db.getCollection(table);

        BasicDBObject object = new BasicDBObject();
        object.put(PRIMARY_INDEX_FIELD, key);
        collection.remove(object);
    }

    public static String sanitizeInput(String input) {
        if (input == null) {
            return null;
        }
        return input.replaceAll(".", "\\uff0E");
    }

    public static String sanitizeOutput(String output) {
        if (output == null) {
            return null;
        }
        return output.replaceAll("\\uff0E", ".");
    }

}
