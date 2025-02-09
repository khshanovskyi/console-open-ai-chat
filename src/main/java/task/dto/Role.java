package task.dto;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Contains constants for roles (system, user, assistant, etc)
 */
public enum Role {
    // TODO: Define enum constants with their string representations:
    // - SYSTEM should represent "system"
    // - USER should represent "user"
    // - AI should represent "assistant"
    ;

    // TODO: Add private final field to store the string value
    // private final String value;

    // TODO: Create constructor that accepts string value parameter
    // Role(String value) {
    //     this.value = value;
    // }

    // TODO: Create getter method for the string value
    // - Method should be public and return String
    // - Add @JsonValue annotation for proper JSON serialization
    // - Method should return the stored string value
}
