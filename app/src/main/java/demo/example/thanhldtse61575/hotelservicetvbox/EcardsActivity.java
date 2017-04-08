package demo.example.thanhldtse61575.hotelservicetvbox;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.Ecards;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.Order;

public class EcardsActivity extends AppCompatActivity {

    private static int count = 0;
    private static double total = 0;
    //private static int voucher = 0;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private static String dear = "";
    private static String mess = "";
    private static List<Ecards> list = new ArrayList<>();
    private static String sender = "";

    private static EditText receiver;
    private static String name = "";
    private static EditText revMail;
    private static String mail = "";

    private static Button btnSend;
    private static ImageView imageView;
    private static TextView tvMessage;
    TextView roomid;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private static ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_ecards);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
        TextView abTitle=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        abTitle.setText(getResources().getString(R.string.ecard));
        roomid = (TextView) findViewById(R.id.roomid);
        roomid.setText(getResources().getString(R.string.roomid) + " " + getRoomID());

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        dear = getResources().getString(R.string.dear);
        sender = getCustName();
        list = getImageList();
        total = getOrder().getSubTotal();
        //voucher = getOrder().getVoucher();

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

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
                new DatePickerDialog(EcardsActivity.this,date,myCalen.get(Calendar.YEAR), myCalen.get(Calendar.MONTH),
                        myCalen.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private List<Ecards> getImageList(){
        Gson gson = new Gson();
        List<Ecards> list = new ArrayList<>();
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("SharedEcard", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("EcardList", "");

        Type type = new TypeToken<List<Ecards>>() {}.getType();
        list = gson.fromJson(jsonPreferences, type);

        return list;
    }

    private String getRoomID(){

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("ShareRoom", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("RoomID", "");

        return jsonPreferences;
    }

    private String getCustName(){

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("ShareCust", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("CustName", "");

        return jsonPreferences;
    }

    private static boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches();
    }

    private Order getOrder(){

        Gson gson = new Gson();
        Order bill;
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("SharedBill", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("BillTotal", "");

        Type type = new TypeToken<Order>() {}.getType();
        bill = gson.fromJson(jsonPreferences, type);

        return bill;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public void PassData2Fragment(int pos){
            final String imageLink = list.get(pos).getImage(); /*"https://www.safarihotelsnamibia.com/wp-content/uploads/2014/11/Safari-Court-Hotel-Pool.jpg"*/
            Picasso.with(getActivity())
                    .load(imageLink)
                    .placeholder(R.drawable.loading)
                    .into(imageView);
//            Glide.with(getActivity())
//                    .load(imageLink)
//                    .placeholder(R.drawable.loading)
//                    .centerCrop()
//                    .into(imageView);
            mess = list.get(pos).getMessage();
            tvMessage.setText(mess);
            revMail.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mail = s.toString().trim().replaceAll("\\s+$", "");
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    // TODO Auto-generated method stub
                }

                @Override
                public void afterTextChanged(Editable s) {
                    mail = s.toString().trim().replaceAll("\\s+$", "");
                }
            });
            revMail.setText(mail);
            receiver.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    name = s.toString().trim().replaceAll("\\s+$", "");
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    // TODO Auto-generated method stub
                }

                @Override
                public void afterTextChanged(Editable s) {
                    name = s.toString().trim().replaceAll("\\s+$", "");
                }
            });
            receiver.setText(name);
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
                            if(!isEmailValid(mail)){
                                Toast toast = Toast.makeText(getActivity(), R.string.validateMail, Toast.LENGTH_SHORT);
                                TextView vToast = (TextView) toast.getView().findViewById(android.R.id.message);
                                vToast.setTextColor(Color.RED);
                                vToast.setTextSize(30);
                                toast.show();
                            } else if (name.equals("")){
                                Toast toast = Toast.makeText(getActivity(), R.string.validName, Toast.LENGTH_SHORT);
                                TextView vToast = (TextView) toast.getView().findViewById(android.R.id.message);
                                vToast.setTextColor(Color.RED);
                                vToast.setTextSize(30);
                                toast.show();
                            } else if(count<10){
                                //http://localhost:3781/api/ECardsApi/UpdateVoucher/501
                                new EcardsActivity.Download().execute(imageLink,"haha.png");
                                Toast toast = Toast.makeText(getActivity(), R.string.sent, Toast.LENGTH_SHORT);
                                TextView vToast = (TextView) toast.getView().findViewById(android.R.id.message);
                                vToast.setTextColor(Color.WHITE);
                                vToast.setTextSize(20);
                                vToast.setTypeface(null, Typeface.BOLD);
                                toast.show();
                                count = count + 1;
                            } else if(count>=10){
                                if(total>500000&&total<1000000) count = count - 1;
                                else if(total>1000000&&total<2000000) count = count - 3;
                                else if(total>2000000) count = count - 5;
                                else {
                                    Toast toast = Toast.makeText(getActivity(), R.string.prevent, Toast.LENGTH_SHORT);
                                    TextView vToast = (TextView) toast.getView().findViewById(android.R.id.message);
                                    vToast.setTextColor(Color.RED);
                                    vToast.setTextSize(20);
                                    vToast.setTypeface(null, Typeface.BOLD);
                                    toast.show();
                                    btnSend.setEnabled(false);
                                }
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
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            int indexTab = getArguments().getInt(ARG_SECTION_NUMBER);
            final View rootView = inflater.inflate(R.layout.fragment_ecards, container, false);
            revMail = (EditText) rootView.findViewById(R.id.txtMailRecv);
            receiver = (EditText) rootView.findViewById(R.id.txtRecvName);
            btnSend = (Button) rootView.findViewById(R.id.btnSend);
            imageView = (ImageView) rootView.findViewById(R.id.image);
            tvMessage = (TextView) rootView.findViewById(R.id.tvContent);
            TextView tvYourName = (TextView) rootView.findViewById(R.id.tvYourName);
            tvYourName.setText(sender);
            for(int i = 0; i < list.size(); i++){
                if(indexTab==i+1){
                    PassData2Fragment(i);
                    return rootView;
                }
            }
            return null;
        }
    }

    static class Download extends AsyncTask<String, Void, String> {

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
            new EcardsActivity.SendEmail().execute(mail,
                    "Hotel Foody TV Box", dear + " " + name + ",\n\n"
                            + sender + " " + mess + "\n\nWelcome!" , feed);
        }
    }

    static class SendEmail extends AsyncTask<String, Void, String> {

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
            //
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show number of total pages.
            return list.size();
        }
    }
}
