package com.rushfusion.appstore.bate.film;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.rushfusion.appstore.bate.AppController;
import com.rushfusion.appstore.bate.R;
import com.rushfusion.appstore.bate.entity.AppInfo;
import com.rushfusion.appstore.bate.entity.SortInfo;
import com.rushfusion.appstore.bate.page.BasePage;
import com.rushfusion.appstore.bate.utils.Repository;
import com.rushfusion.appstore.bate.widget.CategoryAdapter;

public class CategoryPage extends BasePage {
	
	public static final int GO = 200 ;
	private boolean isChange = false ;

	public CategoryPage(Activity context, AppController controller) {
		super(context, controller);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View createContentView() {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(mContext).inflate(R.layout.app_categories, null) ;
		return view;
	}

	@Override
	public void update(List<?> params) {
		// TODO Auto-generated method stub
		initView() ;
		initHead(params) ;
	}
	
	AdapterView.OnItemSelectedListener itemSelectedListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			AppInfo appInfo = appInfos.get(position) ;
			AQuery query = new AQuery(mContext) ;
			query.id(R.id.app_icn).image(appInfo.getIcon()) ;
			previewName.setText(appInfo.getName()) ;
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
	};
	
	AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Intent it = new Intent(mContext, DetailPage.class);
			Bundle bundle = new Bundle();
			bundle.putString("id", appInfos.get(position).getId());
			it.putExtras(bundle);
			mContext.startActivity(it);
			mContext.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
		}
	};
	
	List<SortInfo> sort1 ;
	SortInfo currentInfo ;
	int position = 1 ;
	private void initHead(List<?> params) {
		sort1 = Repository.instance(mContext).getSort1() ;
		if(sort1!=null) {
			mController.mNavigation.removeAllViews() ;
			for(int i=0; i<sort1.size(); i++) {
				Button child = new Button(mContext) ;
				child.setTag(i) ;
				child.setText(sort1.get(i).getDescription()) ;
				int width = (int) Math.ceil(getScreenWidth() / sort1.size()) ;
				child.setLayoutParams(new LayoutParams(width, LayoutParams.WRAP_CONTENT));
				child.setBackgroundResource(R.drawable.home_btn_bg) ;
				child.setTextColor(Color.WHITE);
				child.setGravity(Gravity.CENTER) ;
				setButtonBackGroundColor(child, 28) ;
				mController.mNavigation.addView(child) ;
				child.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						currentPage = 0 ;
						isChange = false ;
						position = (Integer)v.getTag() ;
						currentInfo = sort1.get(position) ;
						loadPage(0, currentInfo) ;
					}
				}) ;
			}
			currentInfo = sort1.get(position) ;
			loadPage(currentPage, currentInfo) ;
		}
	}
	
	private int getScreenWidth() {
		DisplayMetrics metric = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;    
	}
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO:
				loading = false ;
				mController.mProgressBar.setVisibility(View.INVISIBLE) ;
				appInfos = (ArrayList<AppInfo>)msg.obj ;
				CategoryAdapter adapter = new CategoryAdapter(mContext, appInfos) ;
				gridView.setAdapter(adapter) ;
				showView() ;
				updatePageIndex() ;
				if(!isChange) addBooter() ;
				break;

			default:
				break;
			}
		};
	} ;
	
	List<AppInfo> appInfos = null ;
	private void loadPage(int currentPage, SortInfo info) {
		mController.mProgressBar.setVisibility(View.VISIBLE) ;
		loading = true ;
		Repository.instance(mContext).getContentList(currentPage, info.getName(), new Repository.ContentCallBack() {
			
			@Override
			public void contentCallBack(List<AppInfo> params) {
				// TODO Auto-generated method stub
				if(params==null) {
					showToast(mContext.getResources().getString(R.string.net_error)) ;
					return ;
				}
				Message msg = new Message() ;
				msg.obj = params ;
				msg.what = GO ;
				handler.sendMessage(msg) ;
			}
		}) ;
	}
	
	private void addBooter() {
		Map<String, List<SortInfo>> sort2Node = Repository.instance(mContext).getSort2() ;
		if(sort2Node!=null) {
			List<SortInfo> list = sort2Node.get(currentInfo.getTid()) ;
			mController.mRecommend.removeAllViews() ;
			if(list!=null&&list.size()>0) {
				for(int i=0; i<list.size(); i++) {
					final SortInfo info = list.get(i) ;
					Button btn = new Button(mContext);
					int width = (int) Math.ceil(getScreenWidth() / list.size()) ;
					btn.setLayoutParams(new LayoutParams(width, LayoutParams.WRAP_CONTENT));
					btn.setText(info.getDescription());
					setButtonBackGroundColor(btn, 24) ;
					btn.setOnClickListener(new OnClickListener() {
						
						public void onClick(View v) {
							currentPage = 0 ;
							currentInfo = info ;
							isChange = true ;
							loadPage(0, currentInfo) ;
						}
					});
					mController.mRecommend.addView(btn) ;
				}
			}
		}
	}
	
	private void showToast(final String tostText) {
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Toast.makeText(mContext, tostText, Toast.LENGTH_LONG).show() ;
			}
		}) ;
	}
	
	private void setButtonBackGroundColor(Button btn, int textSize) {
		btn.setTextSize(textSize) ;
		btn.setTextColor(Color.WHITE);
		btn.setBackgroundColor(Color.argb(50, 80, 80, 80));
		btn.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					v.setBackgroundResource(R.drawable.home_btn_bg_s) ;
				}else{
					v.setBackgroundColor(Color.argb(50, 80, 80, 80));
				}
			}
		});
	}
	
	private GridView gridView ;
	private TextView previewName, pageIndex ;
	private Button prePage, nextPage ;
	private boolean loading = false ;
	private void initView() {
		gridView = (GridView)mContentView.findViewById(R.id.app_gridView) ;
		previewName = (TextView)mContentView.findViewById(R.id.app_name) ;
		pageIndex = (TextView)mContentView.findViewById(R.id.app_page_index) ;
		prePage = (Button)mContentView.findViewById(R.id.app_pre_page) ;
		prePage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!loading)
					prePage() ;
			}
		}) ;
		setButtonBackGroundColor(prePage, 22) ;
		nextPage = (Button)mContentView.findViewById(R.id.app_next_page) ;
		nextPage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!loading)
					nextPage() ;
			}
		}) ;
		setButtonBackGroundColor(nextPage, 22) ;
		gridView.setOnItemSelectedListener(itemSelectedListener) ;
		gridView.setOnItemClickListener(clickListener) ;
	}
	
	private void showView() {
		pageIndex.setVisibility(View.VISIBLE) ;
		prePage.setVisibility(View.VISIBLE) ;
		nextPage.setVisibility(View.VISIBLE) ;
	}
	
	private void prePage() {
		if(currentPage>=0) {
			--currentPage ;
			updatePageIndex() ;
			loadPage(currentPage, currentInfo) ;
		}
	}
	
	private void nextPage() {
		if(currentPage<Repository.instance(mContext).getPageCount()-1) {
			++currentPage ;
			updatePageIndex() ;
			loadPage(currentPage, currentInfo) ;
		}
	}
	
	private int currentPage = 0 ;
	private void updatePageIndex() {
		int totalPage = Repository.instance(mContext).getPageCount() ;
		if(currentPage <= 0 && totalPage>=0){
			prePage.setEnabled(false);
			nextPage.setEnabled(true);
		}else if(currentPage>0 && currentPage>=totalPage){
			nextPage.setEnabled(false);
			prePage.setEnabled(true) ;
		}else{
			prePage.setEnabled(true);
			nextPage.setEnabled(true);
		}
		pageIndex.setText((currentPage+1)+"/"+totalPage);
	}

}
