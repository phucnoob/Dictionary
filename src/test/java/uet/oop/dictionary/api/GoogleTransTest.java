package uet.oop.dictionary.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GoogleTransTest {

    @Test
    void translate() {
        Translator translator = new GoogleTrans();
        var translatedData = translator.translate(Translator.LanguageCode.EN, Translator.LanguageCode.VI, "bye");

        assertEquals("từ biệt", translatedData.getTranslated());
    }
}