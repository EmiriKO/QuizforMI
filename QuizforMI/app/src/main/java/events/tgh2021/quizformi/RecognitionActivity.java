package events.tgh2021.quizformi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.util.ArrayList;

public class RecognitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognition);

        //spinnerの設定
        String[] arraySpinner = new String[] {
                "I", "ate", "an", "apple", "yesterday", "at", "home"
        };
        Spinner s = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        TextView wordSelected = findViewById(R.id.text_selected);
        TextView result = findViewById(R.id.text_translated);

        // 保存ボタン
        Button bSave = (Button) findViewById(R.id.button_save);
        bSave.setEnabled(false);

        Button bTranslate = findViewById(R.id.button_translate);
        bTranslate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Create an English-Japanese translator:
                        TranslatorOptions options =
                                new TranslatorOptions.Builder()
                                        .setSourceLanguage(TranslateLanguage.ENGLISH)
                                        .setTargetLanguage(TranslateLanguage.JAPANESE)
                                        .build();
                        final Translator englishJapaneseTranslator = Translation.getClient(options);

                        DownloadConditions conditions = new DownloadConditions.Builder()
                                .requireWifi()
                                .build();
                        englishJapaneseTranslator.downloadModelIfNeeded(conditions)
                                .addOnSuccessListener(
                                        (OnSuccessListener) v -> {
                                            // Model downloaded successfully. Okay to start translating.
                                            // (Set a flag, unhide the translation UI, etc.)
                                            Spinner spinner = (Spinner)findViewById(R.id.spinner);
                                            String text = spinner.getSelectedItem().toString();
                                            englishJapaneseTranslator.translate(text)
                                                    .addOnSuccessListener(
                                                            (OnSuccessListener) translatedText -> {
                                                                // Translation successful.
                                                                wordSelected.setText((CharSequence) text);
                                                                result.setText((CharSequence) translatedText);
                                                                //保存ボタンの設定
                                                                bSave.setEnabled(true);
                                                                bSave.setOnClickListener(
                                                                        new View.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(View view) {
                                                                                PreferenceUtils.save(getApplicationContext(), text, String.valueOf(translatedText));
                                                                                //PreferenceUtils.load(getApplicationContext());
                                                                                wordSelected.setText("");
                                                                                result.setText("");
                                                                                bSave.setEnabled(false);
                                                                            }
                                                                        }
                                                                );
                                                            })
                                                    .addOnFailureListener(
                                                            e -> {
                                                                // Error.
                                                                // ...
                                                            });
                                        })
                                .addOnFailureListener(
                                        e -> {
                                            // Model couldn’t be downloaded or other internal error.
                                            // ...
                                        });
                    }
                }
        );


    }
}