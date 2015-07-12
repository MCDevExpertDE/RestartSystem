package me.CryCore.RestartSystem;

import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

@SuppressWarnings("unused")
public class Main
  extends JavaPlugin
{
  public void onEnable()
  {
    loadConfig();
    startTimer();
  }
  
  private void startTimer()
  {
    Bukkit.getScheduler().runTaskTimer(this, new Runnable()
    {
      @SuppressWarnings("deprecation")
	public void run()
      {
        Date d = new Date();
        String time = Main.this.getConfig().getString("time");
        if (time.contains(":"))
        {
          int hour = Integer.parseInt(time.split(":")[0]);
          int minute = Integer.parseInt(time.split(":")[1]);
          if ((d.getHours() == hour) && (d.getMinutes() == minute)) {
            if (Main.this.getConfig().getString("type").equalsIgnoreCase("stop")) {
              Bukkit.shutdown();
            } else {
              Bukkit.reload();
            }
          }
        }
        else
        {
          Main.this.getConfig().set("time", "19:30");
        }
      }
    }, 600L, 600L);
  }
  
  private void loadConfig()
  {
    getConfig().options().header("Plugin by MCDevExpertDE and CryCore\nTime\nhour:minute\n(24h)\n\nTypes:\nstop -> Stop the server\nreload -> reload the server\n ");
    check("time", "19:30");
    check("type", "stop");
  }
  
  private boolean check(String path, Object value)
  {
    if (!getConfig().contains(path))
    {
      getConfig().set(path, value);
      saveConfig();
      return false;
    }
    return true;
  }
}
