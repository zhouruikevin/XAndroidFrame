package org.panda.xandroidframe.ui;

public interface IFragmentFunction {
	/**
	 * 替换Fragmnet
	 * 
	 * @param res
	 *            布局文件
	 * @param supportFrag
	 *            fragment实体
	 */
	void changeFragment(int res, XSupportFragment supportFrag);

	/**
	 * 移除fragment
	 */
	void removeFragment(XSupportFragment supportFrag);

	/**
	 * 添加fragment
	 */
	void addFragment(int res, XSupportFragment supportFrag);

	/**
	 * 显示fragment
	 */
	void showFragment(XSupportFragment supportFrag);

	/**
	 * 隐藏fragment
	 */
	void hideFragment(XSupportFragment supportFrag);
}
