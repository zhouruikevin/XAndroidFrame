package org.panda.xandroidframe.ui;

import android.content.Intent;

public interface IActivityStartFunction {

	/**
	 * 跳转到目标Activity，当前Activity不关闭，Intent携带数据
	 */
	void activityShow(Intent intent);

	/**
	 * 跳转到目标Activity，当前Activity不关闭
	 */
	void activityShow(Class<?> clazz);
}
