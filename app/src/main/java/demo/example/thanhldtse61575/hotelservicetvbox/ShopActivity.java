package demo.example.thanhldtse61575.hotelservicetvbox;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.BagdeDrawable;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.CartItem;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.Foody;

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
    private static List<Foody> serviceList = new ArrayList<>();
    private static List<CartItem> cart = new ArrayList<>();
    private static GridView gridView;
    private static RelativeLayout relativeLayout;
    private static PopupWindow popup;
    private static LayoutInflater layoutInflater;
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
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem itemCart = menu.findItem(R.id.action_cart);
        LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
        setBadgeCount(this, icon, getCartQuantity(cart)+"");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_cart:
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

        invalidateOptionsMenu();

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

    private int getCartQuantity(List<CartItem> cart){
        int q = 0;
        for (int i = 0; i < cart.size(); i++){
            q += cart.get(i).getQuantity();
        }
        return q;
    }

    private String getRoomID(){

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("ShareRoom", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("RoomID", "");

        return jsonPreferences;
    }

    private List<Foody> getServiceList(){
        Gson gson = new Gson();
        List<Foody> list = new ArrayList<>();
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("SharedService", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("ServiceList", "");

        Type type = new TypeToken<List<Foody>>() {}.getType();
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
            final List<Foody> serviceCagList = new ArrayList<Foody>();
            for (Foody ac : serviceList) {
                String cagName = ac.getCategoryName().toString().toUpperCase().trim().replaceAll("\\s+$", "");
                if (cagName.equals(cag)) {
                    serviceCagList.add(new Foody(ac.getServiceID(),ac.getServiceName(),ac.getCategoryID(),ac.getCategoryName(),ac.getUnitPrice(),ac.getDescription(),ac.getImage()));
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

                    Button btnPlus = (Button) container.findViewById(R.id.btnPlus);
                    Button btnMinus = (Button) container.findViewById(R.id.btnMinus);
                    final Button btnOrder = (Button) container.findViewById(R.id.btnOrder);
                    ImageView imgIcon = (ImageView) container.findViewById(R.id.imageViewDetail);
                    TextView item = (TextView) container.findViewById(R.id.txtServiceName);
                    TextView price = (TextView) container.findViewById(R.id.txtUnitPrice);
                    TextView description = (TextView) container.findViewById(R.id.txtDescription);
                    final EditText quantty = (EditText) container.findViewById(R.id.txtQuantity);
                    quantty.setText("1");
                    final int[] qty = {0};

                    String url = serviceCagList.get(position).getImage();
                    Picasso.with(getActivity())
                            .load(url)
                            .placeholder(R.drawable.loading)
                            .fit()
                            .centerCrop().into(imgIcon);
                    item.setText(serviceCagList.get(position).getServiceName());
                    final DecimalFormat format = new DecimalFormat("###,###.#");
                    price.setText(format.format(serviceCagList.get(position).getUnitPrice()) + " " + container.getResources().getString(R.string.USD));
                    description.setText(serviceCagList.get(position).getDescription());

                    btnMinus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int n = Integer.parseInt(quantty.getText().toString());
                            if (n > 1) {
                                qty[0] -= 1;
                                quantty.setText(qty[0]+"");
                            }
                        }
                    });
                    btnPlus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int n = Integer.parseInt(quantty.getText().toString());
                            if (n < 100) {
                                qty[0] +=1;
                                quantty.setText(qty[0]+"");
                            }
                        }
                    });

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
                                    Foody sv = serviceCagList.get(position);
                                    if (cart.size() == 0) {
                                        if(Integer.parseInt(quantty.getText().toString())>100){
                                            btnOrder.setEnabled(false);
                                            Toast toast = Toast.makeText(getActivity(), R.string.over, Toast.LENGTH_SHORT);
                                            TextView vToast = (TextView) toast.getView().findViewById(android.R.id.message);
                                            vToast.setTextColor(Color.RED);
                                            vToast.setTextSize(20);
                                            toast.show();
                                        } else {
                                            cart.add(new CartItem(sv.getServiceID(), sv.getServiceName(), sv.getCategoryID(),
                                                    sv.getUnitPrice(), sv.getDescription(), sv.getImage(),
                                                    Integer.parseInt(quantty.getText().toString()), ""));
                                            Toast toast = Toast.makeText(getActivity(), R.string.added, Toast.LENGTH_SHORT);
                                            TextView vToast = (TextView) toast.getView().findViewById(android.R.id.message);
                                            vToast.setTextColor(Color.CYAN);
                                            vToast.setTextSize(20);
                                            vToast.setTypeface(null, Typeface.BOLD);
                                            toast.show();
                                        }
                                    }
                                    else {
                                        boolean isHave = false;
                                        for (CartItem od : cart) {
                                            if (od.getServiceID() == sv.getServiceID()) {
                                                isHave = true;
                                                if(od.getQuantity() + Integer.parseInt(quantty.getText().toString())>100){
                                                    btnOrder.setEnabled(false);
                                                    Toast toast = Toast.makeText(getActivity(), R.string.over, Toast.LENGTH_SHORT);
                                                    TextView vToast = (TextView) toast.getView().findViewById(android.R.id.message);
                                                    vToast.setTextColor(Color.RED);
                                                    vToast.setTextSize(20);
                                                    toast.show();
                                                } else {
                                                    od.setQuantity(od.getQuantity() + Integer.parseInt(quantty.getText().toString()));
                                                    Toast toast = Toast.makeText(getActivity(), R.string.added, Toast.LENGTH_SHORT);
                                                    TextView vToast = (TextView) toast.getView().findViewById(android.R.id.message);
                                                    vToast.setTextColor(Color.CYAN);
                                                    vToast.setTextSize(20);
                                                    vToast.setTypeface(null, Typeface.BOLD);
                                                    toast.show();
                                                }
                                            }
                                        }
                                        if (!isHave) {
                                            if(Integer.parseInt(quantty.getText().toString())>100){
                                                btnOrder.setEnabled(false);
                                                Toast toast = Toast.makeText(getActivity(), R.string.over, Toast.LENGTH_SHORT);
                                                TextView vToast = (TextView) toast.getView().findViewById(android.R.id.message);
                                                vToast.setTextColor(Color.RED);
                                                vToast.setTextSize(20);
                                                toast.show();
                                            } else {
                                                cart.add(new CartItem(sv.getServiceID(), sv.getServiceName(), sv.getCategoryID(),
                                                        sv.getUnitPrice(), sv.getDescription(), sv.getImage(),
                                                        Integer.parseInt(quantty.getText().toString()), ""));
                                                Toast toast = Toast.makeText(getActivity(), R.string.added, Toast.LENGTH_SHORT);
                                                TextView vToast = (TextView) toast.getView().findViewById(android.R.id.message);
                                                vToast.setTextColor(Color.CYAN);
                                                vToast.setTextSize(20);
                                                vToast.setTypeface(null, Typeface.BOLD);
                                                toast.show();
                                            }
                                        }
                                    }
                                    getActivity().invalidateOptionsMenu();

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

    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {

        BagdeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BagdeDrawable) {
            badge = (BagdeDrawable) reuse;
        } else {
            badge = new BagdeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
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