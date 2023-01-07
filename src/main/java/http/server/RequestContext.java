package http.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import http.header.Header;

import java.util.List;

public class RequestContext {

    private static final String CONTENT_LENGTH_HEADER_NAME = "Content-Length";
    private String httpVerb;
    private String path;

    private String subpath;
    private List<Header> headers;
    private String body;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getHttpVerb() {
        return httpVerb;
    }

    public void setHttpVerb(String httpVerb) {
        this.httpVerb = httpVerb;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        if(path.startsWith("/users/")) {
            this.path = path.substring(0, 7);
            this.subpath = path.substring(7);
        } else if(path.startsWith("/tradings/")) {
            this.path = path.substring(0, 10);
            this.subpath = path.substring(10);
        }else {
            this.path = path;
        }
    }
    public String getSubpath() {
        return subpath;
    }

    public void setSubpath(String subpath) {
        this.subpath = subpath;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getContentLength() {
        return headers.stream()
                .filter(header -> CONTENT_LENGTH_HEADER_NAME.equals(header.getName()))
                .findFirst()
                .map(Header::getValue)
                .map(Integer::parseInt)
                .orElse(0);
    }

    public void print() {
        System.out.println("HTTP-Verb: " + this.getHttpVerb());
        System.out.println("Path: " + this.getPath());
        System.out.println("Headers: " + this.getHeaders());
        System.out.println("Body: " + this.getBody());
    }

    public <T> T getBodyAs(Class<T> clazz) {
        try {
            return objectMapper.readValue(body, clazz);
        } catch (JsonProcessingException e) {
            System.err.println(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }
    public String getToken() {
        String rawtoken = "";
        for (Header header : headers) {
            if (header.getName().equals("Authorization")) {
                rawtoken = header.getValue();
            }
        }
        if(rawtoken.isEmpty()) {
            throw new UnauthorizedException("Access token is missing or invalid");
        }
        return rawtoken.split(" ")[1];
    }
}
