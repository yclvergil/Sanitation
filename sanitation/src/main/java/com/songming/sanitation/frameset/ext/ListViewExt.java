package com.songming.sanitation.frameset.ext;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * listView内嵌套ScrollView显示不全
 * 
 * @author yunjiezhou
 * */
public class ListViewExt extends ListView {

	public ListViewExt(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ListViewExt(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	

	public ListViewExt(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// 根据模式计算每个child的高度和宽度
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
