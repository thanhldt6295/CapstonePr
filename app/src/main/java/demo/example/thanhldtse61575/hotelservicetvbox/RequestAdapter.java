package demo.example.thanhldtse61575.hotelservicetvbox;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.CartItem;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.Service;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.ToServer;

/**
 * Created by ThanhLDTSE61575 on 3/1/2017.
 */

public class RequestAdapter extends BaseAdapter {

    private int q = 0;

    private RelativeLayout relativeLayout;
    private PopupWindow popup;
    private LayoutInflater popupInflater;

    private String[] arraySpinner;
    private List<CartItem> cart = new ArrayList<>();
    private List<CartItem> temp = new ArrayList<>();

    private Context ctx;
    private ListView listView;
    private List<Service> list;
    private Spinner spin;
    private Button finalize;
    private LayoutInflater layoutInflater;

    RequestAdapter(Context c, ListView listView, List<Service> list, Spinner spin, Button finalize, RelativeLayout relativeLayout){
        this.ctx = c;
        this.listView = listView;
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
        temp.clear();
        cart.clear();

        convertView = layoutInflater.inflate(R.layout.layout_requestitem, null);

        this.arraySpinner = new String[] {
                "15", "30", "45", "60", "100"
        };
        SpinnerAdapter adapter = new SpinnerAdapter(ctx,
                android.R.layout.simple_spinner_item, arraySpinner);
        spin.setAdapter(adapter);

        for (Service sv: list) {
            temp.add(new CartItem(sv.getServiceID(), sv.getServiceName(), sv.getCategoryID(),
                    0, sv.getDescription(), sv.getImage(), 0, ""));
        }

        TextView name = (TextView) convertView.findViewById(R.id.txtRequestName);
        name.setText(list.get(position).getServiceName());
        final EditText comment = (EditText) convertView.findViewById(R.id.txtComment);
        comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                temp.get(position).setComment(s.toString().trim());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                temp.get(position).setComment(s.toString().trim());
            }
        });
        final CheckBox check = (CheckBox) convertView.findViewById(R.id.chkBox);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                if(cb.isChecked()){
                    check.setChecked(true);
                    temp.get(position).setQuantity(1);
                } else {
                    check.setChecked(false);
                    temp.get(position).setQuantity(0);
                }
            }
        });

        finalize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart.clear();
                q=0;
                for (CartItem ci: temp){
                    if(ci.getQuantity()!=0){
                        cart.add(new CartItem(ci.getServiceID(), ci.getServiceName(), ci.getCategoryID(),
                                ci.getUnitPrice(), ci.getDescription(), ci.getImage(), ci.getQuantity(), ci.getComment()));
                    } else if(!ci.getComment().toString().equals("")){
                        Toast toast = Toast.makeText(ctx, R.string.notifychoose, Toast.LENGTH_SHORT);
                        TextView vToast = (TextView) toast.getView().findViewById(android.R.id.message);
                        vToast.setTextColor(Color.RED);
                        vToast.setTextSize(30);
                        toast.show();
                        q=1;
                    }
                }
                if(cart.size()!=0&&q==0) {
                    popupInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    ViewGroup container = (ViewGroup) popupInflater.inflate(R.layout.confirm_popup, null);

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
                            final String returnList = new Gson().toJson(cart);
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
                                        temp.clear();
                                        cart.clear();
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
                    Toast toast = Toast.makeText(ctx, R.string.notifychoose, Toast.LENGTH_SHORT);
                    TextView vToast = (TextView) toast.getView().findViewById(android.R.id.message);
                    vToast.setTextColor(Color.RED);
                    vToast.setTextSize(30);
                    toast.show();
                }
            }
        });

        return  convertView;
    }

    private String getRoomID(){

        SharedPreferences sharedPref = ctx.getSharedPreferences("ShareRoom", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("RoomID", "");

        return jsonPreferences;
    }
}
