package githubpeekbyamit.example.githubpeek;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {


    ImageView button,liked;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.getdetails_1);
        editText = findViewById(R.id.users_name);
        liked = findViewById(R.id.liked_folder);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = editText.getText().toString();
                if (!TextUtils.isEmpty(string)){
                    Intent intent = new Intent(MainActivity.this,UserActivity.class);
                    intent.putExtra("USER_ID",string);
                    startActivity(intent);
                }

            }
        });

        liked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this,FavActivity.class);
                startActivity(intent1);
            }
        });
    }

    public void LINKEDINCLICK(View view) {

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.linkedin.com/in/maityamit"));
        startActivity(browserIntent);

    }


    public void GITHUBCLICK(View view) {

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.github.com/maityamit"));
        startActivity(browserIntent);

    }
}