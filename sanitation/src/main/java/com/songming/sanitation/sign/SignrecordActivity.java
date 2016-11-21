package com.songming.sanitation.sign;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.sign.adapter.RecordAdapter;
import com.songming.sanitation.sign.db.DBUtils;
import com.songming.sanitation.sign.db.SignBean;

import org.json.JSONObject;

import java.util.List;

public class SignrecordActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout layoutback;
    private TextView topTitle;
    private ImageView layoutbackimg;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signrecord);
        findViews();
        initViews();
    }

    @Override
    protected void findViews() {
        layoutback = (LinearLayout) findViewById(R.id.layoutback);
        topTitle = (TextView) findViewById(R.id.topTitle);
        layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);
        listView = (ListView) findViewById(R.id.lv_sign_record);
    }

    @Override
    protected void initViews() {
        String cate = getIntent().getStringExtra("cate");
        topTitle.setText(cate);
        layoutback.setVisibility(View.VISIBLE);
        layoutbackimg.setOnClickListener(this);
        List<SignBean> all = null;
        if ("本人签到记录".equals(cate)) {
            all = DBUtils.instance(this).findByType(1);
        } else {
            //员工签到记录
            all = DBUtils.instance(this).findByType(2);
        }
        RecordAdapter adapter = new RecordAdapter(this.getApplicationContext(),all);
        listView.setAdapter(adapter);
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
