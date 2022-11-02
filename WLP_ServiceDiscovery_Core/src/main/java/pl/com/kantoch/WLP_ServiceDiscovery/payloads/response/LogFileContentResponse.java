package pl.com.kantoch.WLP_ServiceDiscovery.payloads.response;

public class LogFileContentResponse {
    private String content;

    public LogFileContentResponse(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
