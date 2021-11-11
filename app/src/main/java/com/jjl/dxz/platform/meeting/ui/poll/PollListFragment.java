package com.jjl.dxz.platform.meeting.ui.poll;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStoreOwner;

import com.jjl.dxz.platform.meeting.adapter.PollListAdapter;
import com.jjl.dxz.platform.meeting.base.frame.BaseFragment;
import com.jjl.dxz.platform.meeting.constant.BundleKey;
import com.jjl.dxz.platform.meeting.databinding.FragmentPollListBinding;
import com.jjl.dxz.platform.meeting.vm.poll.PollMainViewModel;

public class PollListFragment extends BaseFragment<FragmentPollListBinding, PollMainViewModel> {

    @Override
    protected int getViewModelVariable() {
        return 0;
    }

    @Override
    protected LifecycleOwner getLifecycleOwner() {
        return requireActivity();
    }

    @Override
    protected ViewModelStoreOwner getViewModelStoreOwner() {
        return requireActivity();
    }

    @Override
    protected void init() {
        PollListAdapter adapter = new PollListAdapter();
        mBinding.rv.setAdapter(adapter);
        mViewModel.getPolls().observe(getViewLifecycleOwner(), adapter::submit);

        mBinding.btnNew.setOnClickListener(v -> ((PollMainActivity) requireActivity()).gotoNewPoll());
        mViewModel.getPollList(requireActivity().getIntent().getIntExtra(BundleKey.MEETING_NUMBER, -1));
    }
}