package warmup;

import java.time.Instant;
import java.util.Map;

public class ResponseSample extends RequestSample{
    private String id;
    private String createdAt;

    public ResponseSample() {
    }

    public ResponseSample(String id, String name, Map<String, Object> data, Instant createdAt) {
        super(name, data);
        this.id = id;
        this.createdAt = createdAt.toString();
    }

    public String getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "ResponseSample{" +
                "id='" + id + '\'' +
                ", name='" + getName() + '\'' +
                ", data=" + getData() +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
