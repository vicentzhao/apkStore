package com.rushfusion.appstore.bate.widget;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.rushfusion.appstore.bate.R;
import com.rushfusion.appstore.bate.entity.AppInfo;

public class CategoryAdapter extends BaseAdapter {
	private Context mContext ;
	private List<AppInfo> mParams ;
	public CategoryAdapter(Context context, List<AppInfo> params) {
		this.mContext = context ;
		this.mParams = params ;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mParams.size() ;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mParams.get(position) ;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position ;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null ;
		if(convertView==null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.applist_gv_item, null);
			viewHolder.appIcon=(ImageView)convertView.findViewById(R.id.icon_gvitem_applist);
			viewHolder.appName=(TextView)convertView.findViewById(R.id.name_gvitem_applist);
			viewHolder.appIcon.setImageResource(R.drawable.ic_launcher);
			convertView.setTag(viewHolder) ;
		} else {
			viewHolder = (ViewHolder)convertView.getTag() ;
		}
		AQuery query = new AQuery(convertView) ;
		query.find(R.id.icon_gvitem_applist).image(mParams.get(position).getIcon(), true, true) ;
		viewHolder.appName.setText(mParams.get(position).getName());
		return convertView;
	}

	public class ViewHolder{
		ImageView appIcon ;
		TextView appName;
	}

}
