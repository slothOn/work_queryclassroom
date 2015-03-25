package com.example.work1;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Loader.ForceLoadContentObserver;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {
	LayoutInflater inflater;
    private Activity a;

    public ArrayList<District> groups;

    
    public MyExpandableListAdapter() {
		// TODO Auto-generated constructor stub
    	
	}
    public MyExpandableListAdapter(Activity a) {
		// TODO Auto-generated constructor stub
    	this.a=a;
    	inflater=LayoutInflater.from(a);

    	groups=MainActivity.districts;
	}
    
    public Object getChild(int groupPosition, int childPosition) {
    	return MainActivity.districts.get(groupPosition).buildings.get(childPosition);
        //return children[groupPosition][childPosition];
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public int getChildrenCount(int groupPosition) {
        return MainActivity.districts.get(groupPosition).buildings.size();
    	//return children[groupPosition].length;
    }

    public TextView getGenericView() {
        // Layout parameters for the ExpandableListView
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 64);

        
        TextView textView = new TextView(a);
        textView.setLayoutParams(lp);
        // Center the text vertically
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        // Set the text starting position
        textView.setPadding(36, 0, 0, 0);
        return textView;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
            View convertView, ViewGroup parent) {
//        TextView textView = getGenericView();
//        textView.setText(getChild(groupPosition, childPosition).toString());
//        return textView;
    	if(convertView==null){
    		convertView=inflater.inflate(R.layout.layout_childview, null);
    	}
    	//View v=inflater.inflate(R.layout.layout_childview, null);
    	TextView titletext=(TextView) convertView.findViewById(R.id.id_childtext);
    	titletext.setText(getChild(groupPosition, childPosition).toString());
    	titletext.setTextSize(18);
    	
    	TableLayout tableview=(TableLayout) convertView.findViewById(R.id.id_childtable);
    	Building b=(Building) getChild(groupPosition, childPosition);
    	int column=2;
    	//int count=b.rooms.size();
    	int row=(b.rooms.size()+1)/2;
    	//int last=b.rooms.size()%2;
    	
    	LayoutParams lyp=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    	TableRow.LayoutParams lp=new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
    	for(int r=0;r<row;r++){
    		TableRow tablerow=new TableRow(convertView.getContext());
    		tablerow.setLayoutParams(lyp);
    		
    		
    		for(int l=0;l<column;l++){
    			TextView textView=new TextView(convertView.getContext());
    			//textView.setLayoutParams(lp);
    			if((2*r+l)<b.rooms.size()){
    				textView.setText(b.rooms.get(2*r+l).toString());
    			}
    			else{
    				textView.setText("");
    			}
    			//textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT/2, LayoutParams.WRAP_CONTENT));
    			//textView.setWidth(80);
    			tablerow.addView(textView);
    			textView.setLayoutParams(lp);
    		}
    		tableview.addView(tablerow);
    	}
    	
    	return convertView;
    }

    public Object getGroup(int groupPosition) {
    	return groups.get(groupPosition);
        //return groups[groupPosition];
    }

    public int getGroupCount() {
    	return groups.size();
        //return groups.length;
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
            ViewGroup parent) {
        TextView textView = getGenericView();
        textView.setText(getGroup(groupPosition).toString());
        textView.setTextSize(24);
        return textView;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public boolean hasStableIds() {
        return true;
    }

}
