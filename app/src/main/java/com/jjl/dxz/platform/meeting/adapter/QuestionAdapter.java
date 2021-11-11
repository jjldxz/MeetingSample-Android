package com.jjl.dxz.platform.meeting.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jjl.dxz.platform.meeting.R;
import com.jjl.dxz.platform.meeting.bean.pojo.Question;
import com.jjl.dxz.platform.meeting.databinding.ItemQuestionFootBinding;
import com.jjl.dxz.platform.meeting.databinding.ItemQuestionHeadBinding;
import com.jjl.dxz.platform.meeting.databinding.ItemQuestionOptionBinding;
import com.jjl.dxz.platform.meeting.util.ToastUtils;

public class QuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Question question;

    public QuestionAdapter(Question question) {
        this.question = question;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new HeadViewHolder(ItemQuestionHeadBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            case 2:
                return new FootViewHolder(ItemQuestionFootBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            case 1:
            default:
                return new OptionViewHolder(ItemQuestionOptionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadViewHolder) {
            ((HeadViewHolder) holder).bind(question);
        } else if (holder instanceof OptionViewHolder) {
            ((OptionViewHolder) holder).bind(question, position - 1);
        } else {
            ((FootViewHolder) holder).bind(question);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else if (position == question.getOptions().size() + 1) {
            return 2;
        }
        return 1;
    }

    @Override
    public int getItemCount() {
        return question.getOptions().size() + 2;
    }

    protected static class HeadViewHolder extends RecyclerView.ViewHolder {
        private final ItemQuestionHeadBinding mBinding;

        public HeadViewHolder(@NonNull ItemQuestionHeadBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }

        public void bind(Question question) {
            mBinding.setQuestion(question);
            mBinding.groupType.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
                if (isChecked) {
                    question.setSingle(checkedId == R.id.btn_single);
                }
            });
        }
    }

    protected class OptionViewHolder extends RecyclerView.ViewHolder {
        private final ItemQuestionOptionBinding mBinding;

        public OptionViewHolder(@NonNull ItemQuestionOptionBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }

        public void bind(Question question, int position) {
            mBinding.setOption(question.getOptions().get(position));
            mBinding.tilQuestion.addOnEndIconChangedListener((textInputLayout, previousIcon) -> {
                if (question.getOptions().size() > 2) {
                    question.getOptions().remove(position);
                    notifyItemRemoved(position + 1);
                } else {
                    ToastUtils.showShort(mBinding.getRoot().getContext(), R.string.keep_least_two_selections);
                }
            });
        }
    }

    protected class FootViewHolder extends RecyclerView.ViewHolder {
        private final ItemQuestionFootBinding mBinding;

        public FootViewHolder(@NonNull ItemQuestionFootBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }

        public void bind(Question question) {
            mBinding.tvAddOption.setOnClickListener(v -> {
                Question.Option option = new Question.Option();
                question.getOptions().add(option);
                notifyItemInserted(question.getOptions().size() + 1);
            });
        }
    }
}
