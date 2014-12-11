package pl.edu.agh.ki.bd.htmlIndexer.model;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by novy on 28.11.14.
 */
public class ProcessedUrl {

    private long id;
    private String url;
    private Date date = new Date();
    private List<Sentence> sentences = new LinkedList<Sentence>();

    public ProcessedUrl() {}

    public ProcessedUrl(String url) {
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    public void addSentence(Sentence sentence) {
        sentences.add(sentence);
    }

    @Override
    public String toString() {
        return "url: " + url + ", " +
                "parsed at: " + date;

    }
}
