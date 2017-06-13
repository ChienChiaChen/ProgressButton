package com.example.chiachen.progressbutton;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.os.Message;
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
	private static final int STATUS_INIT = 0;
	private static final int ANIMATION_TIME = 2500;
	private ProgressBar mActionButtonProgress;
	private TextView mActionButtonText;
	private View mActionButton;
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case STATUS_INIT:
					resetUI();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// forTest();
		initUI();
	}

	private void forTest(){
		(findViewById(R.id.test)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				android.widget.Toast.makeText(MainActivity.this, "Test", Toast.LENGTH_SHORT).show();
				Log.e(TAG, TAG);
			}
		});
	}

	public void initUI(){
		mActionButtonText = (TextView) findViewById(R.id.action_button_text);
		mActionButton = findViewById(R.id.action_button);
		mActionButtonProgress = (ProgressBar) findViewById(R.id.action_button_progress);
		resetUI();
	}

	private void resetUI(){
		if (mActionButtonText == null || mActionButton == null || mActionButtonProgress == null)
			return;

		mActionButtonText.setText("Download");
		mActionButton.setSelected(false);
		mActionButton.setEnabled(true);
		setButtonClickListener(onClickListener, mActionButton);
	}

	private void handleDownloading() {
		mActionButtonProgress.setProgress(0);
		mActionButtonProgress.setVisibility(View.VISIBLE);
		mActionButtonText.setText("Downloading");
		android.widget.Toast.makeText(MainActivity.this, "Downloading", Toast.LENGTH_SHORT).show();
		updateButtonProgress(100);
	}

	private void handleDownloadEnd() {
		mActionButtonText.setText("Downloaded");
		mActionButton.setSelected(true);
		mActionButton.setEnabled(false);
		mActionButtonProgress.setVisibility(View.GONE);
		android.widget.Toast.makeText(MainActivity.this, "Downloaded", Toast.LENGTH_SHORT).show();
		mHandler.sendEmptyMessageDelayed(STATUS_INIT, ANIMATION_TIME);
	}

	private void updateButtonProgress(int progressTo){
		ObjectAnimator animation = ObjectAnimator.ofInt(
				mActionButtonProgress,
				"progress",
				mActionButtonProgress.getProgress(),
				progressTo);

		animation.setDuration(ANIMATION_TIME);
		animation.setInterpolator(new DecelerateInterpolator());
		animation.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				if (100 == mActionButtonProgress.getProgress()) {
					handleDownloadEnd();
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
					handleDownloading();
					break;
				}
			}
		}
	};
}
