package com.devops.web.domain.frontend;

/**
 * Created by IBS on 01/02/2017.
 */
public class FeedbackPojo {

    private static final Long serialVersionUID = 1L;

    private String email;
    private String firsName;
    private String lastName;
    private String feedback;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirsName() {
        return firsName;
    }

    public void setFirsName(String firsName) {
        this.firsName = firsName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }


    @Override
    public String toString() {
        return "FeedbackPojo{" +
                "email='" + email + '\'' +
                ", firsName='" + firsName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
