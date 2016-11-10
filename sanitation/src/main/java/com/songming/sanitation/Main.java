package com.songming.sanitation;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.panosdk.plugin.indoor.util.ScreenUtils;
import com.google.gson.Gson;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.VolleyRequestVo;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.frameset.utils.UpdateManager;
import com.songming.sanitation.frameset.widget.ImageCarousel;
import com.songming.sanitation.manage.ManageActivity;
import com.songming.sanitation.manage.bean.VersionDTO;
import com.songming.sanitation.sign.SignActivity;
import com.songming.sanitation.user.AboutActivity;
import com.songming.sanitation.user.LoginAcitivity;
import com.songming.sanitation.user.UserCenterAcitivity;
import com.songming.sanitation.workdeal.WorkActivity;
import com.songming.sanitation.workdeal.WorkDealActivity;

/**
 * 主页面
 *
 * @Date 2016年5月28日
 * @Version 1.0
 */
public class Main extends BaseActivity implements OnClickListener {

    private UpdateManager update;
    private ImageCarousel imageCarousel;// 图片轮播组件

    private TextView topTitle;
    private TextView manage;
    private LinearLayout main_sign;
    private LinearLayout main_workdeal;
    private LinearLayout main_workupload;
    private LinearLayout main_check;

    private ImageView main_signimage;
    private ImageView main_workdealimage;
    private ImageView main_workuploadimage;
    private ImageView main_checkimage;

    private long time = 0;
    private long stationId;
    private boolean fromNotification;
    private boolean signNotification;

    private TextView unread;// 工单处理未读
    private String downloadUrl;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private UpdateManager updateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fromNotification = getIntent().getBooleanExtra("fromNotification",
                false);
        signNotification = getIntent().getBooleanExtra("signNotification",
                false);

        if (fromNotification) {
            startActivity(this, WorkDealActivity.class);
        }
        if (signNotification) {
            startActivity(this, SignActivity.class);
        }

        setContentView(R.layout.main);

        findViews();
        initViews();
        // 获取用户登记并判断是什么角色 展示出不同的首页
        // getUserPosition();

