package com.jjl.dxz.platform.meeting.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jjl.dxz.platform.meeting.bean.resp.PollResp;
import com.jjl.dxz.platform.meeting.databinding.ItemPollListBinding;

import java.util.List;

public class PollListAdapter extends RecyclerView.Adapter<PollListAdapter.ViewHolder> {

    private final AsyncListDiffer<PollResp> mDiffer;

    public PollListAdapter() {
        mDiffer = new AsyncListDiffer<>(this, new DiffUtil.ItemCallback<PollResp>() {
            @Override
            public boolean areItemsTheSame(@NonNull PollResp oldItem, @NonNull PollResp newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull PollResp oldItem, @NonNull PollResp newItem) {
                return oldItem.getTitle().equals(newItem.getTitle())
                        && oldItem.getStatus() == newItem.getStatus()
                        && oldItem.getQuestionCount() == newItem.getQuestionCount();
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemPollListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mBinding.setItem(mDiffer.getCurrentList().get(position));
        holder.mBinding.executePendingBindings();
        holder.mBinding.clMain.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(mDiffer.getCurrentList().get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDiffer.getCurrentList().size();
    }

    public void submit(List<PollResp> items) {
        mDiffer.submitList(items);
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemPollListBinding mBinding;

        public ViewHolder(@NonNull ItemPollListBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(PollResp poll);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
