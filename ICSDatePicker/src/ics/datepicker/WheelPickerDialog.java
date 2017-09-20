/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ics.datepicker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;

import ics.datepicker.DatePicker.OnDateChangedListener;

/**
 * A simple dialog containing an {@link android.widget.DatePicker}.
 * <p/>
 * <p>
 * See the <a href="{@docRoot}guide/topics/ui/controls/pickers.html">Pickers</a>
 * guide.
 * </p>
 */
public class WheelPickerDialog extends Dialog implements OnClickListener {

    private final WheelPicker wheelPicker;
    private View cancel;
    private View ok;
    private OnSelectListener mListener;

    public static interface OnSelectListener {
        void onSelect(WheelPickerDialog dialog, int leftPosition, int middlePosition, int rightPosition);
    }


    public WheelPickerDialog(Context context) {
        this(context, R.style.WhilePickerDialog);
    }


    public WheelPickerDialog(Context context, int theme) {
        super(context, theme);

        Context themeContext = getContext();

        LayoutInflater inflater = (LayoutInflater) themeContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.wheel_picker_dialog, null);
        ok = view.findViewById(R.id.confirm_btn);
        cancel = view.findViewById(R.id.cancel_btn);
        ok.setOnClickListener(onClickListener);
        cancel.setOnClickListener(onClickListener);
        setContentView(view);
        wheelPicker = (WheelPicker) view.findViewById(R.id.wheel_picker);
    }

    public WheelPicker getWheelPicker() {
        return wheelPicker;
    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (v.getId() == ok.getId()) {
                if (mListener != null) {
                    mListener.onSelect(WheelPickerDialog.this, wheelPicker.getLeftValue(), wheelPicker.getMiddleValue(), wheelPicker.getRightValue());
                }
            }
            cancel();
        }

    };

    public void setSelectListener(OnSelectListener mListener) {
        this.mListener = mListener;
    }

    public void onClick(DialogInterface dialog, int which) {

    }

}
