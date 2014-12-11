package pl.edu.agh.ki.bd.htmlIndexer;

import java.io.IOException;
import java.util.Collection;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Collections2;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pl.edu.agh.ki.bd.htmlIndexer.model.*;

// todo: get rid of those messy session retrieves, instead pass session as constructor param
public class Index {

    final Session session;
    final SentenceSplitter splitter;

    public Index(Session session, SentenceSplitter splitter) {
        this.session = session;
        this.splitter = splitter;
    }

    public void indexWebPage(String url) throws IOException {

        Transaction transaction = session.beginTransaction();

        final ProcessedUrl processedUrl = new ProcessedUrl(url);

        for (Element element : getDocumentElements(url)) {
            if (elementHasAnyContent(element)) {
                processElementContent(element, processedUrl, session);
            }
        }

        transaction.commit();
    }

    private void processElementContent(Element element, ProcessedUrl processedUrl, Session session) {
        for (String sentenceContent : extractSentences(element)) {

            Sentence sentence = new Sentence(processedUrl);
            session.persist(sentence);

            Collection<String> words = splitter.split(sentenceContent);
            for (Word word: transformWordsIntoWordEntities(words)) {
                WordSentence wordSentence = findOrCreateWordSentence(new WordSentenceId(word.getContent(), sentence.getId()));
                connectWordAndSentence(wordSentence, word, sentence);
                persisWordAndSentence(wordSentence, word, sentence);
            }

            processedUrl.addSentence(sentence);
            session.persist(sentence);

        }
    }

    private void connectWordAndSentence(WordSentence wordSentence, Word word, Sentence sentence) {
        word.addWordSentence(wordSentence);
        sentence.addWordSentence(wordSentence);
        wordSentence.setWord(word);
        wordSentence.setSentence(sentence);
        wordSentence.increaseOccurrenceCounter();
    }

    private void persisWordAndSentence(WordSentence wordSentence, Word word, Sentence sentence) {
        session.persist(word);
        session.persist(sentence);
        session.persist(wordSentence);
    }

    private String[] extractSentences(Element element) {
        return element.ownText().split("\\. ");
    }

    private boolean elementHasAnyContent(Element element) {
        return element.ownText().trim().length() > 1;
    }

    private Elements getDocumentElements(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        return doc.body().select("*");
    }

    public Collection<Sentence> findSentencesByWords(String words) {
        Collection<String> splitWords = splitter.split(words);

        final String queryString = "select s from Sentence s join s.wordSentences as ws join ws.word as w " +
                "where w.content in (:words) group by s order by count(s) desc";

        Collection<Sentence> result = session.createQuery(queryString)
                .setParameterList("words", splitWords)
                .list();

        return result;
    }

    public Collection<Sentence> findSentencesLongerThan(int length) {
        final String query = "select s from Sentence s join s.wordSentences as ws join ws.word as w " +
                "group by s having sum(ws.occurrence * length(w.content)) > :length";

        Collection<Sentence> result = session.createQuery(query)
                .setParameter("length", Long.valueOf(length))
                .list();


        return result;
    }

    public Collection<ProcessedUrl> findUrlsContaining(String words) {
        final Collection<String> splitWords = new SentenceSplitter().split(words);

        final String queryString = "select distinct p from ProcessedUrl p join p.sentences as s join s.wordSentences ws where ws.word.content in (:words)";

        final Query query = session.createQuery(
                queryString)
                .setParameterList("words", splitWords);

        Collection<ProcessedUrl> result = query.list();

        return result;

    }

    public Collection<Statistics> showStatistics() {

        final Query query = session.createQuery(
                "select new pl.edu.agh.ki.bd.htmlIndexer.model.Statistics(p, count(p)) " +
                "from ProcessedUrl p join p.sentences s " +
                "group by p.id order by count(p) desc"
        );

        Collection<Statistics> result = query.list();


        return result;
    }

    public Collection<Word> transformWordsIntoWordEntities(Collection<String> words) {
        return Collections2.transform(words, new Function<String, Word>() {
            @Override
            public Word apply(String s) {
                return findOrCreateWord(s);
            }
        });
    }

    public Word findOrCreateWord(String content) {
        return MoreObjects.firstNonNull((Word) session.get(Word.class, content), new Word(content));

    }

    public WordSentence findOrCreateWordSentence(WordSentenceId wordSentenceId) {
        return MoreObjects.firstNonNull((WordSentence)session.get(WordSentence.class, wordSentenceId), new WordSentence(wordSentenceId));
    }

}
