package com.jiualng.widget.utlis;

import android.content.Context;
import android.util.TypedValue;

/**
 * @author JIULANG
 * 创建日期：2021/12/1
 * 描述：显示工具
 */
public class JLDisplayUtil {
    /**
     * dp转px
     */
    public static int dpToPx(Context context, Float dpValue) {
        if (context == null) return (int) (dpValue * 1.5f + 0.5f);
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * sp转px
     */
    public static int spToPx(Context context, Float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转sp
     */
    public static float pxToSp(Context context, Float pxVal) {
        return pxVal / context.getResources().getDisplayMetrics().scaledDensity;
    }
}
