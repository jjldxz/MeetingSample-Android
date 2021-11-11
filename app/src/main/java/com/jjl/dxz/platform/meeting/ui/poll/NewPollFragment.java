package com.jjl.dxz.platform.meeting.ui.poll;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.jjl.dxz.platform.meeting.BR;
import com.jjl.dxz.platform.meeting.base.frame.BaseFragment;
import com.jjl.dxz.platform.meeting.databinding.FragmentNewPollBinding;
import com.jjl.dxz.platform.meeting.vm.poll.PollMainViewModel;

public class NewPollFragment extends BaseFragment<FragmentNewPollBinding, PollMainViewModel> {

    private ConcatAdapter concatAdapter;

    @Override
    protected int getViewModelVariable() {
        return BR.vm;
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
        concatAdapter = new ConcatAdapter();
        for (RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter : concatAdapter.getAdapters()) {

        }
    }
}