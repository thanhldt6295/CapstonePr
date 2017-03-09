package demo.example.thanhldtse61575.hotelservicetvbox;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by ThanhLDTSE61575 on 1/11/2017.
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
    public void onBindViewHolder(Holder holder, int position) {
        holder.img.setImageResource(images[position]);
        holder.txt.setText(titles[position]);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                switch (pos){
                    case 0:
                        c.startActivity(new Intent(c, FoodyActivity.class));
                        break;
                    case 1:
                        c.startActivity(new Intent(c, HousekeepingActivity.class));
                        break;
                    case 2:
                        c.startActivity(new Intent(c, MaintenanceActivity.class));
                        break;
                    case 3:
                        new AlertDialog.Builder(c)
                                .setTitle(R.string.confirm_service)
                                .setMessage(R.string.confirm_question_do)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Toast.makeText(c, R.string.confirm_answer_accepted, Toast.LENGTH_SHORT).show();
                                    }})
                                .setNegativeButton(android.R.string.no, null).show();
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
