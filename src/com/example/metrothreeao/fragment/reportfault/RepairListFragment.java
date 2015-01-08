package com.example.metrothreeao.fragment.reportfault;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.metrothreeao.R;
import com.example.metrothreeao.activity.StationErrorActivity;
import com.example.metrothreeao.constant.HTTPConstant;
import com.example.metrothreeao.constant.TaskConstant;
import com.example.metrothreeao.dialog.ConfirmRepairDialog;
import com.example.metrothreeao.dialog.ExecuteRepairDialog;
import com.example.metrothreeao.dialog.TakeRepairDialog;
import com.example.metrothreeao.entity.repairfault.FaultFilterParams;
import com.example.metrothreeao.entity.repairfault.GzInfo;
import com.example.metrothreeao.face.IChangeFragment;
import com.example.metrothreeao.globals.Globals;
import com.example.metrothreeao.myview.xlistview.XListView;
import com.example.metrothreeao.myview.xlistview.XListView.IXListViewListener;
import com.example.metrothreeao.server.webservice.WSAsyncTask;
import com.example.metrothreeao.server.webservice.WSRequestParams;
import com.example.metrothreeao.server.webservice.WSResponseInterface;
import com.example.metrothreeao.server.webservice.WSSoapParams;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class RepairListFragment extends Fragment implements IXListViewListener,
		WSResponseInterface, IChangeFragment {

	private String TAG = "RepairListFragment";
	private XListView xlvFault;
	private Button btnFilter;
	private TextView lblTitle;
	private List<GzInfo> gzInfoItems = new ArrayList<GzInfo>();
	private GzInfoAdapter adapter;

	private FaultFilterParams filterParams = new FaultFilterParams();
	private int pageIndex = 1;
	private int pageSize = 20;
	private String type;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_repairlist, container,
				false);
		initView(view);
		initListener();
		init();
		getFirstData();
		return view;
	}

	@Override
	public void onRefresh() {
		getFirstData();
	}

	@Override
	public void onLoadMore() {
		pageIndex++;
		getData();
	}
	public void setType(String type){
		this.type=type;
	}

	private void initView(View view) {
		xlvFault = (XListView) view.findViewById(R.id.xlvFault);
		btnFilter = (Button) view.findViewById(R.id.btnFilter);
		lblTitle = (TextView) view.findViewById(R.id.lblTitle);
	}

	private void initListener() {
		xlvFault.setXListViewListener(this);
		btnFilter.setOnClickListener(btnFilterOnClickListener);
		xlvFault.setOnItemClickListener(xlvFaultOnItemClickListener);
	}

	private void init() {
		xlvFault.setPullLoadEnable(true);// 下部更新
		xlvFault.setPullRefreshEnable(true); // 上部更新
		adapter = new GzInfoAdapter();
		xlvFault.setAdapter(adapter);
		if("1".equals(type)){
			lblTitle.setText("报修列表");
		}else if ("11".equals(type)) {
			lblTitle.setText("未接修列表");
		}else if ("2".equals(type)) {
			lblTitle.setText("待确认故障列表");
		}else if ("12".equals(type)) {
			lblTitle.setText("在处理列表");
		}else if ("13".equals(type)) {
			lblTitle.setText("已修复列表");
		}else if ("13".equals(type)) {
			lblTitle.setText("故障列表");
		}
	}

	private void getFirstData() {
		pageIndex = 1;
		gzInfoItems.clear();
		getData();
	}

	private void getData() {
		ArrayList<WSSoapParams> soapParams = new ArrayList<WSSoapParams>();
		if("1".equals(type) || "2".equals(type)){
			soapParams.add(new WSSoapParams("depid", Globals.getInstance()
					.getUser().getDepartmentID()));
		}else {
			soapParams.add(new WSSoapParams("depid", Globals.getInstance()
					.getUser().getUserID()));
		}		
		soapParams.add(new WSSoapParams("KsTime", filterParams.getKsTime()));
		soapParams.add(new WSSoapParams("JsTime", filterParams.getJsTime()));
		soapParams.add(new WSSoapParams("Xl", filterParams.getXl()));
		soapParams.add(new WSSoapParams("Zd", filterParams.getZd()));
		soapParams.add(new WSSoapParams("Bxdw", filterParams.getBxdw()));
		soapParams.add(new WSSoapParams("Jxdw", filterParams.getJxdw()));
		soapParams.add(new WSSoapParams("Dj", filterParams.getDj()));
		soapParams.add(new WSSoapParams("Flag", filterParams.getFlag()));
		soapParams.add(new WSSoapParams("Lh", filterParams.getLh()));
		soapParams.add(new WSSoapParams("CxType", filterParams.getCxType()));
		soapParams
				.add(new WSSoapParams("TimeType", filterParams.getTimeType()));
		soapParams.add(new WSSoapParams("Time", filterParams.getTime()));
		soapParams.add(new WSSoapParams("KeyWord", filterParams.getKeyWord()));
		soapParams.add(new WSSoapParams("PageIndex", "" + pageIndex));
		soapParams.add(new WSSoapParams("PageSize", "" + pageSize));
		soapParams.add(new WSSoapParams("Sb", filterParams.getSb()));
		soapParams.add(new WSSoapParams("Type", type));

		new WSAsyncTask(RepairListFragment.this).execute(new WSRequestParams(
				soapParams, HTTPConstant.GET_LIST_BY_STATION,
				HTTPConstant.LOGIN_URL, null));
	}

	private void deleteGz(String gzId) {
		ArrayList<WSSoapParams> soapParams = new ArrayList<WSSoapParams>();
		soapParams.add(new WSSoapParams("gzid", gzId));
		new WSAsyncTask(RepairListFragment.this).execute(new WSRequestParams(
				soapParams, HTTPConstant.DELETE_GZ_BY_ID,
				HTTPConstant.LOGIN_URL, null));
	}

	@Override
	public void callBackResponse(String result, String responseMethod) {
		Gson gson = new Gson();
		if (responseMethod.equals(HTTPConstant.GET_LIST_BY_STATION)) {
			xlvFault.stopRefresh();
			xlvFault.stopLoadMore();
			if (HTTPConstant.ERROR.equals(result)) {
				Toast.makeText(getActivity(), "请求服务器数据失败,请检查网络是否正常",
						Toast.LENGTH_LONG).show();
				return;
			}

			Log.i(TAG, result);

			try {
				JSONObject jsonObject = new JSONObject(result);
				if ("1".equals(jsonObject.getString("isSuccess"))
						&& !"{}".equals(jsonObject.getString("Msg"))) {
					List<GzInfo> gzInfos = gson.fromJson(jsonObject
							.getJSONObject("Msg").getString("GzInfos"),
							new TypeToken<List<GzInfo>>() {
							}.getType());
					if (gzInfos != null && gzInfos.size() > 0) {
						gzInfoItems.addAll(gzInfos);
					}
					adapter.notifyDataSetChanged();
				} else {
					Toast.makeText(getActivity(), jsonObject.getString("Msg"),
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (responseMethod.equals(HTTPConstant.DELETE_GZ_BY_ID)) {
			if (HTTPConstant.ERROR.equals(result)) {
				Toast.makeText(getActivity(), "请求服务器数据失败,请检查网络是否正常",
						Toast.LENGTH_LONG).show();
				return;
			}

			Log.i(TAG, result);

			try {
				JSONObject jsonObject = new JSONObject(result);
				if ("1".equals(jsonObject.getString("isSuccess"))
						&& !"{}".equals(jsonObject.getString("Msg"))) {
					Toast.makeText(getActivity(), "删除成功！", Toast.LENGTH_LONG)
							.show();
					getFirstData();
				} else {
					Toast.makeText(getActivity(), jsonObject.getString("Msg"),
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private OnClickListener btnFilterOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			FilterFaultFragment fragment = new FilterFaultFragment();
			fragment.setFilterParams(filterParams);
			fragment.setFragment(RepairListFragment.this);
			((StationErrorActivity) getActivity()).changeRightMenu(fragment);
			((StationErrorActivity) getActivity()).openRightMenu();
		}
	};

	private OnItemClickListener xlvFaultOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			GzInfo info = gzInfoItems.get(position - 1);
			// Toast.makeText(getActivity(), info.getGz_id(),
			// Toast.LENGTH_LONG).show();
			FaultDetailFragment fragment = new FaultDetailFragment();
			fragment.setGzInfo(info);
			((StationErrorActivity) getActivity()).changeRightMenu(fragment);
			((StationErrorActivity) getActivity()).openRightMenu();
		}
	};

	private class GzInfoAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return gzInfoItems.size();
		}

		@Override
		public Object getItem(int position) {
			return gzInfoItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			GzInfo item = gzInfoItems.get(position);
			Holder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(getActivity()).inflate(
						R.layout.item_gzinfo, null);
				((ViewGroup) convertView)
						.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
				holder = new Holder();
				holder.lblGzId = (TextView) convertView
						.findViewById(R.id.lblGzId);
				holder.lblBianHao = (TextView) convertView
						.findViewById(R.id.lblBianHao);
				holder.lblXl = (TextView) convertView.findViewById(R.id.lblXl);
				holder.lblZd = (TextView) convertView.findViewById(R.id.lblZd);
				holder.lblGzZhuanYeName = (TextView) convertView
						.findViewById(R.id.lblGzZhuanYeName);
				holder.lblGzContent = (TextView) convertView
						.findViewById(R.id.lblGzContent);
				holder.lblGzTime = (TextView) convertView
						.findViewById(R.id.lblGzTime);
				holder.lblGzFlag = (TextView) convertView
						.findViewById(R.id.lblGzFlag);
				holder.lblYcTime = (TextView) convertView
						.findViewById(R.id.lblYcTime);
				holder.lblXyTime = (TextView) convertView
						.findViewById(R.id.lblXyTime);
				holder.llButton = (LinearLayout) convertView
						.findViewById(R.id.llButton);
				holder.btnUpdate = (Button) convertView
						.findViewById(R.id.btnUpdate);
				holder.btnDelete = (Button) convertView
						.findViewById(R.id.btnDelete);
				holder.lblDj = (TextView) convertView.findViewById(R.id.lblDj);
				holder.llSecondButton = (LinearLayout) convertView.findViewById(R.id.llSecondButton);
				holder.btnSecondButton = (Button) convertView.findViewById(R.id.btnSecondButton);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}

			holder.lblGzId.setText(item.getGz_id());
			holder.lblBianHao.setText(item.getBianhao());
			holder.lblXl.setText(item.getLx());
			holder.lblZd.setText(item.getZd());
			holder.lblGzZhuanYeName.setText(item.getGz_zhuanye_name());
			holder.lblGzContent.setText(item.getGz_content());
			holder.lblGzTime.setText(item.getGz_time());
			holder.lblGzFlag.setText(Globals.getInstance().getGzStateMap()
					.get(item.getGz_flag()));
			int yctime = Integer.parseInt(item.getYc_time());
			holder.lblYcTime.setText(getChineseTime(yctime));
			int xytime = Integer.parseInt(item.getXy_time());
			holder.lblXyTime.setText(getChineseTime(xytime));
			
			if(type.equals("1")){
				if (item.getGz_flag().equals("0")) {
					holder.llButton.setVisibility(View.VISIBLE);
					holder.btnDelete
							.setOnClickListener(new ItemDeleteOnClickListener(item));
					holder.btnUpdate
							.setOnClickListener(new ItemUpdateOnClickListener(item));
				} else {
					holder.llButton.setVisibility(View.GONE);
				}
			}else {
				holder.llButton.setVisibility(View.GONE);
			}
			
			if(type.equals("11")){
				holder.llSecondButton.setVisibility(View.VISIBLE);
				holder.btnSecondButton.setText("接修");
				holder.btnSecondButton.setOnClickListener(new ItemTakeRepairOnClickListener(item));
			}else if(type.equals("12")){
				holder.llSecondButton.setVisibility(View.VISIBLE);
				holder.btnSecondButton.setText("操作");
				holder.btnSecondButton.setOnClickListener(new ItemExecuteRepairOnClickListener(item));
			}else if(type.equals("2")){
				holder.llSecondButton.setVisibility(View.VISIBLE);
				holder.btnSecondButton.setText("操作");
				holder.btnSecondButton.setOnClickListener(new ItemConfirmRepairOnClickListener(item));
			}
			
			else {
				holder.llSecondButton.setVisibility(View.GONE);
			}
			
			
			holder.lblDj.setText(Globals.getInstance().getGzDjMap().get(item.getDj()));
			return convertView;
		}
		
		private String getChineseTime(int time){
			int day = time/(60*24);
			String dayStr="";
			if(day>0){
				dayStr=day+"天";
				time = time%(60*24);
			}	
			int hour = time / 60;
			int minute = time % 60;
			String hourStr = hour > 0 ? hour + "小时" : "";
			return dayStr+hourStr+minute+"分钟";
		}

		class Holder {
			TextView lblGzId, lblBianHao, lblXl, lblZd, lblGzZhuanYeName,
					lblGzContent, lblGzTime, lblGzFlag, lblYcTime, lblXyTime,lblDj;
			LinearLayout llButton,llSecondButton;
			Button btnUpdate, btnDelete,btnSecondButton;
		}

		class ItemUpdateOnClickListener implements OnClickListener {
			private GzInfo info;

			public ItemUpdateOnClickListener(GzInfo info) {
				super();
				this.info = info;
			}

			@Override
			public void onClick(View v) {
				FillFaultFragment fragment = new FillFaultFragment();
				fragment.setGzInfo(info);
				((StationErrorActivity) getActivity())
						.changeRightMenu(fragment);
				((StationErrorActivity) getActivity()).openRightMenu();

			}

		}

		class ItemDeleteOnClickListener implements OnClickListener {
			private GzInfo info;

			public ItemDeleteOnClickListener(GzInfo info) {
				super();
				this.info = info;
			}

			@Override
			public void onClick(View v) {
				Builder builder = new Builder(getActivity());
				builder.setTitle("提示");
				builder.setMessage("是否要删除此故障信息？");
				builder.setNegativeButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								deleteGz(info.getGz_id());
							}
						});
				builder.setPositiveButton("取消", null);
				builder.create().show();
			}

		}
		
		class ItemTakeRepairOnClickListener implements OnClickListener {
			private GzInfo info;

			public ItemTakeRepairOnClickListener(GzInfo info) {
				super();
				this.info = info;
			}

			@Override
			public void onClick(View v) {
				TakeRepairDialog dialog = new TakeRepairDialog(getActivity());
				dialog.setGzInfo(info);
				dialog.setFragment(RepairListFragment.this);
				dialog.show();
			}

		}
		
		class ItemExecuteRepairOnClickListener implements OnClickListener {
			private GzInfo info;

			public ItemExecuteRepairOnClickListener(GzInfo info) {
				super();
				this.info = info;
			}

			@Override
			public void onClick(View v) {
				ExecuteRepairDialog dialog = new ExecuteRepairDialog(getActivity());
				dialog.setGzInfo(info);
				dialog.setFragment(RepairListFragment.this);
				dialog.show();
			}

		}
		
		class ItemConfirmRepairOnClickListener implements OnClickListener {
			private GzInfo info;

			public ItemConfirmRepairOnClickListener(GzInfo info) {
				super();
				this.info = info;
			}

			@Override
			public void onClick(View v) {
				ConfirmRepairDialog dialog = new ConfirmRepairDialog(getActivity());
				dialog.setGzInfo(info);
				dialog.setFragment(RepairListFragment.this);
				dialog.show();
			}

		}

	}

	@Override
	public void changeFragment(int flag, Object params) {
		switch (flag) {
		case TaskConstant.REFRESH_REPAIRLISTFRAGMENT:
			getFirstData();
			break;
		}

	}
}
