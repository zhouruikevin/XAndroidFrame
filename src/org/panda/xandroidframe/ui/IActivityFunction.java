package org.panda.xandroidframe.ui;

import android.view.View;

public interface IActivityFunction {
	int CREATE = 0;
	int START = 1;
	int RESUME = 2;
	int PAUSE = 3;
	int STOP = 4;
	int DESTORY = 5;

	/**
	 * 初始化界面
	 */
	void initContentView();

	/**
	 * 初始化数据
	 */
	void initData();

	/**
	 * 初始化组件
	 */
	void initWidget();

	/**
	 * 从子线程加载数据
	 */
	void initDataByThread();

	/**
	 * findViewById 函数替换
	 * 
	 * @param id
	 *            控件ID
	 * @return
	 */
	void widgetClick(View v);

	<T extends View> T $(int id);

	/**
	 * findViewById函数替换
	 * 
	 * @param id
	 *            控件ID
	 * @param clickFlag
	 *            是否注册点击监听Listener
	 * @return
	 */
	<T extends View> T $(int id, boolean clickFlag);
}
