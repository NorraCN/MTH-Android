package com.norra.cloud.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.norra.cloud.R;

/**
 * Created by liurenhui on 15/1/20.
 */
public class LoadingDialog extends Dialog {
    private View mView;
    private View loadingIcon;
    private Animation roteAnimation;

    public LoadingDialog(Context context) {
        super(context, R.style.LoadingDialog);
        mView = LayoutInflater.from(context).inflate(R.layout.progress_bar, null);
        loadingIcon = mView.findViewById(R.id.progressbar);
        setContentView(mView);
        roteAnimation = new RotateAnimation(0.0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        roteAnimation.setRepeatCount(-1);
        roteAnimation.setInterpolator(new LinearInterpolator());
        roteAnimation.setDuration(2000);
    }

    @Override
    public void show() {
        super.show();
        loadingIcon.startAnimation(roteAnimation);
    }

}
