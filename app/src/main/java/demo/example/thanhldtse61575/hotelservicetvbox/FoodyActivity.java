package demo.example.thanhldtse61575.hotelservicetvbox;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.CartItem;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.Service;

public class FoodyActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private static List<CartItem> cart = new ArrayList<>();
    TextView roomid;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foody);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
        TextView abTitle=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        abTitle.setText(getResources().getString(R.string.food_drink));
        roomid = (TextView) findViewById(R.id.roomid);
        roomid.setText(getResources().getString(R.string.roomid) + " " + getRoomID());

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
                new DatePickerDialog(FoodyActivity.this,date,myCalen.get(Calendar.YEAR), myCalen.get(Calendar.MONTH),
                        myCalen.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabTextColors(-100,-1);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.cart:
                Intent intent = new Intent(this, OrderActivity.class);
                intent.putExtra("storeItem", (Serializable) cart);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        Gson gson = new Gson();

        SharedPreferences sp = getSharedPreferences("cart", Context.MODE_PRIVATE);
        String json = sp.getString("cartinfo", "");

        if (json.length() > 0) {
            cart.clear();
            JsonArray entries = (JsonArray) new JsonParser().parse(json);

            for (int i = 0; i < entries.size(); i++) {
                CartItem cartItem = gson.fromJson(entries.get(i).toString(), CartItem.class);
                cart.add(cartItem);
            }
        }

        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if(cart.size()!=0) {
            final Gson gson = new Gson();
            SharedPreferences sp = getSharedPreferences("cart", Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = sp.edit();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle(R.string.confirm_back);
            builder.setMessage(R.string.confirm_back_question);
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    cart.clear();
                    String str = gson.toJson(cart);
                    editor.putString("cartinfo", str);
                    editor.commit();
                    FoodyActivity.super.onBackPressed();
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //Nothing
                }
            });
            builder.show();
        }
        if(cart.size()==0) {
            FoodyActivity.super.onBackPressed();
        }
    }

    private String getRoomID(){

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("ShareRoom", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("RoomID", "");

        return jsonPreferences;
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
        private GridView grid;
        private RelativeLayout rel;

        private List<Service> getServiceList(){
            Gson gson = new Gson();
            List<Service> list = new ArrayList<>();
            SharedPreferences sharedPref = getActivity().getApplicationContext().getSharedPreferences("SharedService", Context.MODE_PRIVATE);
            String jsonPreferences = sharedPref.getString("ServiceList", "");

            Type type = new TypeToken<List<Service>>() {}.getType();
            list = gson.fromJson(jsonPreferences, type);

            return list;
        }

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

        public void PassData2Tabbed(String cag){
            final List<Service> acc = getServiceList();
            // Search follow categoryName
            final List<Service> accID = new ArrayList<Service>();
            for (Service ac : acc) {
                String cagName = ac.getCategoryName().toString().toUpperCase().trim();
                if (cagName.equals(cag)) {
                    accID.add(new Service(ac.getServiceID(),ac.getServiceName(),ac.getCategoryID(),ac.getCategoryName(),ac.getUnitPrice(),ac.getDescription(),ac.getImage()));
                }
            }
            if(accID.size()==0){
                //Toast.makeText(getActivity().getApplicationContext(), R.string.notitynull, Toast.LENGTH_SHORT).show();
            }
            FoodyAdapter adapter = new FoodyAdapter(getActivity().getApplicationContext(), accID, rel, cart);
            grid.setAdapter(adapter);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            int indexTab = getArguments().getInt(ARG_SECTION_NUMBER);
            View rootView = inflater.inflate(R.layout.fragment_order, container, false);
            grid = (GridView) rootView.findViewById(R.id.gridView);
            rel = (RelativeLayout) rootView.findViewById(R.id.relative);
            if(indexTab==1) {
                PassData2Tabbed(getResources().getString(R.string.breakfast).toString());
                return rootView;
            }
            else if(indexTab==2){
                PassData2Tabbed(getResources().getString(R.string.lunch).toString());
                return rootView;
            }
            else if(indexTab==3){
                PassData2Tabbed(getResources().getString(R.string.dinner).toString());
                return rootView;
            }
            else if(indexTab==4){
                PassData2Tabbed(getResources().getString(R.string.dessert).toString());
                return rootView;
            }
            else if(indexTab==5){
                PassData2Tabbed(getResources().getString(R.string.coffee).toString());
                return rootView;
            }
            else if(indexTab==6){
                PassData2Tabbed(getResources().getString(R.string.wine).toString());
                return rootView;
            }
            else if(indexTab==7){
                PassData2Tabbed(getResources().getString(R.string.fruit).toString());
                return rootView;
            }
            else if(indexTab==8){
                PassData2Tabbed(getResources().getString(R.string.mojito).toString());
                return rootView;
            }
            else if(indexTab==9){
                PassData2Tabbed(getResources().getString(R.string.other_drinks).toString());
                return rootView;
            }
            else {
                return rootView;
            }
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
            // Show 9 total pages.
            return 9;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getString(R.string.breakfast);
                case 1:
                    return getResources().getString(R.string.lunch);
                case 2:
                    return getResources().getString(R.string.dinner);
                case 3:
                    return getResources().getString(R.string.dessert);
                case 4:
                    return getResources().getString(R.string.coffee);
                case 5:
                    return getResources().getString(R.string.wine);
                case 6:
                    return getResources().getString(R.string.fruit);
                case 7:
                    return getResources().getString(R.string.mojito);
                case 8:
                    return getResources().getString(R.string.other_drinks);
            }
            return null;
        }
    }
}
