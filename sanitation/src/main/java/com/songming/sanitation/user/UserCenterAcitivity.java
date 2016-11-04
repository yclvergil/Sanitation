package com.songming.sanitation.user;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.ApplicationExt;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.VolleyRequestVo;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.user.model.UserDto;

/**
 * “我的”主界面
 *
 * @author Administrator
 */
@SuppressLint("NewApi")
public class UserCenterAcitivity extends BaseActivity implements
        OnClickListener {

    private LinearLayout layoutback;
    private TextView topTitle;
    private ImageView layoutbackimg;
    private ImageView user_image;
    private TextView user_name_text;// 名字
    private TextView user_address_text;// 职称

    private LinearLayout message_layout;// 系统消息
    private LinearLayout contacts_layout;// 通信录
    private LinearLayout password_layout;// 修改密码
    private LinearLayout ll_version;// 版本信息
    private LinearLayout exit_layout;// 退出登录
    private LinearLayout car_layout;// 关联车辆
    private LinearLayout checkupload_layout;// 巡查情况上传
    private LinearLayout checkup_layout;// 巡查情况查看
    private LinearLayout check_work_layout;// 查看考勤
    private LinearLayout look_cargrace_layout;// 车辆轨迹查看
    private LinearLayout car_protect_layout;// 车辆保养
    private LinearLayout personage;// 个人资料
    private LinearLayout ll_head;// 头像区域

    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private static final String TAG = "UserCenterAcitivity";
    private UserDto user;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.user_center_fragment);
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.user_topimage)
                .showImageForEmptyUri(R.drawable.user_topimage)
                .showImageOnFail(R.drawable.user_topimage).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(80)).build();// 设置圆角
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));

        findViews();
        initViews();
        getUserInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        user = ApplicationExt.nowUser;
        if (user != null) {
            imageLoader.displayImage(Constants.IMAGE_URL + user.getPhotoUrl(), user_image, options);
        }
    }

    @Override
    protected void findViews() {
        layoutback = (LinearLayout) findViewById(R.id.layoutback);
        topTitle = (TextView) findViewById(R.id.topTitle);
        layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);
        user_image = (ImageView) findViewById(R.id.user_image);

        user_name_text = (TextView) findViewById(R.id.user_name_text);
        user_address_text = (TextView) findViewById(R.id.user_address_text);

        message_layout = (LinearLayout) findViewById(R.id.message_layout);
        ll_head = (LinearLayout) findViewById(R.id.ll_head);
        contacts_layout = (LinearLayout) findViewById(R.id.contacts_layout);
        password_layout = (LinearLayout) findViewById(R.id.password_layout);
        ll_version = (LinearLayout) findViewById(R.id.ll_version);
        exit_layout = (LinearLayout) findViewById(R.id.exit_layout);
        car_layout = (LinearLayout) findViewById(R.id.car_layout);
        car_protect_layout = (LinearLayout) findViewById(R.id.car_protect_layout);
        look_cargrace_layout = (LinearLayout) findViewById(R.id.look_cargrace_layout);
        check_work_layout = (LinearLayout) findViewById(R.id.check_work_layout);
        checkup_layout = (LinearLayout) findViewById(R.id.checkup_layout);
        checkupload_layout = (LinearLayout) findViewById(R.id.checkupload_layout);
        personage = (LinearLayout) findViewById(R.id.personage_deta_layout);
    }

    @Override
    protected void initViews() {

        topTitle.setText("个人");
        layoutback.setVisibility(View.VISIBLE);
        layoutbackimg.setOnClickListener(this);
        ll_head.setOnClickListener(this);
        message_layout.setOnClickListener(this);
        contacts_layout.setOnClickListener(this);
        password_layout.setOnClickListener(this);
        ll_version.setOnClickListener(this);
        exit_layout.setOnClickListener(this);
        car_layout.setOnClickListener(this);
        checkupload_layout.setOnClickListener(this);
        check_work_layout.setOnClickListener(this);
        checkup_layout.setOnClickListener(this);
        look_cargrace_layout.setOnClickListener(this);
        car_protect_layout.setOnClickListener(this);

        personage.setOnClickListener(this);
    }

    /**
     * 查询用户信息
     */
    private void getUserInfo() {
        long id = SharedPreferencesUtils.getLongValue(this,
                SharedPreferencesUtils.STAFF_ID, -1);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("staffId", id);
            requestHttp(jsonObject, TAG, Constants.GETUSERINFO,
                    Constants.SERVER_URL);

        } catch (JSONException e) {
            Toast.makeText(this, "json参数出错:",
                    Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, "请重新启动", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    @Override
    protected void errorCallback(VolleyError volleyError, Object requestTag) {
        this.hideLoading();
        String requestStr = "";

        if (requestTag instanceof VolleyRequestVo) {
            requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
        }
        if (TAG.equals(requestStr)) {
            Toast.makeText(this, "查询失败", Toast.LENGTH_SHORT)
                    .show();
        }

        if (volleyError != null && volleyError.getMessage() != null)
            Log.i(TAG, volleyError.getMessage());
    }

    @Override
    protected void successCallback(JSONObject jsonObject, Object requestTag) {
        this.hideLoading();
        String requestStr = "";

        if (requestTag instanceof VolleyRequestVo) {
            requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
        }
        // 修改用户信息
        if (TAG.equals(requestStr)) {
            String msg = jsonObject.optString("msg");
            boolean flag = jsonObject.optBoolean("flag");
            if (flag) {
                Gson gson = new Gson();
                user = gson.fromJson(jsonObject.optString("data", "{}"),
                        UserDto.class);
                ApplicationExt.nowUser = user;
                SharedPreferencesUtils.setStringValue(this,
                        SharedPreferencesUtils.USERHEAD, user.getPhotoUrl());
                // 设置头像
                imageLoader.displayImage(Constants.IMAGE_URL + user.getPhotoUrl(),
                        user_image, options);
                user_name_text.setText(user.getName());
                if (user.getStationName() != null) {
                    user_address_text.setText(user.getStationName());
                } else {
                    user_address_text.setText("");
                }
            } else {
                Toast.makeText(this, msg, Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_head:
            case R.id.personage_deta_layout:
                // 个人资料
                startActivity(new Intent(this, UserInfoActivity.class));
                break;

            case R.id.message_layout:
                // 系统信息
                startActivity(new Intent(this, SystemMsgAcitivity.class));
                break;

            case R.id.password_layout:
                // 修改密码
                startActivity(new Intent(this, PasswordChangeedAcitivity.class));

                break;
            case R.id.exit_layout:
                // 退出登录 清空当前用户名和密码
                SharedPreferencesUtils.setStringValue(this,
                        SharedPreferencesUtils.LOGIN_NAME, "");
                SharedPreferencesUtils.setStringValue(this,
                        SharedPreferencesUtils.USERPASSWORD, "");
                SharedPreferencesUtils.setBooleanValue(this,
                        Constants.SIGNFLAG, false);

                startActivity(new Intent(this, LoginAcitivity.class));
                this.finish();

                break;
            case R.id.ll_version:
                // 版本信息
                startActivity(this, AboutActivity.class);

                break;
            case R.id.layoutbackimg:
                this.finish();

                break;
            default:
                break;
        }
    }

}
