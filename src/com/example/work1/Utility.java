package com.example.work1;

import java.util.Collections;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.JsonToken;
import android.util.Log;

public class Utility {
	
	public static void parseJsonA(String txt){
		try{
			JSONObject jsonObject=new JSONObject(txt);
			JSONObject infoObj=jsonObject.getJSONObject("info");
			Log.d("JSON", "解析正确1");
			Iterator<String> districtiter=infoObj.keys();
			while(districtiter.hasNext()){
				String districtname=districtiter.next();
				District district=new District(districtname);
				JSONObject districtObj=(JSONObject) infoObj.get(districtname);
				Log.d("JSON", "解析正确2");
				Iterator<String> builditer=districtObj.keys();
				while(builditer.hasNext()){
					String buildname=builditer.next();
					Building build=new Building(buildname);
					JSONObject buildObj=(JSONObject) districtObj.get(buildname);
					Log.d("JSON", "解析正确3");
					Iterator<String> classroomiter=buildObj.keys();
					while(classroomiter.hasNext()){
						String classroomname=classroomiter.next();
						Classroom classroom=new Classroom(classroomname);
						
						JSONArray roomarray= buildObj.getJSONArray(classroomname);
						String roomstr=buildObj.getString(classroomname).trim();
						JSONTokener tokener=new JSONTokener(roomstr);
						tokener.skipPast("[");
						String roomtime=tokener.nextString(']');
						String[] roomchar=roomtime.split(",");
						int[] rooms = new int[roomchar.length-2];
						for(int i=0;i<roomchar.length-2;i++){
							rooms[i]=Integer.valueOf(roomchar[i]);
						}
						classroom.a=rooms;
						build.rooms.add(classroom);
						Collections.sort(build.rooms);
					}
					Log.d("JSON", "解析正确4");
					district.buildings.add(build);
					Collections.sort(district.buildings);
					
				}
				MainActivity.districts.add(district);
				Collections.sort(MainActivity.districts);
				Log.d("JSON", "解析正确");
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
