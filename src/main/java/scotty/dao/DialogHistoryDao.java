package scotty.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DialogHistoryDao {

    private static Map<String, List<String>> dialogHistory = new HashMap<>();

    public static List<String> get(String userId) {

        List<String> dialogs = dialogHistory.get(userId);

        if (dialogs == null) return new ArrayList<>();

        return dialogHistory.get(userId);
    }

    public static void add(String userId, String dialog) {
        List<String> dialogs = dialogHistory.get(userId);

        if (dialogs == null) {
            dialogs = new ArrayList<>();
        }

        dialogs.add(dialog);
        dialogHistory.put(userId, dialogs);
    }

}
