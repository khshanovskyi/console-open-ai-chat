package warmup;

import java.util.Map;

public class RequestSample {
    private String name;
    private Map<String, Object> data;

    public RequestSample() {
    }

    public RequestSample(String name, Map<String, Object> data) {
        this.name = name;
        this.data = data;
    }


    public String getName() {
        return name;
    }

    public Map<String, Object> getData() {
        return data;
    }


    @Override
    public String toString() {
        return "RequestSample{" +
                ", name='" + name + '\'' +
                ", data=" + data +
                '}';
    }
}
