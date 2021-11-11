package com.jjl.dxz.platform.meeting.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jjl.dxz.platform.meeting.bean.pojo.UserVideo;
import com.jjl.dxz.platform.meeting.databinding.ItemMemberListBinding;

import java.util.List;

public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.ViewHolder> {

    private final List<UserVideo> items;

    public MemberListAdapter(List<UserVideo> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemMemberListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mBinding.setItem(items.get(position));
        holder.mBinding.executePendingBindings();
        holder.mBinding.ivMic.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onAudioClick(position);
            }
        });
        holder.mBinding.ivCamera.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onVideoClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemMemberListBinding mBinding;

        public ViewHolder(@NonNull ItemMemberListBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }
    }

    public interface OnItemClickListener {
        void onAudioClick(int position);

        void onVideoClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
