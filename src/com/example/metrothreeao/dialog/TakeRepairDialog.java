package com.example.metrothreeao.dialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONObject;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.metrothreeao.R;
import com.example.metrothreeao.constant.HTTPConstant;
import com.example.metrothreeao.constant.TaskConstant;
import com.example.metrothreeao.entity.repairfault.GzInfo;
import com.example.metrothreeao.face.IChangeFragment;
import com.example.metrothreeao.fragment.reportfault.RepairListFragment;
import com.example.metrothreeao.globals.Globals;
import com.example.metrothreeao.server.webservice.WSAsyncTask;
import com.example.metrothreeao.server.webservice.WSRequestParams;
import com.example.metrothreeao.server.webservice.WSResponseInterface;
import com.example.metrothreeao.server.webservice.WSSoapParams;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TakeRepairDialog extends CustomDialog implements
		WSResponseInterface {

	private View view;
	private RadioGroup rgpTakeRepairStyle;
	private LinearLayout llTake, llUnTake;
	private EditText txtWxtime, txtJxbm, txtJxnote, txtLabbm, txtTruename,
			txtTakeTime, txtDbr, txtJjjxnote, txtJjyh, txtJjsj, txtJjDbr;
	private Button btnSubmit;
	private Context context;
	
	private GzInfo gzInfo;
	private IChangeFragment fragment;

	private SimpleDateFormat format1 = new SimpleDateFormat(
			"yyyy/M/d HH:mm:ss", Locale.getDefault());
	private SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss",
			Locale.getDefault());

	public TakeRepairDialog(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public View getView(Context context) {
		view = LayoutInflater.from(context).inflate(
				R.layout.dialog_take_repair, null, false);
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

	private void initView(View view) {
		rgpTakeRepairStyle = (RadioGroup) view
				.findViewById(R.id.rgpTakeRepairStyle);
		llTake = (LinearLayout) view.findViewById(R.id.llTake);
		llUnTake = (LinearLayout) view.findViewById(R.id.llUnTake);
		txtWxtime = (EditText) view.findViewById(R.id.txtWxtime);
		txtJxbm = (EditText) view.findViewById(R.id.txtJxbm);
		txtJxnote = (EditText) view.findViewById(R.id.txtJxnote);
		txtLabbm = (EditText) view.findViewById(R.id.txtLabbm);
		txtTruename = (EditText) view.findViewById(R.id.txtTruename);
		txtTakeTime = (EditText) view.findViewById(R.id.txtTakeTime);
		txtDbr = (EditText) view.findViewById(R.id.txtDbr);
		txtJjjxnote = (EditText) view.findViewById(R.id.txtJjjxnote);
		txtJjyh = (EditText) view.findViewById(R.id.txtJjyh);
		txtJjsj = (EditText) view.findViewById(R.id.txtJjsj);
		txtJjDbr = (EditText) view.findViewById(R.id.txtJjDbr);
		btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
	}

	private void initListener() {
		rgpTakeRepairStyle
				.setOnCheckedChangeListener(rgpTakeRepairStyleOnCheckedChangeListener);
		txtWxtime.setOnClickListener(txtWxtimeOnClickListener);
		btnSubmit.setOnClickListener(btnSubmitOnClickListener);
	}

	private void init() {
		Date today = new Date();
		txtWxtime.setText(format1.format(today));
		txtJxbm.setText(Globals.getInstance().getUser().getStationName());
		txtJxnote.setText("已接修");
		txtTruename.setText(Globals.getInstance().getUser().getTrueName());
		txtTakeTime.setText(format1.format(today));
		txtJjyh.setText(Globals.getInstance().getUser().getTrueName());
		txtJjsj.setText(format1.format(today));
	}

	private boolean checkFrm() {
		switch (rgpTakeRepairStyle.getCheckedRadioButtonId()) {
		case R.id.rdTake:
			if (txtJxnote.getText().toString().trim().length() == 0) {
				Toast.makeText(context, "接修说明不能为空！", Toast.LENGTH_LONG).show();
				return false;
			}
			if (txtLabbm.getText().toString().trim().length() == 0) {
				Toast.makeText(context, "接修单位不能为空！", Toast.LENGTH_LONG).show();
				return false;
			}
			if (txtDbr.getText().toString().trim().length() == 0) {
				Toast.makeText(context, "当班人不能为空！", Toast.LENGTH_LONG).show();
				return false;
			}
			break;
		case R.id.rdUnTake:
			if (txtJjjxnote.getText().toString().trim().length() == 0) {
				Toast.makeText(context, "拒绝说明不能为空！", Toast.LENGTH_LONG).show();
				return false;
			}
			if (txtJjDbr.getText().toString().trim().length() == 0) {
				Toast.makeText(context, "当班人不能为空！", Toast.LENGTH_LONG).show();
				return false;
			}
			break;
		}

		return true;
	}

	private OnCheckedChangeListener rgpTakeRepairStyleOnCheckedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.rdTake:
				llTake.setVisibility(View.VISIBLE);
				llUnTake.setVisibility(View.GONE);
				break;
			case R.id.rdUnTake:
				llTake.setVisibility(View.GONE);
				llUnTake.setVisibility(View.VISIBLE);
				break;
			}
		}
	};
	private OnClickListener txtWxtimeOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			String[] dates = txtWxtime.getText().toString().split(" ")[0]
					.split("/");
			new DatePickerDialog(context, new OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					txtWxtime.setText(year + "/" + (monthOfYear + 1) + "/"
							+ dayOfMonth + " " + format2.format(new Date()));
				}
			}, Integer.parseInt(dates[0]), Integer.parseInt(dates[1]) - 1,
					Integer.parseInt(dates[2])).show();

		}
	};

	private OnClickListener btnSubmitOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (checkFrm()) {
				takeFault();
			}
		}
	};

	private void takeFault() {
		ArrayList<WSSoapParams> soapParams = new ArrayList<WSSoapParams>();
		soapParams.add(new WSSoapParams("gz_id", gzInfo.getGz_id()));
		switch (rgpTakeRepairStyle.getCheckedRadioButtonId()) {
		case R.id.rdTake:
			soapParams.add(new WSSoapParams("jx_flag", "1"));
			soapParams.add(new WSSoapParams("wxtime", txtWxtime.getText()
					.toString().trim()));
			soapParams.add(new WSSoapParams("txtaddtxt", txtDbr.getText()
					.toString().trim()));
			soapParams.add(new WSSoapParams("labbm", txtLabbm.getText()
					.toString().trim()));
			soapParams.add(new WSSoapParams("truename", txtTruename.getText()
					.toString().trim()));
			soapParams.add(new WSSoapParams("jxnote", txtJxnote.getText()
					.toString().trim()));
			soapParams.add(new WSSoapParams("jjjxnote", ""));
			break;
		case R.id.rdUnTake:
			soapParams.add(new WSSoapParams("jx_flag", "2"));
			soapParams.add(new WSSoapParams("wxtime", txtJjsj.getText().toString().trim()));
			soapParams.add(new WSSoapParams("txtaddtxt", txtJjDbr.getText()
					.toString().trim()));
			soapParams.add(new WSSoapParams("labbm", ""));
			soapParams.add(new WSSoapParams("truename", txtJjyh.getText()
					.toString().trim()));
			soapParams.add(new WSSoapParams("jxnote", ""));
			soapParams.add(new WSSoapParams("jjjxnote", txtJjjxnote.getText()
					.toString().trim()));
			break;
		}

		new WSAsyncTask(TakeRepairDialog.this).execute(new WSRequestParams(
				soapParams, HTTPConstant.TAKE_FAULT, HTTPConstant.LOGIN_URL,
				null));
	}

	@Override
	public void callBackResponse(String result, String responseMethod) {
		Gson gson = new Gson();
		if (responseMethod.equals(HTTPConstant.TAKE_FAULT)) {

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
