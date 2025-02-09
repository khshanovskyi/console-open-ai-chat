package task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import task.dto.Message;
import task.dto.Model;
import task.utils.Constant;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

/**
 * Client for communication with Open AI API
 */
public class OpenAIClient {

    private final ObjectMapper mapper;
    private final HttpClient httpClient;
    private final Model model;
    private final String apiKey;
    private final boolean streamResponse;

    public OpenAIClient(Model model, String apiKey) {
        this(model, apiKey, true);
    }

    public OpenAIClient(Model model, String apiKey, boolean streamResponse) {
        this(model, apiKey, streamResponse, HttpClient.newHttpClient());
    }

    public OpenAIClient(Model model, String apiKey, boolean streamResponse, HttpClient httpClient) {
        this.mapper = new ObjectMapper();
        this.model = model;
        this.apiKey = checkApiKey(apiKey);
        this.streamResponse = streamResponse;
        this.httpClient = httpClient;
    }

    private String checkApiKey(String apiKey) {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("apiKey cannot be null or empty");
        }
        return apiKey;
    }

    /**
     * Goes to Open AI API, prints chunks with AI response to the console and returns Message with AI message.
     *
     * @param messages message history
     * @return AI message
     */
    public Message streamResponseWithMessage(List<Message> messages) throws Exception {
        // todo:
        //  1. Collect history and user request.
        //  2. Create request json body:
        //  {
        //    "model": "gpt-4o-mini",
        //    "messages": [
        //      {
        //        "role": "system",
        //        "content": "You are a helpful assistant."
        //      },
        //      {
        //        "role": "user",
        //        "content": "What is the capital of France?"
        //      }
        //    ],
        //    "stream": true
        //  }
        //  3. Create {@link HttpRequest}, don't forget to add api key and content type
        //      POST https://api.openai.com/v1/chat/completions
        //      Authorization: Bearer YOUR_API_KEY
        //      Content-Type: application/json
        //  4. Request to Open AI:
        //      httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofLines())
        //                  .thenAccept(response -> ...)
        //                  .join();
        //  5. Collect and print 'data' to console.
        //  6. Return AI message with collected 'data' content.

        throw new RuntimeException("Not implemented yet");
    }


    public void addHistory(List<Message> messages, ObjectNode request) {
        // todo:
        //  1. Convert messages List to ArrayNode using mapper.valueToTree
        //  2. Add converted messages array to request object with key "messages"

        throw new RuntimeException("Not implemented yet");
    }

    public HttpRequest generateRequest(ObjectNode request) throws JsonProcessingException {
        // todo:
        //  1. Create new HttpRequest.Builder
        //  2. Set URI to OpenAI API endpoint
        //  3. Add required headers:
        //      Authorization: Bearer YOUR_API_KEY
        //      Content-Type: application/json
        //  4. Set POST method with request body converted to string
        //  5. Build and return the request

        throw new RuntimeException("Not implemented yet");
    }

    public void postAndStreamToConsole(HttpRequest httpRequest, StringBuilder assistantResponse) {
        // todo:
        //  1. Send async request using httpClient with line body handler
        //  2. Process response lines:
        //      - Check if line starts with "data: "
        //      - Remove "data: " prefix and trim
        //      - Skip if line equals "[DONE]"
        //      - Otherwise collect and print content
        //  3. Wait for completion with join()

        throw new RuntimeException("Not implemented yet");
    }

    public void collectAndPrintContent(String data, StringBuilder assistantResponse) {
        // todo:
        //  1. Parse data string to JsonNode using mapper
        //  2. Extract choices array from root node
        //  3. Check if choices exists and is not empty
        //  4. Get delta node from first choice
        //  5. If delta has content:
        //      - Extract content token as text
        //      - Print token to console
        //      - Append token to assistantResponse
        //  6. Handle parsing errors with appropriate error messages

        throw new RuntimeException("Not implemented yet");
    }

}
