package com.stetal.weatherassignment;

import android.content.Context;
import android.graphics.Typeface;

/**
 * @author Stephen Adu
 * @version 0.0.1
 * @since 09/03/2018
 */

public class FontManager {

    private static final String ROOT = "fonts/";
    public static final String FONTAWESOME = ROOT + "weatherfont.ttf";

    public static Typeface getTypeface(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }

}