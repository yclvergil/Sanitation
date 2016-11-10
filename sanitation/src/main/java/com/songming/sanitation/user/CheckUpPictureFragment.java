package com.songming.sanitation.user;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.songming.sanitation.R;
import com.songming.sanitation.frameset.utils.Constants;
import com.songming.sanitation.user.model.TUploadFileDto;
import com.songming.sanitation.workdeal.bean.ImageDto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */

public class CheckUpPictureFragment extends Fragment {
    private View rootView;// 缓存Fragment view
    private ImageView imageview;
    private TextView pageNum;
    private int i;
    private DisplayImageOptions options;
    private ImageLoader imageLoader;
    private  List<TUploadFileDto>  list;


    public static CheckUpPictureFragment newInstance(int i, List<TUploadFileDto> list) {
        Bundle args = new Bundle();
        args.putInt("numberi",i);
        args.putSerializable("listImage", (Serializable) list);
        CheckUpPictureFragment fragment = new CheckUpPictureFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        i = args.getInt("numberi");
        list = (List<TUploadFileDto>) args.getSerializable("listImage");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater
                    .inflate(R.layout.checkup_picture_item, null);
            imageview = (ImageView) rootView.findViewById(R.id.imageview);
            pageNum = (TextView) rootView.findViewById(R.id.pageNum);
        }

        pageNum.setText((i + 1) + "/" + list.size());

        String imgUrl = list.get(i).getFilePath();
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.checkup_details_image)
                .showImageForEmptyUri(R.drawable.checkup_details_image)
                .showImageOnFail(R.drawable.checkup_details_image)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(100)).build();// 设置图片进入动画时间100ms
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration
                .createDefault(getActivity()));

        imageLoader.displayImage(Constants.IMAGE_URL + imgUrl, imageview,
                options);

        // 缓存的rootView需要判断是否已经被加过parent，
        // 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;

    }
}

