package events.tgh2021.quizformi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bRecognition = findViewById(R.id.button_recognition);
        bRecognition.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentMaintoRecognition = new Intent(MainActivity.this, RecognitionActivity.class);
                        startActivity(intentMaintoRecognition);
                    }
                }
        );

        Button bFlashcard = findViewById(R.id.button_flashcard);
        bFlashcard.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentMaintoFlashcard = new Intent(MainActivity.this, FlashcardActivity.class);
                        startActivity(intentMaintoFlashcard);
                    }
                }
        );
    }
}