package com.example.metrothreeao.util;

import android.app.ProgressDialog;
import android.content.Context;

public class DialogUtil {

	public static ProgressDialog showProgressDialog(Context context,String msg){
		ProgressDialog dialog = new ProgressDialog(context);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setMessage(msg);
		dialog.setIndeterminate(false);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(false);		
		return dialog;
	}
}
