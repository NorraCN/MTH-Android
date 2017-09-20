package com.norra.cloud.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.norra.cloud.R;

/**
 * 一个简要对话框
 * 可传入 Title 和 Message
 * @author Garen
 *
 */
public class SimpleDialog extends Dialog {

    private String title;
    private String content;

    private android.view.View.OnClickListener mOkListener;
    /**
     *
     * @param context
     */
    public SimpleDialog(Context context, String title, String content, android.view.View.OnClickListener hander) {
        super(context, R.style.SimpleAlertDialog);
        LayoutParams a = getWindow().getAttributes();
        a.dimAmount = 0;
        getWindow().setAttributes(a);
        setCanceledOnTouchOutside(true);
        this.mOkListener = hander;
        this.title = title;
        this.content = content;
        initViews(context);
    }

    private void initViews(Context context) {
        View layoutView = LayoutInflater.from(context).inflate(
                R.layout.dialog_default, null);
        setContentView(layoutView);

        ((TextView)layoutView.findViewById(R.id.title)).setText(title);
        ((TextView)layoutView.findViewById(R.id.message)).setText(content);

        Button cancel = (Button) layoutView.findViewById(R.id.cancel_btn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        Button confirm = (Button) layoutView.findViewById(R.id.confirm_btn);
        confirm.setOnClickListener(mOkListener);

    }


    public static void show(Context context, String title, String content, View.OnClickListener okListener) {
        SimpleDialog dialog = new SimpleDialog(context, title, content, okListener);
        dialog.show();
    }


}
