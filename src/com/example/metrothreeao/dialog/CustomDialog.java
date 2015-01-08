package com.example.metrothreeao.dialog;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.view.View;

/**
 * һ����װ���Զ���Dialog ����  ,ͨ���̳в�ʵ��GetView�����ͼ̳й��췽���Ϳ�����ʾ��Dialog
 * @author zzc
 */
public abstract class CustomDialog
{
	private AlertDialog mDialog;
	protected Context mContext;
	public CustomDialog(Context context)
	{
		mContext = context;
	}
	
	
	
	public void show()
	{
		mDialog = new Builder(mContext).create();
		mDialog.setView(getView(mContext));
		mDialog.setCancelable(true);
		mDialog.setCanceledOnTouchOutside(true);
		mDialog.show();
	}
	
	public void cancel()
	{
		if(mDialog != null && mDialog.isShowing()) mDialog.cancel();
	}
	
	public abstract View getView(Context context);
}
