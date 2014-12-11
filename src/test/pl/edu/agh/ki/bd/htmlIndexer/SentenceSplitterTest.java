package pl.edu.agh.ki.bd.htmlIndexer;

import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by novy on 30.11.14.
 */
public class SentenceSplitterTest {
    private final SentenceSplitter objectUnderTest = new SentenceSplitter();

    @Test
    public void testSplittingOnRegularSentence() throws Exception {
        final String input = "This is a sentence";

        assertThat(objectUnderTest.split(input)).containsOnly("This", "is", "a", "sentence");
    }

    @Test
    public void sentenceWithNonAlphaNumericCharacters() throws Exception {
        final String input = "?This, is! a sentence..,";

        assertThat(objectUnderTest.split(input)).containsOnly("This", "is", "a", "sentence");

    }

    @Test
    public void sentenceWithWhiteSpaces() throws Exception {
        final String input = "    This   , \n is a sentence..,";

        assertThat(objectUnderTest.split(input)).containsOnly("This", "is", "a", "sentence");

    }
}
