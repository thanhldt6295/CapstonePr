package demo.example.thanhldtse61575.hotelservicetvbox;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.CartItem;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.Service;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.ToServer;

/**
 * Created by ThanhLDTSE61575 on 2/27/2017.
 */

public class ExtraAdapter extends BaseAdapter {

    private RelativeLayout relativeLayout;
    private PopupWindow popup;
    private LayoutInflater popupInflater;

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

    ExtraAdapter(Context c, ListView exList, List<Service> list, Spinner spin, Button finalize, RelativeLayout relativeLayout){
        this.ctx = c;
        this.extraListView = exList;
        this.list = list;
        this.spin = spin;
        this.finalize = finalize;
        this.relativeLayout = relativeLayout;
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
        SpinnerAdapter adapter = new SpinnerAdapter(ctx,
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

        final Button btnPlus = (Button) convertView.findViewById(R.id.btnPlus);
        btnPlus.getBackground().setAlpha(102);
        btnPlus.setFocusable(true);
        btnPlus.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    btnPlus.setTextColor(Color.parseColor("#E2FFE600"));
                }
                else {
                    btnPlus.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
            }
        });
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

        final Button btnMinus = (Button) convertView.findViewById(R.id.btnMinus);
        btnMinus.getBackground().setAlpha(102);
        btnMinus.setFocusable(true);
        btnMinus.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    btnMinus.setTextColor(Color.parseColor("#E2FFE600"));
                }
                else {
                    btnMinus.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
            }
        });
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
                    popupInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    ViewGroup container = (ViewGroup) popupInflater.inflate(R.layout.confirm_popup, null);

//                LinearLayout layoutPopup = (LinearLayout) container.findViewById(R.id.layoutPopup);
//                layoutPopup.getBackground().setAlpha(126);
//                LinearLayout layoutTitle = (LinearLayout) container.findViewById(R.id.layoutTitle);
//                layoutTitle.getBackground().setAlpha(200);
//                LinearLayout layoutContent = (LinearLayout) container.findViewById(R.id.layoutContent);
//                layoutContent.getBackground().setAlpha(238);
//                LinearLayout layoutBtn = (LinearLayout) container.findViewById(R.id.layoutBtn);
//                layoutBtn.getBackground().setAlpha(200);

                    popup = new PopupWindow(container, 600, 300, true);
                    popup.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);

                    popup.setOutsideTouchable(true);
                    popup.getContentView().setFocusableInTouchMode(true);
                    popup.getContentView().setOnKeyListener(new View.OnKeyListener() {
                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {

                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                popup.dismiss();
                                return true;
                            }
                            return false;
                        }
                    });
                    container.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            popup.dismiss();
                            return true;
                        }
                    });
                    TextView confirm = (TextView) container.findViewById(R.id.tvConfirm);
                    confirm.setText(container.getResources().getString(R.string.confirm_service));
                    TextView content = (TextView) container.findViewById(R.id.tvContent);
                    content.setText(container.getResources().getString(R.string.confirm_question_do));
                    Button cancel = (Button) container.findViewById(R.id.btnCancel);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popup.dismiss();
                        }
                    });
                    Button okyes = (Button) container.findViewById(R.id.btnOK);
                    okyes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
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
                                        popup.dismiss();
                                        Toast toast = Toast.makeText(ctx, R.string.confirm_request_wait, Toast.LENGTH_SHORT);
                                        TextView vToast = (TextView) toast.getView().findViewById(android.R.id.message);
                                        vToast.setTextColor(Color.WHITE);
                                        vToast.setTextSize(30);
                                        toast.show();
                                    }
                                    else{
                                        Toast.makeText(ctx, response, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            new SendDataToServer().execute("http://capstoneserver2017.azurewebsites.net/api/RequestsApi/SendRequest", time2Serv+"" , returnList+"", getRoomID());
                        }
                    });
                } else{
                    Toast toast = Toast.makeText(ctx, R.string.notifyqty, Toast.LENGTH_SHORT);
                    TextView vToast = (TextView) toast.getView().findViewById(android.R.id.message);
                    vToast.setTextColor(Color.RED);
                    vToast.setTextSize(30);
                    toast.show();
                }
            }
        });

        return convertView;
    }

    private String getRoomID(){

        SharedPreferences sharedPref = ctx.getSharedPreferences("ShareRoom", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("RoomID", "");

        return jsonPreferences;
    }
}
