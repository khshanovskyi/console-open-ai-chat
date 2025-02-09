package task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import task.dto.Message;
import task.dto.Model;
import task.dto.Role;
import task.utils.Constant;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
class OpenAIClientTest {

    private OpenAIClient openAIClient;
    private ObjectMapper objectMapper;

    @Mock
    private HttpClient mockHttpClient;

    @Mock
    private HttpResponse<Stream<String>> mockResponse;

    private final String API_KEY = "test-api-key";
    private final Model TEST_MODEL = Model.GPT_35_TURBO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        openAIClient = new OpenAIClient(TEST_MODEL, API_KEY, true, mockHttpClient);
    }

    @Nested
    @DisplayName("Constructor Tests")
    @Order(1)
    class ConstructorTests {

        @Test
        @DisplayName("Should create client with valid parameters")
        void shouldCreateClientWithValidParameters() {
            OpenAIClient client = new OpenAIClient(TEST_MODEL, API_KEY);
            assertNotNull(client);
        }

        @Test
        @DisplayName("Should throw exception when API key is null")
        void shouldThrowExceptionWhenApiKeyIsNull() {
            assertThrows(IllegalArgumentException.class,
                    () -> new OpenAIClient(TEST_MODEL, null));
        }

        @Test
        @DisplayName("Should throw exception when API key is empty")
        void shouldThrowExceptionWhenApiKeyIsEmpty() {
            assertThrows(IllegalArgumentException.class,
                    () -> new OpenAIClient(TEST_MODEL, ""));
        }
    }

    @Nested
    @DisplayName("Message History Tests")
    @Order(10)
    class MessageHistoryTests {

        @Test
        @DisplayName("Should add message history to request")
        void shouldAddMessageHistoryToRequest() {
            List<Message> messages = new ArrayList<>();
            messages.add(new Message(Role.USER, "Hello"));
            messages.add(new Message(Role.AI, "Hi there!"));

            ObjectNode request = objectMapper.createObjectNode();
            openAIClient.addHistory(messages, request);

            assertTrue(request.has("messages"));
            assertEquals(2, request.get("messages").size());
        }

        @Test
        @DisplayName("Should handle empty message history")
        void shouldHandleEmptyMessageHistory() {
            List<Message> messages = new ArrayList<>();
            ObjectNode request = objectMapper.createObjectNode();

            openAIClient.addHistory(messages, request);

            assertTrue(request.has("messages"));
            assertEquals(0, request.get("messages").size());
        }
    }

    @Nested
    @DisplayName("Request Generation Tests")
    @Order(20)
    class RequestGenerationTests {

        @Test
        @DisplayName("Should generate valid HTTP request")
        void shouldGenerateValidHttpRequest() throws Exception {
            ObjectNode request = objectMapper.createObjectNode();
            request.put("model", TEST_MODEL.getValue());
            request.put("stream", true);

            HttpRequest httpRequest = openAIClient.generateRequest(request);

            assertNotNull(httpRequest, "Generated HTTP request cannot be null");
            assertEquals(Constant.OPEN_AI_API_URI, httpRequest.uri(), String.format("URI should be the same '%s'", Constant.OPEN_AI_API_URI));
            assertTrue(httpRequest.headers().firstValue("Authorization").isPresent(), "Authorization header is not present");
            String bearer = httpRequest.headers().firstValue("Authorization").get().split(" ")[0];
            assertEquals("Bearer", bearer, "Authorization token should start with 'Bearer'");
            assertEquals("POST", httpRequest.method(), "Method should be POST");
        }
    }

    @Nested
    @DisplayName("Response Processing Tests")
    @Order(30)
    class ResponseProcessingTests {

        @Test
        @DisplayName("Should process valid response data")
        void shouldProcessValidResponseData() {
            StringBuilder assistantResponse = new StringBuilder();
            String validData = """
                {
                    "choices": [{
                        "delta": {
                            "content": "Hello"
                        }
                    }]
                }""";

            openAIClient.collectAndPrintContent(validData, assistantResponse);

            assertEquals("Hello", assistantResponse.toString());
        }

        @Test
        @DisplayName("Should handle malformed JSON response")
        void shouldHandleMalformedJsonResponse() {
            StringBuilder assistantResponse = new StringBuilder();
            String invalidData = "invalid json";

            openAIClient.collectAndPrintContent(invalidData, assistantResponse);

            assertEquals("", assistantResponse.toString());
        }

        @Test
        @DisplayName("Should handle response without content")
        void shouldHandleResponseWithoutContent() {
            StringBuilder assistantResponse = new StringBuilder();
            String dataWithoutContent = """
                {
                    "choices": [{
                        "delta": {}
                    }]
                }""";

            openAIClient.collectAndPrintContent(dataWithoutContent, assistantResponse);

            assertEquals("", assistantResponse.toString());
        }
    }

    @Nested
    @DisplayName("Integration Tests")
    @Order(40)
    class IntegrationTests {

        @Test
        @DisplayName("Should stream response and return message")
        void shouldStreamResponseAndReturnMessage() throws Exception {
            List<Message> messages = List.of(new Message(Role.USER, "Test message"));
            Stream<String> responseStream = Stream.of(
                    "data: {\"choices\":[{\"delta\":{\"content\":\"Hello\"}}]}",
                    "data: {\"choices\":[{\"delta\":{\"content\":\" world\"}}]}",
                    "data: [DONE]"
            );

            when(mockResponse.body()).thenReturn(responseStream);
            when(mockHttpClient.sendAsync(
                    any(HttpRequest.class),
                    any(HttpResponse.BodyHandler.class)))
                    .thenReturn(CompletableFuture.completedFuture(mockResponse));

            Message response = openAIClient.streamResponseWithMessage(messages);

            verify(mockHttpClient, times(1)).sendAsync(
                    any(HttpRequest.class),
                    any(HttpResponse.BodyHandler.class)
            );

            assertNotNull(response, "Response should not be null");
            assertEquals(Role.AI, response.role(), "Response role should be AI");
            assertEquals("Hello world", response.content(), "Response content should match expected");

            verify(mockHttpClient).sendAsync(
                    any(HttpRequest.class),
                    any(HttpResponse.BodyHandler.class)
            );
        }
    }
}