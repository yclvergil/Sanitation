package com.songming.sanitation.map.pup;

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

public class MapPointPup extends PopupWindow {

	private View mMenuView;

	private LinearLayout layout1;
	private LinearLayout layout2;
	private ImageView phone_image;
	private TextView ok_text;
	private TextView name;
	private TextView address_text;
	private TextView phone_text;
	private TextView number_text;
	private LineItemModel data = null;
	private Context context;
	private RadioGroup radioGroup;
	private TextView marker_num;

	/*
	 * name weight number address vista_image
	 */

	public LineItemModel getData() {
		return data;
	}

	public MapPointPup(final Context context, Activity listener) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.map_point_pup, null);
		this.context = context;
		mMenuView.findViewById(R.id.vista_image).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (data != null) {
							Intent intent = new Intent(context,
									PanoMainActivity.class);
							intent.putExtra(
									"LatLng",
									new LatLng(Double.parseDouble(data
											.getStationLatitude()), Double
											.parseDouble(data
													.getStationLongtitude())));
							context.startActivity(intent);
						}
					}
				});
		radioGroup = (RadioGroup) mMenuView.findViewById(R.id.radioGroup);
		radioGroup
				.setOnCheckedChangeListener((OnCheckedChangeListener) listener);
		layout1 = (LinearLayout) mMenuView.findViewById(R.id.layout1);
		layout2 = (LinearLayout) mMenuView.findViewById(R.id.layout2);

		name = (TextView) mMenuView.findViewById(R.id.name);
		address_text = (TextView) mMenuView.findViewById(R.id.address_text);

		phone_text = (TextView) mMenuView.findViewById(R.id.phone_text);
		number_text = (TextView) mMenuView.findViewById(R.id.number_text);
		ok_text = (TextView) mMenuView.findViewById(R.id.ok_text);
		phone_image = (ImageView) mMenuView.findViewById(R.id.phone_image);
		marker_num = (TextView) mMenuView.findViewById(R.id.marker_num);
		phone_image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 进入系统拨号页面
				Intent phoneIntent = new Intent();
				phoneIntent.setAction(Intent.ACTION_DIAL);
				phoneIntent.setData(Uri.parse("tel:"
						+ phone_text.getText().toString().trim()));
				context.startActivity(phoneIntent);
			}
		});
		ok_text.setOnClickListener((OnClickListener) listener);
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

	public void setData(LineItemModel data) {
		this.data = data;
		initdata();
	}

	public void setBgok() {
		if (ok_text != null) {
			ok_text.setBackgroundColor(context.getResources().getColor(
					R.color.map_point_pupbgcolor_08));
		}
	}

	private void initdata() {
		// TODO Auto-generated method stub
		if (data != null) {
			name.setText(data.getStationName() + "");
			address_text.setText(data.getStationAddress() + "");
			String ph = data.getStationuserphone();
			if (ph == null) {
				ph = "";
			}
			phone_text.setText("电话:" + ph);
			if ("0".equals(data.getStatus())) {
				ok_text.setBackgroundColor(context.getResources().getColor(
						R.color.title_bar_28c3b1));
			} else {
				ok_text.setBackgroundColor(context.getResources().getColor(
						R.color.map_point_pupbgcolor_08));
			}
			number_text.setText(data.getGoodstotalcount() + "件货物");

			marker_num.setText(data.getMarker_num());
		}
	}

	/*
	 * i 1为网点详情 2为网点报表
	 */
	public void visiLayout(int i) {
		if (i == 1) {
			layout1.setVisibility(View.VISIBLE);
			layout2.setVisibility(View.GONE);
		} else {
			layout1.setVisibility(View.GONE);
			layout2.setVisibility(View.VISIBLE);
		}
	}
}
