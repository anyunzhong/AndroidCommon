package net.datafans.android.common.widget.imageview;

import android.content.Context;
import android.widget.ImageView;

public class CubeImageViewAdapter extends ImageViewAdapter {

	//private CubeImageView imageView;
	private Context context;

	public CubeImageViewAdapter(Context context) {

		this.context = context;

//		imageView = new CubeImageView(context);
//		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
//				LayoutParams.MATCH_PARENT);
//		imageView.setLayoutParams(params);
	}

	@Override
	public ImageView getImageView() {
		//return imageView;
		return null;
	}

	@Override
	public void loadImage(String url) {
//		ImageLoader imageLoader = ImageLoaderFactory.create(context);
//		imageView.loadImage(imageLoader, url);
	}

}
