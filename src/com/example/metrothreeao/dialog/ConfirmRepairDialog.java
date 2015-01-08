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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

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

public class ConfirmRepairDialog extends CustomDialog implements
		WSResponseInterface {
	@InjectView(R.id.lblTitle)
	TextView lblTitle;
	@InjectView(R.id.rgpConfirmRepairStyle)
	RadioGroup rgpConfirmRepairStyle;
	@InjectView(R.id.rdClose)
	RadioButton rdClose;
	@InjectView(R.id.rdUnClose)
	RadioButton rdUnClose;
	@InjectView (R.id.txtQr_note)
	EditText txtQr_note;
	@InjectView(R.id.txtDctime)
	EditText txtDctime;
	@InjectView(R.id.txtAdder)
	EditText txtAdder;
	@InjectView(R.id.txtAddertxt)
	EditText txtAddertxt;
	@InjectView(R.id.llUnClose)
	LinearLayout llUnClose;
	@InjectView(R.id.llClose)
	LinearLayout llClose;
	@InjectView(R.id.txtQr_jjnote)
	EditText txtQr_jjnote;
	@InjectView(R.id.txtJjdbr)
	EditText txtJjdbr;
	@InjectView (R.id.txtJjadder)
	EditText txtJjadder;
	@InjectView (R.id.txtJjTime)
	EditText txtJjTime;
	@InjectView (R.id.btnSubmit)
	Button btnSubmit;
	
	private GzInfo gzInfo;
	private IChangeFragment fragment;
	private Context context;
	private SimpleDateFormat format1 = new SimpleDateFormat(
			"yyyy/M/d HH:mm:ss", Locale.getDefault());
	private SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss",
			Locale.getDefault());

	public ConfirmRepairDialog(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public View getView(Context context) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.dialog_confirm_repair, null, false);		
		ButterKnife.inject(this, view);
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
		
	private void initListener(){
		rgpConfirmRepairStyle.setOnCheckedChangeListener(rgpConfirmRepairStyleOnCheckedChangeListener);
	}
	
	private void init(){
		Date today = new Date();
		txtDctime.setText(format1.format(today));
		txtAddertxt.setText(Globals.getInstance().getUser().getTrueName());
		txtJjadder.setText(Globals.getInstance().getUser().getTrueName());
		txtJjTime.setText(format1.format(today));
	}
	
	private boolean checkFrm(){
		switch(rgpConfirmRepairStyle.getCheckedRadioButtonId()){
		case R.id.rdClose:
			if(txtQr_note.getText().toString().trim().length()==0){
				Toast.makeText(context, "确认说明不能为空！", Toast.LENGTH_SHORT).show();
				return false;
			}
			if(txtAdder.getText().toString().trim().length()==0){
				Toast.makeText(context, "当班人不能为空！", Toast.LENGTH_SHORT).show();
				return false;
			}
		break;
		case R.id.rdUnClose:
			if(txtQr_jjnote.getText().toString().trim().length()==0){
				Toast.makeText(context, "拒绝说明不能为空！", Toast.LENGTH_SHORT).show();
				return false;
			}
			if(txtJjdbr.getText().toString().trim().length()==0){
				Toast.makeText(context, "当班人不能为空！", Toast.LENGTH_SHORT).show();
				return false;
			}
			
		break;
		}
		return true;
	}
	
	private void confirmRepair(){
		ArrayList<WSSoapParams> soapParams = new ArrayList<WSSoapParams>();
		switch(rgpConfirmRepairStyle.getCheckedRadioButtonId()){
		case R.id.rdClose:
			soapParams.add(new WSSoapParams("type", "1"));
			soapParams.add(new WSSoapParams("gz_id", gzInfo.getGz_id()));
			soapParams.add(new WSSoapParams("adder", txtAdder.getText().toString().trim()));
			soapParams.add(new WSSoapParams("addertxt", txtAddertxt.getText().toString().trim()));
			soapParams.add(new WSSoapParams("qr_note", txtQr_note.getText().toString().trim()));
			soapParams.add(new WSSoapParams("jjadder", ""));
			soapParams.add(new WSSoapParams("jjdbr", ""));
			soapParams.add(new WSSoapParams("qr_jjnote", ""));
			soapParams.add(new WSSoapParams("dctime", txtDctime.getText().toString().trim()));
			break;
		case  R.id.rdUnClose:
			soapParams.add(new WSSoapParams("type", "2"));
			soapParams.add(new WSSoapParams("gz_id",gzInfo.getGz_id()));
			soapParams.add(new WSSoapParams("adder", ""));
			soapParams.add(new WSSoapParams("addertxt", ""));
			soapParams.add(new WSSoapParams("qr_note", ""));
			soapParams.add(new WSSoapParams("jjadder", txtJjadder.getText().toString().trim()));
			soapParams.add(new WSSoapParams("jjdbr", txtJjdbr.getText().toString().trim()));
			soapParams.add(new WSSoapParams("qr_jjnote", txtQr_jjnote.getText().toString().trim()));
			soapParams.add(new WSSoapParams("dctime", ""));
			break;
		}
		new WSAsyncTask(ConfirmRepairDialog.this).execute(new WSRequestParams(
				soapParams, HTTPConstant.CONFIRM_REPAIR, HTTPConstant.LOGIN_URL,
				null));
	}
	
	private OnCheckedChangeListener rgpConfirmRepairStyleOnCheckedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.rdClose:
				llClose.setVisibility(View.VISIBLE);
				llUnClose.setVisibility(View.GONE);
				break;
			case R.id.rdUnClose:
				llClose.setVisibility(View.GONE);
				llUnClose.setVisibility(View.VISIBLE);
				break;
			}
		}
	};
	
	@OnClick(R.id.txtDctime)
	public void txtDctimeOnClick(View view){
		String[] dates = txtDctime.getText().toString().split(" ")[0]
				.split("/");
		new DatePickerDialog(context, new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year,
					int monthOfYear, int dayOfMonth) {
				txtDctime.setText(year + "/" + (monthOfYear + 1) + "/"
						+ dayOfMonth + " " + format2.format(new Date()));
			}
		}, Integer.parseInt(dates[0]), Integer.parseInt(dates[1]) - 1,
				Integer.parseInt(dates[2])).show();
	}
	
	@OnClick(R.id.btnSubmit)
	public void btnSubmitOnClick(View view){
		if(checkFrm()){
			confirmRepair();
		}
	}
	

	@Override
	public void callBackResponse(String result, String responseMethod) {
		Gson gson = new Gson();
		if (responseMethod.equals(HTTPConstant.CONFIRM_REPAIR)) {

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
