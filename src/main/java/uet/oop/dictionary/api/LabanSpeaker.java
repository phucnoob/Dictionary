package uet.oop.dictionary.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.fluent.Content;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.net.URIBuilder;
import uet.oop.dictionary.utils.Config;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class LabanSpeaker implements Speaker {
    @Override
    public SpeakerData speak(String word, Accent accent) {
        try {
            String json = "";
            URI uri = new URIBuilder(Config.LABAN_API)
                    .addParameter("accent", accent.toString())
                    .addParameter("word", word)
                    .build();
            Content content = Request.get(uri).execute().returnContent();
            json = content.asString(StandardCharsets.UTF_8);

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, SpeakerData.class);
        } catch (IOException | URISyntaxException e) {
            System.err.println(e.getMessage());
        }

        return SpeakerData.EMPTY;
    }

    public static void main(String[] args) {
        String data = new LabanSpeaker().speak("book").getData();

        System.out.println(data);
    }
}
