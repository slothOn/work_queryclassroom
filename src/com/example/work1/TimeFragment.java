package com.example.work1;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class TimeFragment extends Fragment implements OnClickListener {
	
	public TextView weektext;
	public TextView datetext;
	FragmentManager manager;
	WeekFragment weekfrag;
	DateFragment datefrag;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=LayoutInflater.from(getActivity()).inflate(R.layout.time_frag, null);
		weekfrag=new WeekFragment();
		datefrag=new DateFragment();
		
		weektext=(TextView) view.findViewById(R.id.id_week);
		datetext=(TextView) view.findViewById(R.id.id_date);
		weektext.setOnClickListener(this);
		datetext.setOnClickListener(this);
		
		weektext.setBackgroundColor(android.graphics.Color.parseColor("#7FFFD4"));
		manager=getFragmentManager();
		
		FragmentTransaction transaction=manager.beginTransaction();
		transaction.replace(R.id.time_frame, weekfrag);
		transaction.commit();
		return view;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		FragmentTransaction tr=manager.beginTransaction();
		switch (v.getId()) {
		case R.id.id_week:
			tr.replace(R.id.time_frame, weekfrag);
			weektext.setBackgroundColor(android.graphics.Color.parseColor("#7FFFD4"));
			datetext.setBackground(null);
			break;
		case R.id.id_date:
			tr.replace(R.id.time_frame, datefrag);
			datetext.setBackgroundColor(android.graphics.Color.parseColor("#7FFFD4"));
			weektext.setBackground(null);
			break;
		default:
			break;
		}
		tr.commit();
	}
}
