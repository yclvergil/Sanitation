package com.songming.sanitation.sign;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;

import org.json.JSONObject;

public class DeclareActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout layoutback;
    private TextView topTitle;
    private ImageView layoutbackimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declare);
        findViews();
        initViews();
    }

    @Override
    protected void findViews() {
        layoutback = (LinearLayout) findViewById(R.id.layoutback);
        topTitle = (TextView) findViewById(R.id.topTitle);
        layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);
    }

    @Override
    protected void initViews() {
        topTitle.setText("功能说明");
        layoutback.setVisibility(View.VISIBLE);
        layoutbackimg.setOnClickListener(this);
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
