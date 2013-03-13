package com.rushfusion.appstore.bate;

import java.util.List;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.rushfusion.appstore.bate.entity.SortInfo;
import com.rushfusion.appstore.bate.film.CategoryPage;
import com.rushfusion.appstore.bate.page.BasePage;
import com.rushfusion.appstore.bate.utils.Repository;

public class AppController {
	
	public Activity mContext ;
	public ViewGroup mNavigation, mContainer, mRecommend ;
	public ProgressBar mProgressBar ;
	public static final int READY = 100 ;
	
	public AppController(Activity context) {
		this.mContext = context ;
		initBaseView() ;
	}
	
	public void setContentView(View contentView) {
		if(mContainer!=null) {
			mContainer.removeAllViews() ;
			mContainer.addView(contentView) ;
		}
	}
	
	public void initBaseView() {
		mContext.setContentView(R.layout.activity_main) ;
		mNavigation = (ViewGroup)mContext.findViewById(R.id.store_navigation_bar) ;
		mContainer = (ViewGroup)mContext.findViewById(R.id.store_container) ;
		mRecommend = (ViewGroup)mContext.findViewById(R.id.store_recommend) ;
		mProgressBar = (ProgressBar)mContext.findViewById(R.id.progress_bar) ;
	}
	
	public void start() {
		mProgressBar.setVisibility(View.VISIBLE) ;
		Repository.instance(mContext).getCategories(new Repository.CategoryCallBack() {
			
			@Override
			public void callBack(List<SortInfo> params) {
				// TODO Auto-generated method stub
				Message msg = new Message() ;
				msg.obj = params ;
				msg.what = READY ;
				handler.sendMessage(msg) ;
			}
		}) ;
	}
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case READY:
				mProgressBar.setVisibility(View.INVISIBLE) ;
				List<SortInfo> params = (List<SortInfo>) msg.obj ;
				page_route(params) ;
				break;

			default:
				break;
			}
		};
	} ;
	
	private BasePage page ;
	public void page_route(List<SortInfo> params) {
		if(page==null) {
			page = new CategoryPage(mContext, this) ;
		}
		page.update(params) ;
	}
	
}
