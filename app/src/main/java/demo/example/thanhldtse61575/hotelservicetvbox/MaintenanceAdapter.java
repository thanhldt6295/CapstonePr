package demo.example.thanhldtse61575.hotelservicetvbox;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.CartItem;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.Service;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.ToServer;

/**
 * Created by ThanhLDTSE61575 on 3/1/2017.
 */

public class MaintenanceAdapter extends BaseAdapter {

    private String[] arraySpinner;
    private int q = 0;
    private List<CartItem> cart = new ArrayList<>();
    private List<CartItem> cart2 = new ArrayList<>();

    private Context ctx;
    private ListView listView;
    private List<Service> list;
    private Spinner spin;
    private Button finalize;
    private LayoutInflater layoutInflater;

    MaintenanceAdapter(Context c, ListView listView, List<Service> list, Spinner spin, Button finalize){
        this.ctx = c;
        this.listView = listView;
        this.list = list;
        this.spin = spin;
        this.finalize = finalize;
        layoutInflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        cart.clear();
        cart2.clear();

        convertView = layoutInflater.inflate(R.layout.layout_maintenanceitem, null);

        this.arraySpinner = new String[] {
                "15", "30", "45", "60", "100"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx,
                android.R.layout.simple_spinner_item, arraySpinner);
        spin.setAdapter(adapter);

        ImageView image = (ImageView) convertView.findViewById(R.id.imageViewDetail);

        String url = list.get(position).getImage();
        Picasso.with(ctx)
                .load(url)
                .placeholder(R.drawable.loading)
                .fit()
                .centerCrop().into(image);
        TextView name = (TextView) convertView.findViewById(R.id.txtRequestName);
        name.setText(list.get(position).getServiceName());

        for (Service sv: list) {
            cart.add(new CartItem(sv.getServiceID(), sv.getServiceName(), sv.getCategoryID(),
                    sv.getUnitPrice(), sv.getDescription(), sv.getImage(),
                    0, ""));
        }

        final EditText comment = (EditText) convertView.findViewById(R.id.edCmt);
        final CheckBox check = (CheckBox) convertView.findViewById(R.id.chkBox);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                if(cb.isChecked()){
                    q=1;
                    check.setChecked(true);
                    cart.get(position).setComment(comment.getText().toString());
                } else {
                    check.setChecked(false);
                }
            }
        });

        finalize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (CartItem ci: cart){
                    if(q!=0&&!(ci.getComment().toString().equals(""))){
                        cart2.add(new CartItem(ci.getServiceID(), ci.getServiceName(), ci.getCategoryID(),
                                ci.getUnitPrice(), ci.getDescription(), ci.getImage(), ci.getQuantity(), ci.getComment()));
                    }
                }
                if(cart2.size()!=0) {
                    new AlertDialog.Builder(ctx)
                            .setTitle(R.string.confirm_order)
                            .setMessage(R.string.confirm_question_do)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    final String returnList = new Gson().toJson(cart2);
                                    final long time2Serv = 60*Long.parseLong(spin.getSelectedItem().toString());
                                    class SendDataToServer extends AsyncTask<String, String, String> {

                                        @Override
                                        protected String doInBackground(String... params) {
                                            CommonService commonService = new CommonService();

                                            List<CartItem> acc = new Gson().fromJson(params[2], new TypeToken<List<CartItem>>() {}.getType());
                                            ToServer toServer = new ToServer( Double.parseDouble(params[1]), acc , Integer.parseInt(params[3]));

                                            return commonService.sendData(params[0], toServer)+"";
                                        }

                                        protected void onPostExecute(String response) {
                                            if(response.equals("200")){
                                                cart.clear();
                                                cart2.clear();
                                                notifyDataSetChanged();

                                                Toast.makeText(ctx, R.string.confirm_answer_accepted, Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                Toast.makeText(ctx, response, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                    new SendDataToServer().execute("http://capstoneserver2017.azurewebsites.net/api/RequestsApi/SendRequest", time2Serv+"" , returnList+"", getDataFromSharedPreferences());
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                } else{
                    Toast.makeText(ctx, R.string.notifychoose, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return  convertView;
    }

    private String getDataFromSharedPreferences(){

        SharedPreferences sharedPref = ctx.getSharedPreferences("ShareRoom", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("RoomID", "");

        return jsonPreferences;
    }
}
