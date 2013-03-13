package com.rushfusion.appstore.bate.page;

import android.app.Activity;
import android.view.View;

import com.rushfusion.appstore.bate.AppController;

public abstract class BasePage implements PageInterface {
	public Activity mContext ;
	public View mContentView ;
	protected AppController mController ;
	
	public BasePage(Activity context, AppController controller) {
		this.mContext = context ;
		this.mController = controller ;
		getContentView() ;
	}
	
	@Override
	public View getContentView() {
		// TODO Auto-generated method stub
		if(mContentView==null) {
			mContentView = createContentView() ;
		}
		mController.setContentView(mContentView) ;
		return mContentView ;
	}
	
	
	public interface LoadCallBack {
		public void onFinished(Object obj);
	}
}
