package com.songming.sanitation.workdeal;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.user.model.PushMessageDto;

import org.json.JSONObject;

import java.io.Serializable;

public class NoticeDetailActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout layoutback;
    private TextView topTitle;
    private ImageView layoutbackimg;
    private TextView msgTitle;
    private TextView msgdate;
    private TextView msgCtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);
        findViews();
        initViews();
    }

    @Override
    protected void findViews() {
        layoutback = (LinearLayout) findViewById(R.id.layoutback);
        topTitle = (TextView) findViewById(R.id.topTitle);
        layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);
        msgTitle = (TextView) findViewById(R.id.msgtitle);
        msgdate = (TextView) findViewById(R.id.msgdate);
        msgCtx = (TextView) findViewById(R.id.msgctx);

    }

    @Override
    protected void initViews() {
        topTitle.setText("消息详情");
        layoutback.setVisibility(View.VISIBLE);
        layoutbackimg.setOnClickListener(this);
        PushMessageDto msg = (PushMessageDto) getIntent().getSerializableExtra("MSG");
        msgTitle.setText(msg.getTitle());
        msgdate.setText(msg.getCreateDate());
        msgCtx.setText(msg.getDescription());
    }

    @Override
    protected void errorCallback(VolleyError volleyError, Object requestTag) {

    }

    @Override
    protected void successCallback(JSONObject jsonObject, Object requestTag) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutbackimg:
                this.finish();
                break;
            default:
                break;
        }
    }
}
