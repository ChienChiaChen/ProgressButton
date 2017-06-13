package com.example.chiachen.progressbutton;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
	public static final String TAG = "MainActivity";
	private ProgressBar mActionButtonProgress;
	private TextView mActionButtonText;
	private View mActionButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initUI();
	}
	public void initUI(){
		mActionButtonText = (TextView) findViewById(R.id.action_button_text);
		mActionButtonText.setText("Download");
		mActionButton = findViewById(R.id.action_button);
		mActionButtonProgress = (ProgressBar) findViewById(R.id.action_button_progress);
		setButtonClickListener(onClickListener, mActionButton);
	}

	private void setButtonClickListener(View.OnClickListener listener, View... buttons) {
		for (View button : buttons) {
			if (button != null)
				button.setOnClickListener(listener);
		}
	}

	private View.OnClickListener onClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.action_button: {
					handleDownload();
					break;
				}
			}
		}
	};

	private void handleDownload() {
		mActionButtonProgress.setProgress(0);
		mActionButtonProgress.setVisibility(View.VISIBLE);
		mActionButtonText.setText("Downloading");
		android.widget.Toast.makeText(MainActivity.this, "Downloading", Toast.LENGTH_SHORT).show();
		updateButtonProgress(100);
	}

	private void showDownloaded() {
		mActionButtonText.setText("Downloaded");
		mActionButton.setSelected(false);
		mActionButtonProgress.setVisibility(View.GONE);
	}

	private void updateButtonProgress(int progressTo){
		ObjectAnimator animation = ObjectAnimator.ofInt(
				mActionButtonProgress,
				"progress",
				mActionButtonProgress.getProgress(),
				progressTo);

		animation.setDuration(3000);
		animation.setInterpolator(new DecelerateInterpolator());
		animation.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				if (100 == mActionButtonProgress.getProgress()) {
					showDownloaded();
					Log.e(TAG, "Downloaded");
				}
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}
		});
		animation.start();
	}

}
