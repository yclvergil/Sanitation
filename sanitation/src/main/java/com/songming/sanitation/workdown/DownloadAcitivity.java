package com.songming.sanitation.workdown;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.BaseActivity;
import com.songming.sanitation.frameset.VolleyRequestVo;
import com.songming.sanitation.frameset.ext.GridViewExt;
import com.songming.sanitation.frameset.utils.Bimp;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.frameset.utils.HttpRequestTool;
import com.songming.sanitation.frameset.utils.ImageUtil;
import com.songming.sanitation.frameset.utils.SharedPreferencesUtils;
import com.songming.sanitation.frameset.utils.StringUtilsExt;
import com.songming.sanitation.map.tool.BdMapLocationUtils;
import com.songming.sanitation.map.tool.BdMapLocationUtils.BdLocationSuccessListenner;
import com.songming.sanitation.workdeal.WorkDealActivity;
import com.songming.sanitation.workdown.adapter.DownloadAdapter;

/**
 * 事件上报页面
 *
 * @author Administrator
 */
public class DownloadAcitivity extends BaseActivity implements OnClickListener,
        TextWatcher {

    private LinearLayout layoutback;
    private TextView topTitle;
    private ImageView layoutbackimg;

    private String address;// 地址
    private EditText phone;// 电话
    private EditText detail;// 事件描述
    private ImageView clearphone;// 清除电话按钮
    private ImageView photo;// 照相
    private Button commit;// 提交
    private String addr;// 地点
    private String desc;// 描述

    private GridViewExt gv;// 图片gridview
    private List<Bitmap> picture = new ArrayList<Bitmap>();
    private List<Long> fileIds = new ArrayList<Long>();

    private File tempFile = new File(Environment.getExternalStorageDirectory(),
            ImageUtil.getPhotoFileName());// 文件存储

    private DownloadAdapter adapter;// gridview适配器
    private static final int SHOWPICTURE = 1000;
    private static final int DELETEPICTURE = 99;
    private static final int UPLOAD = 888;
    private static final int UPLOAD_FAIL = 444;
    private static final String TAG = "DownloadAcitivity";

    private Long stationId;// 职权id
    private EditText name;
    private EditText time;
    private ImageView voice;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.downroadactivity);

        findViews();
        initViews();

    }

    Handler handler = new Handler() {

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case SHOWPICTURE:
                    adapter.setPicture(picture);
                    adapter.notifyDataSetChanged();
                    break;
                case DELETEPICTURE:
                    int position = (Integer) msg.obj;
                    picture.remove(position);
                    adapter.setPicture(picture);
                    adapter.notifyDataSetChanged();
                    break;

                case UPLOAD:

                    reportEvent();
                    break;

                case UPLOAD_FAIL:
                    DownloadAcitivity.this.hideLoading();
                    Toast.makeText(DownloadAcitivity.this, "上传图片失败！",
                            Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void findViews() {
        // TODO Auto-generated method stub
        layoutback = (LinearLayout) findViewById(R.id.layoutback);
        topTitle = (TextView) findViewById(R.id.topTitle);
        layoutbackimg = (ImageView) findViewById(R.id.layoutbackimg);

        name = (EditText) findViewById(R.id.et_post_name);
        time = (EditText) findViewById(R.id.et_post_time);
        phone = (EditText) findViewById(R.id.phone);
        detail = (EditText) findViewById(R.id.detail);
        voice = (ImageView) findViewById(R.id.img_voice);
        clearphone = (ImageView) findViewById(R.id.clearphone);
        photo = (ImageView) findViewById(R.id.photo);
        gv = (GridViewExt) findViewById(R.id.gridview);
        commit = (Button) findViewById(R.id.commit);

    }

    @Override
    protected void initViews() {

        topTitle.setText("上报事件");
        layoutback.setVisibility(View.VISIBLE);
        layoutbackimg.setOnClickListener(this);

        clearphone.setOnClickListener(this);
        photo.setOnClickListener(this);
        commit.setOnClickListener(this);
        phone.addTextChangedListener(this);
        voice.setOnClickListener(this);
        adapter = new DownloadAdapter(DownloadAcitivity.this, picture, handler);

        // 为GridView设置适配器
        gv.setAdapter(adapter);
        //百度地址获取事件
        BdMapLocationUtils.getInstance().startLocation(
                new BdLocationSuccessListenner() {

                    @Override
                    public void locationResult(double _latitude,
                                               double _longitude, String _city,
                                               String _locationAddr, String _locationType) {
                        // TODO Auto-generated method stub
                        address = _locationAddr;
                    }
                });

        String userphone = SharedPreferencesUtils.getStringValue(this,
                SharedPreferencesUtils.PHONE, "");
        String username = SharedPreferencesUtils.getStringValue(this,
                SharedPreferencesUtils.USERNAME, "");

        stationId = SharedPreferencesUtils.getLongValue(this,
                SharedPreferencesUtils.STATIONID, -1);
        name.setText(username);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = sdf.format(new Date());
        time.setText(nowTime);
        phone.setText(userphone);

    }

    @Override
    protected void errorCallback(VolleyError volleyError, Object requestTag) {
        // TODO Auto-generated method stub
        this.hideLoading();
        String requestStr = "";

        if (requestTag instanceof VolleyRequestVo) {
            requestStr = ((VolleyRequestVo) requestTag).getRequestTag();
        }
        if (TAG.equals(requestStr)) {
            Toast.makeText(this, "上传失败！", Toast.LENGTH_SHORT).show();
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

        if (TAG.equals(requestStr)) {

            boolean flag = jsonObject.optBoolean("flag");
            String msg = jsonObject.optString("msg", "上传失败！");
            if (flag) {
                Toast.makeText(this, "上传成功！", Toast.LENGTH_SHORT).show();

                if (stationId == 1 || stationId == 4 || stationId == 6) {
                    startActivity(new Intent(this, IncidentListActivity.class));
                }
                this.finish();

            } else {
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
        }

    }

    /**
     * 判断输入框是否为空
     */
    private void checkEmpty() {
        if (!"".equals(phone.getText().toString().trim())) {
            clearphone.setVisibility(View.VISIBLE);
        } else if ("".equals(phone.getText().toString().trim())) {
            clearphone.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 上报事件
     */
    private void reportEvent() {

        long id = SharedPreferencesUtils.getLongValue(this,
                SharedPreferencesUtils.STAFF_ID, -1);
        int orgId = SharedPreferencesUtils.getIntValue(this,
                SharedPreferencesUtils.ORG_ID, -1);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("eventTitle", desc);
            jsonObject.put("createId", id);
            jsonObject.put("phone", phone.getText().toString());
            jsonObject.put("createOrgId", orgId);
            jsonObject.put("address", addr);

            List<JSONObject> jsons = new ArrayList<JSONObject>();
            for (int i = 0; i < fileIds.size(); i++) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("id", fileIds.get(i));
                jsons.add(jsonObject2);
            }
            if (fileIds.size() > 0) {
                jsonObject.put("fileKeys", jsons.toString());
            }

            requestHttp(jsonObject, TAG, Constants.REPORTEVENT,
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
    protected void onActivityResult(int requestCode, int resultCode,
                                    final Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 102:
                    try {
                        Bitmap bm = Bimp.revitionImageSize(tempFile.getPath());
                        picture.add(bm);
                        handler.sendEmptyMessage(SHOWPICTURE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    }


    private void saveFile() throws IOException {

        String userName = SharedPreferencesUtils.getStringValue(
                DownloadAcitivity.this, SharedPreferencesUtils.USERNAME, "");

        for (int i = 0; i < picture.size(); i++) {

            String fileName = ImageUtil.getPhotoFileName();
            File myCaptureFile = new File(getApplicationContext().getFilesDir()
                    + "/" + fileName);
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(myCaptureFile));
            Bitmap bm = picture.get(i);
            bm.compress(Bitmap.CompressFormat.PNG, 80, bos);
            bos.flush();
            bos.close();

            Map<String, String> mapParams = new HashMap<String, String>();
            Map<String, File> mapFiles = new HashMap<String, File>();
            mapFiles.put(fileName, myCaptureFile);

            try {
                JSONObject json = new JSONObject();
                json.put("fileType", "1"); // 文件类型
                json.put("uploadName", URLEncoder.encode(userName, "UTF-8")); // 上传者
                json.put("eventName", URLEncoder.encode(desc, "UTF-8")); // 事件
                json.put("uploadAddress", URLEncoder.encode(addr, "UTF-8")); // 事件地址
                String url = Constants.SERVER_URL + Constants.UPLOADFILE
                        + "?param=" + json.toString();
                String data = HttpRequestTool.post(url, mapParams, mapFiles);

                JSONObject object = new JSONObject(data);
                boolean flag = object.optBoolean("flag");
                if (flag) {

                    String datastr = object.optString("data");
                    JSONObject object2 = new JSONObject(datastr);
                    long fileId = object2.optLong("id");
                    fileIds.add(fileId);
                    if (i == picture.size() - 1) {
                        handler.sendEmptyMessage(UPLOAD);
                    }

                } else {
                    handler.sendEmptyMessage(UPLOAD_FAIL);
                }
            } catch (Exception e) {
                handler.sendEmptyMessage(UPLOAD_FAIL);
            }
        }

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.clearphone:
                // 清除电话框

                phone.setText("");

                break;
            case R.id.photo:
                // 照相
                if (picture.size() <= 2) {
                    Intent imageCaptureIntent = new Intent(
                            MediaStore.ACTION_IMAGE_CAPTURE);
                    imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(tempFile));
                    startActivityForResult(imageCaptureIntent, 102);
                } else {
                    Toast.makeText(this, "上传的图片不能超过三张！", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.layoutbackimg:
                this.finish();

                break;
            case R.id.commit:
                boolean flag = false;
                desc = detail.getText().toString().trim();
                if (picture.size() > 0 || !StringUtilsExt.isEmpty(desc)) {
                    flag = true;
                }
                Log.d(TAG, "flag:" + flag);

                    // 提交
                    addr = address.trim();
                if (StringUtilsExt.isEmpty(addr)) {
                    Toast.makeText(this, "请填写事件地址！", Toast.LENGTH_SHORT).show();
                    return;
                }
                /*if (StringUtilsExt.isEmpty(desc)) {
                    Toast.makeText(this, "请填写事件描述！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (picture.size() <= 0) {
                    Toast.makeText(this, "请上传照片！", Toast.LENGTH_SHORT).show();
                    return;
                }*/
                if (!flag) {
                    Toast.makeText(this, "请上传照片或填写事件描述！", Toast.LENGTH_SHORT).show();
                    return;
                }

                this.showLoading("上传中，请稍后...", TAG);
                if (picture.size() <= 0) {
                    reportEvent();
                } else {
                    // 开启一个新线程去处理上传图片等耗时操作
                    new Thread(new Runnable() {

                        @Override
                        public void run() {

                            try {
                                saveFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }

                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub
        checkEmpty();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 回收图片，释放资源
        if (picture.size() > 0) {
            for (Bitmap bitmap : picture) {
                bitmap.recycle();
            }
        }
    }

}
