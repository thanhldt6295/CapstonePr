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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import demo.example.thanhldtse61575.hotelservicetvbox.CommonService;
import demo.example.thanhldtse61575.hotelservicetvbox.R;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.CartItem;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.Service;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.ToServer;

/**
 * Created by ThanhLDTSE61575 on 2/27/2017.
 */

public class ExtraAdapter extends BaseAdapter {

    private List<CartItem> cart = new ArrayList<>();
    private List<CartItem> cart2 = new ArrayList<>();
    private int qty = 0;
    private String[] arraySpinner;

    private Context ctx;
    private ListView extraListView;
    private List<Service> list;
    private Button finalize;
    private Spinner spin;
    private LayoutInflater layoutInflater;

    ExtraAdapter(Context c, ListView exList, List<Service> list, Spinner spin, Button finalize){
        this.ctx = c;
        this.extraListView = exList;
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

        convertView = layoutInflater.inflate(R.layout.layout_extraitem, null);

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

        final EditText quantity = (EditText) convertView.findViewById(R.id.txtQuantity);

        Button btnPlus = (Button) convertView.findViewById(R.id.btnPlus);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(quantity.getText().toString());
                if (n < 10) {
                    qty = n + 1;
                    quantity.setText(qty+"");
                    cart.get(position).setQuantity(qty);
                }
            }
        });

        Button btnMinus = (Button) convertView.findViewById(R.id.btnMinus);
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(quantity.getText().toString());
                if (n > 0) {
                    qty = n - 1;
                    quantity.setText(qty+"");
                    cart.get(position).setQuantity(qty);
                }
            }
        });

        for (Service sv: list) {
            cart.add(new CartItem(sv.getServiceID(), sv.getServiceName(), sv.getCategoryID(),
                    sv.getUnitPrice(), sv.getDescription(), sv.getImage(),
                    qty, ""));
        }

        finalize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (CartItem ci: cart){
                    int q = ci.getQuantity();
                    if(q!=0){
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
                                    final long time=60*Long.parseLong(spin.getSelectedItem().toString());
                                    final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+7:00"));
                                    final long time2Serv = calendar.getTimeInMillis()/1000 + time;
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
                    Toast.makeText(ctx, R.string.notifyqty, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }

    private String getDataFromSharedPreferences(){

        SharedPreferences sharedPref = ctx.getSharedPreferences("ShareRoom", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("RoomID", "");

        return jsonPreferences;
    }
}