package task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import task.dto.Role;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldHaveCorrectNumberOfEnumValues() {
        assertEquals(3, Role.values().length);
    }

    @Test
    void shouldContainExpectedEnumValues() {
        Role[] expectedRoles = {Role.SYSTEM, Role.USER, Role.AI};
        assertArrayEquals(expectedRoles, Role.values());
    }

    @ParameterizedTest
    @MethodSource("provideRolesAndExpectedValues")
    void getValue_ShouldReturnCorrectValue(Role role, String expectedValue) {
        assertEquals(expectedValue, role.getValue());
    }

    private static Stream<Arguments> provideRolesAndExpectedValues() {
        return Stream.of(
                Arguments.of(Role.SYSTEM, "system"),
                Arguments.of(Role.USER, "user"),
                Arguments.of(Role.AI, "assistant")
        );
    }

    @ParameterizedTest
    @MethodSource("provideRolesAndExpectedJson")
    void shouldSerializeToJsonCorrectly(Role role, String expectedJson) throws JsonProcessingException {
        String actualJson = objectMapper.writeValueAsString(role);
        assertEquals(expectedJson, actualJson);
    }

    private static Stream<Arguments> provideRolesAndExpectedJson() {
        return Stream.of(
                Arguments.of(Role.SYSTEM, "\"system\""),
                Arguments.of(Role.USER, "\"user\""),
                Arguments.of(Role.AI, "\"assistant\"")
        );
    }
}