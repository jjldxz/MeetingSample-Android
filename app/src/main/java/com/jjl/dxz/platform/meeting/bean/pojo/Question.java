package com.jjl.dxz.platform.meeting.bean.pojo;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class Question {
    public MutableLiveData<String> question = new MutableLiveData<>("");
    private boolean isSingle;
    private List<Option> options;

    public boolean isSingle() {
        return isSingle;
    }

    public void setSingle(boolean single) {
        isSingle = single;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public static class Option {
        public MutableLiveData<String> choice = new MutableLiveData<>("");
    }
}
