package com.example.work1;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LocationFragment extends Fragment {
	 // Scrolling flag
	public WheelView country;
	public WheelView city;
    private boolean scrolling = false;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v=LayoutInflater.from(getActivity()).inflate(R.layout.layout_locfrag, null);
		
		country = (WheelView) v.findViewById(R.id.district);
        country.setVisibleItems(3);
        country.setViewAdapter(new CountryAdapter(getActivity()));
        
        city = (WheelView) v.findViewById(R.id.building);
        city.setVisibleItems(5);

        country.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
			    if (!scrolling) {
			        updateCities(city,  newValue);
			    }
			}
		});
        
        country.addScrollingListener( new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheel) {
                scrolling = true;
            }
            public void onScrollingFinished(WheelView wheel) {
                scrolling = false;
                updateCities(city,  country.getCurrentItem());
            }
        });

        country.setCurrentItem(1);
		
		return v;
	}
	
	private void updateCities(WheelView city,  int index) {
        Building b[]=new Building[MainActivity.districts.get(index).buildings.size()];
        MainActivity.districts.get(index).buildings.toArray(b);
		ArrayWheelAdapter2<Building> adapter =
            new ArrayWheelAdapter2<Building>(getActivity(),b);
        adapter.setTextSize(18);
        city.setViewAdapter(adapter);
        city.setCurrentItem(b.length / 2);
        MainActivity.chosedindex=index;
        MainActivity.chosebindex=adapter.getCustomIndex();
    }
	
	private class CountryAdapter extends AbstractWheelTextAdapter {
        // Countries names
//        private District[] countries =
//            (District[]) MainActivity.districts.toArray();
        private District[] countries =new District[MainActivity.districts.size()];
                 
        
        /**
         * Constructor
         */
        protected CountryAdapter(Context context) {
            super(context, R.layout.item_layout, NO_RESOURCE);
            MainActivity.districts.toArray(countries);
            setItemTextResource(R.id.item_text);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }
        
        @Override
        public int getItemsCount() {
            return countries.length;
        }
        
        @Override
        protected CharSequence getItemText(int index) {
            return countries[index].toString();
        }
    }
	
	private class ArrayWheelAdapter2<T> extends AbstractWheelTextAdapter {
	    
	    // items
	    private T items[];
	    private int customindex;
	   public ArrayWheelAdapter2(Context context, T items[]) {
	        super(context);
	        
	        //setEmptyItemResource(TEXT_VIEW_ITEM_RESOURCE);
	        this.items = items;
	    }
	    
	    @Override
	    public CharSequence getItemText(int index) {
	        if (index >= 0 && index < items.length) {
	            T item = items[index];
	            customindex=index;
	            if (item instanceof CharSequence) {
	                return (CharSequence) item;
	            }
	            return item.toString();
	        }
	        return null;
	    }

	    @Override
	    public int getItemsCount() {
	        return items.length;
	    }
	    public int getCustomIndex(){
	    	return customindex;
	    }
	}
	
}
