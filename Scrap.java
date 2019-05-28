import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


class Scrap {
    public static void main(String[] args) {

        try {
            Document doc = Jsoup.connect("https://icanhazwordz.appspot.com").get();
            Elements elm = doc.select("title");

            for (Elements elms : elm) {
                String title = elms.text();
                System.out.println(title);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}