package events.tgh2020.androidlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = findViewById(R.id.theButton);
        b.setOnClickListener(
            new View.OnClickListener(){
                @Override
                public void onClick(View view){

                    EditText etName = findViewById(R.id.etName);
                    String name = etName.getText().toString();

                    EditText etBirthday = findViewById(R.id.etBirthday);
                    String birthday = etBirthday.getText().toString();

                    Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                    intent.putExtra("NAME", name);
                    intent.putExtra("BIRTHDAY", birthday);
                    startActivity(intent);

                }

            }
        );
    }
}