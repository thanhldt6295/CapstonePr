package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by VULHSE61532 on 3/16/2017.
 */

public class RoomServicesAdapter extends RecyclerView.Adapter<Holder> {

    Context c;
    int[] titles;
    int[] images;

    public RoomServicesAdapter(Context c, int[] titles, int[] images) {
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
    public void onBindViewHolder(final Holder holder, final int position) {
        holder.img.setImageResource(images[position]);
        holder.txt.setText(titles[position]);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                switch (pos) {
                    case 0:
                        c.startActivity(new Intent(c, FoodyActivity.class));
                        break;
                    case 1:
                        c.startActivity(new Intent(c, ExtraActivity.class));
                        break;
                    case 2:
                        c.startActivity(new Intent(c, RequestTicketActivity.class));
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
