package com.jjl.dxz.platform.meeting.ui.poll;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStoreOwner;

import com.jjl.dxz.platform.meeting.base.frame.BaseFragment;
import com.jjl.dxz.platform.meeting.databinding.FragmentPollDetailBinding;
import com.jjl.dxz.platform.meeting.vm.poll.PollMainViewModel;

public class PollDetailFragment extends BaseFragment<FragmentPollDetailBinding, PollMainViewModel> {

    @Override
    protected int getViewModelVariable() {
        return 0;
    }

    @Override
    protected LifecycleOwner getLifecycleOwner() {
        return null;
    }

    @Override
    protected ViewModelStoreOwner getViewModelStoreOwner() {
        return null;
    }

    @Override
    protected void init() {

    }
}