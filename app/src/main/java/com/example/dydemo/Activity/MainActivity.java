package com.example.dydemo.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.dydemo.databinding.ActivityMainBinding;
import com.example.dydemo.adapter.TopbarAdapter;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dydemo.R;
import com.example.dydemo.model.DyDataInstance;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    ActivityMainBinding binding;
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DyDataInstance.getInstance().init(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NavHostFragment host = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (host != null) navController = host.getNavController();
        initTopbar();
        initBottomBar();

    }

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
