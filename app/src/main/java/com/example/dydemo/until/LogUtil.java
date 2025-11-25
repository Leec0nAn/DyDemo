/**

 * Copyright @ 2020 - 2027 iAuto Software(Shanghai) Co., Ltd.

 * All Rights Reserved.

 *

 * Redistribution and use in source and binary forms, with or without

 * modification, are NOT permitted except as agreed by

 * iAuto Software(Shanghai) Co., Ltd.

 *

 * Unless required by applicable law or agreed to in writing, software

 * distributed under the License is distributed on an "AS IS" BASIS,

 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.

 */
package com.example.dydemo.until;

public class LogUtil {
    private static final String TAG = "scenarioUI";
    private static final int CALL_METHOD_LAYER = 2;
    private static final int STACK_LAYER = 3;

    public static void e(String tag, String msg) {
        if (tag == null)
            tag = TAG;
        String method = getMethodName();
        int level = 1;
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        String className = stacks[level].getClassName();
        android.util.Log.e(tag, "[" + className + "::" + method + "] " + msg);
    }

    public static void i(String tag, String msg) {
        if (tag == null)
            tag = TAG;
        String method = getMethodName();
        int level = 1;
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        String className = stacks[level].getClassName();
        android.util.Log.i(tag, "[" + className + "::" + method + "] " + msg);
    }

    public static void d(String tag, String msg) {
        if (tag == null)
            tag = TAG;
        int level = 1;
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        String className = stacks[level].getClassName();
        String method = getMethodName();
        android.util.Log.d(tag, "[" + className + "::" + method + "] " + msg);
    }

    private static String getMethodName() {
        StackTraceElement[] elements = new Throwable().getStackTrace();
        if (STACK_LAYER > elements.length) {
            return "";
        }
        return elements[CALL_METHOD_LAYER].getMethodName();
    }

}
