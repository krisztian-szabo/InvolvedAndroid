package com.involved.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class InvolvedTextView extends TextView {
	/*
	 * Caches typefaces based on their file path and name, so that they don't have to be created every time when they are referenced.
	 */
	private static Typeface mTypeface;

	public InvolvedTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public InvolvedTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public InvolvedTextView(Context context) {
		super(context);
		init(context);
	}
	
	private void init(Context context) {
		 if (mTypeface == null) {
	         mTypeface = Typeface.createFromAsset(context.getAssets(), "Roboto-Light.ttf");
	     }
		 this.setTypeface(mTypeface);
	}

}
