/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.plugin.onebot;

import io.github.harunobot.annotation.HarunoPluginAnnotation;
import io.github.harunobot.annotation.HarunoPluginFilter;
import io.github.harunobot.annotation.HarunoPluginHandler;
import io.github.harunobot.annotation.HarunoPluginInteractiveHandler;
import io.github.harunobot.plugin.HarunoPlugin;
import io.github.harunobot.plugin.PluginFilter;
import io.github.harunobot.plugin.PluginHandler;
import io.github.harunobot.plugin.data.PluginDetail;
import io.github.harunobot.plugin.data.PluginFilterParameter;
import io.github.harunobot.plugin.data.PluginHandlerMatcher;
import io.github.harunobot.plugin.data.PluginRegistration;
import io.github.harunobot.plugin.data.type.PluginMatcherType;
import io.github.harunobot.proto.event.BotEvent;
import io.github.harunobot.proto.request.BotRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.LambdaConversionException;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
            classLoader = new URLClassLoader(new URL[] { jarFile.toURI().toURL()}){
                {
                    byte[] code = gimmeLookupClassDef();
                    defineClass("GimmeLookup", code, 0, code.length); 
                }
            };
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
                    PluginRegistration registration = null;
                    if (classToLoad.isAnnotationPresent(HarunoPluginAnnotation.class)) {
                        MethodHandles.Lookup lookup = (MethodHandles.Lookup)
                        classLoader.loadClass("GimmeLookup").getField("lookup").get(null);
                        try{
                            registration = generatePluginRegistration(classToLoad, plugin, lookup);
                        }catch(Throwable ex){
                            if(ex instanceof AbstractMethodError){
                                LOG.error("Plugin {} annotation information mismatch.", plugin.description().id(), ex);
                            } else if(ex instanceof IllegalArgumentException){
                                LOG.error("Plugin {} load failed.", plugin.description().id());
                            } else {
                                LOG.error("Plugin {} annotation configuration failed.", plugin.description().id(), ex);
                            }
                            try {
                                classLoader.close();
                            } catch (IOException ex1) {
                                LOG.error("", ex1);
                            }
                            return;
                        }
                        if(plugin.onLoad(path) != null){
                            LOG.warn("Plugin {} registration information conflicted. Use annotation configuration. Plugin path {}"
                                , plugin.description().id()
                                , jarFile.getAbsolutePath());
                        }
                    } else {
                        registration = plugin.onLoad(path);
                    }
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
    
    PluginRegistration generatePluginRegistration(Class clazz, HarunoPlugin plugin, MethodHandles.Lookup lookup) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, NoSuchMethodException, AbstractMethodError, LambdaConversionException, Throwable {
        HarunoPluginAnnotation pluginAnnotation = (HarunoPluginAnnotation) clazz.getAnnotation(HarunoPluginAnnotation.class);
        if(pluginAnnotation.permissions().length <= 0 ){
            LOG.warn("Plugin {} permissions not set", plugin.id());
            throw new IllegalArgumentException ();
        }
//        MethodHandles.Lookup lookup = MethodHandles.lookup().in(clazz);
        MethodType pluginFilterType = MethodType.methodType(boolean.class, BotEvent.class);
        MethodType pluginHandlerType = MethodType.methodType(BotRequest.class, String.class, BotEvent.class);
        
        Map<PluginFilterParameter, PluginFilter> filters = new HashMap();
        Map<PluginHandlerMatcher, PluginHandler> handlers = new HashMap();
        PluginHandler interactiveHandler = null;
        for (Method method : clazz.getDeclaredMethods()) {
            HarunoPluginFilter filter = method.getAnnotation(HarunoPluginFilter.class);
            if(filter != null){
                method.setAccessible(true);

                MethodHandle mh = lookup.findVirtual(clazz, method.getName(), pluginFilterType);
                PluginFilter lambda = (PluginFilter) LambdaMetafactory.metafactory(
                        lookup, "toNext", MethodType.methodType(PluginFilter.class, clazz),
                        pluginFilterType, mh, pluginFilterType).getTarget().invoke(plugin);
                filters.put(new PluginFilterParameter(
                        filter.receivedType(),
                        filter.name(),
                        filter.priority()
                ), lambda);
                continue;
            }
            
            HarunoPluginInteractiveHandler interactiveHandlerAnnotation = method.getAnnotation(HarunoPluginInteractiveHandler.class);
            if(interactiveHandlerAnnotation != null){
                if(interactiveHandler != null){
                    LOG.warn("Plugin {} has multiple interactive handler", plugin.id());
                    throw new IllegalArgumentException ();
                }
                method.setAccessible(true);
                MethodHandle mh = lookup.findVirtual(clazz, method.getName(), pluginHandlerType);
                interactiveHandler = (PluginHandler) LambdaMetafactory.metafactory(
                        lookup, "handle", MethodType.methodType(PluginHandler.class, clazz),
                        pluginHandlerType, mh, pluginHandlerType).getTarget().invoke(plugin);
                continue;
            }
            
            HarunoPluginHandler handler = method.getAnnotation(HarunoPluginHandler.class);
            if(handler != null){
                method.setAccessible(true);
                PluginHandlerMatcher handlerMatcher;
                if(handler.matcherType() == PluginMatcherType.COMMAND){
                    String splitRegex;
                    if(handler.splitRegex().length == 0){
                        splitRegex = null;
                    } else if(handler.splitRegex().length == 1) {
                        splitRegex = handler.splitRegex()[0];
                    } else {
                        LOG.warn("Plugin {} handler(method) {} has multiple splitRegex", plugin.id(), method.getName());
                        throw new IllegalArgumentException ();
                    }
                    handlerMatcher = new PluginHandlerMatcher(
                            handler.receivedType(), 
                            handler.trait(), 
                            splitRegex
                    );
                } else {
                    if(handler.textType().length == 0){
                        LOG.warn("Plugin {} handler {}(method) text type not set", plugin.id(), method.getName());
                        throw new IllegalArgumentException ();
                    } else if(handler.textType().length > 1){
                        LOG.warn("Plugin {} handler {}(method) has multiple text type", plugin.id(), method.getName());
                        throw new IllegalArgumentException ();
                    }
                    handlerMatcher = new PluginHandlerMatcher(
                            handler.receivedType(), 
                            handler.matcherType(),
                            handler.textType()[0],
                            handler.trait()
                    );
                }
                MethodHandle mh = lookup.findVirtual(clazz, method.getName(), pluginHandlerType);
                PluginHandler pluginHandler = (PluginHandler) LambdaMetafactory.metafactory(
                        lookup, "handle", MethodType.methodType(PluginHandler.class, clazz),
                        pluginHandlerType, mh, pluginHandlerType).getTarget().invoke(plugin);
                handlers.put(handlerMatcher, pluginHandler);
                continue;
            }
        }
        
        for (Field field : clazz.getDeclaredFields()) {
            HarunoPluginFilter filter = field.getAnnotation(HarunoPluginFilter.class);
            if(filter != null){
                field.setAccessible(true);
                PluginFilter pluginFilter = (PluginFilter) field.get(plugin);
                filters.put(new PluginFilterParameter(
                        filter.receivedType(),
                        filter.name(),
                        filter.priority()
                ), pluginFilter);
                continue;
            }
            
            HarunoPluginInteractiveHandler interactiveHandlerAnnotation = field.getAnnotation(HarunoPluginInteractiveHandler.class);
            if(interactiveHandlerAnnotation != null){
                if(interactiveHandler != null){
                    LOG.warn("Plugin {} has multiple interactive handler", plugin.id());
                    throw new IllegalArgumentException ();
                }
                field.setAccessible(true);
                interactiveHandler = (PluginHandler) field.get(plugin);
                continue;
            }
            
            HarunoPluginHandler handler = field.getAnnotation(HarunoPluginHandler.class);
            if(handler != null){
                field.setAccessible(true);
                PluginHandlerMatcher handlerMatcher;
                if(handler.matcherType() == PluginMatcherType.COMMAND){
                    String splitRegex;
                    if(handler.splitRegex().length == 0){
                        splitRegex = null;
                    } else if(handler.splitRegex().length == 1) {
                        splitRegex = handler.splitRegex()[0];
                    } else {
                        LOG.warn("Plugin {} handler(field) {} has multiple splitRegex", plugin.id(), field.getName());
                        throw new IllegalArgumentException ();
                    }
                    handlerMatcher = new PluginHandlerMatcher(
                            handler.receivedType(), 
                            handler.trait(), 
                            splitRegex
                    );
                } else {
                    if(handler.textType().length == 0){
                        LOG.warn("Plugin {} handler {}(field) text type not set", plugin.id(), field.getName());
                        throw new IllegalArgumentException ();
                    } else if(handler.textType().length > 1){
                        LOG.warn("Plugin {} handler {}(field) has multiple text type", plugin.id(), field.getName());
                        throw new IllegalArgumentException ();
                    }
                    handlerMatcher = new PluginHandlerMatcher(
                            handler.receivedType(), 
                            handler.matcherType(),
                            handler.textType()[0],
                            handler.trait()
                    );
                }
                PluginHandler PluginHandler = (PluginHandler) field.get(plugin);
                handlers.put(handlerMatcher, PluginHandler);
                continue;
            }
        }
        if(interactiveHandler == null){
            return new PluginRegistration(Set.of(pluginAnnotation.permissions()), handlers, filters, null);
        } else {
            return new PluginRegistration(Set.of(pluginAnnotation.permissions()), handlers, filters, interactiveHandler);
        }
    }

//    https://stackoverflow.com/questions/50787116/use-lambdametafactory-to-invoke-one-arg-method-on-class-instance-obtained-from-o
    private final static byte[] gimmeLookupClassDef() {
        return ( "\u00CA\u00FE\u00BA\u00BE\0\0\0001\0\21\1\0\13GimmeLookup\7\0\1\1\0\20"
        +"java/lang/Object\7\0\3\1\0\10<clinit>\1\0\3()V\1\0\4Code\1\0\6lookup\1\0'Ljav"
        +"a/lang/invoke/MethodHandles$Lookup;\14\0\10\0\11\11\0\2\0\12\1\0)()Ljava/lang"
        +"/invoke/MethodHandles$Lookup;\1\0\36java/lang/invoke/MethodHandles\7\0\15\14\0"
        +"\10\0\14\12\0\16\0\17\26\1\0\2\0\4\0\0\0\1\20\31\0\10\0\11\0\0\0\1\20\11\0\5\0"
        +"\6\0\1\0\7\0\0\0\23\0\3\0\3\0\0\0\7\u00B8\0\20\u00B3\0\13\u00B1\0\0\0\0\0\0" )
        .getBytes(StandardCharsets.ISO_8859_1);
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
