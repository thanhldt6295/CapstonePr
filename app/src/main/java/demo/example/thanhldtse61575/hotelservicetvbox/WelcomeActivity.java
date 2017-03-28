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

import demo.example.thanhldtse61575.hotelservicetvbox.entity.Ecards;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.Order;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.Promotional;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.Recommend;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.RecommendImage;
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

        setDefaultPosition2Share();
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

//        class GetCustomerFromServer extends AsyncTask<String, Void, String> {
//
//            protected String doInBackground(String... params) {
//                CommonService commonService = new CommonService();
//                String returnva = commonService.getData(params[0]);
//                return returnva;
//            }
//
//            protected void onPostExecute(String response) {
//                //parse json sang list service
//                final Order acc = new Gson().fromJson(response, new TypeToken<Order>() {
//                }.getType());
//
//                customerWel = (TextView) findViewById(R.id.fullscreen_content3);
//                customerWel.setText(acc.getCustName().toString().toUpperCase().trim());
//                setCust2Share(acc.getCustName());
//                setOrder2Share(acc);
//            }
//        }
//
//        class GetServiceFromServer extends AsyncTask<String, Void, String> {
//
//            protected String doInBackground(String... params) {
//                CommonService commonService = new CommonService();
//                String returnva = commonService.getData(params[0]);
//                return returnva;
//            }
//
//            protected void onPostExecute(String response) {
//                //parse json sang list service
//                final List<Service> acc = new Gson().fromJson(response, new TypeToken<List<Service>>() {
//                }.getType());
//
//                setServiceList2Share(acc);
//            }
//        }
//
//        class GetPromoFromServer extends AsyncTask<String, Void, String> {
//
//            protected String doInBackground(String... params) {
//                CommonService commonService = new CommonService();
//                String returnva = commonService.getData(params[0]);
//                return returnva;
//            }
//
//            protected void onPostExecute(String response) {
//                //parse json sang list service
//                final List<Promotional> acc = new Gson().fromJson(response, new TypeToken<List<Promotional>>() {
//                }.getType());
//
//                setPromoList2Share(acc);
//            }
//        }
//
//        class GetImageFromServer extends AsyncTask<String, Void, String> {
//
//            protected String doInBackground(String... params) {
//                CommonService commonService = new CommonService();
//                String returnva = commonService.getData(params[0]);
//                return returnva;
//            }
//
//            protected void onPostExecute(String response) {
//                //parse json sang list service
//                final List<String> acc = new Gson().fromJson(response, new TypeToken<List<String>>() {
//                }.getType());
//
//                setImageList2Share(acc);
//            }
//        }
//
//        class GetRecommendFromServer extends AsyncTask<String, Void, String> {
//
//            protected String doInBackground(String... params) {
//                CommonService commonService = new CommonService();
//                String returnva = commonService.getData(params[0]);
//                return returnva;
//            }
//
//            protected void onPostExecute(String response) {
//                //parse json sang list service
//                final List<Recommend> acc = new Gson().fromJson(response, new TypeToken<List<Recommend>>() {
//                }.getType());
//
//                setRecommendList2Share(acc);
//            }
//        }
//
//        class GetRecommendImageFromServer extends AsyncTask<String, Void, String> {
//
//            protected String doInBackground(String... params) {
//                CommonService commonService = new CommonService();
//                String returnva = commonService.getData(params[0]);
//                return returnva;
//            }
//
//            protected void onPostExecute(String response) {
//                //parse json sang list service
////                final List<Recommend> acc = new Gson().fromJson(response, new TypeToken<List<Recommend>>() {
////                }.getType());
//
//                setRecommendImageList2Share(response);
//            }
//        }

        class LoadAllDataFromServer extends AsyncTask<String, Void, List<String>> {

            @Override
            protected List<String> doInBackground(String... params) {
                CommonService commonService = new CommonService();
                String Domain = "http://capstoneserver2017.azurewebsites.net/api";
                List<String> return_values = new ArrayList<>();

                //0-OrderInfo
                String GetOrderInfo = "/OrdersApi/GetOrderInfo/" + params[0];
                String return_OrderInfo = commonService.getData(Domain+GetOrderInfo);
                return_values.add(return_OrderInfo);

                //1-AllService
                String GetAllService = "/ServicesApi/GetAllService";
                String return_AllService = commonService.getData(Domain+GetAllService);
                return_values.add(return_AllService);

                //2-Promos
                String GetPromos ="/PromotionalsApi/GetPromos";
                String return_Promos = commonService.getData(Domain+GetPromos);
                return_values.add(return_Promos);

                //3-ECardLinks
                String GetECardLinks = "/ImagesApi/GetECardLinks";
                String return_ECardLinks = commonService.getData(Domain+GetECardLinks);
                return_values.add(return_ECardLinks);

                //4-Recommend
                String GetRecommend = "/RecommendsApi/GetRecommend";
                String return_Recommend = commonService.getData(Domain+GetRecommend);
                return_values.add(return_Recommend);

                //5-RecommendImageLinks
                String GetRecommendImageLinks = "/ImagesApi/GetRecommendLinks";
                String return_RecommendImageLinks = commonService.getData(Domain+GetRecommendImageLinks);
                return_values.add(return_RecommendImageLinks);

                return return_values;
            }

            protected void onPostExecute(List<String> response) {
                //1-AllService
                //List<Service> acc_1 = new Gson().fromJson(response.get(1), new TypeToken<List<Service>>() {}.getType());
                String listService = response.get(1);
                setServiceList2Share(listService);

                //2-Promos
                //List<Promotional> acc_2 = new Gson().fromJson(response.get(2), new TypeToken<List<Promotional>>() {}.getType());
                String listPromotional = response.get(2);
                setPromoList2Share(listPromotional);

                //3-ECardLinks
                //List<Ecards> acc_3 = new Gson().fromJson(response.get(3), new TypeToken<List<Ecards>>() {}.getType());
                String stringImage = response.get(3);
                setImageList2Share(stringImage);

                //4-Recommend
                //List<Recommend> acc_4 = new Gson().fromJson(response.get(4), new TypeToken<List<Recommend>>() {}.getType());
                String listRecommend = response.get(4);
                setRecommendList2Share(listRecommend);

                //5-RecommendImageLinks
                //List<RecommendImage> acc_5 = new Gson().fromJson(response.get(5), new TypeToken<List<RecommendImage>>() {}.getType());
                String listRecommendImage = response.get(5);
                setRecommendImageList2Share(listRecommendImage);


                //0-OrderInfo
                final Order acc_0 = new Gson().fromJson(response.get(0), new TypeToken<Order>() {}.getType());
                customerWel = (TextView) findViewById(R.id.fullscreen_content3);
                customerWel.setText(acc_0.getCustName().toString().toUpperCase().trim());
                setCust2Share(acc_0.getCustName());
                setOrder2Share(acc_0);
            }
        }

