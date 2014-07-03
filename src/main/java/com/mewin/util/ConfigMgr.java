/*
 * Copyright (C) 2013 mewin<mewin001@hotmail.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.mewin.util;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.plugin.Plugin;

public class ConfigMgr
{

    private Plugin plugin;
    private String confName;
    private File confFile;

    public ConfigMgr(Plugin plugin, String confFile)
    {
        this.plugin = plugin;
        confName = confFile;
        this.confFile = new File(plugin.getDataFolder(), confName);
    }

    public ConfigMgr(Plugin plugin)
    {
        this(plugin, "config.yml");
    }

    public void save()
    {
        if(!plugin.getDataFolder().exists() && !plugin.getDataFolder().mkdir())
        {
            plugin.getLogger().log(Level.SEVERE, "Could not create plugin data folder.");
            return;
        }
        
        try
        {
            if(!confFile.exists() && !confFile.createNewFile())
            {
                plugin.getLogger().log(Level.SEVERE, "Could not create config file.");
                return;
            }
            plugin.getConfig().save(confFile);
        }
        catch(IOException ex)
        {
            plugin.getLogger().log(Level.SEVERE, "Could not save config file: ", ex);
        }
    }

    public void load(boolean saveDefaults)
    {
        if(!confFile.exists())
        {
            if(saveDefaults)
            {
                plugin.getLogger().log(Level.INFO, "Config file not found, writing default config.");
                save();
            }
        } else
        {
            try
            {
                plugin.getConfig().load(confFile);
            }
            catch(Exception ex)
            {
                plugin.getLogger().log(Level.SEVERE, "Could not load config file: ", ex);
            }
        }
    }
}
