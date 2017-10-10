package com.lele.menulibrary;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.animation.BaseInterpolator;
import android.view.animation.Interpolator;

/**
 * Created by lele on 2017/9/30.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
public class MyAccelerateDecelerationInterpolator extends BaseInterpolator {
    /**
     * Maps a value representing the elapsed fraction of an animation to a value that represents
     * the interpolated fraction. This interpolated value is then multiplied by the change in
     * value of an animation to derive the animated value at the current elapsed animation time.
     *
     * @param input A value between 0 and 1.0 indicating our current point
     *              in the animation where 0 represents the start and 1.0 represents
     *              the end
     * @return The interpolation value. This value can be more than 1.0 for
     * interpolators which overshoot their targets, or less than 0 for
     * interpolators that undershoot their targets.
     */
    @Override
    public float getInterpolation(float input) {
        return 0;
    }
}
