package org.dochub.idea.arch.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.openapi.project.Project;

import java.util.HashMap;
import java.util.Map;

import static org.dochub.idea.arch.tools.Consts.*;

public class JSGateway {
    private Map<String, Map> message = new HashMap<>();
    ObjectMapper mapper = new ObjectMapper();

    public JSGateway(Project project) {
        project.getMessageBus().connect().subscribe(LineMarkerProvider.ON_NAVIGATE_MESSAGE, new LineMarkerProvider.NavigateMessage() {
            @Override
            public void go(String entity, String id) {
                appendMessage(ACTION_NAVIGATE_COMPONENT, id, null);
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

    public String pullJSONMessage() throws JsonProcessingException {
        String result = message.size() > 0 ? mapper.writeValueAsString(message) : null;
        message.clear();
        return result;
    }
}
