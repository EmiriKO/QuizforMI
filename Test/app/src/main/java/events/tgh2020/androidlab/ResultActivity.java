package events.tgh2020.androidlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String name = intent.getStringExtra("NAME");
        String birthday = intent.getStringExtra("BIRTHDAY");

        FortuneTeller f = new FortuneTeller();
        String fortune = f.tell(birthday);

        TextView result = findViewById(R.id.tvResult);
        result.setText(name + "さんの運勢は" + fortune + "です");

        ImageView imageview = findViewById(R.id.imageView2);
        switch (fortune) {
            case "大吉":
                imageview.setImageResource(R.drawable.superluck);
                break;
            case "吉":
                imageview.setImageResource(R.drawable.goodluck);
                break;
            case "凶":
                imageview.setImageResource(R.drawable.badluck);
                break;
            default:
                break;
        }

        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}