package task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import task.dto.Message;
import task.dto.Model;
import task.dto.Role;
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
        ObjectNode request = mapper.createObjectNode();
        request.put("model", this.model.getValue());
        request.put("stream", this.streamResponse);
        addHistory(messages, request);

        StringBuilder assistantResponse = new StringBuilder();
        HttpRequest httpRequest = generateRequest(request);

        postAndStreamToConsole(httpRequest, assistantResponse);

        return new Message(Role.AI, assistantResponse.toString());
    }

    public void addHistory(List<Message> messages, ObjectNode request) {
        ArrayNode messageArray = mapper.valueToTree(messages);
        request.set("messages", messageArray);
    }

    public HttpRequest generateRequest(ObjectNode request) throws JsonProcessingException {
        return HttpRequest.newBuilder()
                .uri(Constant.OPEN_AI_API_URI)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(request)))
                .build();
    }

    public void postAndStreamToConsole(HttpRequest httpRequest, StringBuilder assistantResponse) {
        httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofLines())
                .thenAccept(response -> {
                    response.body().forEach(line -> {
                        if (line.startsWith("data: ")) {
                            String data = line.substring(6).trim();
                            if (!data.equals("[DONE]")) {
                                collectAndPrintContent(data, assistantResponse);
                            }
                        }
                    });
                })
                .join();
    }

    public void collectAndPrintContent(String data, StringBuilder assistantResponse) {
        try {
            JsonNode rootNode = mapper.readTree(data);
            JsonNode choicesNode = rootNode.get("choices");
            if (choicesNode != null && choicesNode.isArray() && !choicesNode.isEmpty()) {
                JsonNode deltaNode = choicesNode.get(0).get("delta");
                if (deltaNode != null && deltaNode.has("content")) {
                    String token = deltaNode.get("content").asText();
                    System.out.print(token);
                    assistantResponse.append(token);
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing token: " + e.getMessage());
            System.err.println("Unexpected data structure: " + data);
        }
    }

}
