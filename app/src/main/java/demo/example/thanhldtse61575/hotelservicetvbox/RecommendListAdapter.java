package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.Recommend;

/**
 * Created by ASUS on 3/21/2017.
 */

public class RecommendListAdapter extends RecyclerView.Adapter<Recommend_Holder> {

    Context c;
    List<Recommend> recommendList = new ArrayList<>();

    public RecommendListAdapter(Context c, List<Recommend> recommendList) {
        this.c = c;
        this.recommendList = recommendList;
    }


    @Override
    public Recommend_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_recommend_holder, parent, false);
        Recommend_Holder recommend_holder = new Recommend_Holder(v);
        return recommend_holder;
    }

    @Override
    public void onBindViewHolder(Recommend_Holder recommend_holder, final int position) {
        final Recommend recommend = recommendList.get(position);
        Picasso.with(c)
                .load(recommend.getImage())
                .placeholder(R.drawable.loading)
                .fit()
                .centerCrop().into(recommend_holder.img);
        recommend_holder.txt.setText(recommend.getLocationName()+"");

        recommend_holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {

                setRecommend2Share(position);
                c.startActivity(new Intent(c, Recommend_New_Activity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return recommendList.size();
    }

    private static final String SHARE_RE = "SharePosition";
    private static final String RE_TAG = "PositionName";

    private void setRecommend2Share(int  Position){
        SharedPreferences sharedPref = c.getSharedPreferences(SHARE_RE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(RE_TAG, Position+"");
        editor.commit();
    }
}
