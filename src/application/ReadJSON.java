package application;

import org.apache.commons.io.input.BOMInputStream;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ReadJSON {

    private File jsonFile;
    private JSONObject json;

    // constructor, no default value
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

            this.json = new JSONObject(s);



        } catch (IOException error) {
            this.json = new JSONObject();
        }
    }
}