package pl.edu.agh.ki.bd.htmlIndexer.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by novy on 30.11.14.
 */
public class Word {

    private String content;
    private List<WordSentence> wordSentences = new ArrayList<>();

    public Word() {}

    public Word(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<WordSentence> getWordSentences() {
        return wordSentences;
    }

    public void setWordSentences(List<WordSentence> wordSentences) {
        this.wordSentences = wordSentences;
    }

    public void addWordSentence(WordSentence wordSentence) {
        wordSentences.add(wordSentence);
    }

    @Override
    public String toString() {
        return content;
    }
}
