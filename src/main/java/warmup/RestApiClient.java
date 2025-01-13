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
                //FIXME: initialize with Item
        );
        System.out.println(requestSample);

        ResponseSample posted = new RestApiClient().post(requestSample);
        System.out.println(posted);
    }

    public ResponseSample post(RequestSample requestSample) throws IOException, InterruptedException {
        // TODO: Serialize the 'requestSample' object into a JSON string

        // TODO: Create an HTTP POST request
        // - Specify the URI: "https://api.restful-api.dev/objects"
        // - Set the "Content-Type" header to "application/json"
        // - Use the serialized JSON string as the request body

        // TODO: Send the HTTP request using HttpClient
        // - Use 'HttpClient.newHttpClient().send()' to execute the request
        // - Use 'HttpResponse.BodyHandlers.ofString()' to handle the response body

        // TODO: Check if the response status code is 200 (OK)
        // - If true, deserialize the response body to a 'ResponseSample' object
        // - If false, throw a RuntimeException with details about the status code and body

        return null;
    }


}
