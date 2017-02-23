package demo.example.thanhldtse61575.hotelservicetvbox;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import demo.example.thanhldtse61575.hotelservicetvbox.housekeeping.ExtraActivity;
import demo.example.thanhldtse61575.hotelservicetvbox.housekeeping.LaundryActivity;
import demo.example.thanhldtse61575.hotelservicetvbox.housekeeping.RoomCleaningActivity;

/**
 * Created by VULHSE61532 on 1/12/2017.
 */

public class HousekeepingAdapter extends RecyclerView.Adapter<Holder>  {
    Context c;
    int[] titles;
    int[] images;

    public HousekeepingAdapter(Context c, int[] titles, int[] images) {
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
                switch (pos){
                    case 0:
//                        new AlertDialog.Builder(c)
//                                .setTitle(R.string.confirm_service)
//                                .setMessage(R.string.confirm_question_do)
//                                .setIcon(android.R.drawable.ic_dialog_alert)
//                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//
//                                    public void onClick(DialogInterface dialog, int whichButton) {
//                                        class SendDataToServer extends AsyncTask<String, Void, Integer> {
//
//                                            @Override
//                                            protected Integer doInBackground(String... params) {
//                                                CommonService commonService = new CommonService();
//                                                int returnValue = commonService.sendData(params[0], params[1]);
//                                                return returnValue;
//                                            }
//                                        }
//                                        new SendDataToServer().execute("http://localhost:49457/api/getapp/", "roomid=201&serviceName=" + titles[position]);
//                                        Toast.makeText(c, R.string.confirm_answer_wait, Toast.LENGTH_SHORT).show();
//                                    }
//                                })
//                                .setNegativeButton(android.R.string.no, null).show();
//                        break;
                        c.startActivity(new Intent(c, RoomCleaningActivity.class));
                        break;
                    case 1:
                        c.startActivity(new Intent(c, LaundryActivity.class));
                        break;
                    case 2:
                        Intent bathExtra = new Intent(c, ExtraActivity.class);
                        bathExtra.putExtra("type", 1);
                        c.startActivity(bathExtra);
                        break;
                    case 3:
                        Intent bedExtra = new Intent(c, ExtraActivity.class);
                        bedExtra.putExtra("type", 2);
                        c.startActivity(bedExtra);
                        break;
                    case 4:
                        Intent roomExtra = new Intent(c, ExtraActivity.class);
                        roomExtra.putExtra("type", 3);
                        c.startActivity(roomExtra);
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
