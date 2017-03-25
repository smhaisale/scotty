package scotty.dao;

import com.google.common.collect.Lists;

import scotty.common.ChatbotReply;
import scotty.util.HttpUtils;
import scotty.util.JsonUtils;
import scotty.util.SystemUtils;

import java.io.*;
import java.util.ArrayList;
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
    private static final String POSSIBLE_RESPONSES_FIELD = "responses";

    private static final boolean log = true;

    public static String mockJsonCall() {
        return "{\"sys_res\":\"Mock reply\", \"dialog_state\":{ \"cuisine\": \"Italian\" } }";
    }

    //TODO: Update once Bing changes structure of chatbot response to multiple answers
    public static ChatbotReply getReply(String userId, String query, String prevState) {

        if (log) {
            SystemUtils.log("ChatbotServiceDao", "Sending message to chatbot with userId " + userId + " and query " + query);
        }

        HashMap<String, String> params = new HashMap<>();

        params.put(USER_INPUT_FIELD, query);
        params.put(DIALOG_STATE_FIELD, prevState);

        String jsonReply = HttpUtils.doPostWithParams(BOT_URL, params);

        Map<String, Object> fields = JsonUtils.processJSON(jsonReply, null);

        List<String> responses = new ArrayList<>();
        responses.add((String) fields.get(SYSTEM_RESPONSE_FIELD));
        responses.add("This is a sample response 2");
        responses.add("This is a sample response 3");

        ChatbotReply reply = new ChatbotReply();
        reply.setQuery(query);
        reply.setState((String) fields.get(DIALOG_STATE_FIELD));
        reply.setResponses(responses);

        return reply;
    }
}
