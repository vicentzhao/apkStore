package com.rushfusion.appstore.bate.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.rushfusion.appstore.bate.entity.AppInfo;
import com.rushfusion.appstore.bate.entity.SortInfo;
import com.rushfusion.dp.bussiness.appstore.AppByIdReq;
import com.rushfusion.dp.bussiness.appstore.AppsByCategoryReq;
import com.rushfusion.dp.bussiness.appstore.CategoriesReq;
import com.rushfusion.dp.model.Business;
import com.rushfusion.dp.model.CallBack;
import com.rushfusion.dp.model.Factory;
import com.rushfusion.dp.model.Request;
import com.rushfusion.dp.model.State;

public class Repository {
	private static  Repository repository ;
	
	private static Context mContext ;
	
	public static Repository instance(Context context) {
		mContext = context ;
		if(repository==null) {
			repository = new Repository() ;
		}
		return repository ;
	}
	
	List<SortInfo> sorts ;
	public void getCategories(final CategoryCallBack callBack) {
		CategoriesReq req =(CategoriesReq)Factory.createRequest(CategoriesReq.class);
		Business.callBusiness(req, new CallBack() {
			
			public void businessProcess(Request request, Object response, State state) {
				if(state.getState()==State.SUCCESS){
					sorts = Operator.instance(mContext).SortToBean(response);
					callBack.callBack(sorts) ;
				}else{
					System.out.println("wrong in get categories . error code : "+state.getStateCode()+"error info :"+state.getDescription());
				}
			}
		});
	}
	
	public List<SortInfo> getSort1() {
		if(sorts==null) return null ;
		List<SortInfo> sort1 = new ArrayList<SortInfo>() ;
		for(SortInfo info : sorts) {
			String parent = info.getParent() ;
			if(parent.equals("0")) {
				sort1.add(info) ;
			}
		}
		return sort1 ;
	}
	
	public Map<String, List<SortInfo>> getSort2() {
		if(sorts==null) return null ;
		Map<String, List<SortInfo>> sortMap = new HashMap<String, List<SortInfo>>() ;
		for(SortInfo info : sorts) {
			String parent = info.getParent() ;
			if(parent.equals("0")) {
				List<SortInfo> sort2 = new ArrayList<SortInfo>() ;
				String tid = info.getTid() ;
				for(SortInfo info2 : sorts) {
					if(tid.equals(info2.getParent())) {
						sort2.add(info2) ;
					}
				}
				sortMap.put(tid, sort2) ;
			}
		}
		return sortMap ;
	}
	
	private int pageCount ;
	public void getContentList(int currentPage, String type, final ContentCallBack callBack) {
		AppsByCategoryReq req =(AppsByCategoryReq)Factory.createRequest(AppsByCategoryReq.class);
		req.setType(type);
		req.setCurrent(currentPage) ;
		req.setLimit(18);
		Business.callBusiness(req, new CallBack() {
			public void businessProcess(Request request, Object response,com.rushfusion.dp.model.State state) {
				if(state.getState()==com.rushfusion.dp.model.State.SUCCESS){
					AppsByCategoryReq req = (AppsByCategoryReq)request;
					pageCount = req.getTotal_pgaes();
					System.out.println("total page:"+pageCount);
					System.out.println("total items:"+req.getTotal_items());
					callBack.contentCallBack(Operator.instance(mContext).AppsToBean(response)) ;
				}
			}
		}) ;
	}
	
	public void getAppDetail(String appid, final DetailCallBack callBack) {
		if(appid==null) return ;
		AppByIdReq req =(AppByIdReq)Factory.createRequest(AppByIdReq.class);
		req.setId(appid);
		Business.callBusiness(req, new CallBack(){

			public void businessProcess(Request request, Object response,com.rushfusion.dp.model.State state) {
				if(state.getState()==state.SUCCESS){
					AppInfo appInfo = Operator.instance(mContext).getAppDetial(response) ;
					callBack.detailCallBack(appInfo) ;
				}else{
					state.getDescription();
					state.getStateCode();
					
				}
			}
		});
	}
	
	public interface CategoryCallBack {
		public void callBack(List<SortInfo> params) ;
	}
	
	public interface ContentCallBack {
		public void contentCallBack(List<AppInfo> params) ;
	}
	
	public interface DetailCallBack {
		public void detailCallBack(AppInfo appInfo) ;
	}
	
	public int getPageCount() {
		return pageCount ;
	}
}
