package com.jjl.dxz.platform.meeting.base.frame;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;


import com.jjl.dxz.platform.meeting.widget.dialog.ProgressDialogFragment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseActivity<VB extends ViewDataBinding, VM extends BaseViewModel> extends AppCompatActivity {

    protected VB mBinding;
    protected VM mViewModel;
    private final ProgressDialogFragment progressDialog = new ProgressDialogFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewBindingAndViewModel();
        if (mBinding != null) {
            setContentView(mBinding.getRoot());
            if (getViewModelVariable() != 0) {
                mBinding.setVariable(getViewModelVariable(), mViewModel);
            }
            mBinding.setLifecycleOwner(getLifecycleOwner());
        }
        mViewModel.init();
        init();

        mViewModel.getShowDialog().observe(this, isShow -> {
            if (isShow) {
                if (!progressDialog.isResumed()) {
                    progressDialog.show(getSupportFragmentManager(), "progress");
                }
            } else {
                if (progressDialog.isResumed()) {
                    progressDialog.dismiss();
                }
            }
        });
        mViewModel.getStartActivity().observe(this, clazz -> startActivity(new Intent(BaseActivity.this, clazz)));
        mViewModel.getStartActivityByBundle().observe(this, objectSparseArray -> {
            Intent intent = new Intent(BaseActivity.this, (Class<?>) objectSparseArray.get(0));
            intent.putExtras((Bundle) objectSparseArray.get(1));
            startActivity(intent);
        });
        mViewModel.getFinish().observe(this, finish -> finish());
    }

    protected abstract int getViewModelVariable();

    protected abstract LifecycleOwner getLifecycleOwner();

    protected abstract ViewModelStoreOwner getViewModelStoreOwner();

    protected abstract void init();

    @SuppressWarnings("unchecked")
    private void initViewBindingAndViewModel() {

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
                Method method = clazz.getMethod("inflate", LayoutInflater.class);
                mBinding = (VB) method.invoke(null, getLayoutInflater());
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }

            Class<VM> vmClass = (Class<VM>) types[1];
            mViewModel = new ViewModelProvider(getViewModelStoreOwner(), new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(vmClass);
        }
    }
}