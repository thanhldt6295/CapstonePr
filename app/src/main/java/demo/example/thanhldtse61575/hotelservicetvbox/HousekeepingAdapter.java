package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by VULHSE61532 on 1/12/2017.
 */

public class HousekeepingAdapter extends RecyclerView.Adapter<MainHolder>  {
    Context c;
    String[] titles;
    int[] images;

    public HousekeepingAdapter(Context c, String[] titles, int[] images) {
        this.c = c;
        this.titles = titles;
        this.images = images;
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_holder, parent, false);
        MainHolder holder = new MainHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MainHolder holder, int position) {
        holder.img.setImageResource(images[position]);
        holder.txt.setText(titles[position]);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                switch (pos){
                    case 0:
                        c.startActivity(new Intent(c, FoodsBeveragesActivity.class));
                        break;
                    case 1:
                        c.startActivity(new Intent(c, HousekeepingActivity.class));
                        break;
                    case 2:
                        c.startActivity(new Intent(c, MaintenanceActivity.class));
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
