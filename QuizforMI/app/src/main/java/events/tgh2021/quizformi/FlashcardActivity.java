package events.tgh2021.quizformi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

        String[] keywords =keywordsList.split(",");

        Map<String, String> englishList = new HashMap() {
        };

        for (String keyword : keywords) {
            if(keyword.isEmpty()){
                continue;
            };
            englishList.put(keyword, dataStore.getString(keyword, ""));
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

        Map<String,String> word = PreferenceUtils.load(this);

        List testData = Arrays.asList(new Pair("keyword1", "translated1"), new Pair("keyword2", "translated2"));

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        WordListAdapter adapter = new WordListAdapter();
        recyclerView.setAdapter(adapter);
        adapter.submitList(testData);
    }
}

class WordListAdapter extends ListAdapter<Pair<String, String>, WordListAdapter.ViewHolder> {
    public WordListAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.word_view_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView keywordTextView;
        private final TextView translatedTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            keywordTextView = itemView.findViewById(R.id.keyword_word);
            translatedTextView = itemView.findViewById(R.id.translated_word);
        }

        public void bind(Pair<String, String> wordPair) {
            keywordTextView.setText(wordPair.first);
            translatedTextView.setText(wordPair.second);
        }
    }

    public static final DiffUtil.ItemCallback<Pair<String, String>> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Pair<String, String>>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Pair<String, String> oldWord, @NonNull Pair<String, String> newWord) {
                    return oldWord.first.equals(newWord.first);
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull Pair<String, String> oldWord, @NonNull Pair<String, String> newWord) {
                    return oldWord.equals(newWord);
                }
            };
}
