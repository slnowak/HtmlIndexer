package pl.edu.agh.ki.bd.htmlIndexer.model;


public class Sentence {

    private long id;
    private String content;
    private ProcessedUrl url;

    public Sentence() {}

    public Sentence(String content, ProcessedUrl url) {
        this.setContent(content);
        this.setUrl(url);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ProcessedUrl getUrl() {
        return url;
    }

    public void setUrl(ProcessedUrl url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return content + "[" +
                url + "]";
    }
}
