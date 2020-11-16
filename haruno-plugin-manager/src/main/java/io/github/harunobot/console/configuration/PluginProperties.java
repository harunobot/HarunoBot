/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.console.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 *
 * @author iTeam_VEP
 */
public class PluginProperties {
    @JsonProperty(value="folder")
    private List<String> pluginFolder;
    @JsonProperty(value="plugin-permissions")
    private List<String> pluginPermissions;

    /**
     * @return the pluginFolder
     */
    public List<String> getPluginFolder() {
        return pluginFolder;
    }

    /**
     * @param pluginFolder the pluginFolder to set
     */
    public void setPluginFolder(List<String> pluginFolder) {
        this.pluginFolder = pluginFolder;
    }
}
