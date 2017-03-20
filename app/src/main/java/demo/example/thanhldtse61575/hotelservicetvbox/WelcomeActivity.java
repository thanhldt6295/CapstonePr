package demo.example.thanhldtse61575.hotelservicetvbox;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;

import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.Hobby;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.Price;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.Promotional;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.Recommend;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.Service;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WelcomeActivity extends AppCompatActivity {

    TextView customerWel;
    Button dummyBtnEng;
    Button dummyBtnViet;
    public static final String mPath = "roomid.txt";
    private QuoteBank mQuoteBank;
    private List<String> mLines;

    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;

    String[] permissionsRequired;


    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        String[] permission ={  Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION};


        verify(permission);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
        TextView abTitle=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        abTitle.setText("Hotel Service TV Box");
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
        layout.getBackground().setAlpha(51);

        mQuoteBank = new QuoteBank(this);
        try {
            mLines = mQuoteBank.readLine(mPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String roomid = "";
        for (final String string : mLines) {
            setRoomID2Share(string.trim().replaceAll("\\s+$", ""));
            roomid = string;
        }

        class GetCustomerFromServer extends AsyncTask<String, Void, String> {

            protected String doInBackground(String... params) {
                CommonService commonService = new CommonService();
                String returnva = commonService.getData(params[0]);
                return returnva;
            }

            protected void onPostExecute(String response) {
                //parse json sang list service
                final String acc = new Gson().fromJson(response, new TypeToken<String>() {
                }.getType());

                customerWel = (TextView) findViewById(R.id.fullscreen_content3);
                customerWel.setText(acc.toString().toUpperCase().trim());
                setCust2Share(acc);
            }
        }

        class GetServiceFromServer extends AsyncTask<String, Void, String> {

            protected String doInBackground(String... params) {
                CommonService commonService = new CommonService();
                String returnva = commonService.getData(params[0]);
                return returnva;
            }

            protected void onPostExecute(String response) {
                //parse json sang list service
                final List<Service> acc = new Gson().fromJson(response, new TypeToken<List<Service>>() {
                }.getType());

                setServiceList2Share(acc);
            }
        }

        class GetPromoFromServer extends AsyncTask<String, Void, String> {

            protected String doInBackground(String... params) {
                CommonService commonService = new CommonService();
                String returnva = commonService.getData(params[0]);
                return returnva;
            }

            protected void onPostExecute(String response) {
                //parse json sang list service
                final List<Promotional> acc = new Gson().fromJson(response, new TypeToken<List<Promotional>>() {
                }.getType());

                setPromoList2Share(acc);
            }
        }

        class GetImageFromServer extends AsyncTask<String, Void, String> {

            protected String doInBackground(String... params) {
                CommonService commonService = new CommonService();
                String returnva = commonService.getData(params[0]);
                return returnva;
            }

            protected void onPostExecute(String response) {
                //parse json sang list service
                final List<String> acc = new Gson().fromJson(response, new TypeToken<List<String>>() {
                }.getType());

                setImageList2Share(acc);
            }
        }

        class GetHobbyFromServer extends AsyncTask<String, Void, String> {

            protected String doInBackground(String... params) {
                CommonService commonService = new CommonService();
                String returnva = commonService.getData(params[0]);
                return returnva;
            }

            protected void onPostExecute(String response) {
                //parse json sang list service
                final List<Hobby> acc = new Gson().fromJson(response, new TypeToken<List<Hobby>>() {
                }.getType());

                setHobbyList2Share(acc);
            }
        }

        class GetPriceFromServer extends AsyncTask<String, Void, String> {

            protected String doInBackground(String... params) {
                CommonService commonService = new CommonService();
                String returnva = commonService.getData(params[0]);
                return returnva;
            }

            protected void onPostExecute(String response) {
                //parse json sang list service
                final List<Price> acc = new Gson().fromJson(response, new TypeToken<List<Price>>() {
                }.getType());

                setPriceList2Share(acc);
            }
        }

        class GetRecommendFromServer extends AsyncTask<String, Void, String> {

            protected String doInBackground(String... params) {
                CommonService commonService = new CommonService();
                String returnva = commonService.getData(params[0]);
                return returnva;
            }

            protected void onPostExecute(String response) {
                //parse json sang list service
                final List<Recommend> acc = new Gson().fromJson(response, new TypeToken<List<Recommend>>() {
                }.getType());

                setRecommendList2Share(acc);
            }
        }

        new GetCustomerFromServer().execute("http://capstoneserver2017.azurewebsites.net/api/OrdersApi/GetCustNameByRoomID/" + roomid);
        new GetServiceFromServer().execute("http://capstoneserver2017.azurewebsites.net/api/ServicesApi/GetAllService");
        new GetPromoFromServer().execute("http://capstoneserver2017.azurewebsites.net/api/PromotionalsApi/GetPromos");
        new GetImageFromServer().execute("http://capstoneserver2017.azurewebsites.net/api/ImagesApi/GetECardLinks");
        new GetHobbyFromServer().execute("http://capstoneserver2017.azurewebsites.net/api/RecommendsApi/GetHobby");
        new GetPriceFromServer().execute("http://capstoneserver2017.azurewebsites.net/api/RecommendsApi/GetPrice");
        new GetRecommendFromServer().execute("http://capstoneserver2017.azurewebsites.net/api/RecommendsApi/GetRecommend");

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_btnEng2).setOnTouchListener(mDelayHideTouchListener);
        findViewById(R.id.dummy_btnViet2).setOnTouchListener(mDelayHideTouchListener);

        dummyBtnEng = (Button)findViewById(R.id.dummy_btnEng2);
        dummyBtnEng.setFocusable(true);
        dummyBtnEng.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    dummyBtnEng.setBackgroundColor(Color.parseColor("#DABCBCBC"));
                    dummyBtnEng.setTextColor(Color.parseColor("#FFFFFFFF"));
                    dummyBtnEng.setTypeface(Typeface.DEFAULT_BOLD);
                }
                else {
                    dummyBtnEng.setBackgroundColor(Color.parseColor("#dcffffff"));
                    dummyBtnEng.setTextColor(Color.parseColor("#c88b8b8b"));
                    dummyBtnEng.setTypeface(Typeface.DEFAULT);
                }
            }
        });
        dummyBtnViet = (Button)findViewById(R.id.dummy_btnViet2);
        dummyBtnViet.setFocusable(true);
        dummyBtnViet.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    dummyBtnViet.setBackgroundColor(Color.parseColor("#DABCBCBC"));
                    dummyBtnViet.setTextColor(Color.parseColor("#FFFFFFFF"));
                    dummyBtnViet.setTypeface(Typeface.DEFAULT_BOLD);
                }
                else {
                    dummyBtnViet.setBackgroundColor(Color.parseColor("#dcffffff"));
                    dummyBtnViet.setTextColor(Color.parseColor("#c88b8b8b"));
                    dummyBtnViet.setTypeface(Typeface.DEFAULT);
                }
            }
        });

        dummyBtnEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(v.getContext(), "You have selected English", Toast.LENGTH_SHORT);
                TextView vToast = (TextView) toast.getView().findViewById(android.R.id.message);
                vToast.setTextColor(Color.CYAN);
                vToast.setTextSize(30);
                toast.show();
                //Intent mainAct = new Intent(WelcomeActivity.this,MainActivity.class);
                //startActivity(mainAct);
                setLocale("en");
                setLang2Share("en");
            }
        });

        dummyBtnViet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(v.getContext(), "Quý khách đã chọn Tiếng Việt", Toast.LENGTH_SHORT);
                TextView vToast = (TextView) toast.getView().findViewById(android.R.id.message);
                vToast.setTextColor(Color.CYAN);
                vToast.setTextSize(30);
                toast.show();
                setLocale("vi");
                setLang2Share("vi");
            }
        });
    }

    //Begin Check Permission

    public boolean verify(final String[] PERMISSIONS) {

        if (underAPI23()) return true;

        permissionsRequired = PERMISSIONS;
        List<String> pendingPermission = new ArrayList<>();

        for (int i = 0; i < PERMISSIONS.length; i++) {
            int check = ContextCompat.checkSelfPermission(WelcomeActivity.this, PERMISSIONS[i]);
            if (check != PackageManager.PERMISSION_GRANTED) {
                pendingPermission.add(PERMISSIONS[i]);
            }
        }

        int denyPermissionLength = pendingPermission.size();
        final String[] denyPermission = new String[denyPermissionLength];
        for (int i = 0; i < denyPermissionLength; i++) {
            denyPermission[i] = pendingPermission.get(i);
        }

        if (denyPermissionLength > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this);
            builder.setTitle("Need Multiple Permissions");
            builder.setMessage("We needs more Permissions");
            builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    ActivityCompat.requestPermissions(WelcomeActivity.this, denyPermission, PERMISSION_CALLBACK_CONSTANT);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
            return false;
        } else {
            return true;
        }
    }

    public static boolean underAPI23() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_CALLBACK_CONSTANT) {
            //check if all permissions are granted
            boolean allgranted = false;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if(allgranted){
                proceedAfterPermission();
            } else {
                List<String> pendingPermission = new ArrayList<>();

                for (int i = 0; i < permissions.length; i++) {
                    int check = ContextCompat.checkSelfPermission(getBaseContext(), permissions[i]);
                    if (check != PackageManager.PERMISSION_GRANTED) {
                        pendingPermission.add(permissions[i]);
                    }
                }

                int denyPermissionLength = pendingPermission.size();
                final String[] denyPermission = new String[denyPermissionLength];
                for (int i = 0; i < denyPermissionLength; i++) {
                    denyPermission[i] = pendingPermission.get(i);
                }

                if (denyPermissionLength > 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this);
                    builder.setTitle("Need Multiple Permissions");
                    builder.setMessage("Need more permision");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(WelcomeActivity.this, denyPermission, PERMISSION_CALLBACK_CONSTANT);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
            }
        }
    }

    private void proceedAfterPermission() {
        Toast.makeText(getBaseContext(), "We got All Permissions", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(WelcomeActivity.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }

    //End check Permission


    public void setLocale(String lang) {

        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
    }

    private static final String SHARE_LANG = "ShareLang";
    private static final String LOCALE_TAG = "LocalePrefs";

    private void setLang2Share(String lang){
        SharedPreferences sharedPref = this.getSharedPreferences(SHARE_LANG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(LOCALE_TAG, lang);
        editor.commit();
    }

    private static final String SHARE_CUST = "ShareCust";
    private static final String CUST_TAG = "CustName";

    private void setCust2Share(String custname){
        SharedPreferences sharedPref = this.getSharedPreferences(SHARE_CUST, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(CUST_TAG, custname);
        editor.commit();
    }

    private static final String SHARE_ROOM = "ShareRoom";
    private static final String ROOM_ID = "RoomID";

    private void setRoomID2Share(String roomid){
        SharedPreferences sharedPref = this.getSharedPreferences(SHARE_ROOM, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(ROOM_ID, roomid);
        editor.commit();
    }

    private static final String ShSERVICE_TAG = "SharedService";
    private static final String SVLIST_TAG = "ServiceList";

    private void setServiceList2Share(List<Service> list){
        Gson gson = new Gson();
        String jsonCurProduct = gson.toJson(list);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(ShSERVICE_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(SVLIST_TAG, jsonCurProduct);
        editor.commit();
    }

    private static final String ShPROMO_TAG = "SharedPromo";
    private static final String PRLIST_TAG = "PromoList";

    private void setPromoList2Share(List<Promotional> list){
        Gson gson = new Gson();
        String jsonCurProduct = gson.toJson(list);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(ShPROMO_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(PRLIST_TAG, jsonCurProduct);
        editor.commit();
    }

    private static final String ShIMAGE_TAG = "SharedImage";
    private static final String IMGLIST_TAG = "ImageList";

    private void setImageList2Share(List<String> list){
        Gson gson = new Gson();
        String jsonCurProduct = gson.toJson(list);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(ShIMAGE_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(IMGLIST_TAG, jsonCurProduct);
        editor.commit();
    }

    private static final String HOBBY_TAG = "SharedHobby";
    private static final String HOBBY_LIST = "HobbyList";

    private void setHobbyList2Share(List<Hobby> list){
        Gson gson = new Gson();
        String jsonCurProduct = gson.toJson(list);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(HOBBY_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(HOBBY_LIST, jsonCurProduct);
        editor.commit();
    }

    private static final String PRICE_TAG = "SharedPrice";
    private static final String PRICE_LIST = "PriceList";

    private void setPriceList2Share(List<Price> list){
        Gson gson = new Gson();
        String jsonCurProduct = gson.toJson(list);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(PRICE_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(PRICE_LIST, jsonCurProduct);
        editor.commit();
    }

    private static final String RECOMMEND_TAG = "SharedRecommend";
    private static final String RECOMMEND_LIST = "RecommendList";

    private void setRecommendList2Share(List<Recommend> list){
        Gson gson = new Gson();
        String jsonCurProduct = gson.toJson(list);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(RECOMMEND_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(RECOMMEND_LIST, jsonCurProduct);
        editor.commit();
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    public void onBackPressed() { }
}
