package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.Recommend;

/**
 * Created by ThanhLDTSE61575 on 2/24/2017.
 */

public class RecommendAdapter extends BaseAdapter implements Filterable {

    private LayoutInflater mLayoutInflater;
    private List<Recommend> RecommendList;
    private List<Recommend> RecommendFilterList;
    private RecommendFilter RecommendFilter;
    private Context context;

    public RecommendAdapter(Context context, List data){
        RecommendList = data;
        RecommendFilterList=data;
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return RecommendList.size();
    }

    @Override
    public Recommend getItem(int position) {
        return RecommendList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        View updateView;
        ViewHolder viewHolder;
        if (view == null) {
            updateView = mLayoutInflater.inflate(R.layout.layout_recommenditem, null);
            viewHolder = new ViewHolder();

            viewHolder.imageView = (ImageView) updateView.findViewById(R.id.imageView);
            viewHolder.tvName = (TextView) updateView.findViewById(R.id.recNameTV);
            viewHolder.tvDescript = (TextView) updateView.findViewById(R.id.recDescriptTV);

            updateView.setTag(viewHolder);

        } else {
            updateView = view;
            viewHolder = (ViewHolder) updateView.getTag();
        }

        final Recommend item = getItem(position);

        viewHolder.imageView.setImageResource(item.getImage());
        viewHolder.tvName.setText(item.getName());
        viewHolder.tvDescript.setText(String.valueOf(item.getDescription()));

        return updateView;
    }

    @Override
    public Filter getFilter() {
        if (RecommendFilter == null) {
            RecommendFilter = new RecommendFilter();
        }
        return RecommendFilter;
    }

    static class ViewHolder{
        ImageView imageView;
        TextView tvName;
        TextView tvDescript;
    }

// InnerClass for enabling Search feature by implementing the methods

    private class RecommendFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

//below checks the match for the hobId and adds to the filterlist
            long hobId= Long.parseLong(constraint.toString());
            FilterResults results = new FilterResults();

            if (hobId > 0) {
                ArrayList<Recommend> filterList = new ArrayList<Recommend>();
                for (int i = 0; i < RecommendFilterList.size(); i++) {

                    if ( (RecommendFilterList.get(i).getHobbyID() )== hobId) {

                        Recommend Recommend = RecommendFilterList.get(i);
                        filterList.add(Recommend);
                    }
                }

                results.count = filterList.size();
                results.values = filterList;
                setDataFromSharedPreferences(filterList);

            } else {

                results.count = RecommendFilterList.size();
                results.values = RecommendFilterList;
                setDataFromSharedPreferences((ArrayList<Recommend>) RecommendFilterList);

            }
            return results;
        }
        //Publishes the matches found, i.e., the selected hobIds
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            RecommendList = (ArrayList<Recommend>)results.values;
            notifyDataSetChanged();
        }
    }

    private static final String PREFS_TAG = "SharedPrefs";
    private static final String PRODUCT_TAG = "MyProduct";

    private void setDataFromSharedPreferences(ArrayList<Recommend> curProduct){
        Gson gson = new Gson();
        String jsonCurProduct = gson.toJson(curProduct);

        SharedPreferences sharedPref = context.getSharedPreferences(PREFS_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(PRODUCT_TAG, jsonCurProduct);
        editor.commit();
    }
}
