package com.example.dydemo.DataManager;

import android.content.Context;

import com.example.dydemo.bean.TiktokBean;
import com.example.dydemo.until.DataUntil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// 单例模式管理数据
public class DyDataInstance {
    private static volatile DyDataInstance instance;
    private List<TiktokBean> tiktokDataList = Collections.synchronizedList(new CopyOnWriteArrayList<>());

    private DyDataInstance() {
        // 私有构造函数，防止外部直接实例化
    }

    /**
     * 使用双重检查锁定实现单例模式
     * @return
     */
    public static DyDataInstance getInstance() {
        if (instance == null) {
            synchronized (DyDataInstance.class) {
                if (instance == null) {
                    instance = new DyDataInstance();
                }
            }
        }
        return instance;
    }

    /**
     * 初始数据方法
     * @param context
     */
    public void init(Context context){
        tiktokDataList.addAll(DataUntil.parseFromAssets(context, "tiktok_data"));
    }
    public List<TiktokBean> getTiktokDataList() {
        return tiktokDataList;
    }

    public void updateTiktokDataList(List<TiktokBean> datalist){
            tiktokDataList.clear();
            tiktokDataList.addAll(datalist);
    }

    /**
     * 将数据写回Json文件方法
     * @param context
     * @return
     */
    public boolean writeToJsonFile(Context context) {
        try {
            List<TiktokBean> snapshot = new ArrayList<>(tiktokDataList);
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            String json = gson.toJson(snapshot);
            File out = new File(context.getFilesDir(), "tiktok_data.json");
            try (FileOutputStream fos = new FileOutputStream(out, false);
                 OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {
                writer.write(json);
                writer.flush();
            }
            return true;
        } catch (Throwable t) {
            return false;
        }
    }
}
