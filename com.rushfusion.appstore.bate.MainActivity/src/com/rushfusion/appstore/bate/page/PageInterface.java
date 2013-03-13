package com.rushfusion.appstore.bate.page;

import java.util.List;

import android.view.KeyEvent;
import android.view.View;

public interface PageInterface {
	public View createContentView() ;
	public View getContentView() ;
	public void update(List<?> params) ;
}	
