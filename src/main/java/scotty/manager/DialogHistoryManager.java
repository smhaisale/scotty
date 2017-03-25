package scotty.manager;

import scotty.common.DialogEntry;
import scotty.dao.DialogEntryDao;
import scotty.util.SystemUtils;

import java.util.List;

public class DialogHistoryManager {

    private static final Boolean log = true;

    //TODO: Configuration
    private static final Integer DIALOG_HISTORY_THRESHOLD = 10;

    public static List<DialogEntry> getHistory(String userId, Integer turnCount) {

        if (log) {
            SystemUtils.log("DialogHistoryManager", "Get dialog history for " + userId);
        }

        List<DialogEntry> history = DialogEntryDao.getAll(userId);

        if (history.size() < turnCount) {
            return history;
        } else {
            return history.subList(history.size() - turnCount, history.size());
        }
    }

    public static void addEntry(String userId, String source, String text) {

        List<DialogEntry> dialogs = getHistory(userId, DIALOG_HISTORY_THRESHOLD - 1);

        DialogEntry entry = new DialogEntry(source, text);

        if (dialogs.size() == 0) {
            entry.setTurn(1);
        } else {
            entry.setTurn(dialogs.get(dialogs.size() - 1).getTurn() + 1);
        }
        dialogs.add(entry);

        DialogEntryDao.put(userId, dialogs);
    }

    public static void delete(String userId) {
        DialogEntryDao.delete(userId);
    }

    public static void main(String[] args) {
        delete("user1");
        addEntry("user1", "source", "ABC");
        System.out.println(getHistory("user1", 100));
        addEntry("user1", "source", "DEF");
        System.out.println(getHistory("user1", 100));
        addEntry("user1", "user", "XYZ");
        System.out.println(getHistory("user1", 100));
    }
}
