/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.plugin.onebot;

import io.github.harunobot.plugin.HarunoPlugin;
import io.github.harunobot.plugin.data.PluginDetail;
import io.github.harunobot.plugin.data.PluginRegistration;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 *
 * @author iTeam_VEP
 */
public abstract class PluginLoader {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(PluginLoader.class);
    private final Map<String, PluginDetail> pluginDetails = new HashMap();
    private final Map<String, URLClassLoader> loaders = new HashMap();
    final Map<String, HarunoPlugin> plugins = new HashMap();
    
    abstract void initPlugin(HarunoPlugin plugin);
    abstract void manageLoad(HarunoPlugin plugin, PluginRegistration registration);
    abstract void manageUnload(HarunoPlugin plugin, PluginRegistration registration);
    abstract void loadCompleted();
    abstract void unloadCompleted();
    
    void load(String path, File jarFile) {
        MDC.put("module", "PluginLoader");
        URLClassLoader classLoader = null;
//        String id = newIdentifier();
        ClassLoader originClassLoader = Thread.currentThread().getContextClassLoader();
        try(InputStream inputStream = new FileInputStream(jarFile);JarInputStream jarStream = new JarInputStream(inputStream)) {
            String classPath = jarStream.getManifest().getMainAttributes().getValue("Haruno-Plugin-Class").trim();
            if(classPath == null){
                LOG.warn("jar file {} isn't a haruno plugin", jarFile.getAbsoluteFile());
                return;
            }
            classLoader = new URLClassLoader(new URL[] { jarFile.toURI().toURL()});
            Thread.currentThread().setContextClassLoader(classLoader);  
            Class classToLoad = Class.forName(classPath, true, classLoader);
            Constructor constructor  = classToLoad.getConstructor();
            HarunoPlugin plugin =  (HarunoPlugin) constructor.newInstance();
            if(pluginDetails.containsKey(plugin.id())) {
                LOG.warn("plugin id duplicated {} {} {}"
                        , plugin.description().id()
                        , jarFile.getAbsolutePath()
                        , pluginDetails.get(plugin.id()).getPath());
                return;
//                throw new Exception();
            }
            String id = plugin.id();
            initPlugin(plugin);
            try{
                synchronized(this){
                    PluginRegistration registration = plugin.onLoad(path);
                    manageLoad(plugin, registration);
                    MDC.put("module", "PluginLoader");
                    pluginDetails.put(id, new PluginDetail(id, jarFile.getAbsolutePath(), registration));
                    loaders.put(id, classLoader);
                    plugins.put(id, plugin);
                }
                LOG.info("plugin {} {} loaded", plugin.description().id(), plugin.description().version());
            } catch (Exception ex){
                MDC.put("module", "PluginLoader");
                LOG.error("plugin {} {} load failed", plugin.description().id(), plugin.description().version(), ex);
            }
        } catch (IOException ex) {
            MDC.put("module", "PluginLoader");
            LOG.error("", ex);
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException ex) {
            if(classLoader != null){
                try {
                    classLoader.close();
                } catch (IOException ex1) {
                    LOG.error("", ex1);
                }
            }
            MDC.put("module", "PluginLoader");
            LOG.error("", ex);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            if(classLoader != null){
                try {
                    classLoader.close();
                } catch (IOException ex1) {
                    LOG.error("", ex1);
                }
            }
            MDC.put("module", "PluginLoader");
            LOG.error("", ex);
        } finally {
            Thread.currentThread().setContextClassLoader(originClassLoader);  
        }
        MDC.clear();
    }
    
    void unload(String id){
        synchronized(this){
            MDC.put("module", "PluginLoader");
            ClassLoader originClassLoader = Thread.currentThread().getContextClassLoader();
            try (HarunoPlugin plugin = plugins.get(id); URLClassLoader loader = loaders.get(id);) {
                Thread.currentThread().setContextClassLoader(loader);  
                try{
                    manageUnload(plugin, pluginDetails.get(id).getRegistration());
                    if(plugin.onUnload()){
                        LOG.info("plugin {} {} unloaded", plugin.description().id(), plugin.description().version());
                    } else {
                        LOG.info("plugin {} {} unload failed", plugin.description().id(), plugin.description().version());
                    }
                } catch(Exception ex){
                    LOG.error("plugin {} execute unload failed", plugin.id(), ex);
                }
                pluginDetails.remove(id);
                plugins.remove(id);
                loaders.remove(id);
                
                Thread.currentThread().setContextClassLoader(originClassLoader);  
                return;
            } catch (IOException ex) {
                MDC.put("module", "PluginLoader");
                LOG.error("unload failed", ex);
            } catch (Exception ex) {
                MDC.put("module", "PluginLoader");
                LOG.error("unload failed", ex);
            }
            Thread.currentThread().setContextClassLoader(originClassLoader);  
        }
    }
    
    public void loadPlugins(File rootFolder){
        File[] files = rootFolder.listFiles();
        for(File file:files){
            if(file.isDirectory()){
                loadPlugins(file);
            }else if(file.getName().endsWith(".jar")){
                load(rootFolder.getAbsolutePath(), file);
            }
        }
    }
    
    public void activePlugins(){
        MDC.put("module", "PluginLoader");
        loadCompleted();
        List<String> waitForUnload = new ArrayList();
        plugins.forEach((id, plugin) -> {
            boolean activated = plugin.onEnable();
            if(!activated){
                waitForUnload.add(id);
                LOG.info("plugin {} {} failed to active", plugin.description().id(), plugin.description().version());
            } else {
                LOG.info("plugin {} {} actived", plugin.description().id(), plugin.description().version());
            }
        });
        waitForUnload.forEach(id -> {
            unload(id);
        });
        MDC.clear();
    }
    
    public void unloadPlugins(){
        new ArrayList<>(plugins.keySet()).forEach(id -> {
            unload(id);
        });
        unloadCompleted();
    }
    
}
