package githubpeekbyamit.example.githubpeek;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    // creating a variable for array list and context.
    private ArrayList<Fav> courseModalArrayList;
    private Context context;

    // creating a constructor for our variables.
    public NotesAdapter(ArrayList<Fav> courseModalArrayList, Context context) {
        this.courseModalArrayList = courseModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {
        // setting data to our views of recycler view.
        Fav modal = courseModalArrayList.get(position);
        holder.courseNameTV.setText(modal.getName());
        holder.courseDescTV.setText(modal.getId());
        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,UserActivity.class);
                intent.putExtra("USER_ID",modal.getId());
                context.startActivity(intent);
            }
        });

        holder.delte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavActivity.courseModalArrayList.remove(position);
                notifyItemRemoved(position);



                SharedPreferences preferences = context.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor mEditor = preferences.edit();
                Gson gson = new Gson();

                    String jsonString = gson.toJson(courseModalArrayList);
                    mEditor.putString("courses", jsonString);
                    mEditor.apply();
                }



        });

    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return courseModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our views.
        private TextView courseNameTV, courseDescTV,click;
        private ImageView delte;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initializing our views with their ids.
            courseNameTV = itemView.findViewById(R.id.fav_user_name);
            courseDescTV = itemView.findViewById(R.id.fav_user_id);
            click = itemView.findViewById(R.id.fav_user_click);
            delte = itemView.findViewById(R.id.fav_user_delete);
        }
    }
}