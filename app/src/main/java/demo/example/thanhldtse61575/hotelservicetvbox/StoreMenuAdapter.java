package demo.example.thanhldtse61575.hotelservicetvbox;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import demo.example.thanhldtse61575.hotelservicetvbox.entity.CartItem;
import demo.example.thanhldtse61575.hotelservicetvbox.entity.Service;

/**
 * Created by ThanhLDTSE61575 on 1/9/2017.
 */
public class StoreMenuAdapter extends BaseExpandableListAdapter {

    private List<String> header_titles;
    private HashMap<String, List<String>> child_titles;
    private Context ctx;
    private GridView grid;
    private List<CartItem> cart;

    public StoreMenuAdapter(List<String> header_titles, HashMap<String, List<String>> child_titles, Context ctx, GridView grid, List<CartItem> cart) {
        this.header_titles = header_titles;
        this.child_titles = child_titles;
        this.ctx = ctx;
        this.grid = grid;
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

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    /*
                    HttpURLConnection urlConnection = null;
                    try {
                        URL url = new URL("http://capstoneserver2017.azurewebsites.net/api/ServicesApi/GetAllService");
                        urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.setRequestMethod("GET");
                        try {
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                            StringBuilder stringBuilder = new StringBuilder();
                            String line;
                            while ((line = bufferedReader.readLine()) != null) {
                                stringBuilder.append(line);
                            }
                            bufferedReader.close();
                        } finally {
                            urlConnection.disconnect();
                        }
                    } catch (Exception e) {
                        Log.e("ERROR", e.getMessage(), e);
                    }
                    */

                List<Service> listService = new ArrayList<>();
                Service s = new Service(1, "A", 1, 10000, "abc", "");
                listService.add(s);

                StoreGridAdapter adapter = new StoreGridAdapter(listService, listService.size(), ctx, cart);
                grid.setAdapter(adapter);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
