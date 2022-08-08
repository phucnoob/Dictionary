package uet.oop.dictionary.api;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public interface Speaker {
    SpeakerData speak(String word, Accent accent);

    default SpeakerData speak(String word) {
        SpeakerData usAccent = speak(word, Accent.US);
        SpeakerData ukAccent = speak(word, Accent.UK);

        if (usAccent != SpeakerData.EMPTY) {
            return usAccent;
        } else if (ukAccent != SpeakerData.EMPTY) {
            return usAccent;
        } else {
            return SpeakerData.EMPTY;
        }
    }

    enum Accent {
        US("us"),
        UK("uk");

        private final String value;
        Accent(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    class SpeakerData {

        public static final SpeakerData EMPTY;

        static {
            EMPTY = new SpeakerData();
        }

        @JsonProperty("error")
        private int error;

        @JsonProperty("id")
        private int id;

        @JsonProperty("data")
        private String data;


        @JsonGetter
        public int getId() {
            return id;
        }

        @JsonSetter
        public void setId(int id) {
            this.id = id;
        }

        private SpeakerData() {

        }

        public SpeakerData(int error, String data) {
            this.error = error;
            this.data = data;
        }

        @JsonGetter
        public int getError() {
            return error;
        }

        @JsonSetter
        public void setError(int error) {
            this.error = error;
        }

        @JsonGetter
        public String getData() {
            return data;
        }

        @JsonSetter
        public void setData(String data) {
            this.data = data;
        }
    }
}
