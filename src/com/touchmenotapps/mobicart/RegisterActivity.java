package com.touchmenotapps.mobicart;

import com.touchmenotapps.mobicart.interfaces.OnRegisterSuccessListener;
import com.touchmenotapps.mobicart.util.AppPreferences;
import com.touchmenotapps.mobicart.util.UserRegisterAsyncTask;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnRegisterSuccessListener {

	private EditText firstName, lastName, emailID, phoneNumber, streetAddress, city, state, zipCode, country, password, confirmPassword;
	private AppPreferences mPrefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_action_bar_bg));
		mPrefs = new AppPreferences(this);
		
		firstName = (EditText) findViewById(R.id.register_first_name);
		lastName = (EditText) findViewById(R.id.register_last_name);
		emailID = (EditText) findViewById(R.id.register_email_address);
		phoneNumber = (EditText) findViewById(R.id.register_phone);
		streetAddress = (EditText) findViewById(R.id.register_address);
		city = (EditText) findViewById(R.id.register_city);
		state = (EditText) findViewById(R.id.register_state);
		zipCode = (EditText) findViewById(R.id.register_zipcode);
		country = (EditText) findViewById(R.id.register_country);
		password = (EditText) findViewById(R.id.register_password);
		confirmPassword = (EditText) findViewById(R.id.register_confirm_password);
		
		findViewById(R.id.register_done_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(firstName.getText().toString().trim().length() > 0 &&
						lastName.getText().toString().trim().length() > 0 && 
						emailID.getText().toString().trim().length() > 0 &&
						phoneNumber.getText().toString().trim().length() > 0 &&
						streetAddress.getText().toString().trim().length() > 0 &&
						city.getText().toString().trim().length() > 0 &&
						state.getText().toString().trim().length() > 0 &&
						zipCode.getText().toString().trim().length() > 0 &&
						country.getText().toString().trim().length() > 0 &&
						password.getText().toString().trim().length() > 0 &&
						confirmPassword.getText().toString().trim().length() > 0) {
					if(password.getText().toString().trim().equals(confirmPassword.getText().toString().trim())) {
						new UserRegisterAsyncTask(RegisterActivity.this, RegisterActivity.this).execute(new String[] {
								firstName.getText().toString().trim(), 
								lastName.getText().toString().trim(), 
								emailID.getText().toString().trim(), 
								phoneNumber.getText().toString().trim(), 
								streetAddress.getText().toString().trim(), 
								city.getText().toString().trim(), 
								state.getText().toString().trim(), 
								zipCode.getText().toString().trim(), 
								country.getText().toString().trim(), 
								password.getText().toString().trim(), 
								confirmPassword.getText().toString().trim()
						});
					} else 
						Toast.makeText(RegisterActivity.this, "Password and Confirm Password fields don't match!", Toast.LENGTH_LONG).show();
				} else
					Toast.makeText(RegisterActivity.this, "Please fill in all the fields above.", Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	public void onRegistrationCompleteListener() {
		mPrefs.setRegistrationDetials(
				firstName.getText().toString().trim(), 
				lastName.getText().toString().trim(), 
				emailID.getText().toString().trim(), 
				phoneNumber.getText().toString().trim(), 
				streetAddress.getText().toString().trim(), 
				city.getText().toString().trim(), 
				state.getText().toString().trim(), 
				zipCode.getText().toString().trim(), 
				country.getText().toString().trim(), 
				password.getText().toString().trim());
		mPrefs.setRegistrationComplete();
		finish();
	}
}

