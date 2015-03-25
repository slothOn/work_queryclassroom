package com.example.work1;

import java.util.ArrayList;

public class District implements Comparable<District>{
	public District() {
		// TODO Auto-generated constructor stub
	}
	public District(String n) {
		// TODO Auto-generated constructor stub
		name=n;
	}
	public String name;
	public ArrayList<Building> buildings=new ArrayList<Building>();
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name+"Çø";
	}
	@Override
	public int compareTo(District another) {
		// TODO Auto-generated method stub
		return name.compareTo(another.name);
	}
}
