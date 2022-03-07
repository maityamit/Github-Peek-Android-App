package githubpeekbyamit.example.githubpeek;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RepoListActivity extends AppCompatActivity {


    EditText edtSearch;
    ListView listView;
    SimpleArcLoader simpleArcLoader;


    RepoModel countryModel;
    MyCustomAdapter myCustomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_list);

        Intent intent = getIntent();
        String string_link = intent.getStringExtra("REPOTYPE");


        listView = findViewById(R.id.listView_);
        edtSearch = findViewById(R.id.edtSearch_repo);
        simpleArcLoader = findViewById(R.id.loader_two);

        fetchData(string_link);



        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                myCustomAdapter.getFilter().filter(s);
                myCustomAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }




@Override
public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if(item.getItemId()==android.R.id.home)
        finish();
    return super.onOptionsItemSelected(item);
}

    private void fetchData(String string_link) {


        simpleArcLoader.start();
       List<RepoModel> countryModelsList = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.GET, string_link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for(int i=0;i<jsonArray.length();i++){

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String repo_name = jsonObject.getString("name");
                                String repo_star = jsonObject.getString("stargazers_count");
                                String repo_forks = jsonObject.getString("forks_count");
                                String repo_language = jsonObject.getString("language");
                                String repo_link = jsonObject.getString("html_url");


//                                JSONObject object = jsonObject.getJSONObject("countryInfo");
//                                String flagUrl = object.getString("flag");

                                countryModel = new RepoModel(repo_name,repo_star,repo_forks,repo_language,repo_link);
                                countryModelsList.add(countryModel);


                            }

                            myCustomAdapter = new MyCustomAdapter(RepoListActivity.this,countryModelsList);
                            listView.setAdapter(myCustomAdapter);
                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);






                        } catch (JSONException e) {
                            e.printStackTrace();
                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                Toast.makeText(RepoListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);


    }


}



