package org.dspace.authenticate;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jonas - jonas@atmire.com on 15/12/15.
 */
@Entity
@Table(name="persistent_logins")
public class PersistentLogin {

    @Column(name = "username",nullable = false)
    private String username;

    @Id
    @Column(name = "series",nullable = false, unique = true)
    private String series;

    @Column(name = "token",nullable = false)
    private String token;

    @Column(name = "last_used",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUsed;

    protected PersistentLogin(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Date endDate) {
        this.lastUsed = endDate;
    }
}
