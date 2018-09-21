package com.ome_r.toolmultiplier;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.java.JavaPlugin;

import xyz.wildseries.wildtools.api.events.SellWandUseEvent;

public class ToolMultiplier extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onSellWandUse(SellWandUseEvent e){
        e.setPrice(e.getPrice() * getMultiplier(e.getPlayer()));
    }

    /**
     * This function receives an active multiplier of a player.
     *
     * @param player The player to check
     * @return The active multiplier of the player (1 if not found any)
     */
    private int getMultiplier(Player player){
        int multiplier = 1;

        for(PermissionAttachmentInfo permissionAttachmentInfo : player.getEffectivePermissions()){
            String permission = permissionAttachmentInfo.getPermission();
            if(permission.matches("wildtools\\.multiplier\\.\\d")){
                try {
                    int currentMultiplier = Integer.parseInt(permission.split("\\.")[2]);
                    if(currentMultiplier > multiplier)
                        multiplier = currentMultiplier;
                }catch(NumberFormatException ignored){}
            }
        }

        return multiplier;
    }

}
