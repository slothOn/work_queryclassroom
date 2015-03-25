package com.example.work1;

import java.util.Calendar;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelClickedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class WeekFragment extends Fragment{
	// Time changed flag
	private boolean timeChanged = false;
		
	// Time scrolled flag
	private boolean timeScrolled = false;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v=LayoutInflater.from(getActivity()).inflate(R.layout.layout_weekfrag, null);
		

		final WheelView hours = (WheelView) v.findViewById(R.id.hour);
		hours.setViewAdapter(new WeektextAdapter2(getActivity()));
	
		final WheelView mins = (WheelView) v.findViewById(R.id.mins);
		mins.setViewAdapter(new WeektextAdapter1(getActivity()));
		mins.setCyclic(true);
	
		// set current time
		Calendar c = Calendar.getInstance();
		int curHours = c.get(Calendar.HOUR_OF_DAY);
		int curMinutes = c.get(Calendar.MINUTE);
	
		hours.setCurrentItem(curHours);
		mins.setCurrentItem(curMinutes);

	
		// add listeners
		addChangingListener(mins, "min");
		addChangingListener(hours, "hour");
	
		OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (!timeScrolled) {
					timeChanged = true;
					//picker.setCurrentHour(hours.getCurrentItem());
					//picker.setCurrentMinute(mins.getCurrentItem());
					timeChanged = false;
				}
			}
		};
		hours.addChangingListener(wheelListener);
		mins.addChangingListener(wheelListener);
		
		OnWheelClickedListener click = new OnWheelClickedListener() {
            public void onItemClicked(WheelView wheel, int itemIndex) {
                wheel.setCurrentItem(itemIndex, true);
            }
        };
        hours.addClickingListener(click);
        mins.addClickingListener(click);

		OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
			public void onScrollingStarted(WheelView wheel) {
				timeScrolled = true;
			}
			public void onScrollingFinished(WheelView wheel) {
				timeScrolled = false;
				timeChanged = true;
				//picker.setCurrentHour(hours.getCurrentItem());
				//picker.setCurrentMinute(mins.getCurrentItem());
				timeChanged = false;
			}
		};
		
		hours.addScrollingListener(scrollListener);
		mins.addScrollingListener(scrollListener);
		
		
		
		return v;
	}
	/**
	 * Adds changing listener for wheel that updates the wheel label
	 * @param wheel the wheel
	 * @param label the wheel label
	 */
	private void addChangingListener(final WheelView wheel, final String label) {
		wheel.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				//wheel.setLabel(newValue != 1 ? label + "s" : label);
			}
		});
	}
	
    private class WeektextAdapter1 extends AbstractWheelTextAdapter {
        // Countries names
        private String countries[] =
            new String[] {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
        
        
        /**
         * Constructor
         */
        protected WeektextAdapter1(Context context) {
            super(context, R.layout.item_layout, NO_RESOURCE);
            setItemTextResource(R.id.item_text);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
//            ImageView img = (ImageView) view.findViewById(R.id.flag);
//            img.setImageResource(flags[index]);
            return view;
        }
        
        @Override
        public int getItemsCount() {
            return countries.length;
        }
        
        @Override
        protected CharSequence getItemText(int index) {
            return countries[index];
        }
    }

    private class WeektextAdapter2 extends AbstractWheelTextAdapter {
        // Countries names
        private String countries[] =
            new String[] {"1周", "2周", "3周", "4周", "5周", "6周",
        		"7周", "8周", "9周", "10周", "11周", "12周", 
        		"13周", "14周", "15周", "16周"};
        
        
        /**
         * Constructor
         */
        protected WeektextAdapter2(Context context) {
            super(context, R.layout.item_layout, NO_RESOURCE);
            setItemTextResource(R.id.item_text);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
//            ImageView img = (ImageView) view.findViewById(R.id.flag);
//            img.setImageResource(flags[index]);
            return view;
        }
        
        @Override
        public int getItemsCount() {
            return countries.length;
        }
        
        @Override
        protected CharSequence getItemText(int index) {
            return countries[index];
        }
    }
}
