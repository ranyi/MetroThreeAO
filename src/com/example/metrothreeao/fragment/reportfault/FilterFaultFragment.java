package com.example.metrothreeao.fragment.reportfault;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import com.example.metrothreeao.R;
import com.example.metrothreeao.activity.StationErrorActivity;
import com.example.metrothreeao.constant.HTTPConstant;
import com.example.metrothreeao.constant.TaskConstant;
import com.example.metrothreeao.entity.main.Bxdw;
import com.example.metrothreeao.entity.main.Device;
import com.example.metrothreeao.entity.main.Dj;
import com.example.metrothreeao.entity.main.Line;
import com.example.metrothreeao.entity.main.SpinnerItem;
import com.example.metrothreeao.entity.main.Station;
import com.example.metrothreeao.entity.repairfault.FaultFilterParams;
import com.example.metrothreeao.face.IChangeFragment;
import com.example.metrothreeao.globals.Globals;
import com.example.metrothreeao.server.webservice.WSAsyncTask;
import com.example.metrothreeao.server.webservice.WSRequestParams;
import com.example.metrothreeao.server.webservice.WSResponseInterface;
import com.example.metrothreeao.server.webservice.WSSoapParams;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class FilterFaultFragment extends Fragment implements
		WSResponseInterface {
	private String TAG = "FilterFaultFragment";
	private Button btnClose, btnFilter;
	private EditText txtKsTime, txtJsTime, txtTime, txtKeyWord;
	private Spinner spXl, spZd, spBxdw, spJxdw, spCxType, spTimeType, spDj,
			spFlag, spSb1, spSb2, spSb3, spLh;
	private FaultFilterParams filterParams;

	private List<SpinnerItem> xlItems = new ArrayList<SpinnerItem>();
	private List<SpinnerItem> zdItems = new ArrayList<SpinnerItem>();
	private List<SpinnerItem> bxdwItems = new ArrayList<SpinnerItem>();
	private List<SpinnerItem> jxdwItems = new ArrayList<SpinnerItem>();
	private List<SpinnerItem> cxTypeItems = new ArrayList<SpinnerItem>();
	private List<SpinnerItem> timeTypeItems = new ArrayList<SpinnerItem>();
	private List<SpinnerItem> djItems = new ArrayList<SpinnerItem>();
	private List<SpinnerItem> flagItems = new ArrayList<SpinnerItem>();
	private List<SpinnerItem> sb1Items = new ArrayList<SpinnerItem>();
	private List<SpinnerItem> sb2Items = new ArrayList<SpinnerItem>();
	private List<SpinnerItem> sb3Items = new ArrayList<SpinnerItem>();
	private List<SpinnerItem> lhItems = new ArrayList<SpinnerItem>();

	private SimpleDateFormat format1 = new SimpleDateFormat("yyyy-M-d");
	private SimpleDateFormat format2 = new SimpleDateFormat("H:m");
	private SimpleDateFormat format3 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private static final String FLAGGETSTYLE2="getstyle2";
	private static final String FLAGGETSTYLE3 = "getstyle3";
	
	private IChangeFragment fragment;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_filter_fault, container,
				false);
		initView(view);
		initListener();
		init();
		return view;
	}

	public void setFilterParams(FaultFilterParams filterParams) {
		this.filterParams = filterParams;
	}
	
	public void setFragment(IChangeFragment fragment){
		this.fragment = fragment;
	}

	private void initView(View view) {
		btnClose = (Button) view.findViewById(R.id.btnClose);
		btnFilter = (Button) view.findViewById(R.id.btnFilter);
		txtKsTime = (EditText) view.findViewById(R.id.txtKsTime);
		txtJsTime = (EditText) view.findViewById(R.id.txtJsTime);
		txtTime = (EditText) view.findViewById(R.id.txtTime);
		txtKeyWord = (EditText) view.findViewById(R.id.txtKeyWord);
		spXl = (Spinner) view.findViewById(R.id.spXl);
		spZd = (Spinner) view.findViewById(R.id.spZd);
		spBxdw = (Spinner) view.findViewById(R.id.spBxdw);
		spJxdw = (Spinner) view.findViewById(R.id.spJxdw);
		spCxType = (Spinner) view.findViewById(R.id.spCxType);
		spTimeType = (Spinner) view.findViewById(R.id.spTimeType);
		spDj = (Spinner) view.findViewById(R.id.spDj);
		spFlag = (Spinner) view.findViewById(R.id.spFlag);
		spSb1 = (Spinner) view.findViewById(R.id.spSb1);
		spSb2 = (Spinner) view.findViewById(R.id.spSb2);
		spSb3 = (Spinner) view.findViewById(R.id.spSb3);
		spLh = (Spinner) view.findViewById(R.id.spLh);
	}

	private void initListener() {
		btnClose.setOnClickListener(btnCloseOnClickListener);
		txtKsTime.setOnClickListener(txtKsTimeOnClickListener);
		txtJsTime.setOnClickListener(txtJsTimeOnClickListener);
		spXl.setOnItemSelectedListener(spXlOnItemSelectedListener);
		spSb1.setOnItemSelectedListener(spSb1OnItemSelectedListener);
		spSb2.setOnItemSelectedListener(spSb2onItemSelectedListener);
		btnFilter.setOnClickListener(btnFilterOnClickListener);
	}

	private void init() {
		initXl();
		initZd();
		initSb1();
		initSb2();
		initSb3();
		initBxdw();
		initJxdw();
		initCxType();
		initTimeType();
		initDj();
		initFlag();
		initLh();
		getXL();
		getBxdw();
		getDj();
		getSB();
	}

	private void initXl() {
		xlItems.clear();
		xlItems.add(new SpinnerItem("", "请选择"));
		spXl.setAdapter(new ArrayAdapter<SpinnerItem>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, xlItems));
	}

	private void initZd() {
		zdItems.clear();
		zdItems.add(new SpinnerItem("", "请选择"));
		spZd.setAdapter(new ArrayAdapter<SpinnerItem>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, zdItems));
	}


	private void initBxdw() {
		bxdwItems.clear();
		bxdwItems.add(new SpinnerItem("", "请选择"));
		spBxdw.setAdapter(new ArrayAdapter<SpinnerItem>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, bxdwItems));
	}
	
	private void initJxdw() {
		jxdwItems.clear();
		jxdwItems.add(new SpinnerItem("", "请选择"));
		spJxdw.setAdapter(new ArrayAdapter<SpinnerItem>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, jxdwItems));
	}
	
	private void initCxType(){
		cxTypeItems.clear();
		cxTypeItems.add(new SpinnerItem("0", "处理时间大于"));
		cxTypeItems.add(new SpinnerItem("1", "响应时间大于"));
		spCxType.setAdapter(new ArrayAdapter<SpinnerItem>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, cxTypeItems));
	}
	
	private void initTimeType(){
		timeTypeItems.clear();
		timeTypeItems.add(new SpinnerItem("天", "天"));
		timeTypeItems.add(new SpinnerItem("小时", "小时"));
		timeTypeItems.add(new SpinnerItem("分钟", "分钟"));
		spTimeType.setAdapter(new ArrayAdapter<SpinnerItem>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, timeTypeItems));
	}
	
	private void initDj(){
		djItems.clear();
		djItems.add(new SpinnerItem("","请选择"));
		spDj.setAdapter(new ArrayAdapter<SpinnerItem>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, djItems));
	}
	
	private void initFlag(){
		flagItems.clear();
		flagItems.add(new SpinnerItem("", "请选择"));
		flagItems.add(new SpinnerItem("0", "新报修"));
		flagItems.add(new SpinnerItem("1", "在调度"));
		flagItems.add(new SpinnerItem("2", "未接修"));
		flagItems.add(new SpinnerItem("3", "在接修"));
		flagItems.add(new SpinnerItem("4", "在处理"));
		flagItems.add(new SpinnerItem("5", "拒绝接修"));
		flagItems.add(new SpinnerItem("8", "待确认"));
		flagItems.add(new SpinnerItem("9", "在确认"));
		flagItems.add(new SpinnerItem("10", "闭环"));
		flagItems.add(new SpinnerItem("11", "拒绝闭环"));
		flagItems.add(new SpinnerItem("12", "关闭"));
		flagItems.add(new SpinnerItem("13", "直接闭环"));
		flagItems.add(new SpinnerItem("14", "待协调故障"));
		flagItems.add(new SpinnerItem("15", "已删除"));
		spFlag.setAdapter(new ArrayAdapter<SpinnerItem>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, flagItems));
	}
	

	private void initSb1() {
		sb1Items.clear();
		sb1Items.add(new SpinnerItem("", "请选择"));
		spSb1.setAdapter(new ArrayAdapter<SpinnerItem>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, sb1Items));
	}
	private void initSb2() {
		sb2Items.clear();
		sb2Items.add(new SpinnerItem("", "请选择"));
		spSb2.setAdapter(new ArrayAdapter<SpinnerItem>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, sb2Items));
	}
	private void initSb3() {
		sb3Items.clear();
		sb3Items.add(new SpinnerItem("", "请选择"));
		spSb3.setAdapter(new ArrayAdapter<SpinnerItem>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, sb3Items));
	}
	
	private void initLh(){
		lhItems.clear();
		lhItems.add(new SpinnerItem("", "所有"));
		lhItems.add(new SpinnerItem("1", "有令号"));
		lhItems.add(new SpinnerItem("2", "无令号"));
		spLh.setAdapter(new ArrayAdapter<SpinnerItem>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, lhItems));
	}
	
	

	private void fretchXl(List<Line> lines) {
		xlItems.clear();
		xlItems.add(new SpinnerItem("", "请选择"));
		for (Line line : lines) {
			xlItems.add(new SpinnerItem(line.getXlid(), line.getXlName()));
		}
		spXl.setAdapter(new ArrayAdapter<SpinnerItem>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, xlItems));
	}

	private void fretchStation(List<Station> stations) {
		zdItems.clear();
		zdItems.add(new SpinnerItem("", "请选择"));
		for (Station station : stations) {
			zdItems.add(new SpinnerItem(station.getStationid(), station
					.getStationName()));
		}
		spZd.setAdapter(new ArrayAdapter<SpinnerItem>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, zdItems));

	}

	private void fretchBxdw(List<Bxdw> bxdws) {
		bxdwItems.clear();
		bxdwItems.add(new SpinnerItem("", "请选择"));
		for (Bxdw bxdw : bxdws) {
			bxdwItems.add(new SpinnerItem(bxdw.getCo_id(), bxdw.getCompany()));
		}
		spBxdw.setAdapter(new ArrayAdapter<SpinnerItem>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, bxdwItems));
	}
	
	private void fretchJxdw(List<Bxdw> bxdws) {
		jxdwItems.clear();
		jxdwItems.add(new SpinnerItem("", "请选择"));
		for (Bxdw bxdw : bxdws) {
			jxdwItems.add(new SpinnerItem(bxdw.getCo_id(), bxdw.getCompany()));
		}
		spJxdw.setAdapter(new ArrayAdapter<SpinnerItem>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, jxdwItems));
	}
	
	private void fretchDj(List<Dj> djs){
		djItems.clear();
		djItems.add(new SpinnerItem("", "请选择"));
		for(Dj dj:djs){
			djItems.add(new SpinnerItem(dj.getDJ_ID(), dj.getDJ_Name()));
		}
		spDj.setAdapter(new ArrayAdapter<SpinnerItem>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, djItems));
	}
	
	private void fretchSB(List<Device> devices){
		sb1Items.clear();
		sb1Items.add(new SpinnerItem("", "请选择"));
		for(Device device:devices){
			sb1Items.add(new SpinnerItem(device.getDevicesId(), device.getDevicesName()));
		}
		spSb1.setAdapter(new ArrayAdapter<SpinnerItem>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, sb1Items));
	}
	
	private void fretchSubSB(List<Device> devices,String flag){
		if(flag.equals(FilterFaultFragment.FLAGGETSTYLE2)){
			sb2Items.clear();
			sb2Items.add(new SpinnerItem("", "请选择"));
			for(Device device:devices){
				sb2Items.add(new SpinnerItem(device.getDevicesId(), device.getDevicesName()));
			}
			spSb2.setAdapter(new ArrayAdapter<SpinnerItem>(getActivity(),
					android.R.layout.simple_spinner_dropdown_item, sb2Items));
			spSb2.setVisibility(View.VISIBLE);
		}else if(flag.equals(FilterFaultFragment.FLAGGETSTYLE3)){
			sb3Items.clear();
			sb3Items.add(new SpinnerItem("", "请选择"));
			for(Device device:devices){
				sb3Items.add(new SpinnerItem(device.getDevicesId(), device.getDevicesName()));
			}
			spSb3.setAdapter(new ArrayAdapter<SpinnerItem>(getActivity(),
					android.R.layout.simple_spinner_dropdown_item, sb3Items));
			spSb3.setVisibility(View.VISIBLE);
		}
	}

	private OnClickListener btnCloseOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			((StationErrorActivity) getActivity()).closeRightMenu();
		}
	};

	private OnClickListener txtKsTimeOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			String timeStr = txtKsTime.getText().toString().trim();
			if (timeStr.length() == 0) {
				timeStr = format1.format(new Date());
			}
			String[] dates = timeStr.split("-");
			new DatePickerDialog(getActivity(), new OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					txtKsTime.setText(year + "-" + (monthOfYear + 1) + "-"
							+ dayOfMonth);
				}
			}, Integer.parseInt(dates[0]), Integer.parseInt(dates[1]) - 1,
					Integer.parseInt(dates[2])).show();

		}
	};

	private OnClickListener txtJsTimeOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			String timeStr = txtJsTime.getText().toString().trim();
			if (timeStr.length() == 0) {
				timeStr = format1.format(new Date());
			}
			String[] dates = timeStr.split("-");
			new DatePickerDialog(getActivity(), new OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					txtJsTime.setText(year + "-" + (monthOfYear + 1) + "-"
							+ dayOfMonth);
				}
			}, Integer.parseInt(dates[0]), Integer.parseInt(dates[1]) - 1,
					Integer.parseInt(dates[2])).show();

		}
	};

	private OnItemSelectedListener spXlOnItemSelectedListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			String xlId = ((SpinnerItem) spXl.getSelectedItem()).getId().trim();
			if (xlId.length() == 0) {
				initZd();
			} else {
				getStationByLineId(xlId);
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {

		}
	};
	
	private OnItemSelectedListener spSb1OnItemSelectedListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			if(position==0){
				initSb2();
				initSb3();
				spSb2.setVisibility(View.GONE);
				spSb3.setVisibility(View.GONE);
			}else {
				String code = ((SpinnerItem)spSb1.getAdapter().getItem(position)).getId();
				getSubSB(code, FilterFaultFragment.FLAGGETSTYLE2);
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			
		}
	};
	
	private OnItemSelectedListener spSb2onItemSelectedListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			if(position==0){
				initSb3();
				spSb3.setVisibility(View.GONE);
			}else {
				String code = ((SpinnerItem)spSb2.getAdapter().getItem(position)).getId();
				getSubSB(code, FilterFaultFragment.FLAGGETSTYLE3);
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			
		}
	};
	
	private OnClickListener btnFilterOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			filterParams.setKsTime(txtKsTime.getText().toString().trim());
			filterParams.setJsTime(txtJsTime.getText().toString().trim());
			filterParams.setXl(((SpinnerItem)spXl.getSelectedItem()).getId().trim());
			filterParams.setZd(((SpinnerItem)spZd.getSelectedItem()).getId().trim());
			filterParams.setBxdw(((SpinnerItem)spBxdw.getSelectedItem()).getId().trim());
			filterParams.setJxdw(((SpinnerItem)spJxdw.getSelectedItem()).getId().trim());
			filterParams.setDj(((SpinnerItem)spDj.getSelectedItem()).getId().trim());
			filterParams.setFlag(((SpinnerItem)spFlag.getSelectedItem()).getId().trim());
			filterParams.setLh(((SpinnerItem)spLh.getSelectedItem()).getId().trim());
			filterParams.setCxType(((SpinnerItem)spCxType.getSelectedItem()).getId().trim());
			filterParams.setTimeType(((SpinnerItem)spTimeType.getSelectedItem()).getId().trim());
			filterParams.setTime(txtTime.getText().toString().trim());
			filterParams.setKeyWord(txtKeyWord.getText().toString().trim());
			String sb = "";
			sb = ((SpinnerItem)spSb3.getSelectedItem()).getId().trim();
			if(sb.length()==0){
				sb=((SpinnerItem)spSb2.getSelectedItem()).getId().trim();
			}
			if(sb.length()==0){
				sb = ((SpinnerItem)spSb1.getSelectedItem()).getId().trim();
			}
			filterParams.setSb(sb);
			fragment.changeFragment(TaskConstant.REFRESH_REPAIRLISTFRAGMENT,null);
			((StationErrorActivity) getActivity()).closeRightMenu();
		}
	};

	/**
	 * 获取线路
	 */
	private void getXL() {
		ArrayList<WSSoapParams> soapParams = new ArrayList<WSSoapParams>();
		new WSAsyncTask(FilterFaultFragment.this).execute(new WSRequestParams(
				soapParams, HTTPConstant.GET_XL, HTTPConstant.LOGIN_URL, null));
	}

	/**
	 * 根据线路id获取站点
	 * 
	 * @param xlId
	 */
	private void getStationByLineId(String xlId) {
		ArrayList<WSSoapParams> soapParams = new ArrayList<WSSoapParams>();
		soapParams.add(new WSSoapParams("xlid", xlId));
		new WSAsyncTask(FilterFaultFragment.this).execute(new WSRequestParams(
				soapParams, HTTPConstant.GET_STATIONS, HTTPConstant.LOGIN_URL,
				null));
	}

	/**
	 * 获取报修单位
	 */
	private void getBxdw() {
		ArrayList<WSSoapParams> soapParams = new ArrayList<WSSoapParams>();
		new WSAsyncTask(FilterFaultFragment.this)
				.execute(new WSRequestParams(soapParams, HTTPConstant.GET_BXDW,
						HTTPConstant.LOGIN_URL, null));
	}
	/**
	 * 获取故障等级
	 */
	private void getDj(){
		ArrayList<WSSoapParams> soapParams = new ArrayList<WSSoapParams>();
		new WSAsyncTask(FilterFaultFragment.this)
				.execute(new WSRequestParams(soapParams, HTTPConstant.GET_DJ,
						HTTPConstant.LOGIN_URL, null));
	}
	
	/**
	 * 获取设备信息
	 */
	private void getSB() {
		ArrayList<WSSoapParams> soapParams = new ArrayList<WSSoapParams>();
		new WSAsyncTask(FilterFaultFragment.this).execute(new WSRequestParams(
				soapParams, HTTPConstant.GET_SB, HTTPConstant.LOGIN_URL, null));
	}
	
	private void getSubSB(String code,String flag){
		ArrayList<WSSoapParams> soapParams = new ArrayList<WSSoapParams>();
		soapParams.add(new WSSoapParams("Code", code));
		soapParams.add(new WSSoapParams("Flag", flag));
		new WSAsyncTask(FilterFaultFragment.this).execute(new WSRequestParams(
				soapParams, HTTPConstant.GET_SB_BY_SBID, HTTPConstant.LOGIN_URL, null));
	}

	@Override
	public void callBackResponse(String result, String responseMethod) {
		Gson gson = new Gson();
		//获取线路
		if (responseMethod.equals(HTTPConstant.GET_XL)) {
			if (HTTPConstant.ERROR.equals(result)) {
				Toast.makeText(getActivity(), "请求服务器数据失败,请检查网络是否正常",
						Toast.LENGTH_LONG).show();
				return;
			}
			try {
				JSONObject jsonObject = new JSONObject(result);
				if ("1".equals(jsonObject.getString("isSuccess"))) {
					List<Line> lines = gson.fromJson(
							jsonObject.getJSONObject("Msg").getString("lines"),
							new TypeToken<List<Line>>() {
							}.getType());
					fretchXl(lines);
				} else {
					Toast.makeText(getActivity(), jsonObject.getString("Msg"),
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//获取站点
		else if (responseMethod.equals(HTTPConstant.GET_STATIONS)) {
			if (HTTPConstant.ERROR.equals(result)) {
				Toast.makeText(getActivity(), "请求服务器数据失败,请检查网络是否正常",
						Toast.LENGTH_LONG).show();
				return;
			}

			try {
				JSONObject jsonObject = new JSONObject(result);
				if ("1".equals(jsonObject.getString("isSuccess"))) {
					List<Station> stations = gson.fromJson(jsonObject
							.getJSONObject("Msg").getString("station"),
							new TypeToken<List<Station>>() {
							}.getType());
					fretchStation(stations);
				} else {
					Toast.makeText(getActivity(), jsonObject.getString("Msg"),
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//获取报修单位
		else if (responseMethod.equals(HTTPConstant.GET_BXDW)) {
			if (HTTPConstant.ERROR.equals(result)) {
				Toast.makeText(getActivity(), "请求服务器数据失败,请检查网络是否正常",
						Toast.LENGTH_LONG).show();
				return;
			}

			try {
				JSONObject jsonObject = new JSONObject(result);
				if ("1".equals(jsonObject.getString("isSuccess"))) {
					List<Bxdw> bxdws = gson.fromJson(jsonObject
							.getJSONObject("Msg").getString("Bxdw"),
							new TypeToken<List<Bxdw>>() {
							}.getType());
					fretchBxdw(bxdws);
					fretchJxdw(bxdws);
				} else {
					Toast.makeText(getActivity(), jsonObject.getString("Msg"),
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//获取故障等级
		else if (responseMethod.equals(HTTPConstant.GET_DJ)) {
			if (HTTPConstant.ERROR.equals(result)) {
				Toast.makeText(getActivity(), "请求服务器数据失败,请检查网络是否正常",
						Toast.LENGTH_LONG).show();
				return;
			}

			Log.i(TAG, responseMethod + "===>" + result);
			try {
				JSONObject jsonObject = new JSONObject(result);
				if ("1".equals(jsonObject.getString("isSuccess"))) {
					List<Dj> djs = gson.fromJson(jsonObject
							.getJSONObject("Msg").getString("Dj"),
							new TypeToken<List<Dj>>() {
							}.getType());
					fretchDj(djs);
				} else {
					Toast.makeText(getActivity(), jsonObject.getString("Msg"),
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//获取sb1信息
		else if (responseMethod.equals(HTTPConstant.GET_SB)) {
			if (HTTPConstant.ERROR.equals(result)) {
				Toast.makeText(getActivity(), "请求服务器数据失败,请检查网络是否正常",
						Toast.LENGTH_LONG).show();
				return;
			}
			try {
				JSONObject jsonObject = new JSONObject(result);
				if ("1".equals(jsonObject.getString("isSuccess"))) {
					List<Device> devices = gson.fromJson(jsonObject
							.getJSONObject("Msg").getString("Devices"),
							new TypeToken<List<Device>>() {
							}.getType());
					fretchSB(devices);
				} else {
					Toast.makeText(getActivity(), jsonObject.getString("Msg"),
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//获取二级和三级设备数据
		else if (responseMethod.equals(HTTPConstant.GET_SB_BY_SBID)) {
			if (HTTPConstant.ERROR.equals(result)) {
				Toast.makeText(getActivity(), "请求服务器数据失败,请检查网络是否正常",
						Toast.LENGTH_LONG).show();
				return;
			}
			try {
				JSONObject jsonObject = new JSONObject(result);
				if ("1".equals(jsonObject.getString("isSuccess"))) {
					String flag = jsonObject
							.getJSONObject("Msg").getString("Flag");
					List<Device> devices = gson.fromJson(jsonObject
							.getJSONObject("Msg").getString("Devices"),
							new TypeToken<List<Device>>() {
							}.getType());
					fretchSubSB(devices, flag);
				} else {
					Toast.makeText(getActivity(), jsonObject.getString("Msg"),
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
