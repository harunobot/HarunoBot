/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.concurrent;

import io.vertx.core.Context;
import io.vertx.core.Vertx;
import java.util.concurrent.Executor;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 *
 * @author iTeam_VEP
 */
public final class VertxExecutor implements Executor {

    private final Context context;
    
    public VertxExecutor(){
        this.context = Vertx.vertx().getOrCreateContext();
    }

    public VertxExecutor(@NonNull Vertx vertx) {
        this.context = vertx.getOrCreateContext();
    }

    @Override
    public void execute(@NonNull Runnable command) {
        context.runOnContext(v -> command.run());
    }
}
