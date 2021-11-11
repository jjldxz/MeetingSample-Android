package com.jjl.dxz.platform.meeting.widget.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jjl.dxz.platform.meeting.R;
import com.jjl.dxz.platform.meeting.adapter.MemberListAdapter;
import com.jjl.dxz.platform.meeting.databinding.DialogMemberListBinding;
import com.jjl.dxz.platform.meeting.vm.meeting.MeetingViewModel;

public class MemberListDialog extends BottomSheetDialogFragment {

    private BottomSheetBehavior<FrameLayout> behavior;
    private DialogMemberListBinding mBinding;
    private MeetingViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DialogMemberListBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(getActivity(), ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(MeetingViewModel.class);
        return mBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            FrameLayout frameLayout = getDialog().findViewById(R.id.design_bottom_sheet);
            ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            frameLayout.setLayoutParams(layoutParams);
            behavior = BottomSheetBehavior.from(frameLayout);
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                }
            });
        }

        MemberListAdapter adapter = new MemberListAdapter(mViewModel.getUserVideos());
        adapter.setOnItemClickListener(new MemberListAdapter.OnItemClickListener() {
            @Override
            public void onAudioClick(int position) {
                if (onItemClickListener != null) {
                    onItemClickListener.onAudioClick(position);
                }
            }

            @Override
            public void onVideoClick(int position) {
                if (onItemClickListener != null) {
                    onItemClickListener.onCameraClick(position);
                }
            }
        });
        mBinding.rvMember.setAdapter(adapter);
        mBinding.rvMember.setItemAnimator(null);
        mViewModel.getAddVideo().observe(getActivity(), adapter::notifyItemInserted);
        mViewModel.getRefreshVideo().observe(getActivity(), adapter::notifyItemChanged);
        mViewModel.getRemoveVideo().observe(getActivity(), adapter::notifyItemRemoved);
    }

    public interface OnItemClickListener {
        void onAudioClick(int position);

        void onCameraClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
