package com.jjl.dxz.platform.meeting.bean.resp;

import java.util.List;

public class GroupDetailResp {

    private List<GroupInfo> group;

    public List<GroupInfo> getGroup() {
        return group;
    }

    public void setGroup(List<GroupInfo> group) {
        this.group = group;
    }

    public static class GroupInfo {
        private int id;
        private String name;
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
