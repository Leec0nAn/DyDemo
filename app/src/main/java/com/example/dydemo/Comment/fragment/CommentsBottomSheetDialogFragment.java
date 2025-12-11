package com.example.dydemo.Comment.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dydemo.R;
import com.example.dydemo.Comment.Adapter.CommentAdapter;
import com.example.dydemo.bean.CommentBean;
import com.example.dydemo.bean.CommentUser;
import com.example.dydemo.bean.TiktokBean;
import com.example.dydemo.until.NumberFormatUtil;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.util.ArrayList;
import java.util.List;

public class CommentsBottomSheetDialogFragment extends BottomSheetDialogFragment {
    private static final String TAG = "VideoActivity";
    private static final String ARG_TIKTOK = "arg_tiktok";
    private TiktokBean tiktok;
    private View root;
    private ImageView btnFull;
    private ImageView btnClose;
    private CommentAdapter adapter;
    private TextView count;

    /**
     * 创建DialogFragment对象
     * @param t
     * @return
     */
    public static CommentsBottomSheetDialogFragment newInstance(TiktokBean t) {
        CommentsBottomSheetDialogFragment f = new CommentsBottomSheetDialogFragment();
        Bundle b = new Bundle();
        b.putSerializable(ARG_TIKTOK, t);
        f.setArguments(b);
        return f;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog d = new BottomSheetDialog(requireContext());
        Window w = d.getWindow();
        if (w != null) {
            w.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }
        return d;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.bottom_comment_view, container, false);
        //获得当前页面的数据
        if (getArguments() != null) {
            Object obj = getArguments().getSerializable(ARG_TIKTOK);
            if (obj instanceof TiktokBean) {
                tiktok = (TiktokBean) obj;
            }
        }

        count = root.findViewById(R.id.comment_count);
        RecyclerView list = root.findViewById(R.id.comment_list);
        btnFull = root.findViewById(R.id.comment_full);
        btnClose = root.findViewById(R.id.comment_close);
        EditText et = root.findViewById(R.id.comment_et);
        TextView send = root.findViewById(R.id.comment_send);

        if (tiktok != null) {
            count.setText(NumberFormatUtil.formatCountCn(tiktok.getCommentCount())+"条评论");
        }
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CommentAdapter();
        if (tiktok != null) {
            List<CommentBean> sorted = new ArrayList<>(tiktok.getComments());
            sorted.sort((a, b) -> Long.compare(b.getPublishTime(), a.getPublishTime()));
            adapter.setData(sorted);
        }
        list.setAdapter(adapter);
        list.setNestedScrollingEnabled(true);
        //添加输入框的监听，用于控制发送按钮的可视化
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                send.setVisibility(s != null && s.length() > 0 ? View.VISIBLE : View.GONE);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tiktok == null) return;
                CharSequence content = et.getText();
                if (content == null || content.length() == 0) return;
                CommentBean c = new CommentBean();
                c.setId("c-" + System.currentTimeMillis());
                c.setContent(content.toString());
                c.setPublishTime(System.currentTimeMillis());
                c.setPublishIp("111.13.100.91");
                CommentUser u = new CommentUser();
                u.setUserId("u-local");
                u.setUserName("我");
                u.setAvatarUrl("https://p9-dy.byteimg.com/aweme/100x100/bdf80017d3278f461445.jpeg");
                c.setUser(u);
                tiktok.addComment(c);
                adapter.addToTop(c);
                count.setText(NumberFormatUtil.formatCountCn(tiktok.getCommentCount()) + "条评论");
                et.setText("");
                et.clearFocus();
                InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
                if (imm != null) imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
                send.setVisibility(View.GONE);
            }
        });
        et.setOnClickListener(v -> {
            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
            if (imm != null) imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
        });


        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        if (dialog == null) return;
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        View dRoot = dialog.getWindow() != null ? dialog.getWindow().findViewById(com.google.android.material.R.id.design_bottom_sheet) : null;
        if (dRoot instanceof FrameLayout) {
            //设置弹出窗口的高度
            FrameLayout bottomSheet = (FrameLayout) dRoot;
            bottomSheet.setBackground(new ColorDrawable(Color.TRANSPARENT));
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams();
            lp.height = getPeekHeight();
            bottomSheet.setLayoutParams(lp);

            //设置弹出窗口的状态
            BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(bottomSheet);
            behavior.setPeekHeight(getPeekHeight());
            try { behavior.setDraggable(false); } catch (Throwable ignored) {}
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            btnFull.setOnClickListener(v -> {
                CoordinatorLayout.LayoutParams flp = (CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams();
                flp.height = getScreenHeight();
                bottomSheet.setLayoutParams(flp);
                behavior.setPeekHeight(getScreenHeight());
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            });
            btnClose.setOnClickListener(v -> dismiss());
        }
    }

    private int getScreenHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获得弹出高度 默认是品目的60%
     * @return
     */
    private int getPeekHeight() {
        return (int) (getResources().getDisplayMetrics().heightPixels * 0.6f);
    }
}
