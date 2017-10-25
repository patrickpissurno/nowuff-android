package com.exe.jpg.nowuff;

import android.animation.Animator;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

/**
 * Created by Patrick on 25/10/2017.
 */

public class LoadingView extends FrameLayout
{
    public static final int TYPE = 0;
    private AppCompatActivity activity;
    private ViewGroup parent;
    private View view;

    private boolean mAnimatingIn = false;
    private boolean mAnimatingInEnded = false;
    private boolean mAnimatingOut = false;
    private boolean mAnimatingOutEnded = false;
    private boolean fadeOutASAP = false;
    private Runnable fadeOutASAPCallback;

    private boolean skipFadeIn;
    private boolean shouldTintStatusBar = true;

    public LoadingView(Context context) {
        super(context);
        inflate(context);
    }

    public LoadingView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        inflate(context);
    }

    public LoadingView(Context context, boolean skipFadeIn){
        super(context);
        this.skipFadeIn = skipFadeIn;
        inflate(context);
    }

    private void inflate(Context context)
    {
        final LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.view = inflater.inflate(getLayoutResource(), this, true);
        if(!skipFadeIn)
            fadeIn(null);
        onFinishInflating(this.view);
    }

    protected void onFinishInflating(View v){

    }

    protected int getLayoutResource(){
        return R.layout.loading_view;
    }

    public void Hide(Runnable onEnd) {
        Hide(onEnd, false);
    }

    public void Hide(Runnable onEnd, boolean skipFadeOut){
        if(!skipFadeOut) {
            fadeOut(() -> {
                if (activity != null) {
                    if(activity.getSupportActionBar() != null)
                        activity.getSupportActionBar().show();
                    parent.removeView(this);
                    if (onEnd != null)
                        onEnd.run();
                }
            });
        }
        else if (activity != null) {
            parent.removeView(this);
            if (onEnd != null)
                onEnd.run();
        }
    }


    public static LoadingView Show(AppCompatActivity activity)
    {
        return LoadingView.Show(activity, TYPE, false);
    }

    public static LoadingView Show(AppCompatActivity activity, boolean skipFadeIn)
    {
        return LoadingView.Show(activity, TYPE, skipFadeIn);
    }

    public static LoadingView Show(AppCompatActivity activity, int type, boolean skipFadeIn){

        final ViewGroup parent = ((ViewGroup)activity.findViewById(R.id.loading_holder));
        if(activity.getSupportActionBar() != null)
            activity.getSupportActionBar().hide();
        if(parent != null) {
            final LoadingView f;
            if(type == TYPE)
                f = new LoadingView(activity, skipFadeIn);
            else
                f = null;
            f.setLayoutParams(new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT));
            f.activity = activity;
            f.parent = parent;
            parent.addView(f);
            return f;
        }
        else
            return null;
    }

    public void tintStatusBar(){
        if(shouldTintStatusBar) {
            final Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    public void resetStatusBar(){
        if(shouldTintStatusBar) {
            final Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    public LoadingView dontTintStatusBar(){
        shouldTintStatusBar = false;
        return this;
    }

    protected final boolean isFadeOutASAP(){
        return fadeOutASAP;
    }

    protected final void fadeIn(Runnable onEnd){
        if(!mAnimatingIn && !mAnimatingInEnded) {
            view.setAlpha(0);
            view.animate().alpha(1).setDuration(300).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    new Handler().postDelayed(LoadingView.this::tintStatusBar, 225);
                }

                @Override
                public void onAnimationEnd(Animator animator) {

                    mAnimatingInEnded = true;
                    if(onEnd != null)
                        onEnd.run();
                    if(fadeOutASAP)
                        new Handler().postDelayed(() -> fadeOut(fadeOutASAPCallback), 500);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            }).start();
            mAnimatingIn = true;
        }
    }

    protected final void fadeOut(Runnable onEnd){
        if(mAnimatingIn && !mAnimatingInEnded)
        {
            fadeOutASAP = true;
            fadeOutASAPCallback = onEnd;
            return;
        }
        if(!mAnimatingOut && !mAnimatingOutEnded) {
            view.setAlpha(1);
            view.animate().alpha(0).setDuration(300).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    resetStatusBar();
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    mAnimatingOutEnded = true;
                    if(onEnd != null)
                        onEnd.run();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            }).start();
            mAnimatingOut = true;
        }
    }
}