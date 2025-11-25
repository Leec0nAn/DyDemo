package com.example.dydemo.until;

import android.content.Context;
import android.util.TypedValue;

public class viewUntil {
    public  static int dpToPx(int dp, Context context) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                context.getResources().getDisplayMetrics()
        );
    }
}
