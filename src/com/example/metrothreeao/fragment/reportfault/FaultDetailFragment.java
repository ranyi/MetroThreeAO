package com.example.metrothreeao.fragment.reportfault;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.metrothreeao.R;
import com.example.metrothreeao.activity.StationErrorActivity;
import com.example.metrothreeao.constant.HTTPConstant;
import com.example.metrothreeao.constant.TaskConstant;
import com.example.metrothreeao.dialog.AddProgressReportDialog;
import com.example.metrothreeao.entity.repairfault.FaultDetail1;
import com.example.metrothreeao.entity.repairfault.FaultDetail2;
import com.example.metrothreeao.entity.repairfault.FaultDetail3;
import com.example.metrothreeao.entity.repairfault.FaultDetail4;
import com.example.metrothreeao.entity.repairfault.FaultDetail5;
import com.example.metrothreeao.entity.repairfault.FaultDetail6;
import com.example.metrothreeao.entity.repairfault.FaultDetail7;
import com.example.metrothreeao.entity.repairfault.GzInfo;
import com.example.metrothreeao.face.IChangeFragment;
import com.example.metrothreeao.globals.Globals;
import com.example.metrothreeao.server.webservice.WSAsyncTask;
import com.example.metrothreeao.server.webservice.WSRequestParams;
import com.example.metrothreeao.server.webservice.WSResponseInterface;
import com.example.metrothreeao.server.webservice.WSSoapParams;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

