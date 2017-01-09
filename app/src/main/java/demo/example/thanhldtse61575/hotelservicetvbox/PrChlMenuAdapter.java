package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by VULHSE61532 on 1/8/2017.
 */
public class PrChlMenuAdapter extends RecyclerView.Adapter<PrChlMenuHolder> {

    Context c;
    String[] titles;
    int[] images;

    public PrChlMenuAdapter(Context c, String[] titles, int[] images) {
        this.c = c;
        this.titles = titles;
        this.images = images;
    }

    @Override
    public PrChlMenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_item_fragment, parent, false);
        PrChlMenuHolder holder = new PrChlMenuHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(PrChlMenuHolder holder, int position) {
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
