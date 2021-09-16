package events.tgh2021.quizformi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

public class RecognitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognition);

        // 保存ボタンの無効化
        Button bSave = (Button) findViewById(R.id.button_save);
        bSave.setEnabled(false);
        bSave.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                }
        );

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
                                            TextView targetText = findViewById(R.id.text_target);
                                            String text = targetText.getText().toString();
                                            englishJapaneseTranslator.translate(text)
                                                    .addOnSuccessListener(
                                                            (OnSuccessListener) translatedText -> {
                                                                // Translation successful.
                                                                TextView result = findViewById(R.id.text_translated);
                                                                result.setText((CharSequence) translatedText);
                                                                bSave.setEnabled(true);
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