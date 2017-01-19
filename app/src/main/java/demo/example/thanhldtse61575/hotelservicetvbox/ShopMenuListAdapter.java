package demo.example.thanhldtse61575.hotelservicetvbox;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ThanhLDTSE61575 on 1/9/2017.
 */
public class ShopMenuListAdapter extends BaseExpandableListAdapter {
    private List<String> header_titles;
    private HashMap<String,List<String>> child_titles;
    private Context ctx;
    private GridView grid;
    private ImageView detail;
    ShopMenuListAdapter(Context ctx, List<String> header_titles, HashMap<String,List<String>> child_titles, GridView grid, ImageView detail){
        this.ctx=ctx;
        this.child_titles=child_titles;
        this.header_titles=header_titles;
        this.grid=grid;
        this.detail=detail;
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
        String title = (String) this.getChild(groupPosition,childPosition);
        if(convertView==null){
            LayoutInflater layoutInflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout_childmenuitem,null);
        }

        final TextView textView = (TextView) convertView.findViewById(R.id.textViewChildList);

        textView.setText(title);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(groupPosition==0&childPosition==0){
                    String itemList[] = {"A", "B", "C", "D", "E", "F"};
                    int itemIcon[] = {R.drawable.img, R.drawable.demo, R.drawable.demo, R.drawable.demo, R.drawable.img, R.drawable.demo};
                    ItemGridAdapter adapter = new ItemGridAdapter(ctx, itemIcon, itemList);
                    grid.setAdapter(adapter);
                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if(position==0) {
                                detail.setImageResource(R.drawable.img);
                            }
                            if(position==1) {
                                detail.setImageResource(R.drawable.demo);
                            }
                        }
                    });
                }
                if(groupPosition==0&childPosition==1){
                    String itemList[] = {"A", "B", "C", "D"};
                    int itemIcon[] = {R.drawable.demo, R.drawable.img, R.drawable.img, R.drawable.img};

                    ItemGridAdapter adapter = new ItemGridAdapter(ctx, itemIcon, itemList);
                    grid.setAdapter(adapter);
                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //Toast.makeText(ShopMenuListAdapter.this,itemList[position],Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if(groupPosition==0&childPosition==2){
                    String itemList[] = {"C", "B", "C", "C", "C", "C"};
                    int itemIcon[] = {R.drawable.demo, R.drawable.img, R.drawable.img, R.drawable.img, R.drawable.demo, R.drawable.img};

                    ItemGridAdapter adapter = new ItemGridAdapter(ctx, itemIcon, itemList);
                    grid.setAdapter(adapter);
                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //Toast.makeText(ShopMenuListAdapter.this,itemList[position],Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if(groupPosition==1&childPosition==0){
                    String itemList[] = {"B", "B", "B", "B", "B", "B"};
                    int itemIcon[] = {R.drawable.img, R.drawable.img, R.drawable.img, R.drawable.img, R.drawable.img, R.drawable.img};

                    ItemGridAdapter adapter = new ItemGridAdapter(ctx, itemIcon, itemList);
                    grid.setAdapter(adapter);
                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //Toast.makeText(ShopMenuListAdapter.this,itemList[position],Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if(groupPosition==1&childPosition==1){
                    String itemList[] = {"A", "B", "C", "D"};
                    int itemIcon[] = {R.drawable.img, R.drawable.img, R.drawable.img, R.drawable.img};

                    ItemGridAdapter adapter = new ItemGridAdapter(ctx, itemIcon, itemList);
                    grid.setAdapter(adapter);
                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //Toast.makeText(ShopMenuListAdapter.this,itemList[position],Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if(groupPosition==1&childPosition==2){
                    String itemList[] = {"TTTTT", "BC"};
                    int itemIcon[] = {R.drawable.demo, R.drawable.img};

                    ItemGridAdapter adapter = new ItemGridAdapter(ctx, itemIcon, itemList);
                    grid.setAdapter(adapter);
                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //Toast.makeText(ShopMenuListAdapter.this,itemList[position],Toast.LENGTH_SHORT).show();
                        }
                    });
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
