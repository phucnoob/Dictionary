package uet.oop.dictionary.api;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public interface Translator {
    TranslatedData translate(Translator.LanguageCode src,
                     Translator.LanguageCode dest,
                     String text);

    enum LanguageCode {
        @JsonProperty("en")
        EN("en"),
        @JsonProperty("vi")
        VI("vi");

        final String code;

        LanguageCode(String code) {
            this.code = code;
        }
        @Override
        public String toString() {
            return code;
        }

    }
    class TranslatedData {

        public static TranslatedData EMPTY;

        static {
            EMPTY = new TranslatedData();
        }

        @JsonProperty("text")
        private String text;

        @JsonProperty("translated")
        private String translated;

        @JsonProperty("src")
        private LanguageCode src;

        @JsonProperty("dest")
        private LanguageCode dest;

        private TranslatedData() {

        }

        public TranslatedData(String text, String translate, LanguageCode src, LanguageCode dest) {
            this.text = text;
            this.translated = translate;
            this.src = src;
            this.dest = dest;
        }

        @JsonGetter
        public String getText() {
            return text;
        }

        @JsonSetter
        public void setText(String text) {
            this.text = text;
        }

        @JsonGetter
        public String getTranslated() {
            return translated;
        }

        @JsonGetter
        public void setTranslated(String translated) {
            this.translated = translated;
        }

        @JsonGetter
        public LanguageCode getSrc() {
            return src;
        }

        @JsonSetter
        public void setSrc(LanguageCode src) {
            this.src = src;
        }

        @JsonGetter
        public LanguageCode getDest() {
            return dest;
        }

        @JsonSetter
        public void setDest(LanguageCode dest) {
            this.dest = dest;
        }
    }
}
