package com.touchmenotapps.mobicart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class RegisterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_action_bar_bg));
		
		findViewById(R.id.register_done_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(RegisterActivity.this, MainActivity.class));
				finish();
			}
		});
	}
}
