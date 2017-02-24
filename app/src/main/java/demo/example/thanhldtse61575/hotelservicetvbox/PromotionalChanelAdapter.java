package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by ThanhLDTSE61575 on 1/11/2017.
 */
public class PromotionalChanelAdapter extends RecyclerView.Adapter<Holder> {

    Context c;
    int[] titles;
    int[] images;

    public PromotionalChanelAdapter(Context c, int[] titles, int[] images) {
        this.c = c;
        this.titles = titles;
        this.images = images;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_holder, parent, false);
        Holder holder = new Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.img.setImageResource(images[position]);
        holder.txt.setText(titles[position]);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                Intent i = new Intent(c, PromoDetailsActivity.class);
                i.putExtra("type", pos);
                c.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