        // 检查版本更新
        //requstData();
        checkUpdate();

    }

    private void checkUpdate() {
        String url = "/json.txt";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Constants.SERVER_URL + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s, Object o) {
                Log.d("AboutActivity", s);
                handlerData(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError, Object o) {
                Log.d("AboutActivity", "volleyError:" + volleyError);
            }
        });
        queue.add(request);
    }

    private void handlerData(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            int newVersion = jsonObject.getInt("version");
            downloadUrl = jsonObject.getString("url");
            int olVersion = UpdateManager.getVersionCode(this);
            if (newVersion != olVersion) {

                builder = new AlertDialog.Builder(this);
                alertDialog = builder
                        .setTitle("更新提示")
                        .setMessage("发现新版本，是否进行更新！")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                updateManager = new UpdateManager(Main.this, Constants.SERVER_IP
                                        + downloadUrl);
                                // 直接下载更新
                                updateManager.showDownloadDialog();
                            }
                        })
                        .create();
                alertDialog.show();


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void findViews() {
        topTitle = (TextView) findViewById(R.id.topTitle);
        manage = (TextView) findViewById(R.id.manage);
        imageCarousel = (ImageCarousel) findViewById(R.id.carousel);

        main_sign = (LinearLayout) findViewById(R.id.main_sign);
        main_workdeal = (LinearLayout) findViewById(R.id.main_workdeal);
        main_workupload = (LinearLayout) findViewById(R.id.main_workupload);
        main_check = (LinearLayout) findViewById(R.id.main_check);

        main_signimage = (ImageView) findViewById(R.id.main_signimage);
        main_workdealimage = (ImageView) findViewById(R.id.main_workdealimage);
        main_workuploadimage = (ImageView) findViewById(R.id.main_workuploadimage);
        main_checkimage = (ImageView) findViewById(R.id.main_checkimage);

        unread = (TextView) findViewById(R.id.unread);

    }

    @Override
    protected void initViews() {

        topTitle.setText("首页");

        int[] imagesRes = {R.drawable.home_ad_default1,
                R.drawable.home_ad_default2, R.drawable.home_ad_default3};
        imageCarousel.setImagesRes(imagesRes);
        int sreenWith = ScreenUtils.getWidth(this);
        int height = sreenWith * 462 / 720; // 图片原始比例
        ViewGroup.LayoutParams params = imageCarousel.getLayoutParams();
        params.height = height;
        imageCarousel.setLayoutParams(params);

        main_sign.setOnClickListener(this);
        main_workdeal.setOnClickListener(this);
        main_workupload.setOnClickListener(this);
        main_check.setOnClickListener(this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    // public void getUserPosition() {
    //
    // stationId = SharedPreferencesUtils.getLongValue(this,
    // SharedPreferencesUtils.STATIONID, -1);

    // stationId 1：总经理，2：车队长，3：主管，4：区域经理，5：司机,6:总部
    // if (stationId == 6) {
    // manage.setText("各项管理");
    // main_workuploadimage.setBackgroundResource(R.drawable.maincheck);
    // } else {
    // manage.setText("事件上报");
    // main_workuploadimage
    // .setBackgroundResource(R.drawable.mainwworkupload);
    //
    // }
    // }

    @Override
    protected void onResume() {
        super.onResume();
        // getUserPosition();
        unRead();
    }

    @Override
    protected void errorCallback(VolleyError volleyError, Object requestTag) {
        this.hideLoading();
    }

    @Override
    protected void successCallback(JSONObject jsonObject, Object requestTag) {
        this.hideLoading();
        String requestStr = "";

        if (requestTag instanceof VolleyRequestVo) {
            requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
        }

        if ("findlateversion".equals(requestStr)) {

            try {
                Gson gson = new Gson();
                VersionDTO version = gson.fromJson(
                        jsonObject.optString("data", "{}"), VersionDTO.class);
                if (version != null) {
                    int newVersion = Integer.parseInt(version.getVersionId());
                    int olVersion = UpdateManager.getVersionCode(this);
                    if (newVersion > olVersion) {
                        update = new UpdateManager(this, Constants.SERVER_APK
                                + version.getDownloadUrl());
                        // 直接下载更新
                        update.showDownloadDialog();
                        // update.showNoticeDialog(version.getVersionDescription());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if ("unread".equals(requestStr)) {

            int data = jsonObject.optInt("data");

            if (data > 0) {
                unread.setVisibility(View.VISIBLE);
                unread.setText(data + "");
            } else {
                unread.setVisibility(View.GONE);
            }

        }
    }

    /**
     * 连按两次退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - time > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                time = System.currentTimeMillis();
                return true;
            } else {
                this.finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {

        String loginName = SharedPreferencesUtils.getStringValue(this,
                SharedPreferencesUtils.LOGIN_NAME, "");
        if ("".equals(loginName)) {
            Toast.makeText(this, "您尚未登陆，请登陆后再试！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginAcitivity.class);
            intent.putExtra("reLogin", true);
            startActivity(intent);
            return;
        }

        int id = v.getId();
        switch (id) {
            case R.id.main_sign:
                // 签到
                main_signimage.setBackgroundResource(R.drawable.mainsign);
                main_workdealimage.setBackgroundResource(R.drawable.mainworkdeal);
                main_workuploadimage.setBackgroundResource(R.drawable.maincheck);
                main_checkimage.setBackgroundResource(R.drawable.mainmine01);

                startActivity(new Intent(this, SignActivity.class));

                break;
            case R.id.main_workdeal:
                // 工作
                main_signimage.setBackgroundResource(R.drawable.mainsign2);
                main_workdealimage.setBackgroundResource(R.drawable.mainworkdeal2);
                main_workuploadimage.setBackgroundResource(R.drawable.maincheck);
                main_checkimage.setBackgroundResource(R.drawable.mainmine01);

                startActivity(new Intent(this, WorkActivity.class));
                break;

            case R.id.main_workupload:

                main_signimage.setBackgroundResource(R.drawable.mainsign2);
                main_workdealimage.setBackgroundResource(R.drawable.mainworkdeal);
                main_checkimage.setBackgroundResource(R.drawable.mainmine01);
                // 管理
                main_workuploadimage.setBackgroundResource(R.drawable.maincheck2);
                startActivity(new Intent(this, ManageActivity.class));

                break;
            case R.id.main_check:
                // 我的
                main_signimage.setBackgroundResource(R.drawable.mainsign2);
                main_workdealimage.setBackgroundResource(R.drawable.mainworkdeal);
                main_workuploadimage.setBackgroundResource(R.drawable.maincheck);
                main_checkimage.setBackgroundResource(R.drawable.mainmine02);

                // 用户资料
                startActivity(new Intent(this, UserCenterAcitivity.class));
                break;
            default:
                break;
        }

    }

    /**
     * 描述：是否更新
     */
    private void requstData() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("projectCategory", "6");// 6-智慧环卫
            jsonObject.put("versionType", "1");
            requestHttp(jsonObject, "findlateversion",
                    Constants.FINDLATEVERSION, Constants.SERVER_URL_SM);
        } catch (JSONException e) {
            Toast.makeText(this.getApplicationContext(),
                    "json参数出错:" + e.getMessage(), Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this.getApplicationContext(), "请重新启动",
                    Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 工单处理未读提醒
     */
    private void unRead() {
        JSONObject jsonObject = new JSONObject();

        long staffId = SharedPreferencesUtils.getLongValue(this,
                SharedPreferencesUtils.STAFF_ID, -1);
        try {
            jsonObject.put("executeId", staffId);
            requestHttp(jsonObject, "unread", Constants.UNREAD,
                    Constants.SERVER_URL);
        } catch (JSONException e) {
            Toast.makeText(this.getApplicationContext(),
                    "json参数出错:" + e.getMessage(), Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this.getApplicationContext(), "请重新启动",
                    Toast.LENGTH_SHORT).show();
        }

    }

}
