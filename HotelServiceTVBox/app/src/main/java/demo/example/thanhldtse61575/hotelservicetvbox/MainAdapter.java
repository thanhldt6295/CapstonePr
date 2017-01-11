package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by ThanhLDTSE61575 on 1/10/2017.
 */
public class MainAdapter extends RecyclerView.Adapter<MainHolder> {

    Context c;
    String[] titles;
    int[] images;

    public MainAdapter(Context c, String[] titles, int[] images) {
        this.c = c;
        this.titles = titles;
        this.images = images;
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_fragment, parent, false);
        MainHolder holder = new MainHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MainHolder holder, final int position) {
        holder.img.setImageResource(images[position]);
        holder.txt.setText(titles[position]);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                if(pos==0) {
                    Toast.makeText(c, titles[pos], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(c, PromotionalChanelActivity.class);
                    c.startActivity(i);
                }
                if(pos==1){
                    Toast.makeText(c, titles[pos], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(c, RoomServicesActivity.class);
                    c.startActivity(i);
                }
                /*if(pos==2){
                    Toast.makeText(c, titles[pos], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(c, RoomServicesActivity.class);
                    c.startActivity(i);
                }
                if(pos==3){
                    Toast.makeText(c, titles[pos], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(c, RoomServicesActivity.class);
                    c.startActivity(i);
                }
                if(pos==4){
                    Toast.makeText(c, titles[pos], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(c, RoomServicesActivity.class);
                    c.startActivity(i);
                }
                if(pos==5){
                    Toast.makeText(c, titles[pos], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(c, RoomServicesActivity.class);
                    c.startActivity(i);
                }*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

}
