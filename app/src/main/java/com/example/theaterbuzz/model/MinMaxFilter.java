package com.example.theaterbuzz.model;

import android.text.InputFilter;
import android.text.Spanned;

// got the help from -> https://www.tutorialspoint.com/how-to-define-a-min-and-max-value-for-edittext-in-android
public class MinMaxFilter implements InputFilter {

    private int mIntMin;
    private int mIntMax;

    public MinMaxFilter (int minValue, int maxValue) {
        this.mIntMax = maxValue;
        this.mIntMin = minValue;
    }

    public MinMaxFilter(String minValue, String maxValue) {
        this.mIntMin = Integer.parseInt(minValue);
        this.mIntMax = Integer.parseInt(maxValue);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try{
            int input = Integer.parseInt(dest.toString() + source.toString());
            if(isInRange(mIntMin, mIntMax, input))
                return null;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "";
    }

    private boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >=b && c <= a;
    }
}
