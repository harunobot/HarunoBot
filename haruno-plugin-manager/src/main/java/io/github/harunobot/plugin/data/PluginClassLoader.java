/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.plugin.data;

import java.net.URLClassLoader;

/**
 *
 * @author iTeam_VEP
 */
public class PluginClassLoader {
    private URLClassLoader classLoader;
    private Thread thread;

    /**
     * @return the classLoader
     */
    public URLClassLoader getClassLoader() {
        return classLoader;
    }

    /**
     * @param classLoader the classLoader to set
     */
    public void setClassLoader(URLClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /**
     * @return the thread
     */
    public Thread getThread() {
        return thread;
    }

    /**
     * @param thread the thread to set
     */
    public void setThread(Thread thread) {
        this.thread = thread;
    }
}
