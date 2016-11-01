package com.songming.sanitation.map.model;

import android.widget.LinearLayout;
import android.widget.TextView;

public class MapModel {
	private String name; // 网点名称
	private String weight;// 货物重量
	private String number;// 货物数目
	private String duration;// 预计完成时间

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

}
