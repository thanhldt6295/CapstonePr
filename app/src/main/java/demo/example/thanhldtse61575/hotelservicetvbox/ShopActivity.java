package demo.example.thanhldtse61575.hotelservicetvbox;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.CartItem;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.Service;

/**
 * Created by ThanhLDTSE61575 on 3/11/2017.
 */

public class ShopActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private ShopActivity.SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private static Menu mMenu;
    private static List<Service> serviceList = new ArrayList<>();
    private static List<CartItem> cart = new ArrayList<>();
    private static GridView gridView;
    private static RelativeLayout relativeLayout;
    private static PopupWindow popup;
    private static LayoutInflater layoutInflater;
    private static int quantity = 0;
    TextView roomid;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foody);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar);
        TextView abTitle=(TextView)findViewById(getResources().getIdentifier("action_bar_title", "id", getPackageName()));
        abTitle.setText(getResources().getString(R.string.shopping));
        roomid = (TextView) findViewById(R.id.roomid);
        roomid.setText(getResources().getString(R.string.roomid) + " " + getRoomID());

        serviceList = getServiceList();

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
                new DatePickerDialog(ShopActivity.this,date,myCalen.get(Calendar.YEAR), myCalen.get(Calendar.MONTH),
                        myCalen.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new ShopActivity.SectionsPagerAdapter(getSupportFragmentManager());

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
        mMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    private static void setActionIcon(boolean showWithBadge)
    {
        MenuItem item = mMenu.findItem(R.id.cart);

        if(mMenu != null)
        {
            if(showWithBadge)
            {
                item.setIcon(R.drawable.ic_carted);
            }
            else
            {
                item.setIcon(R.drawable.ic_cart);
            }
        }
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
                    ShopActivity.super.onBackPressed();
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
            ShopActivity.super.onBackPressed();
        }
    }

    private String getRoomID(){

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("ShareRoom", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("RoomID", "");

        return jsonPreferences;
    }

    private List<Service> getServiceList(){
        Gson gson = new Gson();
        List<Service> list = new ArrayList<>();
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("SharedService", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("ServiceList", "");

        Type type = new TypeToken<List<Service>>() {}.getType();
        list = gson.fromJson(jsonPreferences, type);

        return list;
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
        public static ShopActivity.PlaceholderFragment newInstance(int sectionNumber) {
            ShopActivity.PlaceholderFragment fragment = new ShopActivity.PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public void PassData2Tabbed(String cag){
            // Search follow categoryName
            final List<Service> serviceCagList = new ArrayList<Service>();
            for (Service ac : serviceList) {
                String cagName = ac.getCategoryName().toString().toUpperCase().trim();
                if (cagName.equals(cag)) {
                    serviceCagList.add(new Service(ac.getServiceID(),ac.getServiceName(),ac.getCategoryID(),ac.getCategoryName(),ac.getUnitPrice(),ac.getDescription(),ac.getImage()));
                }
            }
            if(serviceCagList.size()==0){
                //Toast.makeText(getActivity().getApplicationContext(), R.string.notitynull, Toast.LENGTH_SHORT).show();
            }
            FoodyAdapter adapter = new FoodyAdapter(getActivity().getApplicationContext(), serviceCagList);
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.layout_itemdetails, null);
                    popup = new PopupWindow(container, 600, 600, true);
                    popup.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);

                    Button btnOrder = (Button) container.findViewById(R.id.btnOrder);
                    ImageView icon = (ImageView) container.findViewById(R.id.imageViewDetail);
                    TextView item = (TextView) container.findViewById(R.id.txtServiceName);
                    TextView price = (TextView) container.findViewById(R.id.txtUnitPrice);
                    TextView description = (TextView) container.findViewById(R.id.txtDescription);

                    String url = serviceCagList.get(position).getImage();
                    Picasso.with(getActivity())
                            .load(url)
                            .placeholder(R.drawable.loading)
                            .fit()
                            .centerCrop().into(icon);
                    item.setText(serviceCagList.get(position).getServiceName());
                    price.setText(serviceCagList.get(position).getUnitPrice() + "");
                    description.setText(serviceCagList.get(position).getDescription());

                    btnOrder.setOnTouchListener(new View.OnTouchListener() {
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
                                    if(cart.size()==0) {
                                        quantity = 0;
                                        setActionIcon(false);
                                    }
                                    quantity = quantity + 1;
                                    Toast toast = Toast.makeText(getActivity(), quantity+"", Toast.LENGTH_SHORT);
                                    TextView vToast = (TextView) toast.getView().findViewById(android.R.id.message);
                                    vToast.setTextColor(Color.CYAN);
                                    vToast.setTextSize(30);
                                    toast.show();
                                    setActionIcon(true);

                                    Service sv = serviceCagList.get(position);
                                    if (cart.size() == 0) {
                                        cart.add(new CartItem(sv.getServiceID(), sv.getServiceName(), sv.getCategoryID(),
                                                sv.getUnitPrice(), sv.getDescription(), sv.getImage(), 1, ""));
                                    }
                                    else {
                                        boolean isHave = false;
                                        for (CartItem od : cart) {
                                            if (od.getServiceID() == sv.getServiceID()) {
                                                isHave = true;
                                                od.setQuantity(od.getQuantity() + 1);
                                            }
                                        }
                                        if (!isHave) {
                                            cart.add(new CartItem(sv.getServiceID(), sv.getServiceName(), sv.getCategoryID(),
                                                    sv.getUnitPrice(), sv.getDescription(), sv.getImage(), 1, ""));
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

                    container.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            popup.dismiss();
                            return true;
                        }
                    });
                }
            });
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_order, container, false);
            gridView = (GridView) rootView.findViewById(R.id.gridView);
            relativeLayout = (RelativeLayout) rootView.findViewById(R.id.relative);
            if(getArguments().getInt(ARG_SECTION_NUMBER)==1) {
                PassData2Tabbed(getResources().getString(R.string.souvenir).toString());
                return rootView;
            }
            if(getArguments().getInt(ARG_SECTION_NUMBER)==2){
                PassData2Tabbed(getResources().getString(R.string.keychain).toString());
                return rootView;
            }
            if(getArguments().getInt(ARG_SECTION_NUMBER)==3){
                PassData2Tabbed(getResources().getString(R.string.houseware).toString());
                return rootView;
            }
            if(getArguments().getInt(ARG_SECTION_NUMBER)==4){
                PassData2Tabbed(getResources().getString(R.string.lighter).toString());
                return rootView;
            }
            if(getArguments().getInt(ARG_SECTION_NUMBER)==5){
                PassData2Tabbed(getResources().getString(R.string.tshirtf).toString());
                return rootView;
            }
            if(getArguments().getInt(ARG_SECTION_NUMBER)==6){
                PassData2Tabbed(getResources().getString(R.string.tshirtm).toString());
                return rootView;
            }
            if(getArguments().getInt(ARG_SECTION_NUMBER)==7){
                PassData2Tabbed(getResources().getString(R.string.coatf).toString());
                return rootView;
            }
            if(getArguments().getInt(ARG_SECTION_NUMBER)==8){
                PassData2Tabbed(getResources().getString(R.string.coatm).toString());
                return rootView;
            }
            if(getArguments().getInt(ARG_SECTION_NUMBER)==9){
                PassData2Tabbed(getResources().getString(R.string.toy).toString());
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
            return ShopActivity.PlaceholderFragment.newInstance(position + 1);
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
                    return getResources().getString(R.string.souvenir);
                case 1:
                    return getResources().getString(R.string.keychain);
                case 2:
                    return getResources().getString(R.string.houseware);
                case 3:
                    return getResources().getString(R.string.lighter);
                case 4:
                    return getResources().getString(R.string.tshirtf);
                case 5:
                    return getResources().getString(R.string.tshirtm);
                case 6:
                    return getResources().getString(R.string.coatf);
                case 7:
                    return getResources().getString(R.string.coatm);
                case 8:
                    return getResources().getString(R.string.toy);
            }
            return null;
        }
    }
}