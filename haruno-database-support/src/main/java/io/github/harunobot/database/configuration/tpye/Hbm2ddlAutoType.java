/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.database.configuration.tpye;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author iTeam_VEP
 */
public enum Hbm2ddlAutoType {
    @JsonProperty(value="validate")
    VALIDATE("validate"),   //validate the schema, makes no changes to the database.
    @JsonProperty(value="update")
    UPDATE("update"), //update the schema.
    @JsonProperty(value="create")
    CREATE("create"), //creates the schema, destroying previous data.
    @JsonProperty(value="create-drop")
    CREATE_DROP("create-drop"),    //drop the schema when the SessionFactory is closed explicitly, typically when the application is stopped.
    @JsonProperty(value="none")
    NONE("none")    //does nothing with the schema, makes no changes to the database
    ;
    
    private final String value;
    
    Hbm2ddlAutoType(String value){
        this.value = value;
    }
    
    public String value(){
        return value;
    }

}
