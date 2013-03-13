package com.rushfusion.appstore.bate.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.util.Log;

import com.rushfusion.appstore.bate.entity.AppInfo;
import com.rushfusion.appstore.bate.entity.SortInfo;

public class Operator {
	public static final String TAG = "Operator" ;
	private static Operator operator ;
	private static Context mContext ;
	
	public static Operator instance(Context context) {
		mContext = context ;
		if(operator==null) {
			operator = new Operator() ;
		}
		return operator ;
	}
	
	public List<SortInfo> SortToBean(Object response){
		if(response==null) return null ;
		Log.i(TAG, "sort:"+response) ;
		List<SortInfo> list = new ArrayList<SortInfo>();
		Map parentMap = (Map)response;
		Set set = parentMap.keySet();
		Iterator it = set.iterator();
		while(it.hasNext()){
			SortInfo si = new SortInfo();
			String str = (String)it.next() ;
			Map map = (Map)parentMap.get(str);
			si.setTid((String)map.get("tid")+"");
			si.setName((String)map.get("name")+"");
			si.setParent((String)map.get("parent")+"");
			si.setDescription((String)map.get("description")+"");
			list.add(si);
		}
		return list;
	}
	
	public AppInfo AppToBean(Object response){
		if(response==null) return null ;
		Log.i(TAG, "appInfo:"+response) ;
		AppInfo appInfo = new AppInfo();
		Map map = (Map)response;
		appInfo.setApkfile((String)map.get("apkfile")+"");
		appInfo.setAuthor((String)map.get("author")+"");
		String string = ""+map.get("comment_count");
		int cou = Integer.valueOf(string);
		appInfo.setComment_count(cou);
		appInfo.setDescription((String)map.get("description")+"");
		appInfo.setIcon((String)map.get("icon")+"");
		appInfo.setId((String)map.get("id")+"");
		appInfo.setName((String)map.get("name")+"");
		appInfo.setScreenshots((List<String>)map.get("screenshots"));
		appInfo.setSource((String)map.get("source")+"");
		appInfo.setTypes((List<String>)map.get("types"));
		appInfo.setVersion((String)map.get("version")+"");
		return appInfo;
	}
	
	public List<AppInfo> AppsToBean(Object response){
		if(response==null) return null ;
		List<AppInfo> list = new ArrayList<AppInfo>();
		List mList =  (List)response;
		for(Object obj : mList){
			list.add(AppToBean(obj));
		}
		return list;
	}
	
	public AppInfo getAppDetial(Object response) {
		if(response==null) return null ;
		Log.i(TAG, "getAppDetial appInfo:"+response) ;
		Map appNode = (Map)response ;
		String author = "" ;
		if(appNode.get("author")!=null) 
			author = appNode.get("author")+"" ;
		return new AppInfo(new Integer(appNode.get("comment_count")+""), appNode.get("id")+"", 
				appNode.get("icon")+"", author, appNode.get("source")+"", 
				(List)appNode.get("screenshots"), appNode.get("description")+"", appNode.get("name")+"", 
				(List)appNode.get("types"), appNode.get("apkfile")+"", appNode.get("version")+"") ;
	}
	
}