//        new GetCustomerFromServer().execute("http://capstoneserver2017.azurewebsites.net/api/OrdersApi/GetOrderInfo/" + roomid);
//        new GetServiceFromServer().execute("http://capstoneserver2017.azurewebsites.net/api/ServicesApi/GetAllService");
//        new GetPromoFromServer().execute("http://capstoneserver2017.azurewebsites.net/api/PromotionalsApi/GetPromos");
//        new GetImageFromServer().execute("http://capstoneserver2017.azurewebsites.net/api/ImagesApi/GetECardLinks");
//        new GetRecommendFromServer().execute("http://capstoneserver2017.azurewebsites.net/api/RecommendsApi/GetRecommend");
//        new GetRecommendImageFromServer().execute("http://capstoneserver2017.azurewebsites.net/api/ImagesApi/GetRecommendLinks");

        new LoadAllDataFromServer().execute(roomid);

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
                    dummyBtnEng.setText("ENGLISH");
                    dummyBtnEng.setTextColor(Color.parseColor("#FFFFFFFF"));
//                    dummyBtnEng.setBackgroundColor(Color.parseColor("#DABCBCBC"));
//                    dummyBtnEng.setTextColor(Color.parseColor("#FFFFFFFF"));
//                    dummyBtnEng.setTypeface(Typeface.DEFAULT_BOLD);
                }
                else {
                    dummyBtnEng.setText("");
//                    dummyBtnEng.setBackgroundColor(Color.parseColor("#dcffffff"));
//                    dummyBtnEng.setTextColor(Color.parseColor("#c88b8b8b"));
//                    dummyBtnEng.setTypeface(Typeface.DEFAULT);
                }
            }
        });
        dummyBtnViet = (Button)findViewById(R.id.dummy_btnViet2);
        dummyBtnViet.setFocusable(true);
        dummyBtnViet.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    dummyBtnViet.setText("TIẾNG VIỆT");
                    dummyBtnViet.setTextColor(Color.parseColor("#FFFFFFFF"));
                }
                else {
                    dummyBtnViet.setText("");
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

    private void setServiceList2Share(String listService){
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(ShSERVICE_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(SVLIST_TAG, listService);
        editor.commit();
    }

    private static final String ShPROMO_TAG = "SharedPromo";
    private static final String PRLIST_TAG = "PromoList";

    private void setPromoList2Share(String listPromotional){
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(ShPROMO_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(PRLIST_TAG, listPromotional);
        editor.commit();
    }

    private static final String ShIMAGE_TAG = "SharedEcard";
    private static final String IMGLIST_TAG = "EcardList";

    private void setImageList2Share(String stringImage){
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(ShIMAGE_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(IMGLIST_TAG, stringImage);
        editor.commit();
    }

    private static final String RECOMMEND_TAG = "SharedRecommend";
    private static final String RECOMMEND_LIST = "RecommendList";

    private void setRecommendList2Share(String listRecommend){
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(RECOMMEND_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(RECOMMEND_LIST, listRecommend);
        editor.commit();
    }

    private static final String ImageShare_TAG = "SharedImageList";
    private static final String ImageShare_LIST = "ImageShareList";

    private void setRecommendImageList2Share(String image_list){

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(ImageShare_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(ImageShare_LIST, image_list);
        editor.commit();
    }


    private static final String SHARE_BILL = "SharedBill";
    private static final String TOTAL_BILL = "BillTotal";

    private void setOrder2Share(Order bill){
        Gson gson = new Gson();
        String jsonCurProduct = gson.toJson(bill);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(SHARE_BILL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(TOTAL_BILL, jsonCurProduct);
        editor.commit();
    }

    private static final String SHARE_RE = "SharePosition";
    private static final String RE_TAG = "PositionName";

    private void setDefaultPosition2Share(){
        SharedPreferences sharedPref = getSharedPreferences(SHARE_RE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(RE_TAG, 0+"");
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
