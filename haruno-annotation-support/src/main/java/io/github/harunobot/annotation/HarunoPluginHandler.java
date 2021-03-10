/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.annotation;

import io.github.harunobot.plugin.data.type.PluginMatcherType;
import io.github.harunobot.plugin.data.type.PluginReceivedType;
import io.github.harunobot.plugin.data.type.PluginTextType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author iTeam_VEP
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HarunoPluginHandler {
    PluginTextType[] textType() default {};
    PluginMatcherType matcherType();
    PluginReceivedType receivedType();
    String trait();
    String[] splitRegex() default {};
}
