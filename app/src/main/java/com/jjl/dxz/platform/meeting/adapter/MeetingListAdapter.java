package com.jjl.dxz.platform.meeting.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jjl.dxz.platform.meeting.bean.resp.MeetingListResp;
import com.jjl.dxz.platform.meeting.databinding.ItemMeetingListBinding;
import com.jjl.dxz.platform.meeting.util.TimeUtils;

import java.util.List;

public class MeetingListAdapter extends RecyclerView.Adapter<MeetingListAdapter.ViewHolder> {

    private final AsyncListDiffer<MeetingListResp> mDiffer;

    public MeetingListAdapter() {
        this.mDiffer = new AsyncListDiffer<>(this, new DiffUtil.ItemCallback<MeetingListResp>() {
            @Override
            public boolean areItemsTheSame(@NonNull MeetingListResp oldItem, @NonNull MeetingListResp newItem) {
                return oldItem.getNumber() == newItem.getNumber();
            }

            @Override
            public boolean areContentsTheSame(@NonNull MeetingListResp oldItem, @NonNull MeetingListResp newItem) {
                return oldItem.getNumber() == newItem.getNumber();
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemMeetingListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mBinding.clMain.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(mDiffer.getCurrentList().get(position));
            }
        });
        mDiffer.getCurrentList().get(position).setBeginAt(TimeUtils.getLocalTime(mDiffer.getCurrentList().get(position).getBeginAt()));
        mDiffer.getCurrentList().get(position).setEndAt(TimeUtils.getLocalTime(mDiffer.getCurrentList().get(position).getEndAt()));
        holder.mBinding.clMain.setBackgroundColor(position % 2 == 0 ? Color.parseColor("#00000000") : Color.parseColor("#080808"));
        holder.mBinding.setItem(mDiffer.getCurrentList().get(position));
        holder.mBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mDiffer.getCurrentList().size();
    }

    public void submit(List<MeetingListResp> items) {
        mDiffer.submitList(items);
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemMeetingListBinding mBinding;

        public ViewHolder(@NonNull ItemMeetingListBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(MeetingListResp meetingListResp);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
