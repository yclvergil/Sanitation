package com.songming.sanitation.message.pup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.songming.sanitation.R;
import com.songming.sanitation.map.MapDetailActivity;
import com.songming.sanitation.map.PanoMainActivity;
import com.songming.sanitation.map.model.LineItemModel;
import com.songming.sanitation.message.MessageDetailAcitivity;

public class MessagePup extends PopupWindow {

	private View mMenuView;

	private Context context;
	private TextView name;
	private String id = "";
	private LinearLayout layout;

	public MessagePup(final Context context, Activity listener) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.message_pup, null);
		name = (TextView) mMenuView.findViewById(R.id.name);
		layout = (LinearLayout) mMenuView.findViewById(R.id.layout);
		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,
						MessageDetailAcitivity.class);
				intent.putExtra("id", id);
				context.startActivity(intent);
				MessagePup.this.dismiss();
			}
		});
		this.context = context;
		this.setContentView(mMenuView);
		this.setFocusable(true);
		this.setWidth(LayoutParams.FILL_PARENT);
		this.setHeight(LayoutParams.MATCH_PARENT);
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		this.setBackgroundDrawable(dw);

		mMenuView.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {

				int height = mMenuView.findViewById(R.id.layout).getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < height) {
						dismiss();
					}
				}
				return true;
			}
		});
	}

	public void setcontent(String content) {
		name.setText(content);
	}

	public void setid(String stringExtra) {
		// TODO Auto-generated method stub
		id = stringExtra;
	}
}
