package events.tgh2021.quizformi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

class PreferenceUtils {

    public static void save(Context context, String keyword, String translated) {
        //EnglishDataの中に新しい単語を保存する
        SharedPreferences dataStore = context.getSharedPreferences("MIZ", Context.MODE_PRIVATE);
        String keywordsList = dataStore.getString("keywords", "");//これまでのkeywordsデータを取得
        SharedPreferences.Editor editor = dataStore.edit();
        editor.putString(keyword, translated);
        editor.putString("keywords", keywordsList + "," + keyword);
        editor.apply();
    }

    ;

    public static Map<String, String> load(Context context) {
        //表示するリストの取得
        SharedPreferences dataStore = context.getSharedPreferences("MIZ", Context.MODE_PRIVATE);
        String keywordsList = dataStore.getString("keywords", "");//これまでのkeywordsデータを取得

        String[] keywords = (String[]) Arrays.stream(keywordsList.split(",")).filter(str -> !str.isEmpty()).toArray();

        Map<String, String> englishList = new HashMap() {
        };

        for (int i = 0; i < keywords.length; i++) {
            englishList.put(keywords[i], dataStore.getString(keywords[i], ""));
        }
        ;
        return englishList;
    }

};

public class FlashcardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);

        getSharedPreferences("MIZ",MODE_PRIVATE).edit().clear().apply();

        PreferenceUtils.save(this, "apple", "りんご");
        Map<String,String> word = PreferenceUtils.load(this);


        ListView listView = new ListView(this);
        setContentView(listView);

        /*
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, );

        listView.setAdapter(arrayAdapter);
        */


    }

}



