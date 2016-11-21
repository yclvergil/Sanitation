package com.songming.sanitation.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
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
import com.songming.sanitation.manage.CarSearchActivity;
import com.songming.sanitation.user.adapter.SystemMsgAdapter;
import com.songming.sanitation.user.model.PushMessageDto;
import com.songming.sanitation.workdeal.NoticeDetailActivity;
import com.songming.sanitation.workdeal.SendNoticeActivity;
import com.songming.sanitation.workdown.IncidentListActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息列表
 *
 * @author Administrator
 */
@SuppressLint("NewApi")
public class SystemMsgAcitivity extends BaseActivity implements OnClickListener {

    private LinearLayout layoutback;
    private TextView topTitle;
    private ImageView layoutbackimg;
    private TextView sendNotice;
    private ImageView layoutsearchimg;

    private PullToRefreshListView prlistview;// 下拉列表
    private ListView mListView;// 下拉列表ListView
    private int mCurIndex = 1;
    private SystemMsgAdapter adapter;
    private Long stationId;// 员工ID

    private List<PushMessageDto> list = new ArrayList<PushMessageDto>();

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.system_msg);
        stationId = SharedPreferencesUtils.getLongValue(this,
                SharedPreferencesUtils.STATIONID, -1);
        findViews();
        initViews();
        requestData();
    }

    @Override
    protected void findViews() {

        layoutback = (LinearLayout) findViewById(R.id.layoutback);
        topTitle = (TextView) findViewById(R.id.topTitle);
        layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);
        sendNotice = (TextView) findViewById(R.id.tv_send_notice);
        layoutsearchimg = (ImageView) findViewById(R.id.layoutsearchimg);
        prlistview = (PullToRefreshListView) findViewById(R.id.prlistview);

    }

    @Override
    protected void initViews() {

        topTitle.setText("消息");
        layoutback.setVisibility(View.VISIBLE);
        layoutbackimg.setOnClickListener(this);
        layoutsearchimg.setVisibility(View.VISIBLE);
        layoutsearchimg.setOnClickListener(this);
        sendNotice.setOnClickListener(this);
        mListView = prlistview.getRefreshableView();
        adapter = new SystemMsgAdapter(this, list);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SystemMsgAcitivity.this, NoticeDetailActivity.class);
                intent.putExtra("MSG", list.get(position - 1));
                startActivity(intent);
            }
        });

        prlistview.setOnRefreshListener(new OnRefreshListener2() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase refreshView) {
                mCurIndex = 1;
                requestData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase refreshView) {
                mCurIndex++;
                requestData();
            }
        });
        RefreshUtils.setRefreshPrompt(prlistview);
    }

    @Override
    protected void errorCallback(VolleyError volleyError, Object requestTag) {
        // TODO Auto-generated method stub
        this.hideLoading();
        String requestStr = "";
        if (requestTag instanceof VolleyRequestVo) {
            requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
        }
        if ("list".equals(requestStr)) {
            Toast.makeText(this, "数据获取错误", Toast.LENGTH_SHORT).show();
            prlistview.onRefreshComplete();
        }

        if (volleyError != null && volleyError.getMessage() != null)
            Log.i("list", volleyError.getMessage());
    }

    @Override
    protected void successCallback(JSONObject jsonObject, Object requestTag) {
        this.hideLoading();
        String requestStr = "";

        if (requestTag instanceof VolleyRequestVo) {
            requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
        }
        if ("list".equals(requestStr)) {
            boolean flag = jsonObject.optBoolean("flag");
            if (flag) {
                String data = jsonObject.optString("data");
                Gson gson = new Gson();
                List<PushMessageDto> bean = gson.fromJson(data,
                        new TypeToken<List<PushMessageDto>>() {
                        }.getType());
                if (mCurIndex <= 1) {
                    if (bean == null || bean.size() == 0) {
                        Toast.makeText(SystemMsgAcitivity.this, "暂无任何消息！", Toast.LENGTH_SHORT)
                                .show();
                    }
                    list = bean;
                } else {
                    if (bean != null)
                        list.addAll(bean);
                }
                adapter.setData(list);
                adapter.notifyDataSetChanged();
            } else {
                String msg = jsonObject.optString("msg", "数据接收失败！");
                Toast.makeText(SystemMsgAcitivity.this, msg, Toast.LENGTH_SHORT)
                        .show();

            }

            prlistview.onRefreshComplete();
        }

    }

    /**
     * 描述：接收系统消息提示
     */
    private void requestData() {

        Long staffid = SharedPreferencesUtils.getLongValue(this,
                SharedPreferencesUtils.STAFF_ID, -1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();
        try {
            jsonObject.put("page", mCurIndex);
            jsonObject.put("paramsMap", jsonObject2);
            jsonObject2.put("staffId", staffid);
            jsonObject.put("rows", 20);
            this.showLoading("正在查询中...", "list");
            requestHttp(jsonObject, "list", Constants.SYSTEMMESSAGE,
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
            case R.id.layoutbackimg:
                this.finish();

                break;
            case R.id.tv_send_notice:
                // 发公告
                // stationId 1：总经理，2：车队长，3：主管，4：区域经理，5：司机,6:总部
                if (stationId == 1 || stationId == 2 || stationId == 3
                        || stationId == 4 || stationId == 6) {
                    startActivity(new Intent(this, SendNoticeActivity.class));
                } else {
                    Toast.makeText(this,
                            getResources().getString(R.string.nopower),
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.layoutsearchimg:
                // 搜索按钮
                // 搜索按钮
                Intent intent = new Intent(SystemMsgAcitivity.this, MsgSearchActivity.class);
                intent.putExtra("cate", "消息");
                startActivity(intent);
            default:
                break;
        }
    }

}
