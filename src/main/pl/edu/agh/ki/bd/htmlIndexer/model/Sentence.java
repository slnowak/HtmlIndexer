package pl.edu.agh.ki.bd.htmlIndexer.model;


import java.util.ArrayList;
import java.util.List;

public class Sentence {

    private long id;
    private List<WordSentence> wordSentences = new ArrayList<>();
    private ProcessedUrl url;

    public Sentence() {}

    public Sentence(ProcessedUrl url) {
        this.setUrl(url);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<WordSentence> getWordSentences() {
        return wordSentences;
    }

    public void setWordSentences(List<WordSentence> wordSentences) {
        this.wordSentences = wordSentences;
    }

    public ProcessedUrl getUrl() {
        return url;
    }

    public void setUrl(ProcessedUrl url) {
        this.url = url;
    }


    public void addWordSentence(WordSentence wordSentence) {
        wordSentences.add(wordSentence);
    }

    @Override
    public String toString() {
        String repr = "";
        for (WordSentence wordSentence : getWordSentences()) {
            repr += wordSentence.getWord() + " ";
        }

        return repr;
    }
}
