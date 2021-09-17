package events.tgh2021.quizformi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.graphics.Point;
import android.graphics.Rect;
import androidx.annotation.NonNull;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;





public class MainActivity extends AppCompatActivity {
    //追加
    private static final int REQUEST_GALLERY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView1 = findViewById(R.id.logo1);
        imageView1.setImageResource(R.drawable.pictionary_);
        ImageView imageView2 = findViewById(R.id.logo2);
        imageView2.setImageResource(R.drawable.pictionary_for_mi_logo);


        Button bRecognition = findViewById(R.id.button_recognition);
        bRecognition.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //追加
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, REQUEST_GALLERY);

                        //Intent intentMaintoRecognition = new Intent(MainActivity.this, RecognitionActivity.class);
                        //startActivity(intentMaintoRecognition);
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




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
            try {
                BufferedInputStream inputStream = new BufferedInputStream(getContentResolver().openInputStream(data.getData()));
                Bitmap image = BitmapFactory.decodeStream(inputStream);
                TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

                InputImage recognizingImage = InputImage.fromBitmap(image, 0);

                recognizer.process(recognizingImage)
                        .addOnSuccessListener(visionText -> {
                            System.out.println("onSuccess");

                            List<String> listForElements = new ArrayList<>();
                            for (Text.TextBlock block : visionText.getTextBlocks()) {
                                for (Text.Line line : block.getLines()) {
                                    for (Text.Element element : line.getElements()) {
                                        listForElements.add(element.getText());
                                    }
                                }
                            }
                            Intent intentMaintoRecognition = new Intent(MainActivity.this, RecognitionActivity.class);
                            intentMaintoRecognition.putExtra("listForElements", (ArrayList)listForElements);
                            startActivity(intentMaintoRecognition);
                        })
                        .addOnFailureListener(e -> {
                            System.out.println("onFailure");
                        });

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
