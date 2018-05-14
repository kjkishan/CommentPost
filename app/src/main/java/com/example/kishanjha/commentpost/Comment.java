package com.example.kishanjha.commentpost;

public class Comment {

    String name,comments,times,id,userID;

    public Comment() {

    }

    public Comment(String name , String comments , String times,String userID) {
        this.name = name;
        this.comments = comments;
        this.times = times;
        this.userID = userID;
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }
}