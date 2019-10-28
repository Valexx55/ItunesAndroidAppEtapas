package com.example.itunesapp.anim;

import android.util.Log;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class AnimacionEntreFragments implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;

    public void transformPage(View view, float position) {

        Log.d("MENSAJE", "tfVAL " +position);
        if (position == 0.0f) { //en 0.0 queda el fragment visible
            Log.d("MENSAJE", "QUEDA VISIBLE ");
        }
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

        if (position < -1) { // [-Infinity,-1)
           //ha dejado de verse el fragment por la izquierda
            view.setAlpha(0);

        } else if (position <= 1) { // [-1,1]
            // está tansitando
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0) {
                view.setTranslationX(horzMargin - vertMargin / 2);
            } else {
                view.setTranslationX(-horzMargin + vertMargin / 2);
            }


            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);


            view.setAlpha(MIN_ALPHA +
                    (scaleFactor - MIN_SCALE) /
                            (1 - MIN_SCALE) * (1 - MIN_ALPHA));

        } else {
           //se va a la derecha
            view.setAlpha(0);
        }
    }
}