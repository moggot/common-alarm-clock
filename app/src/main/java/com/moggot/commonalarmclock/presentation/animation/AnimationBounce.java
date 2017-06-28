package com.moggot.commonalarmclock.presentation.animation;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.moggot.commonalarmclock.R;

public abstract class AnimationBounce {

    private final Animation animation;
    protected Context context;

    public AnimationBounce(Context context) {
        this.context = context;
        this.animation = AnimationUtils.loadAnimation(context, R.anim.bounce);

        initAnimation();
    }

    public final void animate(View view) {
        initButton(view);
        startAnimation();
    }

    private void initAnimation() {
        animation.setDuration(500);

        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.50, 15);
        animation.setInterpolator(interpolator);
    }

    private void initButton(View view) {
        view.startAnimation(animation);
    }

    protected abstract void animationAction();

    private void startAnimation() {
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                animationAction();
            }
        });
    }
}
