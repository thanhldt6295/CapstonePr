package demo.example.thanhldtse61575.hotelservicetvbox;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

public class PromoDetailsActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    public static final String API_KEY = "AIzaSyBW3RfFWMGi3ah-Ji1K8ODKZtcg6bBbww0";
    public static String VIDEO_ID = "";

    TextView roomid;
    TextView name;
    TextView hour;
    FloatingActionButton btnOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_details);
        TextView abTitle=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        roomid = (TextView) findViewById(R.id.roomid);
        roomid.setText(getResources().getString(R.string.roomid) + " " + getRoomID());

        name = (TextView) findViewById(R.id.tvPromoName);
        hour = (TextView) findViewById(R.id.tvWorkHour);

        Bundle extra = getIntent().getExtras();
        int type = extra.getInt("type");
        switch (type){
            case 0:
                VIDEO_ID = "fyAMWF3aNiM";
                YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.videoView);
                youTubePlayerView.initialize(API_KEY, (YouTubePlayer.OnInitializedListener) this);
                name.setText(R.string.spa);
                hour.setText(R.string.spahour);
                btnOrder = (FloatingActionButton) findViewById(R.id.btnBooking);
                btnOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(PromoDetailsActivity.this)
                                .setTitle(R.string.confirm_service)
                                .setMessage(R.string.confirm_question_do)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Toast toast = Toast.makeText(PromoDetailsActivity.this, getResources().getString(R.string.confirm_order_accepted), Toast.LENGTH_SHORT);
                                        TextView vToast = (TextView) toast.getView().findViewById(android.R.id.message);
                                        vToast.setTextColor(Color.WHITE);
                                        vToast.setTextSize(30);
                                        toast.show();
                                    }})
                                .setNegativeButton(android.R.string.no, null).show();
                    }
                });
                break;
            case 1:
                VIDEO_ID = "PvWBLOsOdp8";
                youTubePlayerView = (YouTubePlayerView) findViewById(R.id.videoView);
                youTubePlayerView.initialize(API_KEY, (YouTubePlayer.OnInitializedListener) this);
                name.setText(R.string.gym);
                hour.setText(R.string.gymhour);
                btnOrder = (FloatingActionButton) findViewById(R.id.btnBooking);
                btnOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(PromoDetailsActivity.this)
                                .setTitle(R.string.confirm_service)
                                .setMessage(R.string.confirm_question_do)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Toast toast = Toast.makeText(PromoDetailsActivity.this, getResources().getString(R.string.confirm_order_accepted), Toast.LENGTH_SHORT);
                                        TextView vToast = (TextView) toast.getView().findViewById(android.R.id.message);
                                        vToast.setTextColor(Color.WHITE);
                                        vToast.setTextSize(30);
                                        toast.show();
                                    }})
                                .setNegativeButton(android.R.string.no, null).show();
                    }
                });
                break;
            case 2:
                VIDEO_ID = "-eoVJyZBFMw";
                youTubePlayerView = (YouTubePlayerView) findViewById(R.id.videoView);
                youTubePlayerView.initialize(API_KEY, (YouTubePlayer.OnInitializedListener) this);
                name.setText(R.string.pool);
                hour.setText(R.string.poolhour);
                btnOrder = (FloatingActionButton) findViewById(R.id.btnBooking);
                btnOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(PromoDetailsActivity.this)
                                .setTitle(R.string.confirm_service)
                                .setMessage(R.string.confirm_question_do)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Toast toast = Toast.makeText(PromoDetailsActivity.this, getResources().getString(R.string.confirm_order_accepted), Toast.LENGTH_SHORT);
                                        TextView vToast = (TextView) toast.getView().findViewById(android.R.id.message);
                                        vToast.setTextColor(Color.WHITE);
                                        vToast.setTextSize(30);
                                        toast.show();
                                    }})
                                .setNegativeButton(android.R.string.no, null).show();
                    }
                });
                break;
            case 3:
                VIDEO_ID = "Cl4JiA4cgOU";
                youTubePlayerView = (YouTubePlayerView) findViewById(R.id.videoView);
                youTubePlayerView.initialize(API_KEY, (YouTubePlayer.OnInitializedListener) this);
                name.setText(R.string.tennis);
                btnOrder = (FloatingActionButton) findViewById(R.id.btnBooking);
                btnOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(PromoDetailsActivity.this)
                                .setTitle(R.string.confirm_service)
                                .setMessage(R.string.confirm_question_do)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Toast toast = Toast.makeText(PromoDetailsActivity.this, getResources().getString(R.string.confirm_order_accepted), Toast.LENGTH_SHORT);
                                        TextView vToast = (TextView) toast.getView().findViewById(android.R.id.message);
                                        vToast.setTextColor(Color.WHITE);
                                        vToast.setTextSize(30);
                                        toast.show();
                                    }})
                                .setNegativeButton(android.R.string.no, null).show();
                    }
                });
                break;
            case 4:
                VIDEO_ID = "9Lqr9MC9wM0";
                youTubePlayerView = (YouTubePlayerView) findViewById(R.id.videoView);
                youTubePlayerView.initialize(API_KEY, (YouTubePlayer.OnInitializedListener) this);
                name.setText(R.string.golf);
                btnOrder = (FloatingActionButton) findViewById(R.id.btnBooking);
                btnOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(PromoDetailsActivity.this)
                                .setTitle(R.string.confirm_service)
                                .setMessage(R.string.confirm_question_do)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Toast toast = Toast.makeText(PromoDetailsActivity.this, getResources().getString(R.string.confirm_order_accepted), Toast.LENGTH_SHORT);
                                        TextView vToast = (TextView) toast.getView().findViewById(android.R.id.message);
                                        vToast.setTextColor(Color.WHITE);
                                        vToast.setTextSize(30);
                                        toast.show();
                                    }})
                                .setNegativeButton(android.R.string.no, null).show();
                    }
                });
                break;
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
    public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
/** add listeners to YouTubePlayer instance **/
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);
/** Start buffering **/
        if (!wasRestored) {
            player.loadVideo(VIDEO_ID);
            player.setFullscreen(true);
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
}
