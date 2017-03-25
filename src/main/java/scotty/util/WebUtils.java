package scotty.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class WebUtils {

    private static final String URL = "https://bigbangtrans.wordpress.com/series-2-episode-02-the-codpiece-topology/";

    public static Element fetchPage(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        String title = doc.title();
        System.out.print(title);
        return doc.body();
    }

    public static List<String> getRawConversation(String url) throws IOException {
        Element body = fetchPage(url);
        Elements pList = body.getElementsByTag("p");
        //Elements pList = body.getElementsByAttributeValue("class", "MsoNormal");

        List<String> conversations = new ArrayList<>();

        for(Element e : pList) {
            String content = e.text();
            if (content.contains(":")) {
                String[] split = content.split(":");
                if (split[0].contains("Scene")) {
                    conversations.add("_DIALOG_CHANGE " + content);
                } else if (split.length != 2) {
                    conversations.add(content);
                } else{
                    conversations.add(split[0].split(" ")[0] + ":" + split[1]);
                }
            }
        }

        return conversations;
    }

    public static List<String> getAllLinks(String url) throws IOException {
        Element body = fetchPage(url);

        List<String> links = new ArrayList<>();

        Elements elements = body.getElementsByAttributeValueStarting("class", "page_item");

        for(Element e : elements) {
            String link = e.getElementsByTag("a").get(0).attr("href");
            if (link.contains("series-")) {
                links.add(link);
            }
        }

        System.out.println(links);
        return links;
    }

    public static void writeToFile(List<String> links) {
        for(String link : links) {
            try{
                List<String> conversations = getRawConversation(link);
                String file = link.substring(35, link.length()-1) + ".txt";
                System.out.println("\t" + file);
                PrintWriter writer = new PrintWriter(file, "UTF-8");
                for(String line : conversations) {
                    writer.println(line);
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        List<String> links = getAllLinks(URL);

        writeToFile(links);
    }
}
