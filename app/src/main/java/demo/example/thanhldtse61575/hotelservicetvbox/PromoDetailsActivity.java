package demo.example.thanhldtse61575.hotelservicetvbox;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.CartItem;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.Promotional;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.ToServer;

public class PromoDetailsActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private String API_KEY = "AIzaSyBW3RfFWMGi3ah-Ji1K8ODKZtcg6bBbww0";
    private String VIDEO_ID = "";

    private RelativeLayout relativeLayout;
    private PopupWindow popup;
    private LayoutInflater popupInflater;
    private List<Promotional> promoList = new ArrayList<>();

    YouTubePlayerView youTubePlayerView;
    TextView name;
    TextView hour;
    TextView cap;
    FloatingActionButton btnOrder;
    Button btnBack;
    Button btnNext;

    TextView roomid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_details);
        roomid = (TextView) findViewById(R.id.roomid);
        roomid.setText(getResources().getString(R.string.roomid) + " " + getRoomID());

        promoList = getPromoList();

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.videoView);
        name = (TextView) findViewById(R.id.tvPromoName);
        hour = (TextView) findViewById(R.id.tvWorkHour);
        cap = (TextView) findViewById(R.id.tvCapacity);
        btnOrder = (FloatingActionButton) findViewById(R.id.btnBooking);
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_promo_details);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnNext = (Button) findViewById(R.id.btnNext);

        Bundle extra = getIntent().getExtras();
        int type = extra.getInt("type");
        int count = promoList.size();
        for(int i = 0; i < count; i++){
            if(type==i){
                Display(promoList.get(i),type);
            }
        }

        // Datetime & Calendar
        final TextView txtDate;
        txtDate = (TextView) findViewById(R.id.txtDate);

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String currentDateTimeString = DateFormat.getTimeInstance().format(new Date()) + "  "
                                        + DateFormat.getDateInstance().format(new Date());
                                txtDate.setText(currentDateTimeString);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();

        final Calendar myCalen = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalen.set(Calendar.YEAR, year);
                myCalen.set(Calendar.MONTH, monthOfYear);
                myCalen.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }
        };

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(PromoDetailsActivity.this,date,myCalen.get(Calendar.YEAR), myCalen.get(Calendar.MONTH),
                        myCalen.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private String getRoomID(){

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("ShareRoom", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("RoomID", "");

        return jsonPreferences;
    }

    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult result) {
        Toast.makeText(this, "Failured to Initialize!", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onInitializationSuccess(Provider provider, final YouTubePlayer player, boolean wasRestored) {
/** add listeners to YouTubePlayer instance **/
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);

/** Start buffering **/
        if (!wasRestored) {
            player.cueVideo(VIDEO_ID);
        }
    }
    private PlaybackEventListener playbackEventListener = new PlaybackEventListener() {
        @Override
        public void onBuffering(boolean arg0) {
        }
        @Override
        public void onPaused() {
        }
        @Override
        public void onPlaying() {
        }
        @Override
        public void onSeekTo(int arg0) {
        }
        @Override
        public void onStopped() {
        }
    };
    private PlayerStateChangeListener playerStateChangeListener = new PlayerStateChangeListener() {
        @Override
        public void onAdStarted() {
        }
        @Override
        public void onError(ErrorReason arg0) {
        }
        @Override
        public void onLoaded(String arg0) {
        }
        @Override
        public void onLoading() {
        }
        @Override
        public void onVideoEnded() {
        }
        @Override
        public void onVideoStarted() {
        }
    };

    private List<Promotional> getPromoList(){
        Gson gson = new Gson();
        List<Promotional> list = new ArrayList<>();
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("SharedPromo", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("PromoList", "");

        Type type = new TypeToken<List<Promotional>>() {}.getType();
        list = gson.fromJson(jsonPreferences, type);

        return list;
    }

    private void Display(final Promotional promo, final int index){
        VIDEO_ID = promo.getVideoLink();
        youTubePlayerView.initialize(API_KEY, this);
        name.setText(promo.getName().toUpperCase());
        hour.setText(promo.getWorkHour());
        cap.setText(promo.getCapacity()+"");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index > 0){
                    Intent i = new Intent(PromoDetailsActivity.this, PromoDetailsActivity.class);
                    i.putExtra("type", index-1);
                    startActivity(i);
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index < promoList.size()-1){
                    Intent i = new Intent(PromoDetailsActivity.this, PromoDetailsActivity.class);
                    i.putExtra("type", index+1);
                    startActivity(i);
                }
            }
        });
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                        final List<CartItem> list = new ArrayList<CartItem>();
                        list.add(new CartItem(promo.getID(),name.getText().toString(),0,0,"","",1,""));
                        final String returnList = new Gson().toJson(list);
                        final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+7:00"));
                        final long time2Serv = calendar.getTimeInMillis()/1000 + 900;
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
                                    list.clear();
                                    popup.dismiss();
                                    Toast toast = Toast.makeText(PromoDetailsActivity.this, getResources().getString(R.string.confirm_promotional), Toast.LENGTH_SHORT);
                                    TextView vToast = (TextView) toast.getView().findViewById(android.R.id.message);
                                    vToast.setTextColor(Color.WHITE);
                                    vToast.setTextSize(30);
                                    toast.show();
                                }
                                else{
                                    Toast.makeText(PromoDetailsActivity.this, response, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        new SendDataToServer().execute("http://capstoneserver2017.azurewebsites.net/api/RequestsApi/SendRequest", time2Serv+"" , returnList+"", getRoomID());
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        this.startActivity(new Intent(PromoDetailsActivity.this, PromotionalChanelActivity.class));
    }
}
