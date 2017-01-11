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
public class RoomServicesAdapter extends RecyclerView.Adapter<RoomServicesHolder> {

    Context c;
    String[] titles;
    int[] images;

    public RoomServicesAdapter(Context c, String[] titles, int[] images) {
        this.c = c;
        this.titles = titles;
        this.images = images;
    }

    @Override
    public RoomServicesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.room_services_fragment, parent, false);
        RoomServicesHolder holder = new RoomServicesHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(RoomServicesHolder holder, int position) {
        holder.img.setImageResource(images[position]);
        holder.txt.setText(titles[position]);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                if(pos==0) {
                    Toast.makeText(c, titles[pos], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(c, FoodsandDrinksActivity.class);
                    c.startActivity(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
