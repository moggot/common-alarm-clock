package com.moggot.commonalarmclock.animation;

import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.moggot.commonalarmclock.R;

public abstract class AnimationBounce {

    protected Context context;
    protected final android.view.animation.Animation animation;

    public AnimationBounce(Context context) {
        this.context = context;
        this.animation = AnimationUtils.loadAnimation(context, R.anim.bounce);
        double animationDuration = 0.5 * 1000;
        animation.setDuration((long) animationDuration);

        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.50, 15);

        animation.setInterpolator(interpolator);
    }

    private void initButton(View view) {
        view.startAnimation(animation);
    }

    protected abstract void startAnimation();

    public final void animate(View view) {
        initButton(view);
        startAnimation();
    }

}
