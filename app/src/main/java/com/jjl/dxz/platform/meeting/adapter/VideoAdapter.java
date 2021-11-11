package com.jjl.dxz.platform.meeting.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jjl.dxz.platform.meeting.bean.pojo.UserVideo;
import com.jjl.dxz.platform.meeting.databinding.ItemMeetingVideoBinding;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private final List<UserVideo> items;

    public VideoAdapter(List<UserVideo> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemMeetingVideoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserVideo userVideo = items.get(position);
        holder.mBinding.setItem(userVideo);
        holder.mBinding.executePendingBindings();

        if (userVideo.getSurfaceView().getParent() != null) {
            ((ViewGroup) userVideo.getSurfaceView().getParent()).removeAllViews();
        }

//        if (holder.mBinding.flVideoContainer.getChildCount() > 0) {
//            holder.mBinding.flVideoContainer.removeAllViews();
//        }
        holder.mBinding.flVideoContainer.addView(userVideo.getSurfaceView(), FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        holder.mBinding.ivMic.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onMicClick(position);
            }
        });
        holder.mBinding.ivCamera.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onCameraClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemMeetingVideoBinding mBinding;

        public ViewHolder(@NonNull ItemMeetingVideoBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }

    public interface OnItemClickListener {
        void onMicClick(int position);

        void onCameraClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
