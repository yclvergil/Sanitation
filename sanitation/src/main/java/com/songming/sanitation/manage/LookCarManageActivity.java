package com.songming.sanitation.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.YuvImage;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.VolleyRequestVo;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.RefreshUtils;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.user.CarActivity;
import com.songming.sanitation.user.model.TCarDto;

/**
 * 查看车辆信息
 */
public class LookCarManageActivity extends BaseActivity implements
        OnClickListener {

    private LinearLayout layoutback;
    private TextView topTitle;
    private ImageView layoutbackimg;
    private ExpandableListView mExListView;

    private Long stationId;// 员工ID
    private int mCurIndex = 1;

    private BaseExpandableListAdapter adapter;
    private List<TCarDto> list = new ArrayList<TCarDto>();
    private Set<String> types;
    private List<List<TCarDto>> lists;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.look_car_manage_activity);

        stationId = SharedPreferencesUtils.getLongValue(this,
                SharedPreferencesUtils.STATIONID, -1);
        findViews();
        initViews();
        getCarDetailsList();

    }

    @Override
    protected void findViews() {
        mExListView = (ExpandableListView) findViewById(R.id.expandablelist_view);
        layoutback = (LinearLayout) findViewById(R.id.layoutback);
        topTitle = (TextView) findViewById(R.id.topTitle);
        layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);

    }

    @Override
    protected void initViews() {
        // TODO Auto-generated method stub
        topTitle.setText("查看车辆信息");
        layoutback.setVisibility(View.VISIBLE);
        layoutbackimg.setOnClickListener(this);

        //adapter = new MyAdapter(this);
        /*mListView.setAdapter(adapter);

		mListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				mCurIndex = 1;

			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				mCurIndex++;

			}
		});

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});*/

    }

    /**
     * 车辆列表
     */
    private void getCarDetailsList() {

        long staffId = SharedPreferencesUtils.getLongValue(this,
                SharedPreferencesUtils.STAFF_ID, -1);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("staffId", staffId);
            this.showLoading("正在查询……", "getCarDetailsList");
            requestHttp(jsonObject, "getCarDetailsList", Constants.CARLIST,
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
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        // llayput_maintain.setVisibility(View.VISIBLE);
        // llayput_refuel.setVisibility(View.VISIBLE);
        // llayput_keep.setVisibility(View.VISIBLE);
        // mListView.setVisibility(View.GONE);

    }

    @Override
    protected void errorCallback(VolleyError volleyError, Object requestTag) {
        this.hideLoading();
        String requestStr = "";
        if (requestTag instanceof VolleyRequestVo) {
            requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
        }
        //mListView.onRefreshComplete();
    }

    @Override
    protected void successCallback(JSONObject jsonObject, Object requestTag) {
        this.hideLoading();
        String requestStr = "";
        if (requestTag instanceof VolleyRequestVo) {
            requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
        }
        if ("getCarDetailsList".equals(requestStr)) {
            Gson gson = new Gson();
            String data = jsonObject.optString("data", "{}");
            list = gson.fromJson(data, new TypeToken<List<TCarDto>>() {
            }.getType());
            handlerData(list);
			/*mListView.setVisibility(View.VISIBLE);
			mListView.onRefreshComplete();*/
        }
    }

    /**
     * 对获取的车辆分类型处理
     */
    private void handlerData(List<TCarDto> list) {
        types = new HashSet<>();
        for (TCarDto carDto : list) {
            types.add(carDto.getCarTypeName());
        }
        lists = new ArrayList<>();
        for (String type : types) {
			List<TCarDto> list1 = new ArrayList<>();
            for (TCarDto carDto : list) {
                if (type.equals(carDto.getCarTypeName())) {
                    list1.add(carDto);
                }
            }
            lists.add(list1);
        }
        initAdapter();
        initlistView();
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id = v.getId();
        switch (id) {
            case R.id.layoutbackimg:
                this.finish();
                break;

            default:
                break;
        }
    }

    class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        private Context context;
        private ViewHolder holder;

        public MyAdapter(Context context) {
            this.context = context;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            if (list != null) {
                return list.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            if (list != null) {
                return list.get(position);
            } else {

                return 0;
            }
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.look_car_item, null);
                holder = new ViewHolder();
                holder.tv = (TextView) convertView.findViewById(R.id.text);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final TCarDto dto = list.get(position);

            String carCode;
            if (dto.getCarCode() != null) {
                carCode = dto.getCarCode();
            } else {
                carCode = "";
            }
            String carNo;
            if (dto.getCarNo() != null) {
                carNo = dto.getCarNo();
            } else {
                carNo = "";
            }

            String carTypeName;
            if (dto.getCarTypeName() != null) {
                carTypeName = dto.getCarTypeName();
            } else {
                carTypeName = "";
            }

            holder.tv.setText(carCode + "-" + carNo + "-" + carTypeName);

            if (dto.getId() != null) {

                convertView.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Intent intent = new Intent(LookCarManageActivity.this,
                                LookCarDetailsActivity.class);
                        intent.putExtra("id", dto.getId());
                        Log.i("aa", "dto.getId():" + dto.getId());
                        startActivity(intent);
                    }
                });
            }

            return convertView;
        }

        class ViewHolder {
            TextView tv;
        }

    }
    private void initlistView() {
        mExListView.setAdapter(adapter);
        // 去掉默认箭头
        mExListView.setGroupIndicator(null);

        mExListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                TCarDto carDto = lists.get(groupPosition).get(childPosition);
                Intent intent = new Intent(LookCarManageActivity.this,
                        LookCarDetailsActivity.class);
                intent.putExtra("id", carDto.getId());
                startActivity(intent);
                return true;
            }
        });
    }

    /**
     * 分区适配器
     */
    public void initAdapter() {
        adapter = new BaseExpandableListAdapter() {

            @Override
            public boolean isChildSelectable(int groupPosition,
                                             int childPosition) {
                return true;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }

            // 父层视图
            @Override
            public View getGroupView(int groupPosition, boolean isExpanded,
                                     View convertView, ViewGroup parent) {
                convertView = LayoutInflater.from(LookCarManageActivity.this).inflate(
                        R.layout.manage_car__item, null);

                ImageView Img = (ImageView) convertView
                        .findViewById(R.id.image);
                // 区域分布
                TextView ctsuper = (TextView) convertView
                        .findViewById(R.id.ctsuper);

                ctsuper.setText(lists.get(groupPosition).get(0).getCarTypeName());
                if (isExpanded) {
                    Img.setBackgroundResource(R.drawable.personnel_image1);

                } else {
                    Img.setBackgroundResource(R.drawable.manage_car_image2);

                }

                return convertView;
            }

            @Override
            public long getGroupId(int groupPosition) {
                return groupPosition;
            }

            @Override
            public int getGroupCount() {

                return lists.size();
            }

            @Override
            public Object getGroup(int groupPosition) {
                return lists.get(groupPosition);
            }

            @Override
            public int getChildrenCount(int groupPosition) {

                return lists.get(groupPosition).size();
            }

            @Override
            public View getChildView(int groupPosition, int childPosition,
                                     boolean isLastChild, View convertView, ViewGroup parent) {
                convertView = LayoutInflater.from(LookCarManageActivity.this).inflate(
                        R.layout.manage_car__item, null);
                ImageView Img = (ImageView) convertView
                        .findViewById(R.id.image);
                Img.setBackgroundResource(R.drawable.ic_directions_car_black);
                TextView ctsuper = (TextView) convertView
                        .findViewById(R.id.ctsuper);
                TCarDto dto = lists.get(groupPosition).get(childPosition);
                String carCode;
                if (dto.getCarCode() != null) {
                    carCode = dto.getCarCode();
                } else {
                    carCode = "";
                }
                String carNo;
                if (dto.getCarNo() != null) {
                    carNo = dto.getCarNo();
                } else {
                    carNo = "";
                }
                ctsuper.setText(carCode+"-"+carNo);
                return convertView;
            }

            @Override
            public long getChildId(int groupPosition, int childPosition) {
                return childPosition;
            }

            // getChild相当于getItem,不过它是获取展开后的项，所以参数有2个索引
            // 第1个索引是父项的，第2个是子项的
            @Override
            public Object getChild(int groupPosition, int childPosition) {
                return lists.get(groupPosition).get(childPosition);
            }
        };
    }


}
