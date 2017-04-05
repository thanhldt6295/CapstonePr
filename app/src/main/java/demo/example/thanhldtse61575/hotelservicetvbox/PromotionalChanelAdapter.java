package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.Promotional;

/**
 * Created by ThanhLDTSE61575 on 1/11/2017.
 */
public class PromotionalChanelAdapter extends RecyclerView.Adapter<Holder> {

    Context c;
    List<Promotional> promo;

    public PromotionalChanelAdapter(Context c, List<Promotional> promo) {
        this.c = c;
        this.promo = promo;
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
        Picasso.with(c)
                .load(promo.get(position).getImage())
                .placeholder(R.drawable.loading)
                .fit()
                .centerCrop().into(holder.img);
        holder.txt.setText(promo.get(position).getName());
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
        return promo.size();
    }
}
