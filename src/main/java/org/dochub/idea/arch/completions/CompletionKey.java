package org.dochub.idea.arch.completions;

public class CompletionKey {
    private final String key;

    private final ValueType valueType;

    public CompletionKey(String key, ValueType valueType) {
        this.key = key;
        this.valueType = valueType;
    }

    public CompletionKey(String key) {
        this.key = key;
        this.valueType = ValueType.TEXT;
    }

    public String getKey() {
        return key;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public enum ValueType {
        TEXT, MAP, LIST
    }
}
