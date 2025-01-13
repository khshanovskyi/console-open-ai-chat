package warmup;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RestApiClient {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException, InterruptedException {
        RequestSample requestSample = new RequestSample(
                "Apple MacBook Pro 16",
                Map.of(
                        "year", 2023,
                        "price", 1849.99,
                        "CPU model", "M3 Pro Max",
                        "Hard disk size", "1 TB"
                )
        );
        System.out.println(requestSample);

        ResponseSample posted = new RestApiClient().post(requestSample);
        System.out.println(posted);
    }

    public ResponseSample post(RequestSample requestSample) throws IOException, InterruptedException {
        String jsonPayload = mapper.writeValueAsString(requestSample);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.restful-api.dev/objects"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), ResponseSample.class);
        }
        throw new RuntimeException("Unexpected response code: " + response.statusCode() + "\nBody: " + response.body());
    }

}
