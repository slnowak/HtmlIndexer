package pl.edu.agh.ki.bd.htmlIndexer.model;

import java.io.Serializable;

/**
 * Created by novy on 09.12.14.
 */
public class WordSentenceId implements Serializable {

    private String wordContent;
    private long sentenceId;

    public WordSentenceId(String wordContent, long sentenceId) {
        this.wordContent = wordContent;
        this.sentenceId = sentenceId;
    }

    public String getWordContent() {
        return wordContent;
    }

    public void setWordContent(String wordContent) {
        this.wordContent = wordContent;
    }

    public long getSentenceId() {
        return sentenceId;
    }

    public void setSentenceId(long sentenceId) {
        this.sentenceId = sentenceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordSentenceId that = (WordSentenceId) o;

        if (sentenceId != that.sentenceId) return false;
        if (wordContent != null ? !wordContent.equals(that.wordContent) : that.wordContent != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = wordContent != null ? wordContent.hashCode() : 0;
        result = 31 * result + (int) (sentenceId ^ (sentenceId >>> 32));
        return result;
    }
}
