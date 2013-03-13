package com.rushfusion.appstore.bate.film;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.rushfusion.appstore.bate.R;
import com.rushfusion.appstore.bate.entity.AppInfo;
import com.rushfusion.appstore.bate.utils.Repository;
import com.rushfusion.appstore.bate.version.UpdateVersion;
import com.rushfusion.appstore.bate.widget.DetailAdapter;

public class DetailPage extends Activity {
	private LinearLayout linearLayout ;
	private TextView appAuthor, appSource, appName, appVersion, appComment, appShowInfo ;
	private Button appDownload;
	private Gallery appGallery;
	private boolean isShow = true ;
	private ProgressBar progressbar ;
	
	private static final int REQ_SUCCESS = 300 ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView() ;
		loadDetail() ;
	}
	
	private void loadDetail() {
		Intent intent = this.getIntent() ;
		String id = intent.getStringExtra("id") ;
		Repository.instance(this).getAppDetail(id, new Repository.DetailCallBack() {
			
			@Override
			public void detailCallBack(AppInfo appInfo) {
				// TODO Auto-generated method stub
				if(appInfo==null) {
					showToast(DetailPage.this.getResources().getString(R.string.net_error)) ;
					return ;
				}
				Message msg = new Message() ;
				msg.obj = appInfo ;
				msg.what = REQ_SUCCESS ;
				handler.sendMessage(msg) ;
			}
		}) ;
	}
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case REQ_SUCCESS:
				AppInfo appInfo = (AppInfo)msg.obj ;
				setAttribute(appInfo) ;
				break;

			default:
				break;
			}
		};
	} ;
	
	private void showToast(final String tostText) {
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Toast.makeText(DetailPage.this, tostText, Toast.LENGTH_LONG).show() ;
			}
		}) ;
	}
	
	public void setAttribute(final AppInfo appEntity) {
		if(appEntity==null) return ;
		progressbar.setVisibility(View.INVISIBLE) ;
		linearLayout.setVisibility(View.VISIBLE) ;
		AQuery query = new AQuery(this) ;
		query.id(R.id.app_pic).image(appEntity.getIcon()) ;
		appAuthor.setText(this.getResources().getString(R.string.app_name_author)+appEntity.getAuthor()) ;
		appSource.setText(this.getResources().getString(R.string.app_name_source)+appEntity.getSource()) ;
		appName.setText(this.getResources().getString(R.string.app_name_1)+appEntity.getName()) ;
		appVersion.setText(this.getResources().getString(R.string.app_name_version)+appEntity.getVersion()) ;
		appComment.setText(appEntity.getDescription()) ;
		
		appShowInfo.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showMoreDetail() ;
			}
		}) ;
		appDownload.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				new Thread(UpdateVersion.instance(DetailPage.this, handler).setUpdateUrl(appEntity.getApkfile()).setLoadApkName(appEntity.getName())).start() ;
			}
		}) ;
		
		appGallery.setAdapter(new DetailAdapter(DetailPage.this, appEntity)) ;
		int center = (int) Math.ceil(appEntity.getScreenshots().size()/2) ;
		appGallery.setSelection(center) ;
	}
	
	private void showMoreDetail() {
		if(isShow) {
			appShowInfo.setText(R.string.app_name_click_pack_up) ;
			appComment.setMaxLines(200);
			isShow = false ;
		} else {
			appShowInfo.setText(R.string.app_name_click_more) ;
			appComment.setMaxLines(3) ;
			isShow = true ;
		}
	}
	
	public void initView() {
		setContentView(R.layout.detail) ;
		linearLayout = (LinearLayout)this.findViewById(R.id.app_single_info) ;
		linearLayout.setVisibility(View.INVISIBLE) ;
		appAuthor = (TextView)this.findViewById(R.id.app_author);
		appSource = (TextView)this.findViewById(R.id.app_source);
		appName = (TextView)this.findViewById(R.id.app_name);
		appVersion = (TextView)this.findViewById(R.id.app_version);
		appDownload = (Button)this.findViewById(R.id.app_download);
		appDownload.setNextFocusLeftId(R.id.app_show_info) ;
		appComment = (TextView)this.findViewById(R.id.app_comment);
		appShowInfo = (TextView)this.findViewById(R.id.app_show_info);
		appGallery = (Gallery)this.findViewById(R.id.app_gallery);
		progressbar = (ProgressBar)this.findViewById(R.id.content_progressbar) ;
		progressbar.setVisibility(View.VISIBLE) ;
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
