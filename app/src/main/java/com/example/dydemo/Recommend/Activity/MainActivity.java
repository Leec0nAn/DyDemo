package com.example.dydemo.Recommend.Activity;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.dydemo.databinding.ActivityMainBinding;
import com.example.dydemo.Recommend.Adapter.TopbarAdapter;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dydemo.R;
import com.example.dydemo.DataManager.DyDataInstance;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    //使用viewbinding来获取xml的控件id
    ActivityMainBinding binding;
    //NavController用于跳转fragment
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化本地数据
        DyDataInstance.getInstance().init(this);
        //获取viewbinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NavHostFragment host = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (host != null) navController = host.getNavController();
        initTopbar();
        initBottomBar();

    }

    /**
     * 初始化顶部bar方法
     */
    private void initTopbar() {
        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.topbarRv.setLayoutManager(lm);
        TopbarAdapter adapter = new TopbarAdapter();
        binding.topbarRv.setAdapter(adapter);
        adapter.setOnItemClickListener((position, label) -> {
            if (navController == null) return;
            switch (label) {
                case "购物":
                    navController.navigate(R.id.mainActivity_ShopFragment);
                    break;
                case "经验":
                    navController.navigate(R.id.mainActivity_ExperienceFragment);
                    break;
                case "同城":
                    navController.navigate(R.id.mainActivity_CityFragment);
                    break;
                case "关注":
                    navController.navigate(R.id.mainActivity_FollowFragment);
                    break;
                case "商城":
                    navController.navigate(R.id.mainActivity_MallFragment);
                    break;
                case "推荐":
                    navController.navigate(R.id.mainActivity_RecommendFragment);
                    break;
            }
        });
    }

    /**
     * 初始化底部bar方法
     */
    private void initBottomBar() {
        View home = findViewById(R.id.bottombar_home);
        View friends = findViewById(R.id.bottombar_friends);
        View message = findViewById(R.id.bottombar_message);
        View mine = findViewById(R.id.bottombar_mine);
        setBottomSelected(home);
        if (navController != null) navController.navigate(R.id.mainActivity_MainFragment);
        home.setOnClickListener(v -> {
            if (navController != null) navController.navigate(R.id.mainActivity_MainFragment);
            setBottomSelected(home);
        });
        friends.setOnClickListener(v -> {
            if (navController != null) navController.navigate(R.id.mainActivity_FriendsFragment);
            setBottomSelected(friends);
        });
        message.setOnClickListener(v -> {
            if (navController != null) navController.navigate(R.id.mainActivity_MessageFragment);
            setBottomSelected(message);
        });
        mine.setOnClickListener(v -> {
            if (navController != null) navController.navigate(R.id.mainActivity_MineFragment);
            setBottomSelected(mine);
        });
    }


    /**
     * 用于处理点击bar的按钮 切换的ui变化
     * @param selected
     */
    private void setBottomSelected(View selected) {
        TextView homeTv = findViewById(R.id.bottombar_home);
        TextView friendsTv = findViewById(R.id.bottombar_friends);
        TextView messageTv = findViewById(R.id.bottombar_message);
        TextView mineTv = findViewById(R.id.bottombar_mine);
        int normal = getResources().getColor(R.color.vide_bottom_edit_color);
        int selectedColor = getResources().getColor(R.color.black);
        homeTv.setTextColor(selected == homeTv ? selectedColor : normal);
        friendsTv.setTextColor(selected == friendsTv ? selectedColor : normal);
        messageTv.setTextColor(selected == messageTv ? selectedColor : normal);
        mineTv.setTextColor(selected == mineTv ? selectedColor : normal);
    }
}
