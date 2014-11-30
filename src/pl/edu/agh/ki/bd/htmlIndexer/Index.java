package pl.edu.agh.ki.bd.htmlIndexer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pl.edu.agh.ki.bd.htmlIndexer.model.ProcessedUrl;
import pl.edu.agh.ki.bd.htmlIndexer.model.Sentence;
import pl.edu.agh.ki.bd.htmlIndexer.model.Statistics;
import pl.edu.agh.ki.bd.htmlIndexer.persistence.HibernateUtils;

// todo: get rid of those messy session retrieves, instead pass session as constructor param
public class Index {
    public void indexWebPage(String url) throws IOException {

        Session session = HibernateUtils.getSession();
        Transaction transaction = session.beginTransaction();

        final ProcessedUrl processedUrl = new ProcessedUrl(url);

        for (Element element : getDocumentElements(url)) {
            if (elementHasAnyContent(element)) {
                processElementContent(element, processedUrl, session);
            }
        }

        transaction.commit();
        session.close();
    }

    private void processElementContent(Element element, ProcessedUrl processedUrl, Session session) {
        for (String sentenceContent : extractSentences(element)) {

            Sentence sentence = new Sentence(sentenceContent, processedUrl);
            processedUrl.addSentence(sentence);
            session.persist(sentence);

        }
    }

    private String[] extractSentences(Element element) {
        return element.ownText().split("\\. ");
    }

    private boolean elementHasAnyContent(Element element) {
        return element.ownText().trim().length() > 1;
    }

    // todo: fix charset problem
    private Elements getDocumentElements(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        return doc.body().select("*");
    }

    public Collection<Sentence> findSentencesByWords(String words) {
        Session session = HibernateUtils.getSession();

        String pattern = "%" + words.replace(" ", "%") + "%";

        Collection<Sentence> result = session.createQuery("select s from Sentence s where s.content like :pattern")
                .setParameter("pattern", pattern)
                .list();

        session.close();

        return result;
    }

    public Collection<Sentence> findWordsLongerThan(int length) {
        Session session = HibernateUtils.getSession();

        final String query = "select s from Sentence s where length(s.content) > :length";

        Collection<Sentence> result = session.createQuery(query)
                .setParameter("length", length)
                .list();

        session.close();

        return result;
    }

    public Collection<ProcessedUrl> findUrlsContaining(String words) {
        Session session = HibernateUtils.getSession();

        final String pattern = "%" + words.replace(" ", "%") + "%";
        
        final Query query = session.createQuery(
                "select distinct p from ProcessedUrl p join p.sentences as s where s.content like :pattern")
                .setParameter("pattern", pattern);

        Collection<ProcessedUrl> result = query.list();

        session.close();

        return result;

    }

    public Collection<Statistics> showStatistics() {
        Session session = HibernateUtils.getSession();

        final Query query = session.createQuery(
                "select new pl.edu.agh.ki.bd.htmlIndexer.model.Statistics(p, count(p)) " +
                "from ProcessedUrl p join p.sentences s " +
                "group by p.id order by count(p) desc"
        );

        Collection<Statistics> result = query.list();

        session.close();

        return result;
    }

}
