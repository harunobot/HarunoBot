/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.database.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.harunobot.database.configuration.tpye.Hbm2ddlAutoType;

/**
 *
 * @author iTeam_VEP
 */
public class DatabaseConfiguration {
    private String driver;
    private String url;
    private String username;
    private String password;
    private String dialect;
    @JsonProperty(value="hbm2ddl-auto")
    private Hbm2ddlAutoType hbm2ddlAuto;

    /**
     * @return the driver
     */
    public String getDriver() {
        return driver;
    }

    /**
     * @param driver the driver to set
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the dialect
     */
    public String getDialect() {
        return dialect;
    }

    /**
     * @param dialect the dialect to set
     */
    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    /**
     * @return the hbm2ddlAuto
     */
    public Hbm2ddlAutoType getHbm2ddlAuto() {
        return hbm2ddlAuto;
    }

    /**
     * @param hbm2ddlAuto the hbm2ddlAuto to set
     */
    public void setHbm2ddlAuto(Hbm2ddlAutoType hbm2ddlAuto) {
        this.hbm2ddlAuto = hbm2ddlAuto;
    }

}
