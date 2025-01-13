package task.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Model {
    GPT_35_TURBO("gpt-3.5-turbo"),
    GPT_4o_MINI("gpt-4o-mini"),
    GPT_4o("gpt-4o");

    private final String value;

    Model(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
