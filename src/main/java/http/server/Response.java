package http.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class Response {

    private HttpStatus httpStatus;
    private String body;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Map<String, Object> headers = new HashMap<>();

    public Response(HttpStatus httpStatus, String body) {
        this.httpStatus = httpStatus;
        this.body = body;
        if (body != null && body.length() > 0)
        {
            headers.put("Content-Length", body.length());
            headers.put("Content-Type", "text/plain");
        }
        else
        {
            headers.put("Content-Length", 0);
        }
    }

    public Response(HttpStatus httpStatus, Object body) {
        this.httpStatus = httpStatus;
        this.body = getJSONBodyAsString(body);
        if (body != null && this.body.length() > 0)
        {
            headers.put("Content-Length", this.body.length());
            headers.put("Content-Type", "application/json");
        }
        else
        {
            headers.put("Content-Length", 0);
        }
    }

    public static final String HTTP_VERSION = "HTTP/1.1";

    public Response(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        headers.put("Content-Length", 0);
    }

    public Response() {
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%s %d %s\n",HTTP_VERSION, httpStatus.getStatusCode(), httpStatus.getStatusMessage()));
        for (Map.Entry<String, Object> header : headers.entrySet()) {
            stringBuilder.append(header.getKey()).append(": ").append(header.getValue()).append("\n");
        }

        stringBuilder.append("\n");
        if(body != null && body.length() > 0)
        {
            stringBuilder.append(body);
        }
        return stringBuilder.toString();
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    private String getJSONBodyAsString(Object clazz) {
        try {
            return objectMapper.writeValueAsString(clazz);
        } catch (JsonProcessingException e) {
            System.err.println(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }
}
