package com.example.metrothreeao.fragment.reportfault;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import com.example.metrothreeao.R;
import com.example.metrothreeao.activity.LoginActivity;
import com.example.metrothreeao.activity.MainMenuActivity;
import com.example.metrothreeao.activity.StationErrorActivity;
import com.example.metrothreeao.constant.HTTPConstant;
import com.example.metrothreeao.constant.TaskConstant;
import com.example.metrothreeao.entity.login.User;
import com.example.metrothreeao.entity.main.Device;
import com.example.metrothreeao.entity.main.Line;
import com.example.metrothreeao.entity.main.SpinnerItem;
import com.example.metrothreeao.entity.main.Station;
import com.example.metrothreeao.entity.repairfault.GzInfo;
import com.example.metrothreeao.globals.Globals;
import com.example.metrothreeao.server.webservice.WSAsyncTask;
import com.example.metrothreeao.server.webservice.WSRequestParams;
import com.example.metrothreeao.server.webservice.WSResponseInterface;
import com.example.metrothreeao.server.webservice.WSSoapParams;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html.ImageGetter;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class FillFaultFragment extends Fragment implements WSResponseInterface {
	private String TAG = "FillFaultFragment";
	private static final String FLAGGETSTYLE2="getstyle2";
	private static final String FLAGGETSTYLE3 = "getstyle3";
	private EditText txtBeginDate, txtBeginTime, txtRepairPeople,
			txtRepairPhone, txtFaultDesplay, txtFillDate, txtFillPeople,txtBrand,txtModel;
	private Spinner spLine, spStation, spStyle1, spStyle2, spStyle3;
	private Button btnSubmit,btnClose;
	private LinearLayout llBrand;

	private SimpleDateFormat format1 = new SimpleDateFormat("yyyy-M-d");
	private SimpleDateFormat format2 = new SimpleDateFormat("H:m");
	private SimpleDateFormat format3 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat format4 = new SimpleDateFormat("yyyy/M/d H:m:s");

	private List<SpinnerItem> lineItems = new ArrayList<SpinnerItem>();
	private List<SpinnerItem> stationItems = new ArrayList<SpinnerItem>();
	private List<SpinnerItem> style1Items = new ArrayList<SpinnerItem>();
	private List<SpinnerItem> style2Items = new ArrayList<SpinnerItem>();
	private List<SpinnerItem> style3Items = new ArrayList<SpinnerItem>();

	//故障id
//	private String gzId="";
	//故障信息
	private GzInfo gzInfo;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_fillfault, container,
				false);
		initView(view);
		initListener();
		init();
		return view;
	}
	
	public void setGzInfo(GzInfo gzInfo){
		this.gzInfo = gzInfo;
	}

	private void initView(View view) {
		txtBeginDate = (EditText) view.findViewById(R.id.txtBeginDate);
		txtBeginTime = (EditText) view.findViewById(R.id.txtBeginTime);
		spLine = (Spinner) view.findViewById(R.id.spLine);
		spStation = (Spinner) view.findViewById(R.id.spStation);
		spStyle1 = (Spinner) view.findViewById(R.id.spStyle1);
		spStyle2 = (Spinner) view.findViewById(R.id.spStyle2);
		spStyle3 = (Spinner) view.findViewById(R.id.spStyle3);
		txtRepairPeople = (EditText) view.findViewById(R.id.txtRepairPeople);
		txtRepairPhone = (EditText) view.findViewById(R.id.txtRepairPhone);
		txtFaultDesplay = (EditText) view.findViewById(R.id.txtFaultDesplay);
		txtFillDate = (EditText) view.findViewById(R.id.txtFillDate);
		txtFillPeople = (EditText) view.findViewById(R.id.txtFillPeople);
		btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
		txtBrand = (EditText) view.findViewById(R.id.txtBrand);
		txtModel = (EditText) view.findViewById(R.id.txtModel);
		llBrand = (LinearLayout) view.findViewById(R.id.llBrand);
		btnClose = (Button) view.findViewById(R.id.btnClose);
	}

	private void initListener() {
		txtBeginDate.setOnClickListener(txtBeginDateOnClickListener);
		txtBeginTime.setOnClickListener(txtBeginTimeOnClickListener);
		btnSubmit.setOnClickListener(btnSubmitOnClickListener);
		spStyle1.setOnItemSelectedListener(spStyle1OnItemSelectedListener);
		spStyle2.setOnItemSelectedListener(spStyle2onItemSelectedListener);
		btnClose.setOnClickListener(btnCloseOnClickListener);
	}

	private void init() {
		initLine();
		initStation();
		initStyle1();
		Date today = new Date();
		txtBeginDate.setText(format1.format(today));
		txtBeginTime.setText(format2.format(today));
		txtFillDate.setText(format3.format(today));
		txtFillPeople.setText(Globals.getInstance().getUser().getTrueName());
		String xlName = Globals.getInstance().getUser().getXlName();
		String stationName = Globals.getInstance().getUser().getStationName();
		if(xlName!=null && xlName.trim().length()>0 &&stationName!=null && stationName.trim().length()>0){
			txtFaultDesplay.setText(xlName+"："+stationName);
		}
		
		getXL();
		String xlId = Globals.getInstance().getUser().getXlid();
		if (xlId != null && xlId.trim().length() > 0) {
			getStationByLineId(xlId);
		}
		getSB();
		
		if(gzInfo==null){
			btnClose.setVisibility(View.GONE);
		}
		
		try {
			if(gzInfo!=null){
				Date gzTime = format4.parse(gzInfo.getGz_time());
				txtBeginDate.setText(format1.format(gzTime));
				txtBeginTime.setText(format2.format(gzTime));
				txtRepairPeople.setText(gzInfo.getBxr());
				txtRepairPhone.setText(gzInfo.getBxTel());
				txtBrand.setText(gzInfo.getPh());
				txtModel.setText(gzInfo.getXh());
				txtFaultDesplay.setText(gzInfo.getGz_content());
				txtFillDate.setText(gzInfo.getTbTime());
				txtFillPeople.setText(gzInfo.getTbr());
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void initLine(){
		lineItems.clear();
		lineItems.add(new SpinnerItem("-1", "请选择"));
		spLine.setAdapter(new ArrayAdapter<SpinnerItem>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, lineItems));
	}
	
	private void initStation(){
		stationItems.clear();
		stationItems.add(new SpinnerItem("-1", "请选择"));
		spStation.setAdapter(new ArrayAdapter<SpinnerItem>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, stationItems));
	}

	private void fretchLine(List<Line> lines) {
		lineItems.clear();
		lineItems.add(new SpinnerItem("-1", "请选择"));
		for (Line line : lines) {
			lineItems.add(new SpinnerItem(line.getXlid(), line.getXlName()));
		}
		spLine.setAdapter(new ArrayAdapter<SpinnerItem>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, lineItems));
		String xlId = Globals.getInstance().getUser().getXlid();
		if (xlId != null && xlId.trim().length() > 0) {
			setSpinnerSelect(spLine, xlId);
			spLine.setEnabled(false);
		}

	}

	private void fretchStation(List<Station> stations) {
		stationItems.clear();
		stationItems.add(new SpinnerItem("-1", "请选择"));
		for (Station station : stations) {
			stationItems.add(new SpinnerItem(station.getStationid(), station
					.getStationName()));
		}
		spStation.setAdapter(new ArrayAdapter<SpinnerItem>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, stationItems));
		String stationId = Globals.getInstance().getUser().getStationid();
		if (stationId != null && stationId.trim().length() > 0) {
			setSpinnerSelect(spStation, stationId);
			spStation.setEnabled(false);
		}
	}
	private void initStyle1(){
		style1Items.clear();
		style1Items.add(new SpinnerItem("-1", "请选择"));
		spStyle1.setAdapter(new ArrayAdapter<SpinnerItem>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, style1Items));
	}
	private void fretchSB(List<Device> devices){
		style1Items.clear();
		style1Items.add(new SpinnerItem("-1", "请选择"));
		for(Device device:devices){
			style1Items.add(new SpinnerItem(device.getDevicesId(), device.getDevicesName()));
		}
		spStyle1.setAdapter(new ArrayAdapter<SpinnerItem>(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, style1Items));
		if(gzInfo!=null){
			String zyid = gzInfo.getGz_zhuanye().substring(0, 2);
			setSpinnerSelect(spStyle1, zyid);
		}
	}
	
	private void fretchSubSB(List<Device> devices,String flag){
		if(flag.equals(FillFaultFragment.FLAGGETSTYLE2)){
			style2Items.clear();
			style2Items.add(new SpinnerItem("-1", "请选择"));
			for(Device device:devices){
				style2Items.add(new SpinnerItem(device.getDevicesId(), device.getDevicesName()));
			}
			spStyle2.setAdapter(new ArrayAdapter<SpinnerItem>(getActivity(),
					android.R.layout.simple_spinner_dropdown_item, style2Items));
			spStyle2.setVisibility(View.VISIBLE);
			if(gzInfo!=null){
				String zyid = gzInfo.getGz_zhuanye().substring(0, 4);
				setSpinnerSelect(spStyle2, zyid);
			}
		}else if(flag.equals(FillFaultFragment.FLAGGETSTYLE3)){
			style3Items.clear();
			style3Items.add(new SpinnerItem("-1", "请选择"));
			for(Device device:devices){
				style3Items.add(new SpinnerItem(device.getDevicesId(), device.getDevicesName()));
			}
			spStyle3.setAdapter(new ArrayAdapter<SpinnerItem>(getActivity(),
					android.R.layout.simple_spinner_dropdown_item, style3Items));
			spStyle3.setVisibility(View.VISIBLE);
			if(gzInfo!=null){
				String zyid = gzInfo.getGz_zhuanye();
				setSpinnerSelect(spStyle3, zyid);
			}
		}
	}
	
	

	public void setSpinnerSelect(Spinner spinner, String id) {
		for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
			if (((SpinnerItem) spinner.getAdapter().getItem(i)).getId().trim()
					.equals(id.trim())) {
				spinner.setSelection(i);
				break;
			}
		}
	}
	
	private boolean checkFrm(){
		if(spLine.getSelectedItemPosition()==0){
			Toast.makeText(getActivity(), "请选择线路！", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(spStation.getSelectedItemPosition()==0){
			Toast.makeText(getActivity(), "请选择站点！", Toast.LENGTH_SHORT).show();
			return false;
		}
		
		if(spStyle1.getSelectedItemPosition()==0 || spStyle2.getSelectedItemPosition()==0 || spStyle3.getSelectedItemPosition()==0){
			Toast.makeText(getActivity(), "请选择设备类别！", Toast.LENGTH_SHORT).show();
			return false;
		}
		
		if(txtRepairPeople.getText().toString().trim().length()==0){
			Toast.makeText(getActivity(), "请填写报修人！", Toast.LENGTH_SHORT).show();
			return false;
		}
		
		if(txtRepairPhone.getText().toString().trim().length()==0){
			Toast.makeText(getActivity(), "请填写报修电话！", Toast.LENGTH_SHORT).show();
			return false;
		}
		
		if(((SpinnerItem)spStyle1.getSelectedItem()).getId().trim().equals("25") && txtBrand.getText().toString().trim().length()==0){
			Toast.makeText(getActivity(), "请填写品牌！", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(((SpinnerItem)spStyle1.getSelectedItem()).getId().trim().equals("25") && txtModel.getText().toString().trim().length()==0){
			Toast.makeText(getActivity(), "请填写型号！", Toast.LENGTH_SHORT).show();
			return false;
		}
		
		if( txtFaultDesplay.getText().toString().trim().length()==0){
			Toast.makeText(getActivity(), "请填写故障现象！", Toast.LENGTH_SHORT).show();
			return false;
		}
				
		return true;
	}
	/**
	 * 获取线路
	 */
	private void getXL() {
		ArrayList<WSSoapParams> soapParams = new ArrayList<WSSoapParams>();
		new WSAsyncTask(FillFaultFragment.this).execute(new WSRequestParams(
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
		new WSAsyncTask(FillFaultFragment.this).execute(new WSRequestParams(
				soapParams, HTTPConstant.GET_STATIONS, HTTPConstant.LOGIN_URL,
				null));
	}

	private void getSB() {
		ArrayList<WSSoapParams> soapParams = new ArrayList<WSSoapParams>();
		new WSAsyncTask(FillFaultFragment.this).execute(new WSRequestParams(
				soapParams, HTTPConstant.GET_SB, HTTPConstant.LOGIN_URL, null));
	}
	
	private void getSubSB(String code,String flag){
		ArrayList<WSSoapParams> soapParams = new ArrayList<WSSoapParams>();
		soapParams.add(new WSSoapParams("Code", code));
		soapParams.add(new WSSoapParams("Flag", flag));
		new WSAsyncTask(FillFaultFragment.this).execute(new WSRequestParams(
				soapParams, HTTPConstant.GET_SB_BY_SBID, HTTPConstant.LOGIN_URL, null));
	}
	
	private void insertOrUpdateFault(){
		ArrayList<WSSoapParams> soapParams = new ArrayList<WSSoapParams>();
		soapParams.add(new WSSoapParams("Xl", ((SpinnerItem)spLine.getSelectedItem()).getId().trim()));
		soapParams.add(new WSSoapParams("Zd", ((SpinnerItem)spStation.getSelectedItem()).getId().trim()));
		soapParams.add(new WSSoapParams("Time", txtBeginDate.getText().toString().trim()+" "+txtBeginTime.getText().toString().trim()+":00"));
		soapParams.add(new WSSoapParams("ZhuanYe", ((SpinnerItem)spStyle3.getSelectedItem()).getId().trim()));
		soapParams.add(new WSSoapParams("Bxr", txtRepairPeople.getText().toString().trim()));
		soapParams.add(new WSSoapParams("TbTel", txtRepairPhone.getText().toString().trim()));
		soapParams.add(new WSSoapParams("Content", txtFaultDesplay.getText().toString().trim()));
		soapParams.add(new WSSoapParams("Sblb", ((SpinnerItem)spStyle1.getSelectedItem()).getId().trim()));
		soapParams.add(new WSSoapParams("Pp", txtBrand.getText().toString().trim()));
		soapParams.add(new WSSoapParams("Xh", txtModel.getText().toString().trim()));
		soapParams.add(new WSSoapParams("Tbr", txtFillPeople.getText().toString().trim()));
		soapParams.add(new WSSoapParams("TbTime", txtFillDate.getText().toString().trim()));
		String type = gzInfo==null?"1":"2";
		String gzid = gzInfo==null?"":gzInfo.getGz_id();
		soapParams.add(new WSSoapParams("Type", type));
		soapParams.add(new WSSoapParams("Gzid", gzid));		
		new WSAsyncTask(FillFaultFragment.this).execute(new WSRequestParams(
				soapParams, HTTPConstant.INSERT_OR_UPDATE_FAULT, HTTPConstant.LOGIN_URL, null));
	}

	private OnClickListener txtBeginDateOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			String[] dates = txtBeginDate.getText().toString().split("-");
			new DatePickerDialog(getActivity(), new OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					txtBeginDate.setText(year + "-" + (monthOfYear + 1) + "-"
							+ dayOfMonth);
				}
			}, Integer.parseInt(dates[0]), Integer.parseInt(dates[1]) - 1,
					Integer.parseInt(dates[2])).show();

		}
	};

	private OnClickListener txtBeginTimeOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			String[] times = txtBeginTime.getText().toString().split(":");
			new TimePickerDialog(getActivity(), new OnTimeSetListener() {

				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					txtBeginTime.setText(hourOfDay + ":" + minute);
				}
			}, Integer.parseInt(times[0]), Integer.parseInt(times[1]), true)
					.show();
		}
	};

	private OnClickListener btnSubmitOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if(checkFrm()){
				insertOrUpdateFault();
			}
		}
	};
	

	
	private OnItemSelectedListener spStyle1OnItemSelectedListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			if(position==0){
				spStyle2.setSelection(0);
				spStyle3.setSelection(0);
				spStyle2.setVisibility(View.GONE);
				spStyle3.setVisibility(View.GONE);
			}else {
				String code = ((SpinnerItem)spStyle1.getAdapter().getItem(position)).getId();
				getSubSB(code, FillFaultFragment.FLAGGETSTYLE2);
			}
			
			if(((SpinnerItem)spStyle1.getAdapter().getItem(position)).getId().trim().equals("25")){
				llBrand.setVisibility(View.VISIBLE);
			}else {
				llBrand.setVisibility(View.GONE);
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			
		}
	};
	
	private OnItemSelectedListener spStyle2onItemSelectedListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			if(position==0){
				spStyle3.setSelection(0);
				spStyle3.setVisibility(View.GONE);
			}else {
				String code = ((SpinnerItem)spStyle2.getAdapter().getItem(position)).getId();
				getSubSB(code, FillFaultFragment.FLAGGETSTYLE3);
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			
		}
	};
	
	private OnClickListener btnCloseOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			((StationErrorActivity) getActivity()).closeRightMenu();
		}
	};

	@Override
	public void callBackResponse(String result, String responseMethod) {
		
		Gson gson = new Gson();
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
					fretchLine(lines);
				} else {
					Toast.makeText(getActivity(), jsonObject.getString("Msg"),
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

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
		
		else if (responseMethod.equals(HTTPConstant.INSERT_OR_UPDATE_FAULT)) {
			if (HTTPConstant.ERROR.equals(result)) {
				Toast.makeText(getActivity(), "请求服务器数据失败,请检查网络是否正常",
						Toast.LENGTH_LONG).show();
				return;
			}
			Log.i(TAG, result);
			try {
				JSONObject jsonObject = new JSONObject(result);
				if ("1".equals(jsonObject.getString("isSuccess"))) {
					clearFrm();
					Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_LONG).show();
					((StationErrorActivity)getActivity()).refreshFragment(1, TaskConstant.REFRESH_REPAIRLISTFRAGMENT,null);
					((StationErrorActivity)getActivity()).changeToFragmentByIndex(1);
					
				} else {
					Toast.makeText(getActivity(), jsonObject.getString("Msg"),
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void clearFrm(){
		Date today = new Date();
		txtBeginDate.setText(format1.format(today));
		txtBeginTime.setText(format2.format(today));
		spStyle1.setSelection(0);
		txtRepairPeople.setText("");
		txtRepairPhone.setText("");
		txtBrand.setText("");
		txtModel.setText("");
		String xlName = Globals.getInstance().getUser().getXlName();
		String stationName = Globals.getInstance().getUser().getStationName();
		if(xlName!=null && xlName.trim().length()>0 &&stationName!=null && stationName.trim().length()>0){
			txtFaultDesplay.setText(xlName+"："+stationName);
		}else {
			txtFaultDesplay.setText("");
		}
	}

	
}
