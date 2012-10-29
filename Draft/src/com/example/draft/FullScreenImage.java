package com.example.draft;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FullScreenImage extends Activity
{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.full_image);
		Intent intent = getIntent();
		int imageId = (int) intent.getExtras().getInt("imageId");
		ImageView imageView = (ImageView)this.findViewById(R.id.fullimage);

		imageView.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
		imageView.setImageResource(imageId);
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);

	}
}