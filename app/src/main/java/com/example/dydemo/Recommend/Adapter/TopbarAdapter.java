package com.example.dydemo.Recommend.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dydemo.R;

import java.util.Arrays;
import java.util.List;

public class TopbarAdapter extends RecyclerView.Adapter<TopbarAdapter.VH> {

    //topbar的显示数据写死
    private List<String> data = Arrays.asList("购物","经验","同城","关注","商城","推荐");
    //用于检测item是否被选中
    private int selected = -1;
    public interface OnItemClickListener { void onClick(int position, String label); }
    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener l) { this.listener = l; }

    /**
     * 用于设置当前item的选中情况，并且更新相应的item
     * @param pos
     */
    public void setSelected(int pos) {
        if (pos < 0 || pos >= data.size()) return;
        int old = selected;
        selected = pos;
        if (old >= 0) notifyItemChanged(old);
        notifyItemChanged(selected);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topbar, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        String label = data.get(position);
        holder.tv.setText(label);
        boolean isSelected = position == selected;
        holder.tv.setTextColor(holder.tv.getResources().getColor(isSelected ? R.color.black : R.color.vide_bottom_edit_color));
        holder.choose.setVisibility(isSelected ? View.VISIBLE : View.INVISIBLE);
        holder.itemView.setOnClickListener(v -> {
            setSelected(position);
            if (listener != null) listener.onClick(position, label);
        });
    }

    @Override
    public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tv; View choose;
        VH(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.topbar_tv);
            choose = itemView.findViewById(R.id.topbar_choose);
        }
    }
}
