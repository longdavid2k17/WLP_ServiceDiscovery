package response;

public class LogFileContentResponse {
    private String content;

    public LogFileContentResponse(String content) {
        this.content = content;
    }

    public LogFileContentResponse() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
