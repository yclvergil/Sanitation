package com.songming.sanitation.sign.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.songming.sanitation.R;
import com.songming.sanitation.sign.db.SignBean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */

public class RecordAdapter extends BaseAdapter {
    private Context context;
    private List<SignBean> list;

    public RecordAdapter(Context context, List<SignBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list != null) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SignBean bean = list.get(position);
        ViewHold viewHold = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_sign_record, null);
            viewHold = new ViewHold(convertView);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.signName.setText(bean.getName());
        viewHold.signDate.setText(bean.getDate());
        viewHold.signTime.setText(bean.getTime());

        return convertView;
    }


    static class ViewHold{
        TextView signName;
        TextView signDate;
        TextView signTime;

        public ViewHold(View view) {
            signName = (TextView) view.findViewById(R.id.tv_record_name);
            signDate = (TextView) view.findViewById(R.id.tv_record_date);
            signTime = (TextView) view.findViewById(R.id.tv_record_time);
        }
    }
}
