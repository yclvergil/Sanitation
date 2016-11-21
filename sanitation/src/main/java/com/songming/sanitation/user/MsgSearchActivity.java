package com.songming.sanitation.user;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/11/17.
 */

public class MsgSearchActivity extends BaseActivity implements View.OnClickListener, TextWatcher {
    private TextView cancel;// 搜索取消按键
    private TextView category;// 类别
    private EditText seek;// 搜索输入框
    private ImageView image;// 搜索框清空按钮；
    private ListView mListView;// 搜索展示列表

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.manage_car_search);
        findViews();
        initViews();
    }

    @Override
    protected void findViews() {
        cancel = (TextView) findViewById(R.id.scearch_finish);
        category = (TextView) findViewById(R.id.scearch_category);
        seek = (EditText) findViewById(R.id.searchtext);
        image = (ImageView) findViewById(R.id.scearch_cancel);
        mListView = (ListView) findViewById(R.id.scearch_listview);
    }

    @Override
    protected void initViews() {
        String cate = getIntent().getStringExtra("cate");
        category.setText(cate);
        cancel.setOnClickListener(this);
        image.setOnClickListener(this);
        seek.addTextChangedListener(this);
    }

    @Override
    protected void errorCallback(VolleyError volleyError, Object requestTag) {

    }

    @Override
    protected void successCallback(JSONObject jsonObject, Object requestTag) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scearch_cancel:
                // 搜索文字清空
                seek.setText("");
                break;
            case R.id.scearch_finish:
                this.finish();

                break;

            default:
                break;
        }
    }
}
