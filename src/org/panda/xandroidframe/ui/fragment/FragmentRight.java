package org.panda.xandroidframe.ui.fragment;

import org.panda.xandroidframe.ui.XSupportFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.xandroidframe.R;

public class FragmentRight extends XSupportFragment {

	private Button btnReplace;

	@SuppressLint("InflateParams")
	@Override
	protected View inflaterView(LayoutInflater inflater, ViewGroup container,
			Bundle bundle) {
		View view = inflater.inflate(R.layout.frag_right, null);
		btnReplace = $(R.id.btnReplace, true);
		return view;
	}

	@Override
	protected void widgetClick(View v) {
		super.widgetClick(v);
		switch (v.getId()) {
		case R.id.btnReplace:
			Toast.makeText(getActivity(), "Right", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}

}
