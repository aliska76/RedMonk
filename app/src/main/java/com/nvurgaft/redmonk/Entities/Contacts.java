package com.nvurgaft.redmonk.Entities;

/**
 * Created by Koby on 26-Jun-15.
 */
public class Contacts {

    private String user;
    private String contact_name;
    private String contact_role;
    private String first_number;
    private String second_number;
    private String third_number;

    public Contacts() {
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_role() {
        return contact_role;
    }

    public void setContact_role(String contact_role) {
        this.contact_role = contact_role;
    }

    public String getFirst_number() {
        return first_number;
    }

    public void setFirst_number(String first_number) {
        this.first_number = first_number;
    }

    public String getSecond_number() {
        return second_number;
    }

    public void setSecond_number(String second_number) {
        this.second_number = second_number;
    }

    public String getThird_number() {
        return third_number;
    }

    public void setThird_number(String third_number) {
        this.third_number = third_number;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Contacts{" +
                "contact_name='" + contact_name + '\'' +
                ", user='" + user + '\'' +
                ", contact_role='" + contact_role + '\'' +
                ", first_number='" + first_number + '\'' +
                ", second_number='" + second_number + '\'' +
                ", third_number='" + third_number + '\'' +
                '}';
    }
}
