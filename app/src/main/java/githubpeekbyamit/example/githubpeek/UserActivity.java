package githubpeekbyamit.example.githubpeek;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.text.TextRunShaper;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.leo.simplearcloader.SimpleArcLoader;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashSet;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserActivity extends AppCompatActivity {

    private TextView username,userid,userlocation,followers,following,repo,bio;
    private CircleImageView circleImageView;
    LinearLayout linearLayout,linearLayout2;
    ImageView star;
    String string;
    String user_name;
    SimpleArcLoader simpleArcLoader;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);






        Intent intent = getIntent();
        string = intent.getStringExtra("USER_ID");

        username = findViewById(R.id.User_Name);
        userid = findViewById(R.id.User_Id);
        userlocation = findViewById(R.id.User_Location);
        followers = findViewById(R.id.User_Followers);
        star = findViewById(R.id.star_image);
        simpleArcLoader = findViewById(R.id.simple_arc_loader);
        linearLayout = findViewById(R.id.lin_lay1);
        cardView = findViewById(R.id.lin_lay2);
        linearLayout2=findViewById(R.id.lin_lay3);
        following = findViewById(R.id.User_Following);
        repo = findViewById(R.id.User_PRCount);
        bio = findViewById(R.id.User_Bio);
        circleImageView = findViewById(R.id.User_Image);

        CallAPI(string);



        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialAlertDialogBuilder(UserActivity.this).setBackground(getResources().getDrawable(R.drawable.material_dialog_box)).setTitle("Choose what you want..").setItems(new String[]{"All the Repo", "User's Own Repo"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {

                            case 0:
                               Intent intent11 = new Intent(UserActivity.this,RepoListActivity.class);
                               intent11.putExtra("REPOTYPE","https://api.github.com/users/"+string+"/repos?type=owner");
                               startActivity(intent11);
                               break;


                            case 1:
                                Intent intent12 = new Intent(UserActivity.this,RepoListActivity.class);
                                intent12.putExtra("REPOTYPE","https://api.github.com/users/"+string+"/repos");
                                startActivity(intent12);
                                break;
                        }
                    }
                }).show();
            }
        });


    }

    private void CallAPI(String string) {




        String url = "https://api.github.com/users/"+string;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Toast.makeText(UserActivity.this, "Some eror !", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if (response.isSuccessful()){
                    String resp = response.body().string();
                    UserActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                JSONObject jsonObject = new JSONObject(resp);
                                String string_user_name =  jsonObject.getString("name");
                                String string_user_id = jsonObject.getString("login");
                                String string_followers =  jsonObject.getString("followers");
                                String string_following = jsonObject.getString("following");
                                String string_location =  jsonObject.getString("location");
                                String string_bio = jsonObject.getString("bio");
                                String string_repo = jsonObject.getString("public_repos");
                                String string_avatar = jsonObject.getString("avatar_url");

                                simpleArcLoader.setVisibility(View.GONE);
                                user_name = string_user_name;

                                linearLayout.setVisibility(View.VISIBLE);
                                cardView.setVisibility(View.VISIBLE);

                                userid.setText(string_user_id);
                                username.setText(string_user_name);
                                userlocation.setText(string_location);
                                followers.setText(string_followers);
                                following.setText(string_following);
                                bio.setText(string_bio);
                                repo.setText(string_repo);
                                Picasso.get().load(string_avatar).placeholder(R.drawable.ic_icons8_github_1).error(R.drawable.ic_icons8_github_1).into(circleImageView);





                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

            }
        });


    }

    private void showDialog() {





        // below line is use to add data to array list.
        FavActivity.courseModalArrayList.add(new Fav(user_name, string));
        // notifying adapter when new data added.
        FavActivity.adapter.notifyItemInserted(FavActivity.courseModalArrayList.size());


        // method for saving the data in array list.
        // creating a variable for storing data in
        // shared preferences.
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        // creating a variable for editor to
        // store data in shared preferences.
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // creating a new variable for gson.
        Gson gson = new Gson();

        // getting data from gson and storing it in a string.
        String json = gson.toJson(FavActivity.courseModalArrayList);

        // below line is to save data in shared
        // prefs in the form of string.
        editor.putString("courses", json);

        // below line is to apply changes
        // and save data in shared prefs.
        editor.apply();








        Dialog dialog;
        //Create the Dialog here
        dialog = new Dialog(UserActivity.this);
        dialog.setContentView(R.layout.dialog_fragment);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(UserActivity.this.getDrawable(R.drawable.custom_dialog_background));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog

        Button Okay = dialog.findViewById(R.id.btn_okay);

        Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();






    }

    public void USERBack(View view) {
        Intent intent = new Intent(UserActivity.this,MainActivity.class);
        intent.addFlags ( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity(intent);
        finish();
    }
}