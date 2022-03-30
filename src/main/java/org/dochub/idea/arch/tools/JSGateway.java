package org.dochub.idea.arch.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.openapi.project.Project;

import java.util.HashMap;
import java.util.Map;

public class JSGateway {
    private Map<String, Map> message = new HashMap<>();
    ObjectMapper mapper = new ObjectMapper();

    public JSGateway(Project project) {
        project.getMessageBus().connect().subscribe(LineMarkerProvider.ON_NAVIGATE_MESSAGE, new LineMarkerProvider.NavigateMessage() {
            @Override
            public void go(String entity, String id) {
                appendMessage("navigate/component", id, null);
            }
        });
    }

    public void appendMessage(String action, String id, Object data) {
        Map<String, Object> aData = message.get(action);
        if (aData == null) {
            aData = new HashMap<>();
            message.put(action, aData);
        }
        aData.put(id, data);
    }

    public boolean isFound() {
        return message.size() > 0;
    }

    public String pullJSONMessage() throws JsonProcessingException {
        String result = message.size() > 0 ? mapper.writeValueAsString(message) : null;
        message.clear();
        return result;
    }
}
