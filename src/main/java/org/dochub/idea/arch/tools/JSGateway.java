package org.dochub.idea.arch.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.openapi.project.Project;
import org.dochub.idea.arch.markline.LineMarkerNavigator;

import java.util.HashMap;
import java.util.Map;

public class JSGateway {
    private Map<String, Map> message = new HashMap<>();
    ObjectMapper mapper = new ObjectMapper();

    public JSGateway(Project project) {
        project.getMessageBus().connect().subscribe(LineMarkerNavigator.ON_NAVIGATE_MESSAGE, new LineMarkerNavigator.NavigateMessage() {
            @Override
            public void go(String entity, String id) {
                appendMessage("navigate/" + entity, id, null);
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
