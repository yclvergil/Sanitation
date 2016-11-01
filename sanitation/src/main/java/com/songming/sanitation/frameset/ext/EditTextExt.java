package com.songming.sanitation.frameset.ext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * 
 * @author zyj 自定义editview,插入多张图片
 * 
 */

public class EditTextExt extends EditText {

	public EditTextExt(Context context) {
		super(context);
	}

	public EditTextExt(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void insertDrawable(int id) {

		final SpannableString ss = new SpannableString("pic");
		// 得到drawable对象，即�?��插入的图�?
		Drawable d = getResources().getDrawable(id);
		d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
		// 用这个drawable对象代替字符串pic
		ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
		// 包括0但是不包�?pic".length()即：4。[0,4)
		ss.setSpan(span, 0, "pic".length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		append(ss);

	}

	public void insertDrawableByPath(String path) {

		final SpannableString ss = new SpannableString("pic");
		// 得到drawable对象，即�?��插入的图�?
		int id=0;
		Drawable d = getResources().getDrawable(id);
		d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
		// 用这个drawable对象代替字符串pic
		ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
		// 包括0但是不包�?pic".length()即：4。[0,4)
		ss.setSpan(span, 0, "pic".length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		append(ss);

	}

}
