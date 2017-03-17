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
import com.squareup.picasso.Picasso;

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
        RecommendFilterList = data;
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

        String url = RecommendFilterList.get(position).getImage();
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.loading)
                .fit()
                .centerCrop().into(viewHolder.imageView);
        viewHolder.tvName.setText(item.getLocationName());
        viewHolder.tvDescript.setText(String.valueOf(item.getAddress()));

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
            FilterResults results = new FilterResults();

            if(constraint.toString().equals("0,0")) {
                results.count = RecommendFilterList.size();
                results.values = RecommendFilterList;
                setDataFromSharedPreferences((ArrayList<Recommend>) RecommendFilterList);
            }
            else {
                ArrayList<Recommend> filterList = new ArrayList<Recommend>();
                String[] search = constraint.toString().split(",");
                int h = Integer.parseInt(search[0]);
                int p = Integer.parseInt(search[1]);

                if( h != 0 && p!= 0) {
                    for (Recommend r: RecommendFilterList) {
                        if(r.getHobbyID() == h && r.getPriceID() == p) {
                            filterList.add(r);
                        }
                    }
                }
                if(h == 0) {
                    for (Recommend r : RecommendFilterList) {
                        if (r.getPriceID() == p) {
                            filterList.add(r);
                        }
                    }
                }
                if(p == 0) {
                    for (Recommend r: RecommendFilterList) {
                        if(r.getHobbyID() == h) {
                            filterList.add(r);
                        }
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
                setDataFromSharedPreferences(filterList);
            }
            return results;
        }
        //Publishes the matches found, i.e., the selected hobIds
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

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
