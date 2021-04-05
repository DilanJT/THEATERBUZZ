package com.example.theaterbuzz.model;

import android.text.InputFilter;
import android.text.Spanned;

public class MinFilter implements InputFilter {

    private int minNum;

    public MinFilter(int minNumber) {
        this.minNum = minNumber;
    }

    public MinFilter(String minNumber) {
        this.minNum = Integer.parseInt(minNumber);
    }


    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try{
            int input = Integer.parseInt(dest.toString() + source.toString());
            if(isInRange(minNum, input))
                return null;
        }catch(NumberFormatException e) {
            e.printStackTrace();
        }
        return "";
    }

    private boolean isInRange (int num, int input) {
        return input > num;
    }
}
