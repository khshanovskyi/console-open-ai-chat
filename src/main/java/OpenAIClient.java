import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dto.Message;
import dto.Model;
import dto.Role;
import utils.Constant;

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
    private final boolean streamResponse;

    public OpenAIClient(Model model) {
        this(model, true);
    }

    public OpenAIClient(Model model, boolean streamResponse) {
        this.model = model;
        this.streamResponse = streamResponse;
        this.mapper = new ObjectMapper();
        this.httpClient = HttpClient.newHttpClient();
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

}
