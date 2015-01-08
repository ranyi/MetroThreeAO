package com.example.metrothreeao.server.webservice;

import java.util.ArrayList;

import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;

import android.util.Log;

import com.example.metrothreeao.constant.HTTPConstant;

public class WSRequestHead {

	static private WSRequestHead head;
	private Element[] header;
	private ArrayList<WSHead> datas;

	private WSRequestHead() {
		datas = new ArrayList<WSHead>();
//		datas.add(new WSHead("SessionID", Globals.getInstance().getUser().getLoginInfo().SessionID));
//		datas.add(new WSHead("UserId", Globals.getInstance().getUser().getLoginInfo().UserId));
//		datas.add(new WSHead("ClientID", Globals.getInstance().getUser().getLoginInfo().ClientId));
//		datas.add(new WSHead("Versions",Version.getVersionName(Globals.getInstance().application)));
		header = new Element[1];
		header[0] = new Element().createElement(HTTPConstant.NAME_SPACE, "MySoapHeader");

		for (WSHead head : datas) {
			Element sessionElement = new Element().createElement(HTTPConstant.NAME_SPACE, head.getKey());
			Log.i("zing", "head key = " + head.getKey() + "value = "+ head.getValue());
			sessionElement.addChild(Node.TEXT, head.getValue());
			header[0].addChild(Node.ELEMENT, sessionElement);
		}
	}

	static public WSRequestHead getInstance() {
		if (head == null) {
			Log.i("zing", "´´½¨HEAD");
			head = new WSRequestHead();
		}
		return head;
	}

	public Element[] getHeader() {
		return header;
	}
	
	public void destoryHead()
	{
		Log.i("zing", "Ïú»ÙHEAD");
		datas.clear();
		head = null;
	}

}
