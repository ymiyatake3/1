import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class Scrap {

    Connection connection;
    Scrap() {
        connection = Jsoup.connect("https://icanhazwordz.appspot.com");
    }

    public String read() {
        String input = "";

        try {
            // get all html texts
            Document doc = connection.get();

            // get words with class "letter"
            Elements elms = doc.select(".letter");
            for (Element elm : elms) {
                input += elm.text();
            }

            System.out.println(input);

        }catch(IOException e) {
            e.printStackTrace();
        }
        return input;
    }

    public void post(String s) {
        try {
            connection.post();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
