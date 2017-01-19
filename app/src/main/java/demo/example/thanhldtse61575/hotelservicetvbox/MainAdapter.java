package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ThanhLDTSE61575 on 1/10/2017.
 */
public class MainAdapter extends RecyclerView.Adapter<Holder> {

    Context c;
    String[] titles;
    int[] images;

    public MainAdapter(Context c, String[] titles, int[] images) {
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
    public void onBindViewHolder(Holder holder, final int position) {
        holder.img.setImageResource(images[position]);
        holder.txt.setText(titles[position]);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {

                switch(pos){
                    case 0:
                        c.startActivity(new Intent(c, PromotionalChanelActivity.class));
                        break;
                    case 1:
                        c.startActivity(new Intent(c, RoomServicesActivity.class));
                        break;
                    case 2:
                        c.startActivity(new Intent(c, ShoppingActivity.class));
                        break;
                    case 4:
                        c.startActivity(new Intent(c, ViewBillActivity.class));
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
