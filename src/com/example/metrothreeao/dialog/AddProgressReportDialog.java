package com.example.metrothreeao.dialog;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import com.example.metrothreeao.R;
import com.example.metrothreeao.constant.HTTPConstant;
import com.example.metrothreeao.constant.TaskConstant;
import com.example.metrothreeao.entity.main.Line;
import com.example.metrothreeao.face.IChangeFragment;
import com.example.metrothreeao.fragment.reportfault.FillFaultFragment;
import com.example.metrothreeao.globals.Globals;
import com.example.metrothreeao.server.webservice.WSAsyncTask;
import com.example.metrothreeao.server.webservice.WSRequestParams;
import com.example.metrothreeao.server.webservice.WSResponseInterface;
import com.example.metrothreeao.server.webservice.WSSoapParams;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class AddProgressReportDialog extends CustomDialog implements WSResponseInterface{

	private View view;
	private EditText txtTbadder,txtTbTime,txtDbren,txtXfTime,txtGzNote;
	private Button btnSubmit;
	
	private Context context;
	private String gzId;
	private IChangeFragment fragment;
	private SimpleDateFormat format1 = new SimpleDateFormat("yyyy-M-d");
	private SimpleDateFormat format4 = new SimpleDateFormat("yyyy/M/d H:m:s");
	
	public AddProgressReportDialog(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public View getView(Context context) {
		view = LayoutInflater.from(context).inflate(
				R.layout.dialog_add_progress_report, null, false);
		initView(view);
		initListener();
		init();
		return view;
	}
	
	public void setGzId(String gzId){
		this.gzId = gzId;
	}
	
	public void setFragment(IChangeFragment fragment){
		this.fragment = fragment;
	}
	
	private void initView(View view){
		txtTbadder=(EditText) view.findViewById(R.id.txtTbadder);
		txtTbTime = (EditText) view.findViewById(R.id.txtTbTime);
		txtDbren = (EditText) view.findViewById(R.id.txtDbren);
		txtXfTime = (EditText) view.findViewById(R.id.txtXfTime);
		txtGzNote = (EditText) view.findViewById(R.id.txtGzNote);
		btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
	}
	private void initListener(){
		btnSubmit.setOnClickListener(btnSubmitOnClickListener);
		txtXfTime.setOnClickListener(txtXfTimeOnClickListener);
	}
	
	private void init(){
		txtTbadder.setText(Globals.getInstance().getUser().getTrueName());
		Date today = new Date();
		txtTbTime.setText(format4.format(today));
		txtXfTime.setText(format1.format(today));
	}
	
	private boolean checkFrm(){
		if(txtDbren.getText().toString().trim().length()==0){
			Toast.makeText(context, "请填写当班人！", Toast.LENGTH_LONG).show();
			return false;
		}
		if(txtGzNote.getText().toString().trim().length()==0){
			Toast.makeText(context, "请填写进展说明！", Toast.LENGTH_LONG).show();
			return false;
		}
		
		return true;
	}
	
	
	
	private OnClickListener btnSubmitOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(checkFrm()){
				insertJz();
			}
		}
	};
	
	private OnClickListener txtXfTimeOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			String[] dates = txtXfTime.getText().toString().split("-");
			new DatePickerDialog(context, new OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					txtXfTime.setText(year + "-" + (monthOfYear + 1) + "-"
							+ dayOfMonth);
				}
			}, Integer.parseInt(dates[0]), Integer.parseInt(dates[1]) - 1,
					Integer.parseInt(dates[2])).show();

		}
	};
	
	private void insertJz() {
		ArrayList<WSSoapParams> soapParams = new ArrayList<WSSoapParams>();
		soapParams.add(new WSSoapParams("gz_id", gzId));
		soapParams.add(new WSSoapParams("txtgz_note", txtGzNote.getText().toString().trim()));
		soapParams.add(new WSSoapParams("dbren", txtDbren.getText().toString().trim()));
		soapParams.add(new WSSoapParams("tbadder", txtTbadder.getText().toString().trim()));
		soapParams.add(new WSSoapParams("tbTime", txtTbTime.getText().toString().trim()));
		soapParams.add(new WSSoapParams("lbl_xfTime", txtXfTime.getText().toString().trim()));		
		new WSAsyncTask(AddProgressReportDialog.this).execute(new WSRequestParams(
				soapParams, HTTPConstant.INSERT_JZ, HTTPConstant.LOGIN_URL,
				null));
	}

	@Override
	public void callBackResponse(String result, String responseMethod) {
		Gson gson = new Gson();
		if (responseMethod.equals(HTTPConstant.INSERT_JZ)) {
			if (HTTPConstant.ERROR.equals(result)) {
				Toast.makeText(context, "请求服务器数据失败,请检查网络是否正常",
						Toast.LENGTH_LONG).show();
				return;
			}

			

			try {
				JSONObject jsonObject = new JSONObject(result);
				if ("1".equals(jsonObject.getString("isSuccess"))) {
					fragment.changeFragment(TaskConstant.REFRESH_FAULTDETAILFRAGMENT, null);
					Toast.makeText(context, "添加成功！",
							Toast.LENGTH_LONG).show();
					cancel();
				} else {
					Toast.makeText(context, jsonObject.getString("Msg"),
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	


}
