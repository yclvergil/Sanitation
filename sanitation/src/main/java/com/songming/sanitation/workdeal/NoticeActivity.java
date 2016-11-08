package com.songming.sanitation.workdeal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.user.SystemMsgAcitivity;

import org.json.JSONObject;

//公告
public class NoticeActivity extends BaseActivity implements OnClickListener {

    private LinearLayout layoutback;
    private TextView topTitle;
    private ImageView layoutbackimg;

    private LinearLayout send_notice;// 发送
    private LinearLayout reception_notice;// 接收

    private Long stationId;// 员工ID

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.notice_activity);

        stationId = SharedPreferencesUtils.getLongValue(this,
                SharedPreferencesUtils.STATIONID, -1);

        findViews();
        initViews();

    }

    @Override
    protected void findViews() {
        layoutback = (LinearLayout) findViewById(R.id.layoutback);
        topTitle = (TextView) findViewById(R.id.topTitle);
        layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);

        send_notice = (LinearLayout) findViewById(R.id.send_notice_layout);

        reception_notice = (LinearLayout) findViewById(R.id.reception_notice_layout);
    }

    @Override
    protected void initViews() {
        topTitle.setText("公告通知");
        layoutback.setVisibility(View.VISIBLE);
        layoutbackimg.setOnClickListener(this);

        send_notice.setOnClickListener(this);
        reception_notice.setOnClickListener(this);
    }

    @Override
    protected void errorCallback(VolleyError volleyError, Object requestTag) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void successCallback(JSONObject jsonObject, Object requestTag) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id = v.getId();

        switch (id) {
            case R.id.layoutbackimg:
                this.finish();
                break;
            case R.id.send_notice_layout:
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
            case R.id.reception_notice_layout:
                // 接收的，系统信息
                startActivity(new Intent(this, SystemMsgAcitivity.class));
                break;

            default:
                break;
        }
    }
}
