package pl.edu.agh.ki.bd.htmlIndexer;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by novy on 30.11.14.
 */
public class SentenceSplitter {

    public Collection<String> split(String sentence) {
        Collection<String> words = Arrays.asList(sentence.split("\\s+"));

        Collection<String> withWhitespaceReplaced =  Collections2.transform(words, new Function<String, String>() {
            @Override
            public String apply(String input) {
                return input.replaceAll("[^\\w]", "");
            }
        });

        return Collections2.filter(withWhitespaceReplaced, new Predicate<String>() {
            @Override
            public boolean apply(String s) {
                return !StringUtils.isBlank(s);
            }
        });


    }
}
