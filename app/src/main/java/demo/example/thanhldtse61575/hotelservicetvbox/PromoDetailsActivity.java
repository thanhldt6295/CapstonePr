package demo.example.thanhldtse61575.hotelservicetvbox;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.MediaController;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.VideoView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class PromoDetailsActivity extends AppCompatActivity {

    VideoView videoView;
    TextView roomid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_details);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
        TextView abTitle=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        roomid = (TextView) findViewById(R.id.roomid);
        roomid.setText(getResources().getString(R.string.roomid) + " " + getDataFromSharedPreferences());

        Bundle extra = getIntent().getExtras();
        int type = extra.getInt("type");
        switch (type){
            case 0:
                abTitle.setText(getResources().getString(R.string.spa));
                videoView = (VideoView) findViewById(R.id.videoView);
                videoView.setVideoPath("http://r10---sn-oguesn7z.googlevideo.com/videoplayback?ip=123.20.107.36&gir=yes&sparams=clen,dur,expire,gir,id,initcwndbps,ip,ipbits,itag,lmt,mime,mm,mn,ms,mv,nh,pl,source,upn&id=o-AIQkQXGfCsabISzJRDeI6NxZb7r3FP9uE2mDD8If_owF&upn=m_dV-cMlD8U&itag=135&pl=21&source=youtube&beids=%5B9452306%5D&expire=1488215665&clen=6634582&mime=video%2Fmp4&ipbits=0&lmt=1444011887493316&key=cms1&dur=60.000&signature=016F25FA9997906CBF3AC0A490D7A6AFB37FAF6C.10BBBCA9FBB41D5275690624748F44E3F94345A6&title=Phim+qu%E1%BA%A3ng+c%C3%A1o+truy%E1%BB%81n+h%C3%ACnh+v%E1%BB%81+L%27Beauty+Spa&redirect_counter=1&cm2rm=sn-n8vld7r&req_id=d897d2dfb6e9a3ee&cms_redirect=yes&mm=34&mn=sn-oguesn7z&ms=ltu&mt=1488194120&mv=m");
                videoView.start();
                break;
            case 1:
                abTitle.setText(getResources().getString(R.string.gym));
                videoView = (VideoView) findViewById(R.id.videoView);
                videoView.setVideoPath("http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4");
                videoView.start();
                break;
            case 2:
                abTitle.setText(getResources().getString(R.string.pool));
                videoView = (VideoView) findViewById(R.id.videoView);
                videoView.setVideoURI(Uri.parse("https://youtu.be/yx5GqeP2qQU"));
                MediaController mc = new MediaController(this);
                videoView.setMediaController(mc);
                videoView.requestFocus();
                videoView.start();
                break;
            case 3:
                abTitle.setText(getResources().getString(R.string.tennis));
                break;
            case 4:
                abTitle.setText(getResources().getString(R.string.golf));
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

    private String getDataFromSharedPreferences(){

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("ShareRoom", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("RoomID", "");

        return jsonPreferences;
    }
}
