package com.jjl.dxz.platform.meeting.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jjl.dxz.platform.meeting.bean.pojo.ChatMsg;
import com.jjl.dxz.platform.meeting.databinding.ItemLeftChatBinding;
import com.jjl.dxz.platform.meeting.databinding.ItemRightChatBinding;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ChatMsg> items;

    public ChatAdapter(List<ChatMsg> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new RightViewHolder(ItemRightChatBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
        return new LeftViewHolder(ItemLeftChatBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LeftViewHolder) {
            ((LeftViewHolder) holder).mBinding.setItem(items.get(position));
        } else {
            ((RightViewHolder) holder).mBinding.setItem(items.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected static class LeftViewHolder extends RecyclerView.ViewHolder {
        private final ItemLeftChatBinding mBinding;

        public LeftViewHolder(@NonNull ItemLeftChatBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }
    }

    protected static class RightViewHolder extends RecyclerView.ViewHolder {
        private final ItemRightChatBinding mBinding;

        public RightViewHolder(@NonNull ItemRightChatBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }
    }
}
