package pl.edu.agh.ki.bd.htmlIndexer.model;

/**
 * Created by novy on 30.11.14.
 */
public class Statistics {

    private final ProcessedUrl url;
    private final long sentenceCount;

    public Statistics(ProcessedUrl url, long sentenceCount) {
        this.url = url;
        this.sentenceCount = sentenceCount;
    }

    public ProcessedUrl getUrl() {
        return url;
    }

    public long getSentenceCount() {
        return sentenceCount;
    }

    @Override
    public String toString() {
        return url + ", " +
                " sentences: " + sentenceCount;
    }
}
