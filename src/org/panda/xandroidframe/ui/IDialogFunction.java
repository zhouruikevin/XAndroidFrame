package org.panda.xandroidframe.ui;

public interface IDialogFunction {
	void showProgress();

	void showProgress(String msg);

	/**
	 * @param flag
	 *            是否可以取消，默认是
	 */
	void showProgress(boolean flag);

	void showProgress(String msg, boolean flag);

	/**
	 * 隐藏dialog
	 */
	void hideProgress();
}
