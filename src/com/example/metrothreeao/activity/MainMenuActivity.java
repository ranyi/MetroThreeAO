package com.example.metrothreeao.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.metrothreeao.MyApplication;
import com.example.metrothreeao.R;
import com.example.metrothreeao.entity.mainmenu.MainActivityMenuItem;
import com.example.metrothreeao.globals.Globals;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainMenuActivity extends Activity {

	private ViewPager vpMenu;

	private List<View> menuPageList = new ArrayList<View>();
	private MenuPageAdapter menuPageAdapter;

	private List<MainActivityMenuItem> firstPageMenuList = new ArrayList<MainActivityMenuItem>();
	private MenuAdapter firstPageAdapter;
	
	private Activity context = this;
	
	private long mOnBackTime;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		Globals.allActivitys.put("MainMenuActivity", this);
		initView();
		init();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Globals.allActivitys.remove("MainMenuActivity");
	}
	
	@Override
	public void onBackPressed() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - mOnBackTime > 1200) {
			Toast.makeText(context, "请再按一次返回", Toast.LENGTH_LONG).show();
			mOnBackTime = currentTime;
			return;
		}
		
		Builder builder = new Builder(context);
		builder.setMessage("是否退出");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				((MyApplication)getApplication()).appExit();
			}
		});
		builder.setNegativeButton("取消", null);
		AlertDialog dialog = builder.create();
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		if(!dialog.isShowing()){
			dialog.show();
		}
	}
	
	private void initView() {
		vpMenu = (ViewPager) findViewById(R.id.vpMenu);
	}
	
	private void init() {
		initMenuList();
		RelativeLayout firstPage = (RelativeLayout) LayoutInflater.from(this)
				.inflate(R.layout.item_mainactivity_menupage, null);
		GridView gvFirstPage = (GridView) firstPage.findViewById(R.id.gvMenu);
		firstPageAdapter = new MenuAdapter(firstPageMenuList);
		gvFirstPage.setAdapter(firstPageAdapter);
		gvFirstPage.setOnItemClickListener(gvOnItemClickListener);
		menuPageList.add(firstPage);

		menuPageAdapter = new MenuPageAdapter();
		vpMenu.setAdapter(menuPageAdapter);
	}

	private void initMenuList() {
		firstPageMenuList.add(new MainActivityMenuItem(1, R.drawable.baoxiu,
				"故障保修"));

	}
	
	private OnItemClickListener gvOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			if(id==1){
				Intent intent = new Intent(context,StationErrorActivity.class);
				startActivity(intent);
			}			
			
		}
	};
	
	private class MenuAdapter extends BaseAdapter {
		private List<MainActivityMenuItem> menuList;

		public MenuAdapter(List<MainActivityMenuItem> menuList) {
			this.menuList = menuList;
		}

		public List<MainActivityMenuItem> getMenuList() {
			return menuList;
		}

		@Override
		public int getCount() {
			return menuList.size();
		}

		@Override
		public Object getItem(int position) {
			return menuList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return menuList.get(position).getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			MainActivityMenuItem item = menuList.get(position);
			View view = LayoutInflater.from(context).inflate(
					R.layout.item_mainactivity_menu, null);
			ImageView ivIcon = (ImageView) view.findViewById(R.id.ivIcon);
			TextView lblName = (TextView) view.findViewById(R.id.lblName);
			ivIcon.setImageResource(item.getIcResource());
			lblName.setText(item.getName());
			return view;
		}
		
	

	}
	

	private class MenuPageAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return menuPageList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			View view = (View) object;
			((ViewPager) container).removeView(view);
			view = null;
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		@Override
		public void finishUpdate(ViewGroup container) {
			super.finishUpdate(container);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			((ViewPager) container).addView(menuPageList.get(position));
			return menuPageList.get(position);
		}

	}

	
}
