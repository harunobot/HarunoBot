/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.plugin;

import com.diabolicallabs.vertx.cron.CronObservable;
import io.github.harunobot.async.BotResponseCallback;
import io.github.harunobot.proto.request.BotRequest;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Timed;
import java.util.concurrent.ExecutorService;

/**
 *
 * @author iTeam_VEP
 */
public interface HarunoPluginSupport {
    
    <T> void sendBotRequest(String pluginId, BotRequest request, BotResponseCallback<T> callback);
    
    void sendBotRequest(String pluginId, BotRequest request);
    
    ExecutorService executorService();
    
    <T> void scheduleTask(String pluginId, String taskId, String cron, Consumer<? super Timed<Long>> onNext, Consumer<? super Throwable> onError);
    
    void cancelScheduledTask(String pluginId, String taskId);
    
//    long pluginMarkBotRequest(String pluginId, BotRequest request);
    
}