public class FaultDetailFragment extends Fragment implements
		WSResponseInterface, IChangeFragment {
	private String TAG = "FaultDetailFragment";
	private Button btnClose,btnAddProgressReport;
	private LinearLayout llContent;

//	private String gzId = "";
	private GzInfo gzInfo;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_fault_detail, container,
				false);
		initView(view);
		initListener();
		init();
		return view;
	}

	private void initView(View view) {
		btnClose = (Button) view.findViewById(R.id.btnClose);
		llContent = (LinearLayout) view.findViewById(R.id.llContent);
		btnAddProgressReport = (Button) view.findViewById(R.id.btnAddProgressReport);
	}

	private void initListener() {
		btnClose.setOnClickListener(btnCloseOnClickListener);
		btnAddProgressReport.setOnClickListener(btnAddProgressRepotrOnClickListener);
	}

	private void init() {
		
		if (gzInfo!=null && gzInfo.getGz_id()!=null &&  gzInfo.getGz_id().trim().length() > 0) {
			getGzDetail();
		}
		if(gzInfo!=null && gzInfo.getGz_flag().equals("10")){
			btnAddProgressReport.setVisibility(View.GONE);
		}
	}


	public void setGzInfo(GzInfo gzInfo){
		this.gzInfo = gzInfo;
	}

	private void fretchGzDetail(String jsonStr) {
		try {
			llContent.removeAllViews();
			JSONArray jsonArray = new JSONArray(jsonStr);
			for(int i=0;i<jsonArray.length();i++){
				JSONObject object = jsonArray.getJSONObject(i);
				fretchJsonObject(object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void fretchJsonObject(JSONObject object){
		try {
			Gson gson = new Gson();
			String type = object.getString("type");
			String content = object.getString("content");
			
			if("1".equals(type)){
				List<FaultDetail1> detail1List = gson.fromJson(content, new TypeToken<List<FaultDetail1>>() {
							}.getType());
				
				if(detail1List!=null && detail1List.size()>0){
					LinearLayout layout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.view_faultdetail1, null);
					TextView lblTitle = (TextView) layout.findViewById(R.id.lblTitle);
					lblTitle.setText("故障报修信息");
					LinearLayout contentLayout = (LinearLayout) layout.findViewById(R.id.llContent);
					for(FaultDetail1 detail1:detail1List){
						LinearLayout subContentLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.view_sub_faultdetail1, null);
						TextView lblFssj = (TextView) subContentLayout.findViewById(R.id.lblFssj);
						TextView lblDd = (TextView) subContentLayout.findViewById(R.id.lblDd);
						TextView lblSblb = (TextView) subContentLayout.findViewById(R.id.lblSblb);
						TextView lblZt = (TextView) subContentLayout.findViewById(R.id.lblZt);
						TextView lblBxr = (TextView) subContentLayout.findViewById(R.id.lblBxr);
						TextView lblBxdh = (TextView) subContentLayout.findViewById(R.id.lblBxdh);
						TextView lblGzxx = (TextView) subContentLayout.findViewById(R.id.lblGzxx);
						TextView lblTbsj = (TextView) subContentLayout.findViewById(R.id.lblTbsj);
						TextView lblTbyh = (TextView) subContentLayout.findViewById(R.id.lblTbyh);
						TextView lblDbr = (TextView) subContentLayout.findViewById(R.id.lblDbr);
						
						lblFssj.setText(detail1.getGuzhangfashengshijian());
						lblDd.setText(detail1.getDidian());
						lblSblb.setText(detail1.getGz_zhuanye_name());
						lblZt.setText(detail1.getZhuangtai());
						lblBxr.setText(detail1.getBaoxiuren());
						lblBxdh.setText(detail1.getBaoxiurendianhua());
						lblGzxx.setText(detail1.getGuzhangxianxiang());
						lblTbsj.setText(detail1.getTianbaoshijian());
						lblTbyh.setText(detail1.getTianbaoyonghu());
						lblDbr.setText(detail1.getDangbanren());
						contentLayout.addView(subContentLayout);
					}
					llContent.addView(layout);
				}	
			}
			
			else if("2".equals(type)){
				List<FaultDetail2> detail2List = gson.fromJson(content, new TypeToken<List<FaultDetail2>>() {
							}.getType());
				
				if(detail2List!=null && detail2List.size()>0){
					LinearLayout layout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.view_faultdetail1, null);
					TextView lblTitle = (TextView) layout.findViewById(R.id.lblTitle);
					lblTitle.setText("下发信息");
					LinearLayout contentLayout = (LinearLayout) layout.findViewById(R.id.llContent);
					for(FaultDetail2 detail:detail2List){
						LinearLayout subContentLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.view_sub_faultdetail2, null);
						TextView lblXflx = (TextView) subContentLayout.findViewById(R.id.lblXflx);
						TextView lblDdlh = (TextView) subContentLayout.findViewById(R.id.lblDdlh);
						TextView lblXfsj = (TextView) subContentLayout.findViewById(R.id.lblXfsj);
						TextView lblGzdj = (TextView) subContentLayout.findViewById(R.id.lblGzdj);
						TextView lblSblb = (TextView) subContentLayout.findViewById(R.id.lblSblb);
						TextView lblJxdw = (TextView) subContentLayout.findViewById(R.id.lblJxdw);
						TextView lblXfyh = (TextView) subContentLayout.findViewById(R.id.lblXfyh);
						TextView lblDbdd = (TextView) subContentLayout.findViewById(R.id.lblDbdd);
						TextView lblXfsm = (TextView) subContentLayout.findViewById(R.id.lblXfsm);
						
						lblXflx.setText(detail.getXiafaleixing());
						lblDdlh.setText(detail.getDiaodulinhao());
						lblXfsj.setText(detail.getXiafashijian());
						lblGzdj.setText(detail.getGuzhangdengji());
						lblSblb.setText(detail.getShebeileibie());
						lblJxdw.setText(detail.getJiexiudanwei());
						lblXfyh.setText(detail.getXiafayonghu());
						lblDbdd.setText(detail.getDangbandiaodu());
						lblXfsm.setText(detail.getXiafashuoming());
						
						contentLayout.addView(subContentLayout);
					}
					llContent.addView(layout);
				}	
			}
			
			else if("3".equals(type)){
				List<FaultDetail3> detail3List = gson.fromJson(content, new TypeToken<List<FaultDetail3>>() {
							}.getType());
				
				if(detail3List!=null && detail3List.size()>0){
					LinearLayout layout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.view_faultdetail1, null);
					TextView lblTitle = (TextView) layout.findViewById(R.id.lblTitle);
					lblTitle.setText("接修信息");
					LinearLayout contentLayout = (LinearLayout) layout.findViewById(R.id.llContent);
					for(FaultDetail3 detail:detail3List){
						LinearLayout subContentLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.view_sub_faultdetail3, null);
						TextView lblJxzt = (TextView) subContentLayout.findViewById(R.id.lblJxzt);
						TextView lblJxsj = (TextView) subContentLayout.findViewById(R.id.lblJxsj);
						TextView lblJxyh = (TextView) subContentLayout.findViewById(R.id.lblJxyh);
						TextView lblWxdw = (TextView) subContentLayout.findViewById(R.id.lblWxdw);
						TextView lblTzwxsj = (TextView) subContentLayout.findViewById(R.id.lblTzwxsj);
						TextView lblDbr = (TextView) subContentLayout.findViewById(R.id.lblDbr);
						TextView lblJxsm = (TextView) subContentLayout.findViewById(R.id.lblJxsm);
						
						lblJxzt.setText(detail.getJiexiuzhuangtai());
						lblJxsj.setText(detail.getJiexiushijian());
						lblJxyh.setText(detail.getJiexiuyonghu());
						lblWxdw.setText(detail.getWeixiudanwei());
						lblTzwxsj.setText(detail.getTongzhiweixiushijian());
						lblDbr.setText(detail.getDangbanren());
						lblJxsm.setText(detail.getJiexiushuoming());
						
						contentLayout.addView(subContentLayout);
					}
					llContent.addView(layout);
				}	
			}
			
			else if("4".equals(type)){
				List<FaultDetail4> detail4List = gson.fromJson(content, new TypeToken<List<FaultDetail4>>() {
							}.getType());
				
				if(detail4List!=null && detail4List.size()>0){
					LinearLayout layout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.view_faultdetail1, null);
					TextView lblTitle = (TextView) layout.findViewById(R.id.lblTitle);
					lblTitle.setText("处理结果");
					LinearLayout contentLayout = (LinearLayout) layout.findViewById(R.id.llContent);
					for(FaultDetail4 detail:detail4List){
						LinearLayout subContentLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.view_sub_faultdetail4, null);
						TextView lblDcsj = (TextView) subContentLayout.findViewById(R.id.lblDcsj);
						TextView lblWcsj = (TextView) subContentLayout.findViewById(R.id.lblWcsj);
						TextView lblGzyy = (TextView) subContentLayout.findViewById(R.id.lblGzyy);
						TextView lblWxdd = (TextView) subContentLayout.findViewById(R.id.lblWxdd);
						TextView lblWxdw = (TextView) subContentLayout.findViewById(R.id.lblWxdw);
						TextView lblFzr = (TextView) subContentLayout.findViewById(R.id.lblFzr);
						TextView lblClqk = (TextView) subContentLayout.findViewById(R.id.lblClqk);
						TextView lblTxyh = (TextView) subContentLayout.findViewById(R.id.lblTxyh);
						TextView lblTxsj = (TextView) subContentLayout.findViewById(R.id.lblTxsj);
						TextView lblDbr = (TextView) subContentLayout.findViewById(R.id.lblDbr);

						lblDcsj.setText(detail.getDaochangshijian());
						lblWcsj.setText(detail.getWanchengshijian());
						lblGzyy.setText(detail.getGuzhangyuanyin());
						lblWxdd.setText(detail.getWeixiudidian());
						lblWxdw.setText(detail.getWeixiudanwei());
						lblFzr.setText(detail.getFuzeren());
						lblClqk.setText(detail.getChuliqinkuang());
						lblTxyh.setText(detail.getTianxieyonghu());
						lblTxsj.setText(detail.getTianxieshijian());
						lblDbr.setText(detail.getDangbanren());
						
						contentLayout.addView(subContentLayout);
					}
					llContent.addView(layout);
				}	
			}
			
			else if("5".equals(type)){
				List<FaultDetail5> detail5List = gson.fromJson(content, new TypeToken<List<FaultDetail5>>() {
							}.getType());
				
				if(detail5List!=null && detail5List.size()>0){
					LinearLayout layout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.view_faultdetail1, null);
					TextView lblTitle = (TextView) layout.findViewById(R.id.lblTitle);
					lblTitle.setText("闭环信息");
					LinearLayout contentLayout = (LinearLayout) layout.findViewById(R.id.llContent);
					for(FaultDetail5 detail:detail5List){
						LinearLayout subContentLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.view_sub_faultdetail5, null);
						TextView lblQrqk = (TextView) subContentLayout.findViewById(R.id.lblQrqk);
						TextView lblCzyh = (TextView) subContentLayout.findViewById(R.id.lblCzyh);
						TextView lblCzsj = (TextView) subContentLayout.findViewById(R.id.lblCzsj);
						TextView lblDbr = (TextView) subContentLayout.findViewById(R.id.lblDbr);
						
						lblQrqk.setText(detail.getQuerenqingkuang());
						lblCzyh.setText(detail.getCaozuoyonghu());
						lblCzsj.setText(detail.getCaozuoshijian());
						lblDbr.setText(detail.getDangbanren());
						contentLayout.addView(subContentLayout);
					}
					llContent.addView(layout);
				}	
			}
			
			else if("6".equals(type)){
				List<FaultDetail6> detail6List = gson.fromJson(content, new TypeToken<List<FaultDetail6>>() {
							}.getType());
				
				if(detail6List!=null && detail6List.size()>0){
					LinearLayout layout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.view_faultdetail1, null);
					TextView lblTitle = (TextView) layout.findViewById(R.id.lblTitle);
					lblTitle.setText("协调记录");
					LinearLayout contentLayout = (LinearLayout) layout.findViewById(R.id.llContent);
					for(FaultDetail6 detail:detail6List){
						LinearLayout subContentLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.view_sub_faultdetail6, null);
						TextView lblXtqk = (TextView) subContentLayout.findViewById(R.id.lblXtqk);
						TextView lblTxyh = (TextView) subContentLayout.findViewById(R.id.lblTxyh);
						TextView lblTxsj = (TextView) subContentLayout.findViewById(R.id.lblTxsj);
						TextView lblDbr = (TextView) subContentLayout.findViewById(R.id.lblDbr);
						
						lblXtqk.setText(detail.getXietiaoqingkuang());
						lblTxyh.setText(detail.getTianxieyonghu());
						lblTxsj.setText(detail.getTianxieshijian());
						lblDbr.setText(detail.getDangbanren());
						contentLayout.addView(subContentLayout);
					}
					llContent.addView(layout);
				}	
			}
			
			else if("7".equals(type)){
				List<FaultDetail7> detail7List = gson.fromJson(content, new TypeToken<List<FaultDetail7>>() {
							}.getType());
				
				if(detail7List!=null && detail7List.size()>0){
					LinearLayout layout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.view_faultdetail1, null);
					TextView lblTitle = (TextView) layout.findViewById(R.id.lblTitle);
					lblTitle.setText("进展报告");
					LinearLayout contentLayout = (LinearLayout) layout.findViewById(R.id.llContent);
					for(FaultDetail7 detail:detail7List){
						LinearLayout subContentLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.view_sub_faultdetail7, null);
						TextView lblJzqk = (TextView) subContentLayout.findViewById(R.id.lblJzqk);
						TextView lblTxyh = (TextView) subContentLayout.findViewById(R.id.lblTxyh);
						TextView lblTxsj = (TextView) subContentLayout.findViewById(R.id.lblTxsj);
						TextView lblDbr = (TextView) subContentLayout.findViewById(R.id.lblDbr);
						
						lblJzqk.setText(detail.getJinzhanqingkuang());
						lblTxyh.setText(detail.getTianxieyonghu());
						lblTxsj.setText(detail.getTianxieshijian());
						lblDbr.setText(detail.getDangbanren());
						contentLayout.addView(subContentLayout);
					}
					llContent.addView(layout);
				}	
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private OnClickListener btnCloseOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			((StationErrorActivity) getActivity()).closeRightMenu();
		}
	};
	
	private OnClickListener btnAddProgressRepotrOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			AddProgressReportDialog dialog = new AddProgressReportDialog(getActivity());
			dialog.setGzId(gzInfo.getGz_id());
			dialog.setFragment(FaultDetailFragment.this);
			dialog.show();
		}
	};

	private void getGzDetail() {
		ArrayList<WSSoapParams> soapParams = new ArrayList<WSSoapParams>();
		soapParams.add(new WSSoapParams("gz_id", gzInfo.getGz_id()));
		new WSAsyncTask(FaultDetailFragment.this).execute(new WSRequestParams(
				soapParams, HTTPConstant.GET_GZINFO, HTTPConstant.LOGIN_URL,
				null));
	}

	@Override
	public void callBackResponse(String result, String responseMethod) {
		Gson gson = new Gson();
		// 获取线路
		if (responseMethod.equals(HTTPConstant.GET_GZINFO)) {
			if (HTTPConstant.ERROR.equals(result)) {
				Toast.makeText(getActivity(), "请求服务器数据失败,请检查网络是否正常",
						Toast.LENGTH_LONG).show();
				return;
			}
			Log.i(TAG, responseMethod + "===>" + result);
			try {
				JSONObject jsonObject = new JSONObject(result);
				if ("1".equals(jsonObject.getString("isSuccess"))) {
//					List<Line> lines = gson.fromJson(
//							jsonObject.getJSONObject("Msg").getString("lines"),
//							new TypeToken<List<Line>>() {
//							}.getType());
					String jsonStr = jsonObject.getJSONObject("Msg").getString("GzInfos");
					fretchGzDetail(jsonStr);
				} else {
					Toast.makeText(getActivity(), jsonObject.getString("Msg"),
							Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void changeFragment(int flag, Object params) {
		switch (flag) {
		case TaskConstant.REFRESH_FAULTDETAILFRAGMENT:
			getGzDetail();
			break;
		}
	}

}
