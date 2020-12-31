/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.constant;

import io.github.harunobot.pojo.type.Permission;
import java.util.Collections;
import java.util.Set;

/**
 *
 * @author iTeam_VEP
 */
public class Permissions {
    
    public static final Set<Permission> PUBLIC_MESSAGE
            = Collections.unmodifiableSet(Set.of(
                    Permission.RECEIVE_PUBLIC_MESSAGE,
                    Permission.TRANSMIT_PUBLIC_MESSAGE
            ));
    
    public static final Set<Permission> PRIVATE_MESSAGE 
            = Collections.unmodifiableSet(Set.of(
                    Permission.RECEIVE_PRIVATE_MESSAGE,
                    Permission.TRANSMIT_PRIVATE_MESSAGE
            ));
    
    public static final Set<Permission> PUBLIC_MEMBER_CHANGED_NOTICE
            = Collections.unmodifiableSet(Set.of(
                    Permission.PUBLIC_DECREASE_NOTICE,
                    Permission.PUBLIC_INCREASE_NOTICE
            ));
    
    public static final Set<Permission> RECEIVE_SOCIAL_REQUEST 
            = Collections.unmodifiableSet(Set.of(
                    Permission.RECEIVE_PRIVATE_REQUEST,
                    Permission.RECEIVE_PUBLIC_REQUEST
            ));
    
    public static final Set<Permission> HANDLE_SOCIAL_REQUEST 
            = Collections.unmodifiableSet(Set.of(
                    Permission.RECEIVE_PRIVATE_REQUEST,
                    Permission.RECEIVE_PUBLIC_REQUEST,
                    Permission.TRANSMIT_PRIVATE_REQUEST,
                    Permission.TRANSMIT_PUBLIC_REQUEST
            ));
    
    public static final Set<Permission> ADMIN_PERMISSIONS 
            = Collections.unmodifiableSet(Set.of(
                    Permission.MUTE,
                    Permission.TRANSMIT_PUBLIC_REQUEST
                    
            ));
    
    public static final Set<Permission> SUPER_ADMIN_PERMISSIONS 
            = Collections.unmodifiableSet(Set.of(
                    
            ));
    
    
    public static final Set<Permission> SYSTEM_PERMISSIONS 
            = Collections.unmodifiableSet(Set.of(
                    
            ));
}
