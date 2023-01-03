package http.server;

import java.util.HashMap;
import java.util.Map;

public class Response {

    private HttpStatus httpStatus;
    private String body;

    private Map<String, Object> headers = new HashMap<>();

    public Response(HttpStatus httpStatus, String body) {
        this.httpStatus = httpStatus;
        this.body = body;
        if (body != null && body.length() > 0)
        {
            headers.put("Content-Length", body.length());
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
}
