package org.panda.xandroidframe.ui;

import java.lang.ref.SoftReference;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @ClassName: XActivity
 * @Description:封装Activity基类 实现数据加载 广播注册 点击监听 页面跳转
 * @author: ZhouRui
 * @date: 2015-11-24 下午5:15:54
 * 
 */
public abstract class XActivity extends FragmentActivity implements
		IActivityFunction, IFragmentFunction, IBroadcastFunction,
		IDialogFunction, OnClickListener {

	public final static int X_MSG_FINISH = 0x1000;
	private ThreadCallback callback;
	private XActivityHandler xHandler = new XActivityHandler(this);

	private static class XActivityHandler extends Handler {
		private final SoftReference<XActivity> soft;

		public XActivityHandler(XActivity aty) {
			super();
			this.soft = new SoftReference<XActivity>(aty);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			// 收到消息 并且软引用没有被回收
			if (msg.what == X_MSG_FINISH && soft.get() != null) {
				soft.get().callback.onFinish();
			}
		}
	}

	private void initAll() {
		initData();
		new Thread(new Runnable() {
			@Override
			public void run() {
				// DoSomeThing
				initDataByThread();
				xHandler.sendEmptyMessage(X_MSG_FINISH);
			}
		}).start();
		initWidget();
	}

	protected void initDataByThreadFinish() {
	}

	@Override
	public void initDataByThread() {
		callback = new ThreadCallback() {
			@Override
			public void onFinish() {
				initDataByThreadFinish();
			}
		};
	}

	public int activityState = DESTORY;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		XActivityManager.getManager().addActivity(this);
		initContentView();
		initAll();
		registerBroadcast();
		activityState = CREATE;

	}

	@Override
	public void onClick(View v) {
		widgetClick(v);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		XActivityManager.getManager().finishActivity(this);
		unRegisterBroadcast();
		activityState = DESTORY;
	}

	@Override
	protected void onPause() {
		super.onPause();
		activityState = PAUSE;
	}

	@Override
	protected void onResume() {
		super.onResume();
		activityState = RESUME;
	}

	@Override
	protected void onStart() {
		super.onStart();
		activityState = START;
	}

	@Override
	protected void onStop() {
		super.onStop();
		activityState = STOP;
	}

	@Override
	public void initData() {
		// 数据初始化 申明的Adapter List 数组赋初值
		// 对象 网络初始化 在这里调用他的构造器
	}

	@Override
	public void initWidget() {
		// 控件初始化
		// 比如设置DialogProgress TitleBar 要显示的内容
	}

	private interface ThreadCallback {
		void onFinish();
	}

	@Override
	public void widgetClick(View v) {
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends View> T $(int id) {
		return (T) findViewById(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends View> T $(int id, boolean clickFlag) {
		T view = (T) findViewById(id);
		if (clickFlag) {
			view.setOnClickListener(this);
		}
		return view;
	}

	private ProgressDialog progressDialog;

	@Override
	public void showProgress() {
		showProgress("", true);
	}

	@Override
	public void showProgress(String msg) {
		showProgress(msg, true);
	}

	@Override
	public void showProgress(boolean flag) {
		showProgress("", flag);
	}

	@SuppressLint("InlinedApi")
	@Override
	public void showProgress(String msg, boolean flag) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setProgressStyle(ProgressDialog.THEME_HOLO_LIGHT);
		}
		progressDialog.setCancelable(flag);
		progressDialog.setCanceledOnTouchOutside(flag);
		progressDialog.setMessage(msg);
		progressDialog.show();
	}

	@Override
	public void hideProgress() {
		if (progressDialog == null) {
			return;
		}
		if (progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}

	@Override
	public void registerBroadcast() {
		// 重写实现注册
	}

	@Override
	public void unRegisterBroadcast() {
		// 重写实现反注册
	}

	protected XSupportFragment currentFrag;

	@SuppressLint("Recycle")
	@Override
	public void changeFragment(int res, XSupportFragment supportFrag) {
		if (supportFrag.equals(currentFrag)) {
			return;
		}
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		if (!supportFrag.isAdded()) {
			ft.add(res, supportFrag, supportFrag.getClass().getName());
		}
		if (supportFrag.isHidden()) {
			ft.show(supportFrag);
			supportFrag.onChange();
		}
		if (currentFrag != null && currentFrag.isVisible()) {
			ft.hide(currentFrag);
		}

		currentFrag = supportFrag;
	}

	@Override
	public void removeFragment(XSupportFragment supportFrag) {
		if (supportFrag != null) {
			FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			ft.remove(supportFrag);
			ft.commit();
		}
	}

	@Override
	public void addFragment(int res, XSupportFragment supportFrag) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(res, supportFrag);
		ft.commit();
	}

	@Override
	public void showFragment(XSupportFragment supportFrag) {
		if (supportFrag != null && supportFrag.isHidden()) {
			FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			ft.show(supportFrag);
			ft.commit();
		}
	}

	@Override
	public void hideFragment(XSupportFragment supportFrag) {
		if (supportFrag != null && !supportFrag.isHidden()) {
			FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			ft.hide(supportFrag);
			ft.commit();
		}
	}

}
