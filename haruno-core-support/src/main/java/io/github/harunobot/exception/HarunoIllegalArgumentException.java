/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.exception;

/**
 *
 * @author iTeam_VEP
 */
public class HarunoIllegalArgumentException extends IllegalArgumentException {
    
    public HarunoIllegalArgumentException(){
        super();
    }
    
    public HarunoIllegalArgumentException(String msg){
        super(msg);
    }
    
}
