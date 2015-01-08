package com.example.metrothreeao.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.metrothreeao.R;
import com.example.metrothreeao.entity.content.MenuItem;
import com.example.metrothreeao.face.IChangeFragment;
import com.example.metrothreeao.fragment.reportfault.ConfirmFaultFragment;
import com.example.metrothreeao.fragment.reportfault.FillFaultFragment;
import com.example.metrothreeao.fragment.reportfault.RepairListFragment;
import com.example.metrothreeao.globals.Globals;
import com.example.metrothreeao.myview.ControlScrollViewPager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.TextView;

public class StationErrorActivity extends FragmentActivity {

	private Activity context = this;
	private ListView lvMenu;
	private ControlScrollViewPager cvpFragment;
	private SlidingDrawer sdFragment;
	
	private List<MenuItem> menuList;
	private MenuAdapter menuAdapter;
	private List<Fragment> fragmentList;
	private MainPageAdapter mainPageAdapter;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stationerror);
		initView();
		initListener();
		init();
	}
	
	private void initView(){
		lvMenu = (ListView) findViewById(R.id.lvMenu);
		cvpFragment = (ControlScrollViewPager) findViewById(R.id.cvpFragment);
		sdFragment = (SlidingDrawer) findViewById(R.id.sdFragment);
	}
	
	private void init(){
		menuList = new ArrayList<MenuItem>();
		fragmentList = new ArrayList<Fragment>();
		if(Globals.getInstance().getUser().getPermissions().trim().equals("普通用户")){
			
//			menuList.add(new MenuItem(0, "填报故障"));
//			menuList.add(new MenuItem(1,"报修列表"));
//			menuList.add(new MenuItem(2, "待确认故障"));
//			
//			
//			fragmentList.add(new FillFaultFragment());
//			RepairListFragment repairListFragment = new RepairListFragment();
//			repairListFragment.setType("1");
//			fragmentList.add(repairListFragment);
//			fragmentList.add(new ConfirmFaultFragment());
		}
		else if(Globals.getInstance().getUser().getPermissions().trim().equals("生产调度")){
			
		}
		
		else if(Globals.getInstance().getUser().getPermissions().trim().equals("普通用户,检修车间")){
			menuList.add(new MenuItem(0, "填报故障"));
			menuList.add(new MenuItem(1,"报修列表"));
			menuList.add(new MenuItem(2, "待确认故障"));
			menuList.add(new MenuItem(3, "未接修列表"));
			menuList.add(new MenuItem(4, "在处理列表"));
			menuList.add(new MenuItem(5, "已修复故障"));
			menuList.add(new MenuItem(6, "故障列表"));
			
			
			fragmentList.add(new FillFaultFragment());
			
			RepairListFragment repairListFragment = new RepairListFragment();
			repairListFragment.setType("1");
			fragmentList.add(repairListFragment);
			
			
			RepairListFragment confirmFaultFragment = new RepairListFragment();
			confirmFaultFragment.setType("2");
			fragmentList.add(confirmFaultFragment);
			
			RepairListFragment unRepairListFragment = new RepairListFragment();
			unRepairListFragment.setType("11");
			fragmentList.add(unRepairListFragment);
			
			RepairListFragment executeFaultFragment = new RepairListFragment();
			executeFaultFragment.setType("12");
			fragmentList.add(executeFaultFragment);
			
			RepairListFragment repairedFaultFragment = new RepairListFragment();
			repairedFaultFragment.setType("13");
			fragmentList.add(repairedFaultFragment);
			
			RepairListFragment allFaultFragment = new RepairListFragment();
			allFaultFragment.setType("14");
			fragmentList.add(allFaultFragment);
			
		}

		menuAdapter = new MenuAdapter();
		lvMenu.setAdapter(menuAdapter);

		mainPageAdapter = new MainPageAdapter(getSupportFragmentManager());
		cvpFragment.setAdapter(mainPageAdapter);
		cvpFragment.setCanScroll(false);
		cvpFragment.setOffscreenPageLimit(3);
		
		
	}
	
	public void openRightMenu() {
		if (!sdFragment.isOpened()) {
			sdFragment.animateOpen();
		}
	}

	public void closeRightMenu() {
		if (sdFragment.isOpened()) {
			sdFragment.animateClose();
		}
	}
	
	public void changeRightMenu(Fragment fragment) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.content, fragment);
		ft.commitAllowingStateLoss();
	}
	
	private void initListener(){
		lvMenu.setOnItemClickListener(lvMenuOnItemClickListener);
	}
	
	public void changeToFragmentByIndex(int position){
		cvpFragment.setCurrentItem(position,false);
		menuAdapter.setCurrentPosition(position);
		menuAdapter.notifyDataSetChanged();
		closeRightMenu();
	}
	
	public void refreshFragment(int position,int flag,Object params ){
		((IChangeFragment)fragmentList.get(position)).changeFragment(flag, params);
	}
	
	private OnItemClickListener lvMenuOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			changeToFragmentByIndex(position);
		}
	};
	
	private class MenuAdapter extends BaseAdapter{
		private int currentPosition;		
		public int getCurrentPosition() {
			return currentPosition;
		}

		public void setCurrentPosition(int currentPosition) {
			this.currentPosition = currentPosition;
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
			Holder holder = null;
			MenuItem item = menuList.get(position);
			if(convertView==null){
				convertView = LayoutInflater.from(context).inflate(R.layout.item_content_menu, null);
				holder = new Holder();
				holder.lblMenu = (TextView) convertView.findViewById(R.id.lblMenu);
				convertView.setTag(holder);
			}else {
				holder = (Holder) convertView.getTag();
			}
			
			holder.lblMenu.setText(item.getName());
			if(position==currentPosition){
				holder.lblMenu.setBackgroundResource(R.color.menu_checked);
			}else {
				holder.lblMenu.setBackgroundResource(R.color.menu_normal);
			}
			
			return convertView;
		}
		
		class Holder{
			TextView lblMenu;
		}
		
	}
	
	private class MainPageAdapter extends FragmentPagerAdapter{

		private FragmentManager fm;
		private FragmentTransaction fTransaction;
		public MainPageAdapter(FragmentManager fm) {
			super(fm);
			this.fm = fm;
		}

		@Override
		public Fragment getItem(int location) {
			return fragmentList.get(location);
		}

		@Override
		public int getCount() {
			return fragmentList.size();
		}
		@Override
		public int getItemPosition(Object object)
		{			
				return POSITION_NONE;
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			super.destroyItem(container, position, object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position)
		{
			return super.instantiateItem(container, position);
		}
		
//		@Override
//		public Object instantiateItem(ViewGroup container, int position) {
//			container.addView(views.get(position));
//			return views.get(position);
//		}
//		
//		@Override
//		public void destroyItem(ViewGroup container, int position, Object object) {
//			 View view = (View)object;
//		        ((ViewPager) container).removeView(view);
//		        view = null;
//		}
		
	}
	
	
}
