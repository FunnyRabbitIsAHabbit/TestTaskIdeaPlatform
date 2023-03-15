package application;

import org.apache.commons.io.input.BOMInputStream;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ReadJSON {

    private File jsonFile;
    private JSONObject json;
    private final JSONParser jsonParser = new JSONParser();

    // constructor, no default value, call to business function
    public ReadJSON(String filename) {
        this.retrieveFile(filename);
        this.retrieveJson(this.jsonFile);
    }

    public final JSONObject getJson() {
        return this.json;
    }

    private void retrieveFile(String url) {

        this.jsonFile = new File(url);
    }

    private void retrieveJson(File url) {

        try {

            InputStream inputStream = new FileInputStream(url);
            BOMInputStream bis = new BOMInputStream(inputStream);

            String s = new String(bis.readAllBytes());
            s = s.replaceAll("\n", "");
            s = s.replaceAll(" ", "");

            this.json = (JSONObject) this.jsonParser.parse(s);

        } catch (IOException | ParseException error) {
            this.json = new JSONObject();
        }
    }
}