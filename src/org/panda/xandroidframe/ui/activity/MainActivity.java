package org.panda.xandroidframe.ui.activity;

import org.panda.xandroidframe.ui.XActivity;
import org.panda.xandroidframe.ui.XSupportFragment;
import org.panda.xandroidframe.ui.fragment.FragmentLeft;
import org.panda.xandroidframe.ui.fragment.FragmentRight;

import android.graphics.Xfermode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.xandroidframe.R;

public class MainActivity extends XActivity {

	@SuppressWarnings("unused")
	private Button btnShowDlg, btnChangeFrag;
	private TextView tvHello;
	private XSupportFragment leftFragment, rightFragment;

	// 初始化View
	@Override
	public void initContentView() {
		setContentView(R.layout.activity_main);
	}

	// 子线程加载完成后，切换到主线程做响应操作
	@Override
	protected void initDataByThreadFinish() {
		super.initDataByThreadFinish();
		tvHello.setText("wait 3s");
	}

	// 休眠3秒模拟子线程加载数据
	@Override
	public void initDataByThread() {
		super.initDataByThread();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// 初始化数据
	@Override
	public void initData() {
		leftFragment = new FragmentLeft();
		rightFragment = new FragmentRight();
		super.initData();
	}

	// 初始化组件
	@Override
	public void initWidget() {
		super.initWidget();
		tvHello = $(R.id.tvHello);
		btnShowDlg = $(R.id.btnShowDlg, true);
		btnChangeFrag = $(R.id.btnChangeFrag, true);
	}

	// 点击事件处理
	@Override
	public void widgetClick(View v) {
		super.widgetClick(v);
		switch (v.getId()) {
		case R.id.btnShowDlg:
			showProgress("Waiting...");
			break;
		case R.id.btnChangeFrag:
			break;
		default:
			break;
		}
	}
}
