package events.tgh2021.quizformi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FlashcardActivity extends AppCompatActivity {

    private static final String[] texts = {
            "abc", "bcd", "cde"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);

        // 書き込み
        getPreferences(Context.MODE_PRIVATE).edit().putString("keyword", "testKeyword").apply();

        ListView listView = new ListView(this);
        setContentView(listView);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,texts);

        listView.setAdapter(arrayAdapter);
        
    }
}