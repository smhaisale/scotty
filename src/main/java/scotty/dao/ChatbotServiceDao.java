package scotty.dao;

import com.google.common.collect.Lists;

import scotty.util.HttpUtils;
import scotty.util.JsonUtils;
import scotty.util.SystemUtils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static scotty.common.Config.BOT_URL;

/**
 * Communicates with neural network chatbot via a service call.
 */
public class ChatbotServiceDao {

    private static final String USER_INPUT_FIELD = "user_utt";
    private static final String SYSTEM_RESPONSE_FIELD = "sys_res";
    private static final String DIALOG_STATE_FIELD = "dialog_state";

    private static final boolean log = true;

    public static String mockJsonCall() {
        return "{\"sys_res\":\"Mock reply\", \"dialog_state\":{ \"cuisine\": \"Italian\" } }";
    }

    public static String getReply(String userId, String text, List<String> dialogHistory) throws IOException {

        if (log) {
            SystemUtils.log(ChatbotServiceDao.class, "Sending message to chatbot with userId " + userId + " and text " + text);
        }

        HashMap<String, String> params = new HashMap<>();

        params.put(USER_INPUT_FIELD, text);
        params.put(DIALOG_STATE_FIELD, DialogStateDao.get(userId));

        String jsonReply = HttpUtils.doPostWithParams(BOT_URL, params);

        Map<String, Object> fields = JsonUtils.processJSON(jsonReply, Lists.newArrayList(SYSTEM_RESPONSE_FIELD, DIALOG_STATE_FIELD));

        DialogStateDao.put(userId, fields.get(DIALOG_STATE_FIELD).toString());

        return fields.get(SYSTEM_RESPONSE_FIELD).toString();
    }
}
