package net.datafans.android.common.widget.imageview;

import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoader;
import in.srain.cube.image.ImageLoaderFactory;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class CommonImageView extends FrameLayout {

	private CubeImageView imageView;

	public CommonImageView(Context context) {
		super(context);
	}

	public CommonImageView(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
		imageView = new CubeImageView(context);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		imageView.setLayoutParams(params);
		addView(imageView);
	}

	public void loadImage(String url) {
		ImageLoader imageLoader = ImageLoaderFactory.create(getContext());
		imageView.loadImage(imageLoader, url);
	}

}
