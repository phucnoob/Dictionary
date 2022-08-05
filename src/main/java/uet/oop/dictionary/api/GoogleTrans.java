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

public class GoogleTrans implements Translator {
    @Override
    public TranslatedData translate(LanguageCode src, LanguageCode dest, String text) {
        try {
            URI uri = new URIBuilder(Config.GOOGLE_API)
                    .addParameter("text", text)
                    .addParameter("src", src.toString())
                    .addParameter("dest", dest.toString())
                    .build();
            Content content = Request.get(uri).execute().returnContent();
            String json = content.asString(StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(json, TranslatedData.class);
        } catch (IOException | URISyntaxException e) {
            System.err.println(e.getMessage());
        }

        return TranslatedData.EMPTY;
    }
}
