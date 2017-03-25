package scotty.dao;

import scotty.common.DialogEntry;
import scotty.util.MongoDb;
import scotty.util.SystemUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DialogEntryDao {

    private static final boolean log = true;

    private static final String table = "dialog_history";

    private static final String field = "history";
    private static final String SOURCE_FIELD = "source";
    private static final String TEXT_FIELD = "text";
    private static final String TURN_FIELD = "turn";

    private static MongoDb database = new MongoDb("54.175.153.240", 27017, "scotty");

    private static DialogEntry fromMap(Map map) {

        if (map == null) {
            return null;
        }

        DialogEntry entry = new DialogEntry();

        entry.setSource( (String) map.get(SOURCE_FIELD));
        entry.setText( (String) map.get(TEXT_FIELD));
        entry.setTurn( (Integer) map.get(TURN_FIELD));

        return entry;
    }

    public static Map toMap(DialogEntry entry) {

        if (entry == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<>();

        map.put(SOURCE_FIELD, entry.getSource());
        map.put(TEXT_FIELD, entry.getText());
        map.put(TURN_FIELD, entry.getTurn());

        return map;
    }

    public static List<DialogEntry> getAll(String userId) {

        if (log) {
            SystemUtils.log("DialogEntryDao", "Get dialog history for " + userId);
        }

        List dialogs = (List) database.findOne(table, userId).get(field);

        List<DialogEntry> history = new ArrayList<>();
        if (dialogs == null) return history;

        for(Object dialog : dialogs) {
            history.add(fromMap((Map) dialog));
        }

        return history;
    }

    public static void put(String userId, List<DialogEntry> dialogs) {

        List list = new ArrayList();
        for (DialogEntry dialog : dialogs) {
            list.add(toMap(dialog));
        }

        Map map = new HashMap();
        map.put(field, list);

        database.insert(table, userId, map);
    }

    public static void delete(String userId) {
        database.remove(table, userId);
    }
}
