package com.devops.backend.persistence.domain.backend;

import com.devops.backend.persistence.converters.LocalDateTimeAttributeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by ALadin Zaier PC IBS on 20/02/2017.
 */
@Entity
public class PasswordResetToken implements Serializable {

    /** The Serial Version of the Serializable classes. */
    private static final Long serialVersionUID = 1L;
    
    /** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(PasswordResetToken.class);

    private static final int DEFAULT_TOKEN_LENGTH_IN_MINUTES = 120;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String token;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime expiryDate;


    public PasswordResetToken() {
    }

    public PasswordResetToken(String token, User user, LocalDateTime expiryDate,int expirationInMinutes) {

        if (token == null || user ==null || expiryDate == null) {
        throw new IllegalArgumentException("User, token and expiry date cannot be null.");
        }

        if (expirationInMinutes==0) {
            LOG.warn("expirationInMinutes is zero. Assigning the default value.");
            expirationInMinutes = DEFAULT_TOKEN_LENGTH_IN_MINUTES;
        }

        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate.plusMinutes(expirationInMinutes);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PasswordResetToken that = (PasswordResetToken) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }


    @Override
    public String toString() {
        return "PasswordResetToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", user=" + user +
                '}';
    }
}
