package com.example.work1;

import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;


public class MainActivity extends Activity implements OnClickListener{

	public static ArrayList<District> districts=new ArrayList<District>();
	ExpandableListView elistview;
	ProgressDialog progressdlg;
	MyExpandableListAdapter adapter;
	View timefragview;
	Button changetime;
	Button menuitem;
	TextView datetext;
	SharedPreferences pref;
	SharedPreferences.Editor editor;
	FragmentManager manager;
	TimeFragment timefrag;
	TextView teachtextview;
	
	LocationFragment locfrag;
	LinearLayout locationchoose;
	
	boolean islocchoosing=false;
	public static Building chosenbuild;
	public static District chosedistrict;
	public static int chosebindex;
	public static int chosedindex;
	
	HttpUtility httputil=HttpUtility.getInstance();
	
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 0:
				adapter.notifyDataSetChanged();
				chosebindex=pref.getInt("buildindex", -1);
		        chosedindex=pref.getInt("districtindex", -1);
		        Log.d("PREF", chosebindex+",");
		        //判断是否之前有选择教室
		        if(chosebindex!=-1&&chosedindex!=-1){
		        	teachtextview.setText(districts.get(chosedindex).toString()+" "+
		        			districts.get(chosedindex).buildings.get(chosebindex).toString());
		        	elistview.expandGroup(chosedindex);
					elistview.post(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							elistview.setSelectedChild(chosedindex, chosebindex, true);
						}
					});
		        }
				break;
			default:
				break;
			}
		}
	};
	
	public void showProgressDlg(){
		
    	if(progressdlg==null){
    		progressdlg=new ProgressDialog(MainActivity.this);
    		progressdlg.setMessage("正在加载");
    		progressdlg.setCanceledOnTouchOutside(false);
    	}
    	progressdlg.show();
    }
    public void closeProgressDlg(){
    	if(progressdlg!=null){
    		progressdlg.dismiss();
    	}
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        initView();
        showProgressDlg();
        try {
			getData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    public void initView(){
    	pref=PreferenceManager.getDefaultSharedPreferences(this);
        
    	teachtextview=(TextView) findViewById(R.id.id_tteach);
    	elistview=(ExpandableListView) findViewById(R.id.id_listview);
    	adapter=new MyExpandableListAdapter(MainActivity.this);
    	elistview.setAdapter(adapter);
    	timefragview=findViewById(R.id.time_frag);
    	timefragview.setVisibility(View.GONE);
    	changetime=(Button) findViewById(R.id.id_changedate);
    	changetime.setOnClickListener(this);
    	
    	menuitem=(Button) findViewById(R.id.id_menuitem);
    	menuitem.setOnClickListener(this);
    	menuitem.setVisibility(View.INVISIBLE);
    	
    	datetext=(TextView) findViewById(R.id.id_date);
    	
    	locationchoose=(LinearLayout) findViewById(R.id.location_choose);
    	locationchoose.setOnClickListener(this);
    	
    	
    	

    	elistview.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				// TODO Auto-generated method stub
				for (int i = 0; i < adapter.getGroupCount(); i++) {
					if (groupPosition != i) {
						elistview.collapseGroup(i);
					}
				}
				
				
			}

		});
    	
    }
    public void getData() throws Exception{
    	/*
    	 * 避免多次重载数据
    	 */
    	if(MainActivity.districts.size()>0){
    		closeProgressDlg();
			if(chosebindex!=-1&&chosedindex!=-1){
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						teachtextview.setText(districts.get(chosedindex).toString()+" "+
			        			districts.get(chosedindex).buildings.get(chosebindex).toString());
						elistview.expandGroup(chosedindex);
						elistview.post(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								elistview.setSelectedChild(chosedindex, chosebindex, true);
							}
						});
					}
				});
			}
    		return;
    	}
    	//后台获取数据
    	httputil.getWebdata("http://115.29.17.73:8001/lessons/get.json", new HttpCallbackListener() {
			
			@Override
			public void onFinish(String txt) {
				// TODO Auto-generated method stub
				Utility.parseJsonA(txt);
				Log.d("JSON", "解析A完成");
				
				Message msg=new Message();
				msg.what=0;
				handler.sendMessage(msg);
				
				closeProgressDlg();
			}
			
			@Override
			public void onError(String txt) {
				// TODO Auto-generated method stub
				Log.d("JSON", "解析出差");
			}
		});
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.id_changedate:
			if(changetime.getText().equals("确定")){
				//datetext设置周数、日期,或者确定地点修改的设置
				if(islocchoosing){
					//设置地点选择的变换
					chosebindex=locfrag.city.getCurrentItem();
					chosedistrict=MainActivity.districts.get(chosedindex);
					chosenbuild=chosedistrict.buildings.get(chosebindex);
					elistview.expandGroup(chosedindex);
					elistview.post(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							elistview.setSelectedChild(chosedindex, chosebindex, true);
						}
					});
					
					teachtextview.setText(chosedistrict.toString()+","+chosenbuild.toString());
					timefragview.setVisibility(View.GONE);
					menuitem.setVisibility(View.INVISIBLE);
					changetime.setText("修改时间");
					
					editor=pref.edit();
					editor.putInt("buildindex", chosebindex);
					editor.putInt("districtindex", chosedindex);
					editor.commit();
				}
			}
			else{
				timefragview.setVisibility(View.VISIBLE);
				//menuitem.setBackground(null);
				menuitem.setVisibility(View.VISIBLE);
				manager=getFragmentManager();
				timefrag=new TimeFragment();
				FragmentTransaction transaction=manager.beginTransaction();
				transaction.replace(R.id.time_frag, timefrag);
				transaction.commit();
				changetime.setText("确定");
			}
			
			break;
		case R.id.id_menuitem:
			if(menuitem.getVisibility()==View.VISIBLE){
				timefragview.setVisibility(View.GONE);
				menuitem.setVisibility(View.INVISIBLE);
				changetime.setText("修改时间");
			}
			break;
		case R.id.location_choose:
			//changetime.setClickable(false);
			islocchoosing=true;
			
			timefragview.setVisibility(View.VISIBLE);
			//menuitem.setBackground(null);
			menuitem.setVisibility(View.VISIBLE);
			manager=getFragmentManager();
			locfrag=new LocationFragment();
			FragmentTransaction transaction=manager.beginTransaction();
			transaction.replace(R.id.time_frag, locfrag);
			transaction.commit();
			changetime.setText("确定");
			break;
		default:
			break;
		}
	}
    
}

