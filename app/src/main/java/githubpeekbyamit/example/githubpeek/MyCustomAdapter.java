package githubpeekbyamit.example.githubpeek;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MyCustomAdapter extends ArrayAdapter<RepoModel> {

    private Context context;
    private List<RepoModel> countryModelsList;
    private List<RepoModel> countryModelsListFiltered;

    public MyCustomAdapter( Context context, List<RepoModel> countryModelsList) {
        super(context, R.layout.layout_repo_items,countryModelsList);

        this.context = context;
        this.countryModelsList = countryModelsList;
        this.countryModelsListFiltered = countryModelsList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_repo_items,null,true);
        TextView name = view.findViewById(R.id.list_repo_name);
        TextView star = view.findViewById(R.id.list_repo_star);
        TextView forks = view.findViewById(R.id.list_repo_forks);
        TextView language = view.findViewById(R.id.language_used);
        LinearLayout linearLayout = view.findViewById(R.id.click_lick_lay);


        name.setText(countryModelsListFiltered.get(position).getName());
        star.setText(countryModelsListFiltered.get(position).getStargazers_count());
        forks.setText(countryModelsListFiltered.get(position).getForks_count());
        language.setText(countryModelsListFiltered.get(position).getLanguage());
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(countryModelsList.get(position).getHtml_url()));
                context.startActivity(browserIntent);
            }
        });





        return view;
    }

    @Override
    public int getCount() {
        return countryModelsListFiltered.size();
    }

    @Nullable
    @Override
    public RepoModel getItem(int position) {
        return countryModelsListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = countryModelsList.size();
                    filterResults.values = countryModelsList;

                }else{
                    List<RepoModel> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(RepoModel itemsModel:countryModelsList){
                        if(itemsModel.getName().toLowerCase().contains(searchStr)){
                            resultsModel.add(itemsModel);

                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }


                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                countryModelsListFiltered = (List<RepoModel>) results.values;
                countryModelsList = (List<RepoModel>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }
}
