package com.songming.sanitation.map.tool;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteNode;
import com.baidu.mapapi.search.route.DrivingRouteLine;

public class MapTool {

	public static List<LatLng> getLinePosints(DrivingRouteLine line) {
		// TODO Auto-generated method stub
		List<RouteNode> nodeList = line.getWayPoints();
		
		List<LatLng> list = new ArrayList<LatLng>();

		if (nodeList != null) {
			for (RouteNode node : nodeList) {
				list.add(node.getLocation());
			}
		}
		return list;
	}
}
