package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.RecommendImage;

/**
 * Created by ASUS on 3/22/2017.
 */

public class RecommendImageAdapter extends RecyclerView.Adapter<Image_Holder> {

    Context c;
    List<RecommendImage> recommendImageList = new ArrayList<>();

    public RecommendImageAdapter(Context c, List<RecommendImage> recommendImageList) {
        this.c = c;
        this.recommendImageList = recommendImageList;
    }

    @Override
    public Image_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_recommend_image_holder, parent, false);
        Image_Holder image_holder = new Image_Holder(v);
        return image_holder;
    }

    @Override
    public void onBindViewHolder(Image_Holder image_holder, int position) {
        final RecommendImage recommend = recommendImageList.get(position);
        Picasso.with(c)
                .load(recommend.getLink())
                .placeholder(R.drawable.loading)
                .into(image_holder.img);
    }

    @Override
    public int getItemCount() {
        return recommendImageList.size();
    }
}
