package application;

import org.apache.commons.io.IOUtils;
import org.json.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Set;

public class Main {

    private JSONObject json;
    private URL url;

    public void main(String[] args) {
        try {

            getURL("./tickets.json");
            getJson(this.url);
            JSONObject answer = this.json.getJSONObject("tickets");
            Set<String> toFindDeparture = Set.of("departure_date", "departure_time");
            Set<String> toFindArrival = Set.of("arrival_date", "arrival_time");

//            answer.accumulate()

        } catch (MalformedURLException error) {
            System.out.println("Can't get URL");
        } catch (IOException error) {
            System.out.println("Can't get JSON");
        }
    }

    public void getURL(String url) throws MalformedURLException {
        this.url = new URL("tickets.json");
    }

    public void getJson(URL url) throws IOException {

        String json = IOUtils.toString(url, Charset.forName("UTF-8"));

        this.json = new JSONObject(json);
}
}