package ics.datepicker;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

/**
 * Created by Admin on 2014/12/9 0009.
 */
public class WheelPicker extends FrameLayout {
    private NumberPicker leftPicker;
    private NumberPicker middlePicker;
    private NumberPicker rightPicker;

    DataAdapter dataAdapter;


    public WheelPicker(Context context) {
        super(context);
        init();
    }


    public WheelPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WheelPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.wheel_picker_holo, this);
        leftPicker = (NumberPicker) findViewById(R.id.left_picker);
        middlePicker = (NumberPicker) findViewById(R.id.middle_picker);
        rightPicker = (NumberPicker) findViewById(R.id.right_picker);

        NumberPicker.OnValueChangeListener listener = new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (oldVal == newVal)
                    return;
                if (picker == leftPicker) {
                    refreshMiddle(newVal, 0);
                } else if (picker == middlePicker) {
                    refreshRight(leftPicker.getValue(), newVal, 0);
                }
            }
        };
        leftPicker.setOnValueChangedListener(listener);
        middlePicker.setOnValueChangedListener(listener);
        rightPicker.setOnValueChangedListener(listener);
    }

    public void setDataAdapter(DataAdapter adapter) {
        this.dataAdapter = adapter;
        initLeft();
        refreshMiddle(0, 0);
    }

    public void setCurrent(int left, int middle, int right) {
        leftPicker.setValue(left);
        refreshMiddle(left, middle);
        refreshRight(left, middle, right);
    }

    private void initLeft() {
        leftPicker.setDisplayedValues(null);
        String[] displayValues = dataAdapter.getLeftValues();
        leftPicker.setMinValue(0);
        leftPicker.setMaxValue(displayValues.length - 1);
        leftPicker.setDisplayedValues(displayValues);
        leftPicker.setValue(0);
    }

    private void refreshMiddle(int leftIndex, int middleIndex) {
        middlePicker.setDisplayedValues(null);
        String[] middleValues = dataAdapter.getMiddleValues(leftIndex);
        middlePicker.setMinValue(0);
        middlePicker.setMaxValue(middleValues.length - 1);
        middlePicker.setDisplayedValues(middleValues);
        middlePicker.setValue(middleIndex);

        refreshRight(leftIndex, middleIndex, 0);
    }

    private void refreshRight(int leftIndex, int middleIndex, int rightValue) {
        rightPicker.setDisplayedValues(null);
        String[] rightValues = dataAdapter.getRightValues(leftIndex, middleIndex);
        rightPicker.setMinValue(0);
        rightPicker.setMaxValue(rightValues.length - 1);
        rightPicker.setDisplayedValues(rightValues);
        rightPicker.setValue(rightValue);

    }

    public int getLeftValue() {
        return leftPicker.getValue();
    }

    public int getMiddleValue() {
        return middlePicker.getValue();
    }

    public int getRightValue() {
        return rightPicker.getValue();
    }


    public static abstract class DataAdapter {

        public abstract String[] getLeftValues();

        public abstract String[] getMiddleValues(int leftIndex);

        public abstract String[] getRightValues(int leftIndex, int middleIndex);
    }
}
