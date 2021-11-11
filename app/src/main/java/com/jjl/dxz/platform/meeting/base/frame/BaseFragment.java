package com.jjl.dxz.platform.meeting.base.frame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


public abstract class BaseFragment<VB extends ViewDataBinding, VM extends BaseViewModel> extends Fragment {

    protected VB mBinding;
    protected VM mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initViewBindingAndViewModel(container);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getViewModelVariable() != 0) {
            mBinding.setVariable(getViewModelVariable(), mViewModel);
        }
        mBinding.setLifecycleOwner(getLifecycleOwner());
        init();
        mViewModel.init();
    }

    protected abstract int getViewModelVariable();

    protected abstract LifecycleOwner getLifecycleOwner();

    protected abstract ViewModelStoreOwner getViewModelStoreOwner();

    protected abstract void init();

    @SuppressWarnings("unchecked")
    private void initViewBindingAndViewModel(ViewGroup container) {

        Type type = getClass().getGenericSuperclass();
        if (type == null) {
            return;
        }
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            if (types.length == 0) {
                return;
            }
            Class<VB> clazz = (Class<VB>) types[0];
            try {
                Method method = clazz.getMethod("inflate", LayoutInflater.class, ViewGroup.class, Boolean.class);
                mBinding = (VB) method.invoke(null, getLayoutInflater(), container, false);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }

            Class<VM> vmClass = (Class<VM>) types[1];
            mViewModel = new ViewModelProvider(getViewModelStoreOwner(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(vmClass);
        }
    }
}