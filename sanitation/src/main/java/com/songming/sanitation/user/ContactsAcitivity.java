package com.songming.sanitation.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.songming.sanitation.Main;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.VolleyRequestVo;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.frameset.utils.StringUtilsExt;
import com.songming.sanitation.message.ApprovalOpenAcitivity;
import com.songming.sanitation.user.model.UserDto;
import com.songming.sanitation.user.tool.CharacterParser;
import com.songming.sanitation.user.tool.PinyinComparator;
import com.songming.sanitation.user.view.SideBar;
import com.songming.sanitation.user.view.SideBar.OnTouchingLetterChangedListener;

/**
 * 通讯录页面
 */
@SuppressLint("NewApi")
public class ContactsAcitivity extends BaseActivity implements OnClickListener {

	private LinearLayout layoutback;
	private ImageView layoutbackimg;
	private TextView topTitle;
	private ImageView layoutsearchimg;

	private ExpandableListView listview;// 上层总经理同级等等几个类别
	// private BaseExpandableListAdapter adapter;
	private List<UserDto> list = new ArrayList<UserDto>();
	private List<List<UserDto>> mheadList = new ArrayList<List<UserDto>>();
	private List<String> headList = new ArrayList<String>();
	private MyAdapter adapter;
	private SideBar sideBar;
	private TextView dialog;
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;
	private int group;// 判断第一层第几个item
	private boolean fromApproval;
	private boolean fromPatrol;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.contacts);

		fromApproval = getIntent().getBooleanExtra("fromApproval", false);
		fromPatrol = getIntent().getBooleanExtra("fromPatrol", false);
		findViews();
		initViews();
		requestData();

	}

	@Override
	protected void findViews() {

		layoutback = (LinearLayout) findViewById(R.id.layoutback);
		topTitle = (TextView) findViewById(R.id.topTitle);
		layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);
		listview = (ExpandableListView) findViewById(R.id.expandablelist_view);
		layoutsearchimg = (ImageView) findViewById(R.id.layoutsearchimg);

		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
	}

	@Override
	protected void initViews() {

		if(fromPatrol){
			topTitle.setText("查看巡查情况");
		}else{
			topTitle.setText("通讯录");
		}
		layoutback.setVisibility(View.VISIBLE);
		layoutbackimg.setOnClickListener(this);
		layoutsearchimg.setVisibility(View.VISIBLE);
		layoutsearchimg.setOnClickListener(this);

		sideBar.setTextView(dialog);
		sideBar.setVisibility(View.GONE);
		// 实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();

		// 去掉默认箭头
		listview.setGroupIndicator(null);

		listview.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				// TODO Auto-generated method stub
				// 设置同时只能展开一个
				for (int i = 0; i < adapter.getGroupCount(); i++) {
					if (groupPosition != i) {
						listview.collapseGroup(i);
						// 得到第一层打开的itemID
						group = groupPosition;
					}
				}
			}
		});

		listview.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// TODO Auto-generated method stub
				// 判断是否有展开的。
				if (listview.isGroupExpanded(groupPosition)) {
					// 没有展开的则隐藏右侧字母索引
					sideBar.setVisibility(View.GONE);
				} else {
					// 没展开的则展示右侧字母索引
					sideBar.setVisibility(View.VISIBLE);
				}
				return false;
			}
		});
		// 子项点击事件
		listview.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {

				if (fromApproval) {
					// 从发起审批页面过来拿数据的
					Intent intent = new Intent(ContactsAcitivity.this,
							ApprovalOpenAcitivity.class);
					intent.putExtra("user",
							mheadList.get(groupPosition).get(childPosition));
					setResult(RESULT_OK, intent);
					ContactsAcitivity.this.finish();
				} else if (fromPatrol) {
					// 从巡查页面来
					Intent intent = new Intent(ContactsAcitivity.this,
							CheckUpDetailsActivity.class);
					intent.putExtra("id",
							mheadList.get(groupPosition).get(childPosition)
									.getId());
					startActivity(intent);
				} else {
					// 直接拨号
					String phone = mheadList.get(groupPosition)
							.get(childPosition).getPhone();
					if (!StringUtilsExt.isEmpty(phone)) {
						Intent it = new Intent("android.intent.action.CALL",
								Uri.parse("tel:" + phone));
						startActivity(it);
					}
				}
				return false;
			}
		});

		// 设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				// TODO Auto-generated method stub

				int position = adapter.getPositionForSection(group, s.charAt(0));
				if (position != -1) {

					// listview.setSelection(position);
					// 根据右侧滑动移动item相对应的位置
					listview.setSelectedChild(group, position, true);
				}
			}
		});
	}

	@Override
	protected void errorCallback(VolleyError volleyError, Object requestTag) {
		// TODO Auto-generated method stub
		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}
		if ("contacts".equals(requestStr)) {
			Toast.makeText(this, "获取失败！", Toast.LENGTH_SHORT).show();
		}

		if (volleyError != null && volleyError.getMessage() != null)
			Log.i("contacts", volleyError.getMessage());
	}

	@Override
	protected void successCallback(JSONObject jsonObject, Object requestTag) {
		this.hideLoading();
		String requestStr = "";

		if (requestTag instanceof VolleyRequestVo) {
			requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
		}

		if ("contacts".equals(requestStr)) {

			Gson gson = new Gson();
			String data = jsonObject.optString("data", "{}");
			list = gson.fromJson(data, new TypeToken<List<UserDto>>() {
			}.getType());
			if (list == null || list.size() == 0) {
				Toast.makeText(ContactsAcitivity.this, "您还没有联系人！", 0).show();
			} else {
				
				if (fromPatrol) {
					// stationId 1：总经理，2：车队长，3：主管，4：区域经理，5：司机,6:总部
					long stationId = SharedPreferencesUtils.getLongValue(this,
							SharedPreferencesUtils.STATIONID, -1);
					if (stationId == 1) {
						for (int i = 0; i < list.size(); i++) {
							if (list.get(i).getStationId() == 6) {
								list.remove(i);
								i--;
							}
						}
					} else if (stationId == 2) {
						for (int i = 0; i < list.size(); i++) {
							long id = list.get(i).getStationId();
							if (id == 6 || id == 1 || id == 4) {
								list.remove(i);
								i--;
							}
						}
					} else if (stationId == 3) {
						for (int i = 0; i < list.size(); i++) {
							long id = list.get(i).getStationId();
							if (id == 6 || id == 1 || id == 4) {
								list.remove(i);
								i--;
							}
						}
					} else if (stationId == 4) {
						for (int i = 0; i < list.size(); i++) {
							long id = list.get(i).getStationId();
							if (id == 6 || id == 1) {
								list.remove(i);
								i--;
							}
						}
					} else if (stationId == 5) {
						for (int i = 0; i < list.size(); i++) {
							long id = list.get(i).getStationId();
							if (id == 6 || id == 1 || id == 4 || id == 2
									|| id == 3) {
								list.remove(i);
								i--;
							}
						}
					}
				}else if (fromApproval){
				}else{
					//去掉一级人员
					for (int i = 0; i < list.size(); i++) {
						if (list.get(i).getStationId() == 6) {
							list.remove(i);
							i--;
						}
					}
					
				}
				for (UserDto dto : list) {
					if (!isContains(headList, dto.getStationName())) {
						headList.add(dto.getStationName());
					}
				}

				for (String stationName : headList) {
					mheadList.add(getContactsList(stationName));
				}
				adapter = new MyAdapter(this);
				group = 0;
				listview.setAdapter(adapter);
				// 设置默认展开
				listview.expandGroup(0);
				sideBar.setVisibility(View.VISIBLE);
			}

		}

	}

	private List<UserDto> getContactsList(String stationName) {
		List<UserDto> childList = new ArrayList<UserDto>();
		for (UserDto dto : list) {
			if (dto.getStationName().equals(stationName)) {
				childList.add(dto);

			}
		}
		childList = filledData(childList);
		// 根据a-z进行排序源数据
		Collections.sort(childList, pinyinComparator);
		return childList;

	}

	private boolean isContains(List<String> list, String stationName) {
		if (list == null || list.size() == 0) {
			return false;
		}
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals(stationName)) {
				return true;
			}
		}
		return false;

	}

	/**
	 * 总经理等级别适配器
	 * */
	class MyAdapter extends BaseExpandableListAdapter {

		private Context context;
		private LayoutInflater inflater;

		public MyAdapter(Context context) {
			this.context = context;
			inflater = LayoutInflater.from(context);
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return true;
		}

		// 父层视图
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.contacts_super_item,
						null);
				holder = new ViewHolder();
				holder.Img = (ImageView) convertView.findViewById(R.id.Image);
				holder.ctsuper = (TextView) convertView
						.findViewById(R.id.ctsuper);
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.ctsuper.setText(headList.get(groupPosition));

			if (isExpanded) {
				holder.Img.setBackgroundResource(R.drawable.shrink);
			} else {
				holder.Img.setBackgroundResource(R.drawable.unfold);
			}

			return convertView;
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return groupPosition;
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return mheadList.size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			return mheadList.get(groupPosition);
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			return mheadList.get(groupPosition).size();
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.contacts_item, null);
				holder = new ViewHolder();
				holder.ctname = (TextView) convertView
						.findViewById(R.id.ctname);
				holder.ctsuper = (TextView) convertView
						.findViewById(R.id.ctdetail);
				holder.tvLetter = (TextView) convertView
						.findViewById(R.id.catalog);

				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// 根据position获取分类的首字母的Char ascii值
			int section = getSectionForPosition(group, childPosition);

			// group判断展开的是第几个
			// 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
			if (childPosition == getPositionForSection(group, section)) {
				holder.tvLetter.setVisibility(View.VISIBLE);
				holder.tvLetter.setText(mheadList.get(groupPosition)
						.get(childPosition).getSortLetters());
			} else {
				holder.tvLetter.setVisibility(View.GONE);
			}

			holder.ctname.setText(mheadList.get(groupPosition)
					.get(childPosition).getName());
			String headText = mheadList.get(groupPosition).get(childPosition)
					.getStationName();
			holder.ctsuper.setText(headText);
			return convertView;
		}

		class ViewHolder {
			TextView ctsuper;// 职位
			ImageView Img;// 图片
			TextView ctname;// 名字
			TextView tvLetter;// 字母索引头字母
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childPosition;
		}

		// getChild相当于getItem,不过它是获取展开后的项，所以参数有2个索引
		// 第1个索引是父项的，第2个是子项的
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return mheadList.get(groupPosition).get(childPosition);
		}

		/**
		 * 根据分类的首字母的Char ascii值 获取其第一次出现该首字母的位置 groupPosition获取第一层展开的是第几个
		 */
		public int getPositionForSection(int groupPosition, int section) {
			for (int i = 0; i < getChildrenCount(groupPosition); i++) {
				String sortStr = mheadList.get(groupPosition).get(i)
						.getSortLetters();
				char firstChar = sortStr.toUpperCase().charAt(0);
				if (firstChar == section) {
					return i;
				}
			}

			return -1;
		}

		/**
		 * 根据ListView的当前位置获取分类的首字母的Char ascii值
		 */
		public int getSectionForPosition(int getGroupposition, int position) {
			// 获取数据位置
			return mheadList.get(getGroupposition).get(position)
					.getSortLetters().charAt(0);
		}

	};

	/**
	 * 通讯录查询
	 */
	private void requestData() {

		int orgId = SharedPreferencesUtils.getIntValue(this,
				SharedPreferencesUtils.ORG_ID, -1);
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("orgId", orgId);
			this.showLoading("正在查询……", "contacts");
			requestHttp(jsonObject, "contacts", Constants.CONTACTS,
					Constants.SERVER_URL);
		} catch (JSONException e) {
			Toast.makeText(this.getApplicationContext(),
					"json参数出错:" + e.getMessage(), Toast.LENGTH_SHORT).show();

		} catch (Exception e) {
			Toast.makeText(this.getApplicationContext(), "请重新启动",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.message_layout:
			// 系统信息
			startActivity(new Intent(this, Main.class));

			break;
		case R.id.contacts_layout:
			// 通信录
			startActivity(new Intent(this, Main.class));

			break;
		case R.id.password_layout:
			// 修改密码
			startActivity(new Intent(this, Main.class));

			break;
		case R.id.exit_layout:
			// 退出登录
			startActivity(new Intent(this, Main.class));

			break;
		case R.id.layoutsearchimg:
			/** 通讯录搜索 */
			Intent it = new Intent(this, SearchActivity.class);
			if(fromPatrol){
				it.putExtra("fromPatrol", fromPatrol);
			}else if (fromApproval){
				it.putExtra("fromApproval", fromApproval);
			}else{
				it.putExtra("fromContacts", true);
			}
			startActivity(it);

			break;
		case R.id.layoutbackimg:
			this.finish();

			break;
		default:
			break;
		}
	}

	/**
	 * 为ListView填充数据 进行排序
	 * 
	 * @param date
	 * @return
	 */
	private List<UserDto> filledData(List<UserDto> data) {
		List<UserDto> mSortList = new ArrayList<UserDto>();

		for (int i = 0; i < data.size(); i++) {
			UserDto sortModel = data.get(i);
			// 汉字转换成拼音
			String pinyin = characterParser.getSelling(sortModel.getName());
			String sortString = pinyin.substring(0, 1).toUpperCase();

			// 正则表达式，判断首字母是否是英文字母
			if (sortString.matches("[A-Z]")) {
				sortModel.setSortLetters(sortString.toUpperCase());
			} else {
				sortModel.setSortLetters("#");
			}

			mSortList.add(sortModel);
		}
		return mSortList;

	}

}
