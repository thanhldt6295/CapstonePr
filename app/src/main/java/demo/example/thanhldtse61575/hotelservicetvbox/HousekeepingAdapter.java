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
import android.widget.Toast;

import com.google.gson.Gson;

import demo.example.thanhldtse61575.hotelservicetvbox.housekeeping.BathExtraActivity;
import demo.example.thanhldtse61575.hotelservicetvbox.housekeeping.BedExtraActivity;
import demo.example.thanhldtse61575.hotelservicetvbox.housekeeping.LaundryActivity;
import demo.example.thanhldtse61575.hotelservicetvbox.housekeeping.RoomCleaningActivity;
import demo.example.thanhldtse61575.hotelservicetvbox.housekeeping.RoomExtraActivity;

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
                        c.startActivity(new Intent(c, BathExtraActivity.class));
                        break;
                    case 3:
                        c.startActivity(new Intent(c, BedExtraActivity.class));
                        break;
                    case 4:
                        c.startActivity(new Intent(c, RoomExtraActivity.class));
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
