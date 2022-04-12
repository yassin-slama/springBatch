package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="student")
@Entity
public class StudentDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    private String emailAddress;
    private String name;
    private String purchasedPackage;

    public StudentDTO() {}

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getName() {
        return name;
    }

    public String getPurchasedPackage() {
        return purchasedPackage;
    }

    public long getId() {
        return  id;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPurchasedPackage(String purchasedPackage) {
        this.purchasedPackage = purchasedPackage;
    }

    @Override
    public String toString() {
        return "user:  "+"name: "+getName()+"  ,"+
                "emailAddress: "+getEmailAddress()+"  ,"+
                "purchasedPackage:  "+getPurchasedPackage() + " ," +
                "id:" + getId();
    }
}