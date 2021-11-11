package com.jjl.dxz.platform.meeting.bean.resp;

import java.util.List;

public class PollResultResp {
    private String title;
    private boolean isAnonymous;
    private int status;
    private int voterNum;
    private List<Question> questions;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getVoterNum() {
        return voterNum;
    }

    public void setVoterNum(int voterNum) {
        this.voterNum = voterNum;
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
            private int count;
            private List<Voter> voters;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public List<Voter> getVoters() {
                return voters;
            }

            public void setVoters(List<Voter> voters) {
                this.voters = voters;
            }

            private static class Voter {
                private int id;
                private String username;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getUsername() {
                    return username;
                }

                public void setUsername(String username) {
                    this.username = username;
                }
            }
        }
    }
}
