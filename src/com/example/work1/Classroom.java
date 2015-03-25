package com.example.work1;

public class Classroom implements Comparable<Classroom>{
	//public String district;
	//public String building;
	public Classroom() {
		// TODO Auto-generated constructor stub
	}
	public Classroom(String n) {
		// TODO Auto-generated constructor stub
		name=n;
	}
	public String name;
	public int[] a;
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str=name+"  ";
		if(a[0]==0){
			if(a[1]==0){
				str+="1-";
			}
			else{
				str+="1,";
			}
		}
		for(int i=1;i<a.length-1;i++){
			if(a[i]==0){
				if(a[i-1]!=0&&a[i+1]==0){
					str+=String.valueOf(i+1)+"-";
				}
				if(a[i+1]!=0&&a[i-1]==0){
					str+="-"+String.valueOf(i+1)+",";
				}
				if(a[i+1]!=0&&a[i-1]!=0){
					str+=String.valueOf(i+1)+",";
				}
				if(a[i+1]==0&&a[i-1]==0){
					continue;
				}
			}
		}
		if(a[a.length-1]==0){
			if(a[a.length-2]==0){
				str+="-"+String.valueOf(a.length);
			}
			else{
				str+=","+String.valueOf(a.length);
			}
		}
		
		return str;
	}
	@Override
	public int compareTo(Classroom another) {
		// TODO Auto-generated method stub
		return name.compareTo(another.name);
	}
	
}
