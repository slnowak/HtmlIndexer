package pl.edu.agh.ki.bd.htmlIndexer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import pl.edu.agh.ki.bd.htmlIndexer.model.ProcessedUrl;
import pl.edu.agh.ki.bd.htmlIndexer.model.Sentence;
import pl.edu.agh.ki.bd.htmlIndexer.model.Statistics;
import pl.edu.agh.ki.bd.htmlIndexer.persistence.HibernateUtils;

public class HtmlIndexerApp {

    public static void main(String[] args) throws IOException {
        HibernateUtils.getSession().close();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        Index indexer = new Index(HibernateUtils.getSession(), new SentenceSplitter());

        while (true) {
            System.out.println("\nHtmlIndexer [? for help] > : ");
            String command = bufferedReader.readLine();
            long startAt = new Date().getTime();

            if (command.startsWith("?")) {
                System.out.println("'?'      	- print this help");
                System.out.println("'x'      	- exit HtmlIndexer");
                System.out.println("'i URLs'  	- index URLs, space separated");
                System.out.println("'f WORDS'	- find sentences containing all WORDs, space separated");
                System.out.println("'l LENGTH'	- find sentences longer than given length");
                System.out.println("'fu WORDS'	- find urls containing words");
                System.out.println("'s'	- shows statistics");
            } else if (command.startsWith("x")) {
                System.out.println("HtmlIndexer terminated.");
                HibernateUtils.shutdown();
                break;
            } else if (command.startsWith("i ")) {
                for (String url : command.substring(2).split(" ")) {
                    try {
                        indexer.indexWebPage(url);
                        System.out.println("Indexed: " + url);
                    } catch (IOException e) {
                        System.out.println("Error indexing: " + e.getMessage());
                    }
                }
            } else if (command.startsWith("f ")) {
                for (Sentence sentence : indexer.findSentencesByWords(command.substring(2))) {
                    System.out.println("Found in sentence: " + sentence);
                }
            } else if (command.startsWith("l ")) {
                final int length = Integer.parseInt(command.substring(2));
                for (Sentence sentence: indexer.findSentencesLongerThan(length)) {
                    System.out.println("Sentence with length longer than " + length + ": " + sentence);
                }
            } else if (command.startsWith("fu ")) {
                final String words = command.substring(2);
                for (ProcessedUrl url: indexer.findUrlsContaining(words)) {
                    System.out.println("Url containing " + words + " " + url);
                }
            } else if (command.startsWith("s")) {
                for (Statistics statistics: indexer.showStatistics()) {
                    System.out.println(statistics);
                }
            }

            System.out.println("took " + (new Date().getTime() - startAt) + " ms");

        }

    }

}
