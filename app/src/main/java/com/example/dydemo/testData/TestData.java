package com.example.dydemo.testData;

import com.example.dydemo.R;
import com.example.dydemo.model.DyVideo;

import java.util.ArrayList;
import java.util.List;
//测试数据
public class TestData {
    static List<DyVideo> data = new ArrayList<>();
    public static List<DyVideo> getData() {
        DyVideo dyVideo1 = new DyVideo("奥西吧奥西吧奥西吧奥西吧奥西吧奥西吧奥西吧奥西吧奥西吧奥西吧",
                R.raw.video1,
                R.mipmap.image1,
                "哈机密",
                R.mipmap.image1, 999,
                20, 40, 20);
        DyVideo dyVideo2 = new DyVideo("洗吧洗吧洗吧",
                R.raw.video2,
                R.mipmap.image2,
                "纳贝卢多",
                R.mipmap.image2, 999,
                20, 40, 20);
        DyVideo dyVideo3 = new DyVideo("菌门军们巨门军",
                R.raw.video3,
                R.mipmap.image3,
                "骏哥",
                R.mipmap.image3, 999,
                20, 40, 20);
        DyVideo dyVideo4 = new DyVideo("安卓安卓安卓苹果苹果们军们门军们军们",
                R.raw.video4,
                R.mipmap.image4,
                "胡尘风",
                R.mipmap.image4, 999,
                20, 40, 20);
        DyVideo dyVideo5 = new DyVideo("好事好事好事",
                R.raw.video5,
                R.mipmap.image5,
                "峰哥",
                R.mipmap.image5, 999,
                20, 40, 20);
        DyVideo dyVideo6 = new DyVideo("奥西吧奥西吧奥西吧奥西吧奥西吧",
                R.raw.video1,
                R.mipmap.image1,
                "哈机密",
                R.mipmap.image1, 999,
                20, 40, 20);
        DyVideo dyVideo7 = new DyVideo("奥西吧奥西吧奥西吧奥西吧奥西吧",
                R.raw.video1,
                R.mipmap.image1,
                "哈机密",
                R.mipmap.image1, 999,
                20, 40, 20);
        DyVideo dyVideo8 = new DyVideo("奥西吧奥西吧奥西吧奥西吧奥西吧",
                R.raw.video1,
                R.mipmap.image1,
                "哈机密",
                R.mipmap.image1, 999,
                20, 40, 20);
        data.add(dyVideo1);
        data.add(dyVideo2);
        data.add(dyVideo3);
        data.add(dyVideo4);
        data.add(dyVideo5);
        data.add(dyVideo6);
        data.add(dyVideo7);
        data.add(dyVideo8);
        return data;
    }
}
