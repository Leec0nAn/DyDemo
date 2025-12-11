package com.example.dydemo.until;

import android.content.Context;

import com.example.dydemo.bean.TiktokBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DataUntil {
    private static final String TAG = "DataUntil";
    private static final Gson GSON = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    /**
     * 用于解析存放在assets的json文件
     * @param context
     * @param fileName 文件名
     * @return 获得相应的数据
     */
    public static List<TiktokBean> parseFromAssets(Context context, String fileName) {
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            String jsonString = readStreamToString(inputStream);
            return parseFromString(jsonString);
        } catch (IOException e) {
            LogUtil.e(TAG, "从Assets解析JSON失败: " + fileName);
            return new ArrayList<>();
        }
    }

    /**
     * 从文件流读取数据的方法
     * @param inputStream 文件流对象
     * @return
     * @throws IOException
     */
    private static String readStreamToString(InputStream inputStream) throws IOException {
        StringBuilder content = new StringBuilder();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line);
        }
        reader.close();

        return content.toString();
    }

    /**
     * 调用Gson框架解析json文件
     * @param jsonString
     * @return
     */
    public static List<TiktokBean> parseFromString(String jsonString) {
        try {
            Type type = new TypeToken<List<TiktokBean>>(){}.getType();
            return GSON.fromJson(jsonString, type);
        } catch (Exception e) {
            LogUtil.e(TAG, "解析JSON字符串失败" + e.getMessage());
            return new ArrayList<>();
        }
    }
}
