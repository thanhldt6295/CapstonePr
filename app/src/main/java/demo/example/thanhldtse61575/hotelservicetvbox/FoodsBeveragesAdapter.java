package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.CartItem;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.Service;

/**
 * Created by ThanhLDTSE61575 on 2/19/2017.
 */

public class FoodsBeveragesAdapter extends BaseExpandableListAdapter {
    private List<String> header_titles;
    private HashMap<String, List<String>> child_titles;
    private Context ctx;
    private GridView grid;
    private ImageView image;
    private TextView name;
    private TextView price;
    private TextView description;
    private Button btnOrder;
    private Button btnMinus;
    private Button btnPlus;
    private EditText quantity;
    private List<CartItem> cart = new ArrayList<CartItem>();

    FoodsBeveragesAdapter(Context ctx, List<String> header_titles, HashMap<String,
            List<String>> child_titles, GridView grid, ImageView image, TextView name,
                        TextView price, TextView description, Button btnOrder, Button btnMinus,
                        Button btnPlus, EditText quantity, List<CartItem> cart) {
        this.ctx = ctx;
        this.child_titles = child_titles;
        this.header_titles = header_titles;
        this.grid = grid;
        this.image = image;
        this.name = name;
        this.price = price;
        this.description = description;
        this.btnOrder = btnOrder;
        this.btnMinus = btnMinus;
        this.btnPlus = btnPlus;
        this.quantity = quantity;
        this.cart = cart;
    }

    @Override
    public int getGroupCount() {
        return header_titles.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return child_titles.get(header_titles.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return header_titles.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child_titles.get(header_titles.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String title = (String) this.getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout_headmenuitem, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.textViewHeadList);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(title);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String title = (String) this.getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout_childmenuitem, null);
        }

        final TextView textView = (TextView) convertView.findViewById(R.id.textViewChildList);

        textView.setText(title);

        class GetDataFromServer extends AsyncTask<String, Void, String> {

            protected String doInBackground(String... params) {
                CommonService commonService = new CommonService();
                String returnva = commonService.getData(params[0]);
                return returnva;
            }

            protected void onPostExecute(String response) {
                //parse json sang list service
                final List<Service> acc = new Gson().fromJson(response, new TypeToken<List<Service>>() {
                }.getType());

                ShopGridViewAdapter adapter = new ShopGridViewAdapter(ctx, acc);
                grid.setAdapter(adapter);
                grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        image.setVisibility(View.VISIBLE);
                        name.setVisibility(View.VISIBLE);
                        price.setVisibility(View.VISIBLE);
                        description.setVisibility(View.VISIBLE);
                        btnPlus.setVisibility(View.VISIBLE);
                        btnMinus.setVisibility(View.VISIBLE);
                        quantity.setVisibility(View.VISIBLE);
                        btnOrder.setVisibility(View.VISIBLE);

                        quantity.setText("1");
                        //String url = cart.get(position).getImage();
                        Picasso.with(ctx)
                                .load("http://files.softicons.com/download/system-icons/apple-logo-icons-by-thvg/png/256/Apple%20logo%20icon%20-%20Classic.png")
                                .placeholder(R.drawable.loading)
                                .fit()
                                .centerCrop().into(image);
                        name.setText(acc.get(position).getServiceName());
                        price.setText(acc.get(position).getUnitPrice() + "");
                        description.setText(acc.get(position).getDescription());
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
                                        Service sv = acc.get(position);
                                        if (cart == null)
                                            cart.add(new CartItem(sv.getServiceID(), sv.getServiceName(), sv.getCategoryID(),
                                                    sv.getUnitPrice(), sv.getDescription(), sv.getImage(),
                                                    Integer.parseInt(quantity.getText().toString()), "aaaa"));
                                        else {
                                            boolean isHave = false;
                                            for (CartItem od : cart) {
                                                if (od.getServiceID() == sv.getServiceID()) {
                                                    isHave = true;
                                                    od.setQuantity(od.getQuantity() + Integer.parseInt(quantity.getText().toString()));
                                                }
                                            }
                                            if (!isHave)
                                                cart.add(new CartItem(sv.getServiceID(), sv.getServiceName(), sv.getCategoryID(),
                                                        sv.getUnitPrice(), sv.getDescription(), sv.getImage(),
                                                        Integer.parseInt(quantity.getText().toString()), ""));
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
                        btnMinus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int n = Integer.parseInt(quantity.getText().toString());
                                if (n > 1) {
                                    StringBuilder qty = new StringBuilder();
                                    qty.append(n - 1);
                                    quantity.setText(qty);
                                }
                            }
                        });
                        btnPlus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int n = Integer.parseInt(quantity.getText().toString());
                                if (n < 100) {
                                    StringBuilder qty = new StringBuilder();
                                    qty.append(n + 1);
                                    quantity.setText(qty);
                                }
                            }
                        });

                    }
                });
            }
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (groupPosition == 0 & childPosition == 0) {
                    new GetDataFromServer().execute("http://capstoneserver2017.azurewebsites.net/api/ServicesApi/GetAllService");
                }
                if(groupPosition==0&childPosition==1){

                }
                if(groupPosition==0&childPosition==2){

                }
                if(groupPosition==1&childPosition==0){

                }
                if(groupPosition==1&childPosition==1){

                }
                if(groupPosition==1&childPosition==2){

                }
                if(groupPosition==1&childPosition==3){

                }
                if(groupPosition==1&childPosition==4){

                }
                if(groupPosition==1&childPosition==5){

                }
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
