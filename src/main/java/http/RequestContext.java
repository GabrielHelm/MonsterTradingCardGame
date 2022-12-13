package http;

import java.util.List;

public class RequestContext {

    private static final String CONTENT_LENGTH_HEADER_NAME = "Content-Length";
    private String httpVerb;
    private String path;
    private List<Header> headers;
    private String body;

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
        this.path = path;
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
}
