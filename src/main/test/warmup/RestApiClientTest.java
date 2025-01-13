package warmup;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RestApiClientTest {

    @Test
    void postItem() throws IOException, InterruptedException {
        throw new RuntimeException("NEED TO UNCOMMENT TESTS AND COMMENT THIS ROW");

//        RestApiClient restApiClient = new RestApiClient();
//        Map<String, Object> map = Map.of(
//                "year", 2023,
//                "price", 1111.99,
//                "CPU model", "A17 Bionic",
//                "Hard disk size", "512 GB",
//                "Color", "Black"
//        );
//        RequestSample requestSample = new RequestSample(
//                "Apple Iphone 16",
//                map
//        );
//
//        ResponseSample posted = restApiClient.post(requestSample);
//
//        assertEquals(map, requestSample.getData());
//        assertEquals("Apple Iphone 16", posted.getName());
//        assertNotNull(posted.getId());
//        assertNotNull(posted.getCreatedAt());
    }

}