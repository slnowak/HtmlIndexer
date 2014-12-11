package pl.edu.agh.ki.bd.htmlIndexer.model;

/**
 * Created by novy on 09.12.14.
 */
public class WordSentence {

    private WordSentenceId wordSentenceId;
    private Word word;
    private Sentence sentence;

    private int occurrence = 0;

    public WordSentence(WordSentenceId wordSentenceId) {
        this.wordSentenceId = wordSentenceId;
    }

    public WordSentenceId getWordSentenceId() {
        return wordSentenceId;
    }

    public void setWordSentenceId(WordSentenceId wordSentenceId) {
        this.wordSentenceId = wordSentenceId;
    }

    public int getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(int occurrence) {
        this.occurrence = occurrence;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public Sentence getSentence() {
        return sentence;
    }

    public void setSentence(Sentence sentence) {
        this.sentence = sentence;
    }

    public void increaseOccurrenceCounter() {
        occurrence ++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordSentence that = (WordSentence) o;

        if (wordSentenceId != null ? !wordSentenceId.equals(that.wordSentenceId) : that.wordSentenceId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return wordSentenceId != null ? wordSentenceId.hashCode() : 0;
    }
}
