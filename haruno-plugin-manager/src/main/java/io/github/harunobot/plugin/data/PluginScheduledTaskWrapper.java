/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.plugin.data;

import io.reactivex.disposables.Disposable;

/**
 *
 * @author iTeam_VEP
 */
public class PluginScheduledTaskWrapper {
    private String taskId;
    private Disposable task;
    
    public PluginScheduledTaskWrapper(String taskId, Disposable task){
        this.taskId = taskId;
        this.task = task;
    }

    /**
     * @return the taskId
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * @param taskId the taskId to set
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * @return the task
     */
    public Disposable getTask() {
        return task;
    }

    /**
     * @param task the task to set
     */
    public void setTask(Disposable task) {
        this.task = task;
    }
}
