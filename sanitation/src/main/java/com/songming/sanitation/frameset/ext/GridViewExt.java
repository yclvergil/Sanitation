package com.songming.sanitation.frameset.ext;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * listView内嵌套gridview显示不全
 * @author liudong
 * */
public class GridViewExt extends GridView {

	public GridViewExt(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public GridViewExt(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public GridViewExt(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//listView内嵌套gridview显示不全
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
	}

	
}
