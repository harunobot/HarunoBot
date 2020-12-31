/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.console;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Launcher;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import java.util.concurrent.TimeUnit;
import org.iharu.util.JsonUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 *
 * @author iTeam_VEP
 */
public class BotLaucher extends Launcher {
    private final org.slf4j.Logger LOG = LoggerFactory.getLogger(BotLaucher.class);
    
    public static void main(String[] args) {
//        https://zhuanlan.zhihu.com/p/30913753
//        System.getProperties().setProperty("vertx.disableDnsResolver","true"); 
        new BotLaucher().dispatch(args);
    }

    @Override
    public void afterConfigParsed(JsonObject jo) {
        MDC.put("module", "Laucher");
        LOG.info("Harunobot config: {}", JsonUtils.objectToJson(jo));
    }

    @Override
    public void beforeStartingVertx(VertxOptions options) {
//        options.setWorkerPoolSize(100);
        // check for blocked threads every 5s
        options.setBlockedThreadCheckInterval(5);
        options.setBlockedThreadCheckIntervalUnit(TimeUnit.SECONDS);

        // warn if an event loop thread handler took more than 100ms to execute
        options.setMaxEventLoopExecuteTime(200);
        options.setMaxEventLoopExecuteTimeUnit(TimeUnit.MILLISECONDS);

        // warn if an worker thread handler took more than 10s to execute
        options.setMaxWorkerExecuteTime(10);
        options.setMaxWorkerExecuteTimeUnit(TimeUnit.SECONDS);

         // log the stack trace if an event loop or worker handler took more than 20s to execute
        options.setWarningExceptionTime(30);
        options.setWarningExceptionTimeUnit(TimeUnit.SECONDS);
        MDC.put("module", "Laucher");
        LOG.info("Harunobot configured");
    }

    @Override
    public void afterStartingVertx(Vertx vertx) {
        MDC.put("module", "Laucher");
        LOG.info("Harunobot started");
    }

    @Override
    public void beforeDeployingVerticle(DeploymentOptions d) {
        MDC.put("module", "Laucher");
        LOG.info("Harunobot deploying verticle {}", JsonUtils.objectToJson(d.getConfig()));
    }

    @Override
    public void beforeStoppingVertx(Vertx vertx) {
        MDC.put("module", "Laucher");
        LOG.info("Harunobot stopping");
    }

    @Override
    public void afterStoppingVertx() {
        MDC.put("module", "Laucher");
        LOG.info("Harunobot stopped");
    }

    @Override
    public void handleDeployFailed(Vertx vertx, String string, DeploymentOptions d, Throwable thrwbl) {
        MDC.put("module", "Laucher");
        LOG.error("Harunobot laucher failed {}", string, thrwbl);
    }
    
}
