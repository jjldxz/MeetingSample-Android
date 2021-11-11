package com.jjl.dxz.platform.meeting.bean.req;

import java.util.List;

public class GroupStopReq {
    /**
     * 会议号
     */
    private int number;
    /**
     * 小组集合
     */
    private List<GroupStartReq.GroupInfo> group;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<GroupStartReq.GroupInfo> getGroup() {
        return group;
    }

    public void setGroup(List<GroupStartReq.GroupInfo> group) {
        this.group = group;
    }

    public static class GroupInfo {
        /**
         * 小组id
         */
        private int id;
        /**
         * 小组名称
         */
        private String name;
        /**
         * 组员id
         */
        private List<Integer> users;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Integer> getUsers() {
            return users;
        }

        public void setUsers(List<Integer> users) {
            this.users = users;
        }
    }
}
