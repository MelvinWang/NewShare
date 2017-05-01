package com.melvin.share.alipay;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.melvin.share.R;

import java.util.HashMap;
import java.util.Map;

/**
 *  重要说明:
 *  
 *  这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
 *  真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
 *  防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险； 
 */
public class PayDemoActivity extends FragmentActivity {
	
	/** 支付宝支付业务：入参app_id */
	public static final String APPID = "2017022005784103";


	/** 商户私钥，pkcs8格式 */
	/** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
	/** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
	/** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
	/** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
	/** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
	public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCJOruJBmvGT/9qLMDZ16Gl5GCyZudWbuWFdFv0SOR/yg5VpXEH0VK0ddgXSB/P3zJ3DiBiCbN5ac8irVP/WGzQJlfPrec4ZGVJ5PiQUPKbBFgCLNVhTG66/JFQmD4t0qFiOoT4lH3lJbUXvZDEhNh/lZPFxnku++eIesoATpFOonmmttxS/4lL9egAAwjTVSozxTW7FY4IWULd+ZOkWK30OH4CihcztEqCWm872jrHK3a2gZ1FcALa+12S1m5vpuDODhBPWcgxVF3l65CVhRazq8ImacqlAibsKqNDXK9puJvD4rfPD4cs11HHLefYklcMrGo02cjtW/5Rem7ZvM0tAgMBAAECggEAIRpS3LX4z4jCoxkSmDoE22Cg1sgt69KqeSvddqsSu6TBGnXjMzCSU8fNjagvTFSZ9tAojZ2rdpFIjyPijEkHTprBOe7IRPkq2c1rDF0KeMxucm/gNS1b1K1SXG+tKB6UIX7eTpx7Vghni8eFoXIuS3CIif/bgbLwYiLEkZhiQuZgIw15J6H6NT756biuqim468lPG0AKhbr81yDrLdSGAKmTlI7ogbGsU7nIpSwexaqqMIUkp9flt2lswI0Xr3Ce0vSdzBOgQvSW+QFvo2fiWkc/zIJNaL+7xR9uM+BwPhC+bdnbHp7y1sTZoLmYEnm3K950u/EZPj2ibP4YUo87vQKBgQDnY9wPbrKCCc0xbrrVi94fEcusQc1QVcIKYI6XNI5JCIp0iOGxbnl2GSUGIT5Y0umzrAl2Dg4Lit5xKPOKsP3N9pwcE6ThQsdBSL3Qqfo0Di5xNjFAPqqsGBTIv+wIkzsYO1vFtXbxQAJnhvRSnprHIvKsqzTmmmkOI+7+b4c31wKBgQCX0yCOyet7ga6xqnP27eCbNmgwB3DbhXOifUrSOYw/PFyY0PKw/qogQQPaYHh8Jnca3tKv53381HFBbcqYiNAQFFMoiua5MUlHd9Bqz4UG+0nBY3uyEpJJFb74oMWdtn4WuIkh3M4lSf8weNEjrYaiwKYvb1lOkh79SMoibvoymwKBgQCUu72iNdeMetxon/J8fZFjiz7OFOXyc41sujAtxVyIWXMWAT6BnK96WCNSU2AOHFSYZ0cVmWcxyCu/tUEGr0oVcCVcbswW96O4tRc932SyvULhBqKV1zJBuKDzgVopEOp1YwJUjZZM8IYbIcY+rTB0PGSc1NgGOs64hiSDbBEQtQKBgAMHt7Kh3Q1N/UqWeFIJVV5Dq7iprVr0QuaPUZr4gxAQCFkUcoON0Z9rLMSDAGSZ7+6dW98e46jUJJ6FrnF06ZIolbBNdWVk0m2WupYnJiSJh2NQtPtM25aR1Inpy4fmSIXzkOkYGxUrcOhqwOHRaMp+xdJUipfC2tN7TG17gYrZAoGAVHVB1uV8sq2xU+783mCYd1Bhoq/pOSOFyY7ivVUbwXRW+usSQEN3mzIQ2dEBsWQfOBAWu04eIp1TGShK35wyAqpeSpsU8mLCskWzOA/Q4aGlwOItu8GsAj9Zfc4OgLy0FbsrT8k2PScgzxwcxLFeWZWA+hrMvtIbEvLRVSdHuZI=";
	public static final String RSA_PRIVATE = "";
	
	private static final int SDK_PAY_FLAG = 1;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				@SuppressWarnings("unchecked")
				PayResult payResult = new PayResult((Map<String, String>) msg.obj);
				/**
				 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
				 */
				String resultInfo = payResult.getResult();// 同步返回需要验证的信息
				String resultStatus = payResult.getResultStatus();
				Log.e("sdfkw哈俣",resultStatus);
				// 判断resultStatus 为9000则代表支付成功
				if (TextUtils.equals(resultStatus, "9000")) {
					// 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
					Toast.makeText(PayDemoActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
				} else {
					// 该笔订单真实的支付结果，需要依赖服务端的异步通知。
					Toast.makeText(PayDemoActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
				}
				break;
			}
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}
	/**
	 * 支付宝支付业务
	 * 
	 * @param v
	 */
	public void payV2(View v) {
		if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
			new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialoginterface, int i) {
							//
							finish();
						}
					}).show();
			return;
		}
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
//		Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
		Map<String, String> params = new HashMap<>();
		String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

		String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
		String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
		final String orderInfo = orderParam + "&" + sign;
		
		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				PayTask alipay = new PayTask(PayDemoActivity.this);
				Map<String, String> result = alipay.payV2(orderInfo, true);
				Log.i("msp", result.toString());
				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

}
