package com.lele.menulibrary;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by lele on 2017/9/28.
 */

public class AddMenuView extends FrameLayout {
    private Context context;
    //距离右边的距离
    private static final int PADDING_RIGHT = 32;
    //文字与图片距离
    private static int text_and_picture_space = 36;
    //每一个item高度
    private static int ITEM_HIGHT = 260;

    private boolean isShow = false;
    int[] drawbleIds;
    String[] strs;
    private int topHeight = 90;

    public AddMenuView(Context context, int[] drawbleIds, String[] strs) {
        super(context);
        this.context = context;
        text_and_picture_space = dip2px(context, 18);
        this.drawbleIds = drawbleIds;
        this.strs = strs;
        ITEM_HIGHT = dip2px(context, 90);
        init();
    }

    public AddMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean isShow() {
        return isShow;
    }

    public void setTopHeight(int topHeight) {
        this.topHeight = topHeight;
    }

    private void init() {
        setClipChildren(false);
        setClipToPadding(false);
        for (int i = 0; i < drawbleIds.length; i++) {
            final int position = i;
            TextView tx = new TextView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tx.setLayoutParams(params);
            tx.setCompoundDrawablesWithIntrinsicBounds(null, null,
                    getResources().getDrawable(drawbleIds[i]), null);
            tx.setCompoundDrawablePadding(text_and_picture_space);
            tx.setText(strs[i]);
            tx.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            tx.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(position);
                    }
                }
            });
            tx.setVisibility(GONE);
            addView(tx);
            if (i == 1) {//将中间个设置为最前的
                //这样原先的那个button就会到了最前面。
                tx.bringToFront();
            }
        }
        //android4.4之前的版本需要让view的父控件调用这两个方法使其重绘。
        this.requestLayout();
        this.invalidate();
        showAnimation();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void showAnimation() {
        for (int i = 0; i < drawbleIds.length; i++) {
            PropertyValuesHolder translationHolder = PropertyValuesHolder.ofFloat("translationY", i * ITEM_HIGHT + dip2px(context, 35));
            Keyframe keyframe1 = Keyframe.ofFloat(0.9f, 0.1f);
            Keyframe keyframe2 = Keyframe.ofFloat(1, 1);
//            PropertyValuesHolder alphaHolder = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
            PropertyValuesHolder alphaHolder = PropertyValuesHolder.ofKeyframe("Alpha", keyframe1, keyframe2);
            ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(getChildAt(i), translationHolder, alphaHolder);
            animator.setStartDelay(i * 50);
            if (getChildAt(i).getVisibility() == GONE) {
                if (i > 0) {
                    final int finalI = i;
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            post(new Runnable() {
                                @Override
                                public void run() {
                                    getChildAt(finalI).setVisibility(VISIBLE);
                                }
                            });
                        }
                    }, i * 100);
                } else {
                    getChildAt(i).setVisibility(VISIBLE);
                }
            }
            animator.setDuration(550);
            animator.setInterpolator(new OvershootInterpolator());
            animator.start();
        }
        isShow = true;
    }

    public static int dip2px(Context context, int dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public void hideAnimation() {
        for (int i = 0; i < drawbleIds.length; i++) {
            AnimatorSet animationSet = new AnimatorSet();
            ObjectAnimator translationY = ObjectAnimator.ofFloat(getChildAt(i), "translationY", i * ITEM_HIGHT + dip2px(context, 35), -dip2px(context, 35));
            ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(getChildAt(i), "alpha", 1f, 0f);
            animationSet.playTogether(translationY, alphaAnimation);
            animationSet.setDuration(300);
            animationSet.start();
            if (i == drawbleIds.length - 1) {
                animationSet.addListener(animationListener);
            }
        }
        isShow = false;
    }

    Animator.AnimatorListener animationListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            if (listener != null) {
                listener.hideAnimationEnd();
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    private onItemClickListener listener;

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public interface onItemClickListener {
        void onItemClick(int position);

        void hideAnimationEnd();
    }
}
