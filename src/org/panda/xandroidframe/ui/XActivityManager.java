package org.panda.xandroidframe.ui;

import java.util.Stack;

import android.app.Activity;

/**
 * @ClassName: XActivityManager
 * @Description:Activity管理类，把所有的Acitivity管理起来，方便获取和退出
 * @author: ZhouRui
 * @date: 2015-11-23 下午2:56:52
 * 
 */
public class XActivityManager {
	/** 记录管理器内所有Activity */
	private Stack<IActivityFunction> aties;

	/**
	 * Single模式 私有构造方法和初始化
	 */
	private XActivityManager() {
		if (null == aties) {
			aties = new Stack<IActivityFunction>();
		}
	}

	/**
	 * 建造管理器，Lazy Loading
	 * 
	 * @return
	 */
	public static XActivityManager getManager() {

		return XActivityManagerHolder.manager;
	}

	// 单例使用静态内部类实现 懒加载。
	public static class XActivityManagerHolder {
		private static final XActivityManager manager = new XActivityManager();
	}

	/**
	 * 获取栈内Activity个数
	 * 
	 * @return
	 */
	public int getCount() {
		return aties.size();
	}

	/**
	 * Activity入栈
	 * 
	 * @param aty
	 */
	public void addActivity(IActivityFunction aty) {
		aties.add(aty);
	}

	/**
	 * 获取栈顶Acitivty
	 * 
	 * @return
	 */
	public Activity topActivity() {
		if (getCount() == 0) {
			return null;
		}
		return (Activity) aties.firstElement();
	}

	/**
	 * 结束指定Activity（重载）
	 * 
	 * @param clazz
	 * @return
	 */
	public Activity findActivity(Class<?> clazz) {
		IActivityFunction aty = null;
		for (IActivityFunction activity : aties) {
			if (activity.getClass().equals(clazz)) {
				aty = activity;
				break;
			}
		}
		return (Activity) aty;
	}

	/**
	 * 结束栈顶Activity
	 */
	public void finishActivity() {
		Activity aty = topActivity();
		if (aty != null) {
			finishActivity(aty);
		}
	}

	/**
	 * 结束指定Acitivty
	 * 
	 * @param clazz
	 */
	public void finishActivity(Class<?> clazz) {
		Activity aty = findActivity(clazz);
		if (aty != null) {
			aties.remove(aty);
		}
	}

	/**
	 * 结束除了参数以为的所有Activity
	 * 
	 * @param clazz
	 */
	public void finishOtherActivity(Class<?> clazz) {
		for (IActivityFunction aty : aties) {
			if (aty.getClass().equals(clazz)) {
				finishActivity((Activity) aty);
			}
		}
	}

	/**
	 * 结束所有activity
	 */
	public void finishAllActivity() {
		for (IActivityFunction aty : aties) {
			finishActivity((Activity) aty);
		}
	}

	/**
	 * App退出
	 */
	public void appExit() {
		try {
			finishActivity();
			Runtime.getRuntime().exit(0);
		} catch (Exception e) {
			e.printStackTrace();
			// -1->异常退出
			Runtime.getRuntime().exit(-1);
		}
	}

	/**
	 * 结束指定Activity （重载）
	 * 
	 * @param activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			aties.remove(activity);
			activity = null;
		}
	}

}
