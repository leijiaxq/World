/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.vooda.world.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.vooda.world.base.BaseApplication;
import com.vooda.world.utils.LogUtil;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
	public static final String TAG = WXEntryActivity.class.getSimpleName();
	public static String code;
	public static BaseResp resp = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_wxentry);
		boolean handleIntent = BaseApplication.api.handleIntent(getIntent(), this);
		//下面代码是判断微信分享后返回WXEnteryActivity的，如果handleIntent==false,说明没有调用IWXAPIEventHandler，则需要在这里销毁这个透明的Activity;
		if (handleIntent == false) {
			LogUtil.d(TAG, "onCreate: " + handleIntent);
			finish();
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		BaseApplication.api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq baseReq) {
		LogUtil.d(TAG, "onReq: ");
		finish();
	}

	@Override
	public void onResp(BaseResp baseResp) {
		if (baseResp != null) {
			resp = baseResp;
			code = ((SendAuth.Resp) baseResp).code; //即为所需的code
		}
		switch (baseResp.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				LogUtil.d(TAG, "onResp: 成功");
				finish();
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				LogUtil.d(TAG, "onResp: 用户取消");
				finish();
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				LogUtil.d(TAG, "onResp: 发送请求被拒绝");
				finish();
				break;
		}
	}
}

///** 微信客户端回调activity示例 */
//public class WXEntryActivity extends WechatHandlerActivity {
//
//	/**
//	 * 处理微信发出的向第三方应用请求app message
//	 * <p>
//	 * 在微信客户端中的聊天页面有“添加工具”，可以将本应用的图标添加到其中
//	 * 此后点击图标，下面的代码会被执行。Demo仅仅只是打开自己而已，但你可
//	 * 做点其他的事情，包括根本不打开任何页面
//	 */
//	public void onGetMessageFromWXReq(WXMediaMessage msg) {
//		if (msg != null) {
//			Intent iLaunchMyself = getPackageManager().getLaunchIntentForPackage(getPackageName());
//			startActivity(iLaunchMyself);
//		}
//	}
//
//	/**
//	 * 处理微信向第三方应用发起的消息
//	 * <p>
//	 * 此处用来接收从微信发送过来的消息，比方说本demo在wechatpage里面分享
//	 * 应用时可以不分享应用文件，而分享一段应用的自定义信息。接受方的微信
//	 * 客户端会通过这个方法，将这个信息发送回接收方手机上的本demo中，当作
//	 * 回调。
//	 * <p>
//	 * 本Demo只是将信息展示出来，但你可做点其他的事情，而不仅仅只是Toast
//	 */
//	public void onShowMessageFromWXReq(WXMediaMessage msg) {
//		if (msg != null && msg.mediaObject != null
//				&& (msg.mediaObject instanceof WXAppExtendObject)) {
//			WXAppExtendObject obj = (WXAppExtendObject) msg.mediaObject;
//			Toast.makeText(this, obj.extInfo, Toast.LENGTH_SHORT).show();
//		}
//	}
//
//}
