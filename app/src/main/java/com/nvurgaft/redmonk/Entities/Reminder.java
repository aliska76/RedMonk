package com.nvurgaft.redmonk.Entities;

/**
 * Created by Koby on 02-Jul-15.
 */
public class Reminder {

    private long reminderId;
    private long date;
    private String todo;
    private String resolved;

    public Reminder() {
    }

    public long getReminderId() {
        return reminderId;
    }

    public void setReminderId(long reminderId) {
        this.reminderId = reminderId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getResolved() {
        return resolved;
    }

    public void setResolved(String resolved) {
        this.resolved = resolved;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "reminderId=" + reminderId +
                ", date=" + date +
                ", todo='" + todo + '\'' +
                ", resolved='" + resolved + '\'' +
                '}';
    }
}
