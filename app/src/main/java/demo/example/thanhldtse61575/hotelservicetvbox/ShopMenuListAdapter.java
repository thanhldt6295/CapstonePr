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
 * Created by ThanhLDTSE61575 on 1/9/2017.
 */
public class ShopMenuListAdapter extends BaseExpandableListAdapter {
    private List<String> header_titles;
    private HashMap<String,List<String>> child_titles;
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
    private List<CartItem> list = new ArrayList<CartItem>();
    ShopMenuListAdapter(Context ctx, List<String> header_titles, HashMap<String,List<String>> child_titles, GridView grid,
                        ImageView image, TextView name, TextView price, TextView description, Button btnOrder,
                        Button btnMinus, Button btnPlus, EditText quantity, List<CartItem> list){
        this.ctx=ctx;
        this.child_titles=child_titles;
        this.header_titles=header_titles;
        this.grid=grid;
        this.image = image;
        this.name = name;
        this.price = price;
        this.description = description;
        this.btnOrder = btnOrder;
        this.btnMinus = btnMinus;
        this.btnPlus = btnPlus;
        this.quantity = quantity;
        this.list=list;
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
        if(convertView==null){
            LayoutInflater layoutInflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout_headmenuitem,null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.textViewHeadList);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(title);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String title = (String) this.getChild(groupPosition,childPosition);
        if(convertView==null){
            LayoutInflater layoutInflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout_childmenuitem,null);
        }

        final TextView textView = (TextView) convertView.findViewById(R.id.textViewChildList);

        textView.setText(title);

        class GetDataFromSrever extends AsyncTask<String, Void, String> {

            protected String doInBackground(String... params) {
                CommonService commonService = new CommonService();
                String returnva = commonService.getData(params[0]);
                return returnva;
            }

            protected void onPostExecute(String response) {
                //pare json sang list service
                final List<Service> acc = new Gson().fromJson(response, new TypeToken<List<Service>>() {}.getType());

                ItemGridAdapter adapter = new ItemGridAdapter(ctx, acc);
                grid.setAdapter(adapter);
                grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        quantity.setText("1");
                        image.setImageResource(R.drawable.demo);
//                        Picasso.with(ctx)
//                                .load(url)
//                                .placeholder(R.drawable.loading)
//                                .fit()
//                                .centerCrop().into(image);
                        name.setText(acc.get(position).getServiceName());
                        price.setText(acc.get(position).getUnitPrice()+"");
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
                                        if(list==null) list.add(new CartItem(sv.getServiceID(),sv.getServiceName(),sv.getCategoryID(),
                                                sv.getUnitPrice(), sv.getDescription(), sv.getImage(),
                                                Integer.parseInt(quantity.getText().toString())));
                                        else {
                                            boolean isHave=false;
                                            for (CartItem od: list) {
                                                if(od.getServiceID()==sv.getServiceID()){
                                                    isHave=true;
                                                    od.setQuantity(od.getQuantity()+Integer.parseInt(quantity.getText().toString()));
                                                }
                                            }
                                            if(!isHave) list.add(new CartItem(sv.getServiceID(),sv.getServiceName(),sv.getCategoryID(),
                                                    sv.getUnitPrice(), sv.getDescription(), sv.getImage(),
                                                    Integer.parseInt(quantity.getText().toString())));
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
                                if(n>1) {
                                    StringBuilder qty = new StringBuilder();
                                    qty.append(n-1);
                                    quantity.setText(qty);
                                }
                            }
                        });
                        btnPlus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int n = Integer.parseInt(quantity.getText().toString());
                                if(n<100) {
                                    StringBuilder qty = new StringBuilder();
                                    qty.append(n+1);
                                    quantity.setText(qty);
                                }
                            }
                        });
                    }
                });
                //pare object hoac list object sang json
                String retu = new Gson().toJson(acc);
            }
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(groupPosition==0&childPosition==0){
                    new GetDataFromSrever().execute("http://capstoneserver2017.azurewebsites.net/api/ServicesApi/GetAllService");
                }
