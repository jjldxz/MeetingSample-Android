package com.jjl.dxz.platform.meeting.bean.req;

import com.google.gson.annotations.SerializedName;
import com.jjl.dxz.platform.meeting.sdk.util.GsonUtils;

import java.util.List;

public class PollCommitReq {

    private int pollId;
    private List<Question> questions;

    public int getPollId() {
        return pollId;
    }

    public void setPollId(int pollId) {
        this.pollId = pollId;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public static class Question {
        @SerializedName("id")
        private int questionId;
        private List<Option> options;

        public int getQuestionId() {
            return questionId;
        }

        public void setQuestionId(int questionId) {
            this.questionId = questionId;
        }

        public List<Option> getOptions() {
            return options;
        }

        public void setOptions(List<Option> options) {
            this.options = options;
        }

        public static class Option {
            @SerializedName("id")
            private int optionId;

            public int getOptionId() {
                return optionId;
            }

            public void setOptionId(int optionId) {
                this.optionId = optionId;
            }
        }
    }
}
