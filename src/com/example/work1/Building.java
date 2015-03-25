package com.example.work1;

import java.util.ArrayList;

public class Building implements Comparable<Building>{
	public Building() {
		// TODO Auto-generated constructor stub
	}
	public Building(String n) {
		// TODO Auto-generated constructor stub
		name=n;
	}
	public String name;
	public ArrayList<Classroom> rooms=new ArrayList<Classroom>();
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "½Ì  "+name;
	}
	@Override
	public int compareTo(Building another) {
		// TODO Auto-generated method stub
		return name.compareTo(another.name);
	}
}
