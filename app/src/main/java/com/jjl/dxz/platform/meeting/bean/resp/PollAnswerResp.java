package com.jjl.dxz.platform.meeting.bean.resp;

import java.util.List;

public class PollAnswerResp {

    private String title;
    private List<Question> questions;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public static class Question {
        private String content;
        private boolean isSingle;
        private List<Option> options;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

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
            private String content;
            private boolean select;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public boolean isSelect() {
                return select;
            }

            public void setSelect(boolean select) {
                this.select = select;
            }
        }
    }
}
