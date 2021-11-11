package com.jjl.dxz.platform.meeting.widget.dialog;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.jjl.dxz.platform.meeting.R;
import com.jjl.dxz.platform.meeting.adapter.ChatAdapter;
import com.jjl.dxz.platform.meeting.bean.pojo.UserVideo;
import com.jjl.dxz.platform.meeting.databinding.DialogChatBinding;
import com.jjl.dxz.platform.meeting.vm.meeting.MeetingViewModel;

import java.util.ArrayList;
import java.util.List;

public class ChatDialogFragment extends BottomSheetDialogFragment {

    private BottomSheetBehavior<FrameLayout> behavior;
    private DialogChatBinding mBinding;
    private MeetingViewModel mViewModel;
    private int userId = -1;
    private int choiceIndex = 0;
    private final MutableLiveData<String> msg = new MutableLiveData<>("");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DialogChatBinding.inflate(inflater, container, false);
        mBinding.setLifecycleOwner(getActivity());
        mBinding.setMsg(msg);
        mBinding.setEvent(new Event());
        mViewModel = new ViewModelProvider(getActivity(), ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(MeetingViewModel.class);
        return mBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        mBinding.tvSendTarget.setText(getString(R.string.send_target, getString(R.string.everyone)));
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

        ChatAdapter adapter = new ChatAdapter(mViewModel.getChatMsg());
        mBinding.rvChat.setAdapter(adapter);
        mViewModel.getAddChat().observe(this, adapter::notifyItemInserted);
    }

    public class Event {
        public void onClickTarget() {
            List<String> username = new ArrayList<>();
            for (UserVideo userVideo : mViewModel.getUserVideos()) {
                username.add(userVideo.getUserName());
            }
            username.add(getString(R.string.everyone));
            new AlertDialog.Builder(getContext()).setSingleChoiceItems(username.toArray(new String[]{}), choiceIndex, (dialog, which) -> {
                choiceIndex = which;
                if (choiceIndex == username.size() - 1) {
                    userId = -1;
                    mBinding.tvSendTarget.setText(getString(R.string.send_target, getString(R.string.everyone)));
                } else {
                    for (int i = 0; i < mViewModel.getUserVideos().size(); i++) {
                        if (i == which) {
                            userId = mViewModel.getUserVideos().get(i).getUserId();
                            mBinding.tvSendTarget.setText(getString(R.string.send_target, mViewModel.getUserVideos().get(i).getUserName()));
                            break;
                        }
                    }
                }
            }).setPositiveButton(R.string.confirm, null).show();
        }

        public void onSendMsg() {
            if (msg.getValue() != null && !TextUtils.isEmpty(msg.getValue().trim()) && onSendListener != null) {
                onSendListener.onSend(userId, msg.getValue());
                msg.setValue("");
            }
        }
    }

    public interface OnSendListener {
        void onSend(int userId, String msg);
    }

    private OnSendListener onSendListener;

    public void setOnSendListener(OnSendListener onSendListener) {
        this.onSendListener = onSendListener;
    }
}
