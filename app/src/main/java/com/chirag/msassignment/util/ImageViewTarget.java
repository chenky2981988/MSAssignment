package com.chirag.msassignment.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.ref.WeakReference;

/**
 * Created by chirag on 09/05/16.
 * Custom Target class to display progress bar while image is getting load from wiki.
 */
public class ImageViewTarget implements Target {

    private WeakReference<ImageView> mImageViewReference;
    private WeakReference<ProgressBar> mProgressBarReference;

    public ImageViewTarget(ImageView imageView, ProgressBar progressBar) {
        this.mImageViewReference = new WeakReference<>(imageView);
        this.mProgressBarReference = new WeakReference<>(progressBar);
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

        //you can use this bitmap to load image in image view or save it in image file like the one in the above question.
        ImageView imageView = mImageViewReference.get();
        if (imageView != null) {
            imageView.setImageBitmap(bitmap);
        }

        ProgressBar progressBar = mProgressBarReference.get();
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

    }


    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        ImageView imageView = mImageViewReference.get();
        if (imageView != null) {
            imageView.setImageDrawable(errorDrawable);
        }

        ProgressBar progressBar = mProgressBarReference.get();
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        ImageView imageView = mImageViewReference.get();
        if (imageView != null) {
            imageView.setImageDrawable(placeHolderDrawable);
        }

        ProgressBar progressBar = mProgressBarReference.get();
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }
}
