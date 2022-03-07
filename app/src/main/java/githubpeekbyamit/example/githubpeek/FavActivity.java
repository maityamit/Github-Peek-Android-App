package githubpeekbyamit.example.githubpeek;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FavActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    // variable for our adapter class and array list
    static NotesAdapter adapter;
    static ArrayList<Fav> courseModalArrayList;
    ImageView nothing;

    static SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        recyclerView = findViewById(R.id.fav_list_view);
        nothing = findViewById(R.id.nothin_fav);



        sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);


        Gson gson = new Gson();

        String json = sharedPreferences.getString("courses", null);

        Type type = new TypeToken<ArrayList<Fav>>() {}.getType();


        courseModalArrayList = gson.fromJson(json, type);


        if (courseModalArrayList == null) {
            courseModalArrayList = new ArrayList<>();
        }



        adapter = new NotesAdapter(courseModalArrayList, FavActivity.this);


        if (adapter.getItemCount()!=0){
            nothing.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }


        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);


        recyclerView.setLayoutManager(manager);


        recyclerView.setAdapter(adapter);


    }


    public void FAVBACK(View view) {

        Intent intent = new Intent(FavActivity.this,MainActivity.class);
        intent.addFlags ( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity(intent);
        finish();

    }
}
