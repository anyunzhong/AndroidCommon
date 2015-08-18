package net.datafans.android.common.widget.imageview;

import android.view.View;
import android.widget.ImageView;

public abstract class ImageViewAdapter {

	public abstract ImageView getImageView();

	public abstract void loadImage(String url);
}
