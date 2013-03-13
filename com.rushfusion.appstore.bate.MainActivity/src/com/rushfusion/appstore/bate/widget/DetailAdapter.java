package com.rushfusion.appstore.bate.widget;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.rushfusion.appstore.bate.R;
import com.rushfusion.appstore.bate.entity.AppInfo;

public class DetailAdapter extends BaseAdapter {
	private Context mContext ;
	private AppInfo appInfo ;
	public DetailAdapter(Context context, AppInfo appInfo) {
		this.mContext = context ;
		this.appInfo = appInfo ;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return appInfo.getScreenshots().size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return appInfo.getScreenshots().get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public class ViewHolder{
		ImageView appIcon ;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null ;
		if(convertView==null) {
			viewHolder = new ViewHolder() ;
			convertView = LayoutInflater.from(mContext).inflate(R.layout.detail_item, null) ;
			convertView.setTag(viewHolder) ;
		} else {
			viewHolder = (ViewHolder)convertView.getTag() ;
		}
		List<String> urlList = appInfo.getScreenshots() ; 
		String imageUrl = urlList.get(position) ;
		AQuery aQuery = new AQuery(convertView) ;
		aQuery.id(R.id.app_detail_icon).image(imageUrl) ;
		return convertView;
	}
}
