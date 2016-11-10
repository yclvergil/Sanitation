package com.songming.sanitation.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.utils.UpdateManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 版本信息
 *
 * @author Administrator
 */
public class AboutActivity extends BaseActivity implements OnClickListener {

    //顶部标题栏控件
    private LinearLayout layoutback;
    private TextView topTitle;
    private ImageView layoutbackimg;
    AlertDialog alertDialog = null;
    AlertDialog.Builder builder = null;
    //版本信息
    private String version;
    private TextView versions;//版本名称
    private TextView description;//版本描述
    private UpdateManager updateManager;
    private String downloadUrl;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.version_info);

        try {
            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        findViews();
        initViews();
    }

    @Override
    protected void findViews() {
        //检查更新
        requestData("/WebTest/json.txt");
        //初始化标题栏
        layoutback = (LinearLayout) findViewById(R.id.layoutback);
        topTitle = (TextView) findViewById(R.id.topTitle);
        layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);

        description = (TextView) findViewById(R.id.versionDescription);
        versions = (TextView) findViewById(R.id.versions);
        versions.setText(getResources().getText(R.string.app_name) + " v" + version + " for android");


    }

    @Override
    protected void initViews() {

        topTitle.setText("版本信息");

        String versionDescription = "\t\t\t" + getResources().getText(R.string.app_name) + "是湖南中威斯顿为环卫事业专门研发的一套应用平台。中威斯顿以信息数字化为宗旨，迎合国家的“互联网+”政策，为降低环卫企业的运营成本，提高企业的管理质量，以及实时监控车辆和人员动态而设计的一款智能APP应用。";

        description.setText(versionDescription);

        layoutback.setVisibility(View.VISIBLE);
        layoutbackimg.setOnClickListener(this);
    }

    @Override
    protected void errorCallback(VolleyError volleyError, Object requestTag) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void successCallback(JSONObject jsonObject, Object requestTag) {
        //{"version":"v1.0.17","url":"/App/AppNew.apk"}
        try {
            String newVersion = jsonObject.getString("version");
            if (!newVersion.equals(version)) {
                String updateUrl = jsonObject.getString("url");
                Toast.makeText(this, updateUrl, Toast.LENGTH_SHORT).show();
                //requestData(updateUrl);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 描述：接收系统消息提示
     */
    private void requestData(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest("http://192.168.0.121:8080" + url, new Response.Listener<String>() {
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

    /**
     * 处理数据
     */
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
                                updateManager = new UpdateManager(AboutActivity.this, "http://192.168.0.121:8080"
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
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.layoutbackimg:
                this.finish();
                break;

            default:
                break;
        }
    }

}
