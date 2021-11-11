package com.jjl.dxz.platform.meeting.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStoreOwner;

import com.jjl.dxz.platform.meeting.BR;
import com.jjl.dxz.platform.meeting.R;
import com.jjl.dxz.platform.meeting.adapter.VideoAdapter;
import com.jjl.dxz.platform.meeting.base.frame.BaseActivity;
import com.jjl.dxz.platform.meeting.constant.BundleKey;
import com.jjl.dxz.platform.meeting.databinding.ActivityMeetingBinding;
import com.jjl.dxz.platform.meeting.ui.poll.PollListFragment;
import com.jjl.dxz.platform.meeting.util.ToastUtils;
import com.jjl.dxz.platform.meeting.vm.meeting.MeetingViewModel;
import com.jjl.dxz.platform.meeting.widget.dialog.ChatDialogFragment;
import com.jjl.dxz.platform.meeting.widget.dialog.MemberListDialog;

import java.util.ArrayList;
import java.util.List;

public class MeetingActivity extends BaseActivity<ActivityMeetingBinding, MeetingViewModel> {

    private ChatDialogFragment chatDialogFragment;

    @Override
    protected int getViewModelVariable() {
        return BR.vm;
    }

    @Override
    protected LifecycleOwner getLifecycleOwner() {
        return this;
    }

    @Override
    protected ViewModelStoreOwner getViewModelStoreOwner() {
        return this;
    }

    @Override
    protected void init() {
        mBinding.setEvent(new Event());

        boolean isAdmin = getIntent().getExtras().getBoolean(BundleKey.IS_OWNER);

        chatDialogFragment = new ChatDialogFragment();
        chatDialogFragment.setOnSendListener(mViewModel::chat);

        mBinding.mt.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_soundMode) {
                mViewModel.setSoundOutputRoute(mBinding.mt.getMenu().getItem(0));
                return true;
            }
            if (item.getItemId() == R.id.menu_switch) {
                mViewModel.switchCamera();
                return true;
            }
            if (item.getItemId() == R.id.menu_poll) {
                if (isAdmin) {
                    ToastUtils.showShort(MeetingActivity.this, R.string.only_host);
                    return false;
                }
                Intent intent = new Intent(MeetingActivity.this, PollListFragment.class);
                intent.putExtra(BundleKey.MEETING_NUMBER, getIntent().getIntExtra(BundleKey.MEETING_NUMBER, -1));
                startActivity(intent);
                return true;
            }
            if (item.getItemId() == R.id.menu_group) {
                if (isAdmin) {
                    ToastUtils.showShort(MeetingActivity.this, R.string.only_host);
                    return false;
                }
                Intent intent = new Intent(MeetingActivity.this, GroupDetailActivity.class);
                intent.putExtra(BundleKey.MEETING_NUMBER, getIntent().getIntExtra(BundleKey.MEETING_NUMBER, -1));
                startActivity(intent);
                return true;
            }
            if (item.getItemId() == R.id.menu_chat) {
                chatDialogFragment.show(getSupportFragmentManager(), "chat");
                return true;
            }
            return false;
        });

        VideoAdapter videoAdapter = new VideoAdapter(mViewModel.getUserVideos());
        videoAdapter.setOnItemClickListener(new VideoAdapter.OnItemClickListener() {
            @Override
            public void onMicClick(int position) {
                mViewModel.changeMicState(position);
            }

            @Override
            public void onCameraClick(int position) {
                mViewModel.changeCameraState(position);
            }
        });
        mBinding.rvVideo.setAdapter(videoAdapter);
        mBinding.rvVideo.setItemAnimator(null);
        mViewModel.getAddVideo().observe(this, videoAdapter::notifyItemInserted);
        mViewModel.getRefreshVideo().observe(this, videoAdapter::notifyItemChanged);
        mViewModel.getRemoveVideo().observe(this, videoAdapter::notifyItemRemoved);

        mBinding.wb.setOnCloseListener(mViewModel::shareWhiteboard);
        mBinding.btnStopShare.setOnClickListener(v -> mViewModel.shareScreen());

        mViewModel.getNotifyUserVideo().observe(this, refresh -> videoAdapter.notifyDataSetChanged());

        mViewModel.getMyMenuMic().observe(this, isOpen -> mBinding.ibAudio.setImageResource(isOpen ? R.drawable.ic_mic_off : R.drawable.ic_mic_on));
        mViewModel.getMyMenuCamera().observe(this, isOpen -> mBinding.ibVideo.setImageResource(isOpen ? R.drawable.ic_videocam_off : R.drawable.ic_videocam_on));

        mViewModel.getShareScreen().observe(this, shareScreen -> {
            if (mBinding.flRemoteShare.getChildCount() > 0) {
                mBinding.flRemoteShare.removeAllViews();
            }

            mBinding.flRemoteShare.addView(shareScreen, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        });

        checkPermission();
    }

    public class Event {
        public void share() {
            new AlertDialog.Builder(MeetingActivity.this)
                    .setMessage("请选择共享类型")
                    .setNeutralButton(R.string.cancel, null)
                    .setNegativeButton(R.string.screen, (dialog, which) -> mViewModel.shareScreen())
                    .setPositiveButton(R.string.whiteboard, (dialog, which) -> mViewModel.shareWhiteboard())
                    .show();
        }

        public void openMemberList() {
            MemberListDialog memberListDialog = new MemberListDialog();
            memberListDialog.setOnItemClickListener(new MemberListDialog.OnItemClickListener() {
                @Override
                public void onAudioClick(int position) {
                    mViewModel.changeMicState(position);
                }

                @Override
                public void onCameraClick(int position) {
                    mViewModel.changeCameraState(position);
                }
            });
            memberListDialog.show(getSupportFragmentManager(), "memberList");
        }
    }

    private void checkPermission() {
        List<String> list = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            list.add(Manifest.permission.CAMERA);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            list.add(Manifest.permission.RECORD_AUDIO);
        }
        if (list.size() > 0) {
            ActivityCompat.requestPermissions(this, list.toArray(new String[0]), 1000);
            return;
        }
        mViewModel.init(getIntent().getExtras());
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setMessage("请选择退出方式").setPositiveButton(R.string.close_room, (dialog, which) -> mViewModel.closeRoom()).setNegativeButton(R.string.leave_room, (dialog, which) -> finish()).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mViewModel.init(getIntent().getExtras());
    }
}