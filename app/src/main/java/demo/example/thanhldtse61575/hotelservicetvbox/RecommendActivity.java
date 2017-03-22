package demo.example.thanhldtse61575.hotelservicetvbox;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.Recommend;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.RecommendImage;

public class RecommendActivity extends AppCompatActivity {

    private static List<Recommend> recommendList = new ArrayList<>();
    private static List<RecommendImage> recommendImagesList = new ArrayList<>();
    private static int position = 0;

    TextView roomid;

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
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
        TextView abTitle=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        abTitle.setText(getResources().getString(R.string.recommend));

        roomid = (TextView) findViewById(R.id.roomid);
        roomid.setText(getResources().getString(R.string.roomid) + " " + getRoomID());

        recommendList = getListRecommend();
        recommendImagesList = getRecommendImageList();

        RecyclerView rv = (RecyclerView) findViewById(R.id.location_list);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        RecommendListAdapter ma = new RecommendListAdapter(this, recommendList);
        rv.setAdapter(ma);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        AppBarLayout layoutBar = (AppBarLayout) findViewById(R.id.appbar);
        layoutBar.getBackground().setAlpha(102);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabTextColors(-100,-1);
        tabLayout.setupWithViewPager(mViewPager);

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
                new DatePickerDialog(RecommendActivity.this,date,myCalen.get(Calendar.YEAR), myCalen.get(Calendar.MONTH),
                        myCalen.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment{
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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            position = getRecommend(getContext());
            final Recommend recommend = recommendList.get(position);
            int indexTab = getArguments().getInt(ARG_SECTION_NUMBER);
            if(indexTab == 1) {
                View rootView = inflater.inflate(R.layout.fragment_recommend_info, container, false);

                SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        LatLng fpt = new LatLng(10.8528718, 106.6294349);
                        googleMap.addMarker(new MarkerOptions().position(fpt)
                                .title(recommend.getLocationName()));
                        LatLng sydney = new LatLng(recommend.getX(), recommend.getY());
                        googleMap.addMarker(new MarkerOptions().position(sydney)
                                .title(recommend.getLocationName()));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                    }
                });

                TextView locationName = (TextView) rootView.findViewById(R.id.recNameTV);
                locationName.setText(recommend.getLocationName());

                TextView address = (TextView) rootView.findViewById(R.id.recAddressTV);
                address.setText(recommend.getAddress());

                TextView description = (TextView) rootView.findViewById(R.id.recDescriptTV);
                description.setText(recommend.getDescription());

                TextView hobby = (TextView) rootView.findViewById(R.id.hobbyTV);
                hobby.setText(recommend.getDescription());

                TextView price = (TextView) rootView.findViewById(R.id.priceTV);
                price.setText(recommend.getDescription());

                return rootView;
            }else if( indexTab == 2){
                View rootView = inflater.inflate(R.layout.fragment_recommend_galary, container, false);
                List<RecommendImage> list = new ArrayList<>();
                for (RecommendImage ri: recommendImagesList) {
                    if(ri.getSecondID() == recommend.getID()) list.add(ri);
                }
                RecyclerView rvi = (RecyclerView) rootView.findViewById(R.id.image_list);
                rvi.setHasFixedSize(true);
                rvi.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                RecommendImageAdapter mai = new RecommendImageAdapter(getActivity(), list);
                rvi.setAdapter(mai);

                return rootView;
            }else{
                return null;
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
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Information";
                case 1:
                    return "Gallery";
            }
            return null;
        }
    }

    private List<Recommend> getListRecommend(){
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("SharedRecommend", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("RecommendList", "");
        List<Recommend> acc = new Gson().fromJson(jsonPreferences, new TypeToken<List<Recommend>>() {}.getType());
        return acc;
    }

    private static int getRecommend(Context ctxt){
        SharedPreferences sharedPref =  ctxt.getSharedPreferences("SharePosition", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("PositionName", "");
        return Integer.parseInt(jsonPreferences);
    }

    private List<RecommendImage> getRecommendImageList(){
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("SharedImageList", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("ImageShareList", "");
        List<RecommendImage> acc = new Gson().fromJson(jsonPreferences, new TypeToken<List<RecommendImage>>() {}.getType());
        return acc;
    }

    private String getRoomID(){

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("ShareRoom", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("RoomID", "");

        return jsonPreferences;
    }

    @Override
    public void onBackPressed() {
        this.startActivity(new Intent(RecommendActivity.this, MainActivity.class));
    }
}