//                if(groupPosition==0&childPosition==1){
//                    final String itemList[] = {"A", "B", "C", "D"};
//                    final String itemPrice[] = {"35000", "20000", "15000", "30000"};
//                    final String descript[] = {"dadasdasdasd", "abcdjdadkas", "weqeqwewqe", "dadasdasdasd"};
//                    final int itemIcon[] = {R.drawable.demo, R.drawable.img, R.drawable.img, R.drawable.img};
//
//                    ItemGridAdapter adapter = new ItemGridAdapter(ctx, itemIcon, itemList);
//                    grid.setAdapter(adapter);
//                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            quantity.setText("1");
//                            image.setImageResource(itemIcon[position]);
//                            name.setText(itemList[position]);
//                            price.setText(itemPrice[position]);
//                            description.setText(descript[position]);
//                            btnOrder.setOnTouchListener(new View.OnTouchListener() {
//                                @Override
//                                public boolean onTouch(View v, MotionEvent event) {
//                                    switch (event.getAction()) {
//                                        case MotionEvent.ACTION_DOWN: {
//                                            Button view = (Button) v;
//                                            view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
//                                            v.invalidate();
//                                            break;
//                                        }
//                                        case MotionEvent.ACTION_UP:
//                                            // Your action here on button click
//                                        case MotionEvent.ACTION_CANCEL: {
//                                            Button view = (Button) v;
//                                            view.getBackground().clearColorFilter();
//                                            view.invalidate();
//                                            break;
//                                        }
//                                    }
//                                    return true;
//                                }
//                            });
//                            btnMinus.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    int n = Integer.parseInt(quantity.getText().toString());
//                                    if(n>1) {
//                                        StringBuilder qty = new StringBuilder();
//                                        qty.append(n-1);
//                                        quantity.setText(qty);
//                                    }
//                                }
//                            });
//                            btnPlus.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    int n = Integer.parseInt(quantity.getText().toString());
//                                    if(n<100) {
//                                        StringBuilder qty = new StringBuilder();
//                                        qty.append(n+1);
//                                        quantity.setText(qty);
//                                    }
//                                }
//                            });
//                            //Toast.makeText(ShopMenuListAdapter.this,itemList[position],Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//                if(groupPosition==0&childPosition==2){
//                    final String itemList[] = {"C", "B", "C", "C", "C", "C"};
//                    final String itemPrice[] = {"20000", "25000", "10000", "15000", "15000", "30000"};
//                    final String descript[] = {"abcdjdadkas", "weqeqwewqe", "dadasdasdasd", "abcdjdadkas", "weqeqwewqe", "dadasdasdasd"};
//                    final int itemIcon[] = {R.drawable.demo, R.drawable.img, R.drawable.img, R.drawable.img, R.drawable.demo, R.drawable.img};
//
//                    ItemGridAdapter adapter = new ItemGridAdapter(ctx, itemIcon, itemList);
//                    grid.setAdapter(adapter);
//                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            quantity.setText("1");
//                            image.setImageResource(itemIcon[position]);
//                            name.setText(itemList[position]);
//                            price.setText(itemPrice[position]);
//                            description.setText(descript[position]);
//                            btnOrder.setOnTouchListener(new View.OnTouchListener() {
//                                @Override
//                                public boolean onTouch(View v, MotionEvent event) {
//                                    switch (event.getAction()) {
//                                        case MotionEvent.ACTION_DOWN: {
//                                            Button view = (Button) v;
//                                            view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
//                                            v.invalidate();
//                                            break;
//                                        }
//                                        case MotionEvent.ACTION_UP:
//                                            // Your action here on button click
//                                        case MotionEvent.ACTION_CANCEL: {
//                                            Button view = (Button) v;
//                                            view.getBackground().clearColorFilter();
//                                            view.invalidate();
//                                            break;
//                                        }
//                                    }
//                                    return true;
//                                }
//                            });
//                            btnMinus.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    int n = Integer.parseInt(quantity.getText().toString());
//                                    if(n>1) {
//                                        StringBuilder qty = new StringBuilder();
//                                        qty.append(n-1);
//                                        quantity.setText(qty);
//                                    }
//                                }
//                            });
//                            btnPlus.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    int n = Integer.parseInt(quantity.getText().toString());
//                                    if(n<100) {
//                                        StringBuilder qty = new StringBuilder();
//                                        qty.append(n+1);
//                                        quantity.setText(qty);
//                                    }
//                                }
//                            });
//                            //Toast.makeText(ShopMenuListAdapter.this,itemList[position],Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//                if(groupPosition==1&childPosition==0){
//                    final String itemList[] = {"B", "B", "B", "B", "B", "B"};
//                    final String itemPrice[] = {"20000", "25000", "10000", "15000", "15000", "30000"};
//                    final String descript[] = {"abcdjdadkas", "weqeqwewqe", "dadasdasdasd", "abcdjdadkas", "weqeqwewqe", "dadasdasdasd"};
//                    final int itemIcon[] = {R.drawable.img, R.drawable.img, R.drawable.img, R.drawable.img, R.drawable.img, R.drawable.img};
//
//                    ItemGridAdapter adapter = new ItemGridAdapter(ctx, itemIcon, itemList);
//                    grid.setAdapter(adapter);
//                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            quantity.setText("1");
//                            image.setImageResource(itemIcon[position]);
//                            name.setText(itemList[position]);
//                            price.setText(itemPrice[position]);
//                            description.setText(descript[position]);
//                            btnOrder.setOnTouchListener(new View.OnTouchListener() {
//                                @Override
//                                public boolean onTouch(View v, MotionEvent event) {
//                                    switch (event.getAction()) {
//                                        case MotionEvent.ACTION_DOWN: {
//                                            Button view = (Button) v;
//                                            view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
//                                            v.invalidate();
//                                            break;
//                                        }
//                                        case MotionEvent.ACTION_UP:
//                                            // Your action here on button click
//                                        case MotionEvent.ACTION_CANCEL: {
//                                            Button view = (Button) v;
//                                            view.getBackground().clearColorFilter();
//                                            view.invalidate();
//                                            break;
//                                        }
//                                    }
//                                    return true;
//                                }
//                            });
//                            btnMinus.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    int n = Integer.parseInt(quantity.getText().toString());
//                                    if(n>1) {
//                                        StringBuilder qty = new StringBuilder();
//                                        qty.append(n-1);
//                                        quantity.setText(qty);
//                                    }
//                                }
//                            });
//                            btnPlus.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    int n = Integer.parseInt(quantity.getText().toString());
//                                    if(n<100) {
//                                        StringBuilder qty = new StringBuilder();
//                                        qty.append(n+1);
//                                        quantity.setText(qty);
//                                    }
//                                }
//                            });
//                            //Toast.makeText(ShopMenuListAdapter.this,itemList[position],Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//                if(groupPosition==1&childPosition==1){
//                    final String itemList[] = {"A", "B", "C", "D"};
//                    final String itemPrice[] = {"35000", "20000", "15000", "30000"};
//                    final String descript[] = {"dadasdasdasd", "abcdjdadkas", "weqeqwewqe", "dadasdasdasd"};
//                    final int itemIcon[] = {R.drawable.img, R.drawable.img, R.drawable.img, R.drawable.img};
//
//                    ItemGridAdapter adapter = new ItemGridAdapter(ctx, itemIcon, itemList);
//                    grid.setAdapter(adapter);
//                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            quantity.setText("1");
//                            image.setImageResource(itemIcon[position]);
//                            name.setText(itemList[position]);
//                            price.setText(itemPrice[position]);
//                            description.setText(descript[position]);
//                            btnOrder.setOnTouchListener(new View.OnTouchListener() {
//                                @Override
//                                public boolean onTouch(View v, MotionEvent event) {
//                                    switch (event.getAction()) {
//                                        case MotionEvent.ACTION_DOWN: {
//                                            Button view = (Button) v;
//                                            view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
//                                            v.invalidate();
//                                            break;
//                                        }
//                                        case MotionEvent.ACTION_UP:
//                                            // Your action here on button click
//                                        case MotionEvent.ACTION_CANCEL: {
//                                            Button view = (Button) v;
//                                            view.getBackground().clearColorFilter();
//                                            view.invalidate();
//                                            break;
//                                        }
//                                    }
//                                    return true;
//                                }
//                            });
//                            btnMinus.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    int n = Integer.parseInt(quantity.getText().toString());
//                                    if(n>1) {
//                                        StringBuilder qty = new StringBuilder();
//                                        qty.append(n-1);
//                                        quantity.setText(qty);
//                                    }
//                                }
//                            });
//                            btnPlus.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    int n = Integer.parseInt(quantity.getText().toString());
//                                    if(n<100) {
//                                        StringBuilder qty = new StringBuilder();
//                                        qty.append(n+1);
//                                        quantity.setText(qty);
//                                    }
//                                }
//                            });
//                            //Toast.makeText(ShopMenuListAdapter.this,itemList[position],Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//                if(groupPosition==1&childPosition==2){
//                    final String itemList[] = {"TTTTT", "BC"};
//                    final String itemPrice[] = {"20000","26000"};
//                    final String descript[] = {"weqeqwewqe", "dadasdasdasd"};
//                    final int itemIcon[] = {R.drawable.img, R.drawable.img};
//
//                    ItemGridAdapter adapter = new ItemGridAdapter(ctx, itemIcon, itemList);
//                    grid.setAdapter(adapter);
//                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            quantity.setText("1");
//                            image.setImageResource(itemIcon[position]);
//                            name.setText(itemList[position]);
//                            price.setText(itemPrice[position]);
//                            description.setText(descript[position]);
//                            btnOrder.setOnTouchListener(new View.OnTouchListener() {
//                                @Override
//                                public boolean onTouch(View v, MotionEvent event) {
//                                    switch (event.getAction()) {
//                                        case MotionEvent.ACTION_DOWN: {
//                                            Button view = (Button) v;
//                                            view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
//                                            v.invalidate();
//                                            break;
//                                        }
//                                        case MotionEvent.ACTION_UP:
//                                            // Your action here on button click
//                                        case MotionEvent.ACTION_CANCEL: {
//                                            Button view = (Button) v;
//                                            view.getBackground().clearColorFilter();
//                                            view.invalidate();
//                                            break;
//                                        }
//                                    }
//                                    return true;
//                                }
//                            });
//                            btnMinus.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    int n = Integer.parseInt(quantity.getText().toString());
//                                    if(n>1) {
//                                        StringBuilder qty = new StringBuilder();
//                                        qty.append(n-1);
//                                        quantity.setText(qty);
//                                    }
//                                }
//                            });
//                            btnPlus.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    int n = Integer.parseInt(quantity.getText().toString());
//                                    if(n<100) {
//                                        StringBuilder qty = new StringBuilder();
//                                        qty.append(n+1);
//                                        quantity.setText(qty);
//                                    }
//                                }
//                            });
//                            //Toast.makeText(ShopMenuListAdapter.this,itemList[position],Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//                if(groupPosition==1&childPosition==3){
//                    final String itemList[] = {"AAAA", "FFFFF"};
//                    final String itemPrice[] = {"30000","26000"};
//                    final String descript[] = {"weqeqwewqe", "dadasdasdasd"};
//                    final int itemIcon[] = {R.drawable.demo, R.drawable.demo};
//
//                    ItemGridAdapter adapter = new ItemGridAdapter(ctx, itemIcon, itemList);
//                    grid.setAdapter(adapter);
//                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            quantity.setText("1");
//                            image.setImageResource(itemIcon[position]);
//                            name.setText(itemList[position]);
//                            price.setText(itemPrice[position]);
//                            description.setText(descript[position]);
//                            btnOrder.setOnTouchListener(new View.OnTouchListener() {
//                                @Override
//                                public boolean onTouch(View v, MotionEvent event) {
//                                    switch (event.getAction()) {
//                                        case MotionEvent.ACTION_DOWN: {
//                                            Button view = (Button) v;
//                                            view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
//                                            v.invalidate();
//                                            break;
//                                        }
//                                        case MotionEvent.ACTION_UP:
//                                            // Your action here on button click
//                                        case MotionEvent.ACTION_CANCEL: {
//                                            Button view = (Button) v;
//                                            view.getBackground().clearColorFilter();
//                                            view.invalidate();
//                                            break;
//                                        }
//                                    }
//                                    return true;
//                                }
//                            });
//                            btnMinus.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    int n = Integer.parseInt(quantity.getText().toString());
//                                    if(n>1) {
//                                        StringBuilder qty = new StringBuilder();
//                                        qty.append(n-1);
//                                        quantity.setText(qty);
//                                    }
//                                }
//                            });
//                            btnPlus.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    int n = Integer.parseInt(quantity.getText().toString());
//                                    if(n<100) {
//                                        StringBuilder qty = new StringBuilder();
//                                        qty.append(n+1);
//                                        quantity.setText(qty);
//                                    }
//                                }
//                            });
//                            //Toast.makeText(ShopMenuListAdapter.this,itemList[position],Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//                if(groupPosition==1&childPosition==4){
//                    final String itemList[] = {"TTTTT"};
//                    final String itemPrice[] = {"30000"};
//                    final String descript[] = {"weqeqwewqe"};
//                    final int itemIcon[] = {R.drawable.demo};
//
//                    ItemGridAdapter adapter = new ItemGridAdapter(ctx, itemIcon, itemList);
//                    grid.setAdapter(adapter);
//                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            quantity.setText("1");
//                            image.setImageResource(itemIcon[position]);
//                            name.setText(itemList[position]);
//                            price.setText(itemPrice[position]);
//                            description.setText(descript[position]);
//                            btnOrder.setOnTouchListener(new View.OnTouchListener() {
//                                @Override
//                                public boolean onTouch(View v, MotionEvent event) {
//                                    switch (event.getAction()) {
//                                        case MotionEvent.ACTION_DOWN: {
//                                            Button view = (Button) v;
//                                            view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
//                                            v.invalidate();
//                                            break;
//                                        }
//                                        case MotionEvent.ACTION_UP:
//                                            // Your action here on button click
//                                        case MotionEvent.ACTION_CANCEL: {
//                                            Button view = (Button) v;
//                                            view.getBackground().clearColorFilter();
//                                            view.invalidate();
//                                            break;
//                                        }
//                                    }
//                                    return true;
//                                }
//                            });
//                            btnMinus.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    int n = Integer.parseInt(quantity.getText().toString());
//                                    if(n>1) {
//                                        StringBuilder qty = new StringBuilder();
//                                        qty.append(n-1);
//                                        quantity.setText(qty);
//                                    }
//                                }
//                            });
//                            btnPlus.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    int n = Integer.parseInt(quantity.getText().toString());
//                                    if(n<100) {
//                                        StringBuilder qty = new StringBuilder();
//                                        qty.append(n+1);
//                                        quantity.setText(qty);
//                                    }
//                                }
//                            });
//                            //Toast.makeText(ShopMenuListAdapter.this,itemList[position],Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//                if(groupPosition==1&childPosition==5){
//                    final String itemList[] = {"BC"};
//                    final String itemPrice[] = {"26000"};
//                    final String descript[] = {"dadasdasdasd"};
//                    final int itemIcon[] = {R.drawable.img};
//
//                    ItemGridAdapter adapter = new ItemGridAdapter(ctx, itemIcon, itemList);
//                    grid.setAdapter(adapter);
//                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            quantity.setText("1");
//                            image.setImageResource(itemIcon[position]);
//                            name.setText(itemList[position]);
//                            price.setText(itemPrice[position]);
//                            description.setText(descript[position]);
//                            btnOrder.setOnTouchListener(new View.OnTouchListener() {
//                                @Override
//                                public boolean onTouch(View v, MotionEvent event) {
//                                    switch (event.getAction()) {
//                                        case MotionEvent.ACTION_DOWN: {
//                                            Button view = (Button) v;
//                                            view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
//                                            v.invalidate();
//                                            break;
//                                        }
//                                        case MotionEvent.ACTION_UP:
//                                            // Your action here on button click
//                                        case MotionEvent.ACTION_CANCEL: {
//                                            Button view = (Button) v;
//                                            view.getBackground().clearColorFilter();
//                                            view.invalidate();
//                                            break;
//                                        }
//                                    }
//                                    return true;
//                                }
//                            });
//                            btnMinus.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    int n = Integer.parseInt(quantity.getText().toString());
//                                    if(n>1) {
//                                        StringBuilder qty = new StringBuilder();
//                                        qty.append(n-1);
//                                        quantity.setText(qty);
//                                    }
//                                }
//                            });
//                            btnPlus.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    int n = Integer.parseInt(quantity.getText().toString());
//                                    if(n<100) {
//                                        StringBuilder qty = new StringBuilder();
//                                        qty.append(n+1);
//                                        quantity.setText(qty);
//                                    }
//                                }
//                            });
//                            //Toast.makeText(ShopMenuListAdapter.this,itemList[position],Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
