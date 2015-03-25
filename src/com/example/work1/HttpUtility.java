package com.example.work1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class HttpUtility {
	private HttpUtility(){
		client=new DefaultHttpClient();
	}
	private static HttpUtility httputil;
	public static HttpUtility getInstance(){
		if(httputil==null)
			httputil=new HttpUtility();
		return httputil;
	}
	static HttpClient client;
	public void getWebdata(final String urlstr,final HttpCallbackListener listener){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//HttpClient client=null;
				String response=null;
				try{
					client=new DefaultHttpClient();
					HttpGet httpget=new HttpGet(urlstr);
					HttpResponse httpResponse=client.execute(httpget);
					if(httpResponse.getStatusLine().getStatusCode()==200){
						HttpEntity entity=httpResponse.getEntity();
						response=EntityUtils.toString(entity,"utf-8");
						
					}
					if(listener!=null){
						Log.d("HTTP", "链接正确");
						listener.onFinish(response);
					}
				}catch(Exception e){
					e.printStackTrace();
					if(listener!=null){
						Log.d("HTTP", "链接错误");
						listener.onError(response);
					}
					
				}
			}
		}).start();
		
	}
}
