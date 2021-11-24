package com.example.o_tang;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Transaction extends RealmObject {
    @PrimaryKey
    private String uuid;

    private String userId;
    boolean isOwed, isActive;
    double amount;
    String person, contactDetails;
    Date dateCreated, dateCompleted;

    public Transaction() {
    }

    public Transaction(String uuid, String userId, boolean isOwed, boolean isActive, double amount, String person, String contactDetails, Date dateCreated, Date dateCompleted) {
        this.uuid = uuid;
        this.userId = userId;
        this.isOwed = isOwed;
        this.isActive = isActive;
        this.amount = amount;
        this.person = person;
        this.contactDetails = contactDetails;
        this.dateCreated = dateCreated;
        this.dateCompleted = dateCompleted;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isOwed() {
        return isOwed;
    }

    public void setOwed(boolean owed) {
        isOwed = owed;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(Date dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "uuid='" + uuid + '\'' +
                ", userId='" + userId + '\'' +
                ", isOwed=" + isOwed +
                ", isActive=" + isActive +
                ", amount=" + amount +
                ", person='" + person + '\'' +
                ", contactDetails='" + contactDetails + '\'' +
                ", dateCreated=" + dateCreated +
                ", dateCompleted=" + dateCompleted +
                '}';
    }
}
