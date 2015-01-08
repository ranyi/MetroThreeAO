package com.example.metrothreeao.dialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.json.JSONObject;

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

import com.example.metrothreeao.R;
import com.example.metrothreeao.constant.HTTPConstant;
import com.example.metrothreeao.constant.TaskConstant;
import com.example.metrothreeao.entity.repairfault.GzInfo;
import com.example.metrothreeao.face.IChangeFragment;
import com.example.metrothreeao.globals.Globals;
import com.example.metrothreeao.server.webservice.WSAsyncTask;
import com.example.metrothreeao.server.webservice.WSRequestParams;
import com.example.metrothreeao.server.webservice.WSResponseInterface;
import com.example.metrothreeao.server.webservice.WSSoapParams;
import com.google.gson.Gson;

public class ExecuteRepairDialog extends CustomDialog implements
WSResponseInterface {


	private View view;
	private EditText txtCctime,txtGzyy,txtClnote,txtWxdw,txtWxfzr,txtWxdd,txtTxr,txtTxsj,txtAddtxt;
	private Button btnSubmit;
	private Context context;	
	private GzInfo gzInfo;
	private IChangeFragment fragment;
	private SimpleDateFormat format1 = new SimpleDateFormat(
			"yyyy/M/d HH:mm:ss", Locale.getDefault());
	private SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss",
			Locale.getDefault());
	public ExecuteRepairDialog(Context context) {
		super(context);
		this.context = context;
	}
	
	@Override
	public View getView(Context context) {
		view = LayoutInflater.from(context).inflate(
				R.layout.dialog_execute_repair, null, false);
		initView(view);
		initListener();
		init();
		return view;
	}
	public void setGzInfo(GzInfo gzInfo) {
		this.gzInfo = gzInfo;
	}

	public void setFragment(IChangeFragment fragment) {
		this.fragment = fragment;
	}
	
	private void initView(View view){
		txtCctime = (EditText) view.findViewById(R.id.txtCctime);
		txtGzyy = (EditText) view.findViewById(R.id.txtGzyy);
		txtClnote = (EditText) view.findViewById(R.id.txtClnote);
		txtWxdw = (EditText) view.findViewById(R.id.txtWxdw);
		txtWxfzr = (EditText) view.findViewById(R.id.txtWxfzr);
		txtWxdd = (EditText) view.findViewById(R.id.txtWxdd);
		txtTxr = (EditText) view.findViewById(R.id.txtTxr);
		txtTxsj = (EditText) view.findViewById(R.id.txtTxsj);
		txtAddtxt = (EditText) view.findViewById(R.id.txtAddtxt);
		btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
	}
	
	private void initListener(){
		txtCctime.setOnClickListener(txtCctimeOnClickListener);
		btnSubmit.setOnClickListener(btnSubmitOnClickListener);
	}
	
	private void init(){
		Date today = new Date();
		txtCctime.setText(format1.format(today));
		txtTxr.setText(Globals.getInstance().getUser().getTrueName());
		txtTxsj.setText(format1.format(today));
	}
	
	private boolean checkFrm(){
		if (txtGzyy.getText().toString().trim().length() == 0) {
			Toast.makeText(context, "故障原因不能为空！", Toast.LENGTH_LONG).show();
			return false;
		}
		if (txtClnote.getText().toString().trim().length() == 0) {
			Toast.makeText(context, "处理情况不能为空！", Toast.LENGTH_LONG).show();
			return false;
		}
		if (txtWxdw.getText().toString().trim().length() == 0) {
			Toast.makeText(context, "维修单位不能为空！", Toast.LENGTH_LONG).show();
			return false;
		}
		if (txtWxfzr.getText().toString().trim().length() == 0) {
			Toast.makeText(context, "维修负责人不能为空！", Toast.LENGTH_LONG).show();
			return false;
		}
		if (txtAddtxt.getText().toString().trim().length() == 0) {
			Toast.makeText(context, "当班人不能为空！", Toast.LENGTH_LONG).show();
			return false;
		}		
		return true;
	}
	
	private OnClickListener txtCctimeOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			String[] dates = txtCctime.getText().toString().split(" ")[0]
					.split("/");
			new DatePickerDialog(context, new OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					txtCctime.setText(year + "/" + (monthOfYear + 1) + "/"
							+ dayOfMonth + " " + format2.format(new Date()));
				}
			}, Integer.parseInt(dates[0]), Integer.parseInt(dates[1]) - 1,
					Integer.parseInt(dates[2])).show();

		}
	};
	
	private void executeFault() {
		ArrayList<WSSoapParams> soapParams = new ArrayList<WSSoapParams>();
		soapParams.add(new WSSoapParams("gz_id", gzInfo.getGz_id()));
		soapParams.add(new WSSoapParams("userid", Globals.getInstance().getUser().getUserID()));
		soapParams.add(new WSSoapParams("wxdd", txtWxdd.getText().toString().trim()));
		soapParams.add(new WSSoapParams("addtxt", txtAddtxt.getText().toString().trim()));
		soapParams.add(new WSSoapParams("clnote", txtClnote.getText().toString().trim()));
		soapParams.add(new WSSoapParams("wxdw", txtWxdw.getText().toString().trim()));
		soapParams.add(new WSSoapParams("wxfzr", txtWxfzr.getText().toString().trim()));
		soapParams.add(new WSSoapParams("cctime", txtCctime.getText().toString().trim()));
		soapParams.add(new WSSoapParams("gzyy", txtGzyy.getText().toString().trim()));
		//soapParams.add(new WSSoapParams("xfid", ""));

		new WSAsyncTask(ExecuteRepairDialog.this).execute(new WSRequestParams(
				soapParams, HTTPConstant.EXECUTE_FAULT, HTTPConstant.LOGIN_URL,
				null));
	}
	
	private OnClickListener btnSubmitOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (checkFrm()) {
				executeFault();
			}
		}
	};
	
	@Override
	public void callBackResponse(String result, String responseMethod) {
		Gson gson = new Gson();
		if (responseMethod.equals(HTTPConstant.EXECUTE_FAULT)) {

			if (HTTPConstant.ERROR.equals(result)) {
				Toast.makeText(context, "请求服务器数据失败,请检查网络是否正常",
						Toast.LENGTH_LONG).show();
				return;
			}

			try {
				JSONObject jsonObject = new JSONObject(result);
				if ("1".equals(jsonObject.getString("isSuccess"))) {
					fragment.changeFragment(
							TaskConstant.REFRESH_REPAIRLISTFRAGMENT, null);
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
