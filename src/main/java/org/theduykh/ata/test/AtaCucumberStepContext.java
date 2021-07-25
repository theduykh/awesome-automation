package org.theduykh.ata.test;

import java.util.HashMap;
import java.util.Map;

public class AtaCucumberStepContext {
    private final Map<String, Object> contextList = new HashMap<>();

    public Object getContext(String key) {
        return contextList.get(key);
    }

    public void setContext(String key, Object data) {
        contextList.put(key, data);
    }
}
