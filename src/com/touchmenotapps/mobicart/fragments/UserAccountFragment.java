package com.touchmenotapps.mobicart.fragments;

import com.touchmenotapps.mobicart.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class UserAccountFragment extends Fragment {
	
	private View mViewHolder;
	@SuppressWarnings("unused")
	private EditText mFirstName, mLastName, mEmail, mPhone, mAddress; 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mViewHolder = inflater.inflate(R.layout.fragment_user_account, null);	
		mFirstName = (EditText) mViewHolder.findViewById(R.id.profile_edit_first_name);
		mLastName = (EditText) mViewHolder.findViewById(R.id.profile_edit_last_name);
		mEmail = (EditText) mViewHolder.findViewById(R.id.profile_edit_email_address);
		mPhone = (EditText) mViewHolder.findViewById(R.id.profile_edit_phone);
		mAddress = (EditText) mViewHolder.findViewById(R.id.profile_edit_address);
						
		mViewHolder.findViewById(R.id.profile_edit_save).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getActivity(), R.string.msg_profile_saved, Toast.LENGTH_SHORT).show();
				getActivity().getSupportFragmentManager().popBackStack();
			}
		});
		
		mViewHolder.findViewById(R.id.profile_edit_cancel).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				getActivity().getSupportFragmentManager().popBackStack();
			}
		});
		return mViewHolder;
	}

}
