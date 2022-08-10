package uet.oop.dictionary.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import uet.oop.dictionary.api.GoogleTrans;
import uet.oop.dictionary.api.Translator;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class GoogleTranslateView extends VBox implements Initializable {


    @FXML
    public VBox originalBox;

    @FXML
    public TextArea originalText;

    @FXML
    public TextArea translateText;

    @FXML
    public ComboBox<String> original;

    @FXML
    public ComboBox<String> translate;

    @FXML
    public Button translateBtn;

    @FXML
    public Button swapBtn;

    @FXML
    public Button clearTextBtn;

    @FXML
    public Button copyTextBtn;

    @FXML
    public Button copyTranslatedBtn;

    @FXML
    public Label stats;

    @FXML
    public Label loading;

    private Map<String, Translator.LanguageCode> fullNameMap;

    private Translator translator;

    public GoogleTranslateView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("google-translate-view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            this.applyCss();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addLanguages();

        for (String s : fullNameMap.keySet()) {
            original.getItems().add(s);
            translate.getItems().add(s);
        }
        translator = new GoogleTrans();
        translateBtn.setOnAction(e -> handleTranslate());

        swapBtn.setOnAction(e -> handleSwap());
        clearTextBtn.setOnAction(e -> originalText.clear());
        copyTextBtn.setOnAction(e -> {
            originalText.selectAll();
            originalText.copy();
//            originalText.deselect();
        });

        copyTranslatedBtn.setOnAction(e -> {
            translateText.selectAll();
            translateText.copy();
        });


        originalText.textProperty().addListener((observable, oldValue, newValue) -> {
            String[] splits = stats.getText().split("/");
            splits[0] = Integer.toString(newValue.trim().length());

            stats.setText(String.join("/", splits));
        });
    }

    private void handleTranslate() {
        Translator.LanguageCode src = fullNameMap.get(original.getValue());
        Translator.LanguageCode dest = fullNameMap.get(translate.getValue());
        String text = originalText.getText();

        Thread fetchData = new Thread(new GoogleTranslateHandler(src, dest, text));
        fetchData.setDaemon(true);
        fetchData.start();
    }

    private void handleSwap() {
        String temp = original.getValue();
        original.setValue(translate.getValue());
        translate.setValue(temp);

        temp = originalText.getText();
        originalText.setText(translateText.getText());
        translateText.setText(temp);
    }

    private void addLanguages() {
        fullNameMap = new TreeMap<>();
        fullNameMap.put("Afrikaans", Translator.LanguageCode.AF);
        fullNameMap.put("Albanian", Translator.LanguageCode.SQ);
        fullNameMap.put("Amharic", Translator.LanguageCode.AM);
        fullNameMap.put("Arabic", Translator.LanguageCode.AR);
        fullNameMap.put("Armenian", Translator.LanguageCode.HY);
        fullNameMap.put("Azerbaijani", Translator.LanguageCode.AZ);
        fullNameMap.put("Basque", Translator.LanguageCode.EU);
        fullNameMap.put("Belarusian", Translator.LanguageCode.BE);
        fullNameMap.put("Bengali", Translator.LanguageCode.BN);
        fullNameMap.put("Bosnian", Translator.LanguageCode.BS);
        fullNameMap.put("Bulgarian", Translator.LanguageCode.BG);
        fullNameMap.put("Catalan", Translator.LanguageCode.CA);
        fullNameMap.put("Cebuano", Translator.LanguageCode.CEB);
        fullNameMap.put("Chinese (Simplified)", Translator.LanguageCode.ZH);
        fullNameMap.put("Corsican", Translator.LanguageCode.CO);
        fullNameMap.put("Croatian", Translator.LanguageCode.HR);
        fullNameMap.put("Czech", Translator.LanguageCode.CS);
        fullNameMap.put("Danish", Translator.LanguageCode.DA);
        fullNameMap.put("Dutch", Translator.LanguageCode.NL);
        fullNameMap.put("English", Translator.LanguageCode.EN);
        fullNameMap.put("Esperanto", Translator.LanguageCode.EO);
        fullNameMap.put("Estonian", Translator.LanguageCode.ET);
        fullNameMap.put("Finnish", Translator.LanguageCode.FI);
        fullNameMap.put("French", Translator.LanguageCode.FR);
        fullNameMap.put("Frisian", Translator.LanguageCode.FY);
        fullNameMap.put("Galician", Translator.LanguageCode.GL);
        fullNameMap.put("Georgian", Translator.LanguageCode.KA);
        fullNameMap.put("German", Translator.LanguageCode.DE);
        fullNameMap.put("Greek", Translator.LanguageCode.EL);
        fullNameMap.put("Gujarati", Translator.LanguageCode.GU);
        fullNameMap.put("Haitian Creole", Translator.LanguageCode.HT);
        fullNameMap.put("Hausa", Translator.LanguageCode.HA);
        fullNameMap.put("Hindi", Translator.LanguageCode.HI);
        fullNameMap.put("Hungarian", Translator.LanguageCode.HU);
        fullNameMap.put("Icelandic", Translator.LanguageCode.IS);
        fullNameMap.put("Igbo", Translator.LanguageCode.IG);
        fullNameMap.put("Indonesian", Translator.LanguageCode.ID);
        fullNameMap.put("Irish", Translator.LanguageCode.GA);
        fullNameMap.put("Italian", Translator.LanguageCode.IT);
        fullNameMap.put("Japanese", Translator.LanguageCode.JA);
        fullNameMap.put("Javanese", Translator.LanguageCode.JV);
        fullNameMap.put("Kannada", Translator.LanguageCode.KN);
        fullNameMap.put("Kazakh", Translator.LanguageCode.KK);
        fullNameMap.put("Khmer", Translator.LanguageCode.KM);
        fullNameMap.put("Kinyarwanda", Translator.LanguageCode.RW);
        fullNameMap.put("Korean", Translator.LanguageCode.KO);
        fullNameMap.put("Kurdish", Translator.LanguageCode.KU);
        fullNameMap.put("Kyrgyz", Translator.LanguageCode.KY);
        fullNameMap.put("Lao", Translator.LanguageCode.LO);
        fullNameMap.put("Latin", Translator.LanguageCode.LA);
        fullNameMap.put("Latvian", Translator.LanguageCode.LV);
        fullNameMap.put("Lithuanian", Translator.LanguageCode.LT);
        fullNameMap.put("Luxembourgish", Translator.LanguageCode.LB);
        fullNameMap.put("Macedonian", Translator.LanguageCode.MK);
        fullNameMap.put("Malagasy", Translator.LanguageCode.MG);
        fullNameMap.put("Malay", Translator.LanguageCode.MS);
        fullNameMap.put("Malayalam", Translator.LanguageCode.ML);
        fullNameMap.put("Maltese", Translator.LanguageCode.MT);
        fullNameMap.put("Maori", Translator.LanguageCode.MI);
        fullNameMap.put("Marathi", Translator.LanguageCode.MR);
        fullNameMap.put("Mongolian", Translator.LanguageCode.MN);
        fullNameMap.put("Myanmar (Burmese)", Translator.LanguageCode.MY);
        fullNameMap.put("Nepali", Translator.LanguageCode.NE);
        fullNameMap.put("Norwegian", Translator.LanguageCode.NO);
        fullNameMap.put("Nyanja (Chichewa)", Translator.LanguageCode.NY);
        fullNameMap.put("Odia (Oriya)", Translator.LanguageCode.OR);
        fullNameMap.put("Pashto", Translator.LanguageCode.PS);
        fullNameMap.put("Persian", Translator.LanguageCode.FA);
        fullNameMap.put("Polish", Translator.LanguageCode.PL);
        fullNameMap.put("Portuguese (Portugal, Brazil)", Translator.LanguageCode.PT);
        fullNameMap.put("Punjabi", Translator.LanguageCode.PA);
        fullNameMap.put("Romanian", Translator.LanguageCode.RO);
        fullNameMap.put("Russian", Translator.LanguageCode.RU);
        fullNameMap.put("Samoan", Translator.LanguageCode.SM);
        fullNameMap.put("Scots Gaelic", Translator.LanguageCode.GD);
        fullNameMap.put("Serbian", Translator.LanguageCode.SR);
        fullNameMap.put("Sesotho", Translator.LanguageCode.ST);
        fullNameMap.put("Shona", Translator.LanguageCode.SN);
        fullNameMap.put("Sindhi", Translator.LanguageCode.SD);
        fullNameMap.put("Sinhala (Sinhalese)", Translator.LanguageCode.SI);
        fullNameMap.put("Slovak", Translator.LanguageCode.SK);
        fullNameMap.put("Slovenian", Translator.LanguageCode.SL);
        fullNameMap.put("Somali", Translator.LanguageCode.SO);
        fullNameMap.put("Spanish", Translator.LanguageCode.ES);
        fullNameMap.put("Sundanese", Translator.LanguageCode.SU);
        fullNameMap.put("Swahili", Translator.LanguageCode.SW);
        fullNameMap.put("Swedish", Translator.LanguageCode.SV);
        fullNameMap.put("Tagalog (Filipino)", Translator.LanguageCode.TL);
        fullNameMap.put("Tajik", Translator.LanguageCode.TG);
        fullNameMap.put("Tamil", Translator.LanguageCode.TA);
        fullNameMap.put("Tatar", Translator.LanguageCode.TT);
        fullNameMap.put("Telugu", Translator.LanguageCode.TE);
        fullNameMap.put("Thai", Translator.LanguageCode.TH);
        fullNameMap.put("Turkish", Translator.LanguageCode.TR);
        fullNameMap.put("Turkmen", Translator.LanguageCode.TK);
        fullNameMap.put("Ukrainian", Translator.LanguageCode.UK);
        fullNameMap.put("Urdu", Translator.LanguageCode.UR);
        fullNameMap.put("Uyghur", Translator.LanguageCode.UG);
        fullNameMap.put("Uzbek", Translator.LanguageCode.UZ);
        fullNameMap.put("Vietnamese", Translator.LanguageCode.VI);
        fullNameMap.put("Welsh", Translator.LanguageCode.CY);
        fullNameMap.put("Xhosa", Translator.LanguageCode.XH);
        fullNameMap.put("Yiddish", Translator.LanguageCode.YI);
        fullNameMap.put("Yoruba", Translator.LanguageCode.YO);
        fullNameMap.put("Zulu", Translator.LanguageCode.ZU);
    }

    private class GoogleTranslateHandler implements Runnable {
        private Translator.LanguageCode src;
        private Translator.LanguageCode dest;
        private String text;

        private GoogleTranslateHandler(Translator.LanguageCode src,
                                       Translator.LanguageCode dest,
                                       String text) {
            this.src = src;
            this.dest = dest;
            this.text = text;
        }

        @Override
        public void run() {
            Translator.TranslatedData data = translator.translate(src, dest, text);
            Platform.runLater(() -> translateText.setText(data.getTranslated()));
        }
    }
}
