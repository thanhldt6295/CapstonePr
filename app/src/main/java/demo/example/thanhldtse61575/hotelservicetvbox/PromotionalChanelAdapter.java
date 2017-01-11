package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by ThanhLDTSE61575 on 1/11/2017.
 */
public class PromotionalChanelAdapter extends RecyclerView.Adapter<PromotionalChanelHolder> {

    Context c;
    String[] titles;
    int[] images;

    public PromotionalChanelAdapter(Context c, String[] titles, int[] images) {
        this.c = c;
        this.titles = titles;
        this.images = images;
    }

    @Override
    public PromotionalChanelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.promotinal_chanel_fragment, parent, false);
        PromotionalChanelHolder holder = new PromotionalChanelHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(PromotionalChanelHolder holder, int position) {
        holder.img.setImageResource(images[position]);
        holder.txt.setText(titles[position]);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                Toast.makeText(c, titles[pos], Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
