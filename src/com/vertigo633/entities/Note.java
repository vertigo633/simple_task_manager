package com.vertigo633.entities;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by Vertigo633 on 11.06.2015.
 */
@XStreamAlias("note")
public class Note {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    @XStreamAlias("name")
    private String name;
    @XStreamAlias("e_mail")
    private String e_mail;
    @XStreamAlias("phone_number")
    private String phone_number;
}
