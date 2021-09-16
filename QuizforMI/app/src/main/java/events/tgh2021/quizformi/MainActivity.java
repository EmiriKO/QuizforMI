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

        Button bKeyword = findViewById(R.id.button);
        bKeyword.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentMaintoKeyword = new Intent(MainActivity.this, KeywordActivity.class);
                        startActivity(intentMaintoKeyword);
                    }
                }
        );

        Button bRecommendation = findViewById(R.id.button2);
        bRecommendation.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentMaintoRecommendation = new Intent(MainActivity.this, RecommendationActivity.class);
                        startActivity(intentMaintoRecommendation);
                    }
                }
        );
    }
}