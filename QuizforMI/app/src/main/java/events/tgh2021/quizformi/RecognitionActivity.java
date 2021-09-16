package events.tgh2021.quizformi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
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

        Button bTranslate = findViewById(R.id.button4);
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
                                            TextView targetText = findViewById(R.id.textView3);
                                            String text = targetText.getText().toString();
                                            englishJapaneseTranslator.translate(text)
                                                    .addOnSuccessListener(
                                                            (OnSuccessListener) translatedText -> {
                                                                // Translation successful.
                                                                TextView result = findViewById(R.id.textView2);
                                                                result.setText((CharSequence) translatedText);
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