package demo.example.thanhldtse61575.hotelservicetvbox;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.Image;

public class EcardActivity extends AppCompatActivity {

    private RelativeLayout relativeLayout;
    private PopupWindow popup;
    private LayoutInflater layoutInflater;
    private List<Image> list = new ArrayList<>();
    private String imageLink = "";
    EditText sender;
    EditText revMail;
    EditText subject;
    EditText message;
    Button btnSend;
    GridView gridView;
    TextView roomid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecard);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
        TextView abTitle=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        abTitle.setText(getResources().getString(R.string.ecard));
        roomid = (TextView) findViewById(R.id.roomid);
        roomid.setText(getResources().getString(R.string.roomid) + " " + getRoomID());

        list.add(new Image("https://images.pexels.com/photos/2324/skyline-buildings-new-york-skyscrapers.jpg"));
        list.add(new Image("https://images.pexels.com/photos/28221/pexels-photo-28221.jpg"));
        list.add(new Image("https://images.pexels.com/photos/2324/skyline-buildings-new-york-skyscrapers.jpg"));
        list.add(new Image("https://images.pexels.com/photos/28221/pexels-photo-28221.jpg"));
        list.add(new Image("https://images.pexels.com/photos/2324/skyline-buildings-new-york-skyscrapers.jpg"));
        list.add(new Image("https://images.pexels.com/photos/28221/pexels-photo-28221.jpg"));

        sender = (EditText) findViewById(R.id.txtYourName);
        revMail = (EditText) findViewById(R.id.txtMailRecv);
        subject = (EditText) findViewById(R.id.txtSubject);
        message = (EditText) findViewById(R.id.txtMessage);
        btnSend = (Button) findViewById(R.id.btnSend);
        gridView = (GridView) findViewById(R.id.gridViewCard);
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_ecard);
        EcardAdapter adapter = new EcardAdapter(this, list);
        gridView.setAdapter(adapter);
        //gridView.setAdapter(new ImageAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.layout_ecarditem, null);
                popup = new PopupWindow(container, 1280, 800, true);
                popup.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);

                ImageView imageView = (ImageView) container.findViewById(R.id.thumbImage);
                Button btnChoose = (Button) container.findViewById(R.id.btnChoose);
                final String url = list.get(position).getImage();
                Picasso.with(EcardActivity.this)
                        .load(url)
                        .placeholder(R.drawable.loading)
                        .into(imageView);
                btnChoose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageLink = url;
                    }
                });
                container.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        imageLink = url;
                        Toast toast = Toast.makeText(EcardActivity.this, R.string.attached, Toast.LENGTH_SHORT);
                        TextView vToast = (TextView) toast.getView().findViewById(android.R.id.message);
                        vToast.setTextColor(Color.CYAN);
                        vToast.setTextSize(20);
                        toast.show();
                        popup.dismiss();
                        return true;
                    }
                });
            }
        });

        btnSend.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Button view = (Button) v;
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        if((isEmailValid(revMail.getText().toString()))&&(!message.getText().equals(""))&&(!imageLink.equals(""))) {
                            new Download().execute(imageLink,"haha.jpg");
                        } else {
                            Toast toast = Toast.makeText(EcardActivity.this, R.string.validate, Toast.LENGTH_SHORT);
                            TextView vToast = (TextView) toast.getView().findViewById(android.R.id.message);
                            vToast.setTextColor(Color.RED);
                            vToast.setTextSize(30);
                            toast.show();
                        }

                    case MotionEvent.ACTION_CANCEL: {
                        Button view = (Button) v;
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return true;
            }
        });

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
                new DatePickerDialog(EcardActivity.this,date,myCalen.get(Calendar.YEAR), myCalen.get(Calendar.MONTH),
                        myCalen.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private String getRoomID(){

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("ShareRoom", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("RoomID", "");

        return jsonPreferences;
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches();
    }

    class Download extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            int count;
            try {
                URL url = new URL(params[0]);
                URLConnection connection = url.openConnection();
                connection.connect();

                // this will be useful so that you can show a tipical 0-100%
                // progress bar
                int lenghtOfFile = connection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                File sdDir = Environment.getExternalStorageDirectory();

                // Output stream
                OutputStream output = new FileOutputStream(sdDir + File.separator +params[1]);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    //publishProgress(((total * 100) / lenghtOfFile)+"");

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
            return params[1];
        }
        protected void onPostExecute(String feed) {
            //new SendEmail().execute(reciver mail, subject, body,feed);
            String mail = revMail.getText().toString();
            new SendEmail().execute(revMail.getText().toString(), subject.getText().toString(), message.getText() + "\n\nLove, \n" + sender.getText().toString(),feed);
        }
    }

    class SendEmail extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            Mail m = new Mail("hotelservicetvbox@gmail.com", "capstoneproject");

            String[] toArr = {params[0]};
            m.set_to(toArr);
            m.set_from("hotelservicetvbox@gmail.com");
            m.set_subject(params[1]);
            m.set_body(params[2]);

            try {
                File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), File.separator + params[3]);
                m.addAttachment(filelocation.getAbsolutePath());
                return m.send()+"";
            } catch(Exception e) {
                Log.e("MailApp", "Could not send email", e);
            }
            return null;
        }

        protected void onPostExecute(String feed) {
            Toast toast = Toast.makeText(EcardActivity.this, R.string.sent, Toast.LENGTH_SHORT);
            TextView vToast = (TextView) toast.getView().findViewById(android.R.id.message);
            vToast.setTextColor(Color.WHITE);
            vToast.setTextSize(20);
            vToast.setTypeface(null,Typeface.BOLD);
            toast.show();
            revMail.setText("");
            subject.setText("");
            message.setText("");
            imageLink = "";
        }
    }
}
