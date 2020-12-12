package org.maktab.sunset;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import org.maktab.sunset.databinding.FragmentSunsetBinding;

import java.util.ArrayList;
import java.util.List;

public class SunsetFragment extends Fragment {

    private FragmentSunsetBinding mBinding;

    private int mBlueSkyColor;
    private int mSunsetSkyColor;
    private int mNightSkyColor;
    private boolean mFlagStart;
    private boolean mSkyRise;
    private float sunStartY;
    private float sunEndY;
    private float shadowStartY;
    private float shadowEndY;

    public SunsetFragment() {
        // Required empty public constructor
    }

    public static SunsetFragment newInstance() {
        SunsetFragment fragment = new SunsetFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_sunset,
                container,
                false);

        Resources resources = getResources();
        mBlueSkyColor = resources.getColor(R.color.blue_sky);
        mSunsetSkyColor = resources.getColor(R.color.sunset_sky);
        mNightSkyColor = resources.getColor(R.color.night_sky);
        mSkyRise = true;
        mFlagStart = false;


        mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sunStartY = mBinding.sun.getTop(); //10.000
                sunEndY = mBinding.sea.getTop();

                shadowStartY = mBinding.shadow.getBottom(); //10.000
                shadowEndY = mBinding.sky.getBottom();

                if (mSkyRise) {
                    mSkyRise = false;
                    startSunset();
                } else {
                    mSkyRise = true;
                    startSunrise();
                }
//                startAnimation();
            }
        });

        return mBinding.getRoot();
    }

    private void startSunrise() {

        ObjectAnimator reverseHeightAnimator = ObjectAnimator
                .ofFloat(mBinding.sun, "y", sunEndY, sunStartY)
                .setDuration(4000);
        reverseHeightAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator sunriseAnimator = ObjectAnimator
                .ofInt(mBinding.sky, "backgroundColor", mNightSkyColor, mSunsetSkyColor, mBlueSkyColor)
                .setDuration(4000);
        sunriseAnimator.setEvaluator(new ArgbEvaluator());

        ObjectAnimator reverseHeightShadowAnimator = ObjectAnimator
                .ofFloat(mBinding.shadow, "y", shadowEndY, shadowStartY)
                .setDuration(3000);
        reverseHeightShadowAnimator.setInterpolator(new AccelerateInterpolator());
        reverseHeightShadowAnimator.start();


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet
                .play(reverseHeightAnimator)
                .with(sunriseAnimator);
     /*   animatorSet
                .play(reverseHeightAnimator)
                .with(reverseHeightShadowAnimator);*/

        animatorSet.start();
    }

    private void startSunset() {

        ObjectAnimator reverseHeightShadowAnimator = ObjectAnimator
                .ofFloat(mBinding.shadow, "y", shadowStartY,shadowEndY)
                .setDuration(3000);
        reverseHeightShadowAnimator.setInterpolator(new AccelerateInterpolator());
        reverseHeightShadowAnimator.start();


        ObjectAnimator heightAnimator = ObjectAnimator
                .ofFloat(mBinding.sun, "y", sunStartY, sunEndY)
                .setDuration(4000);
        heightAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator sunsetAnimator = ObjectAnimator
                .ofInt(mBinding.sky, "backgroundColor", mBlueSkyColor, mSunsetSkyColor)
                .setDuration(4000);
        sunsetAnimator.setEvaluator(new ArgbEvaluator());

        ObjectAnimator nightAnimator = ObjectAnimator
                .ofInt(mBinding.sky, "backgroundColor", mSunsetSkyColor, mNightSkyColor)
                .setDuration(3000);
        nightAnimator.setEvaluator(new ArgbEvaluator());
        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet
                .play(heightAnimator)
                .with(sunsetAnimator)
                .before(nightAnimator);
        animatorSet.start();

       /* animatorSet
                .play(heightAnimator)
                .with(reverseHeightShadowAnimator);*/

    }

    private void startAnimation() {
        float sunStartY = mBinding.sun.getTop(); //10.000
        float sunEndY = mBinding.sea.getTop(); //100.000

        ObjectAnimator heightAnimator = ObjectAnimator
                .ofFloat(mBinding.sun, "y", sunStartY, sunEndY)
                .setDuration(4000);
        heightAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator sunsetAnimator = ObjectAnimator
                .ofInt(mBinding.sky, "backgroundColor", mBlueSkyColor, mSunsetSkyColor)
                .setDuration(4000);
        sunsetAnimator.setEvaluator(new ArgbEvaluator());

        ObjectAnimator nightAnimator = ObjectAnimator
                .ofInt(mBinding.sky, "backgroundColor", mSunsetSkyColor, mNightSkyColor)
                .setDuration(3000);
        nightAnimator.setEvaluator(new ArgbEvaluator());

        ObjectAnimator reverseHeightAnimator = ObjectAnimator
                .ofFloat(mBinding.sun, "y", sunEndY, sunStartY)
                .setDuration(4000);
        reverseHeightAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator sunriseAnimator = ObjectAnimator
                .ofInt(mBinding.sky, "backgroundColor", mNightSkyColor, mSunsetSkyColor, mBlueSkyColor)
                .setDuration(4000);
        sunriseAnimator.setEvaluator(new ArgbEvaluator());

        /*heightAnimator.start();
        sunsetAnimator.start();
        nightAnimator.start();*/

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet
                .play(heightAnimator)
                .with(sunsetAnimator)
                .before(nightAnimator);
        animatorSet
                .play(nightAnimator)
                .before(reverseHeightAnimator)
                .before(sunriseAnimator);

        animatorSet.start();
    }
}