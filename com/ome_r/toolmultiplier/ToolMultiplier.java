package com.ome_r.toolmultiplier;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.java.JavaPlugin;

import xyz.wildseries.wildtools.api.events.HarvesterHoeSellEvent;
import xyz.wildseries.wildtools.api.events.SellWandUseEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToolMultiplier extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onSellWandUse(SellWandUseEvent e){
        double multiplier = getMultiplier(e.getPlayer());
        e.setPrice(e.getPrice() * multiplier);
        e.setMessage(e.getMessage().replace("%multiplier%", multiplier == 1 ? "" : " (x"  + multiplier + " Multiplier)"));
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onHarvesterHoeSell(HarvesterHoeSellEvent e){
        double multiplier = getMultiplier(e.getPlayer());
        e.setPrice(e.getPrice() * multiplier);
        e.setMessage(e.getMessage().replace("%multiplier%", multiplier == 1 ? "" : " (x"  + multiplier + " Multiplier)"));
    }

    /**
     * This function receives an active percent boost of a player.
     *
     * @param player The player to check
     * @return The active percent boost of the player (0 if not found any)
     */
    private double getMultiplier(Player player){
        double multiplier = -1;
        boolean hasMultiplier = false;

        Matcher matcher;

        for(PermissionAttachmentInfo permissionAttachmentInfo : player.getEffectivePermissions()){
            String permission = permissionAttachmentInfo.getPermission();
            if((matcher = Pattern.compile("wildtools\\.multiplier\\.(.*)").matcher(permission)).matches()){
                try {
                    double currentMultiplier = Double.parseDouble(matcher.group(1));
                    hasMultiplier = true;
                    if(currentMultiplier > multiplier)
                        multiplier = currentMultiplier;
                }catch(NumberFormatException ignored){}
            }
        }

        return hasMultiplier ?  multiplier : 1;
    }

}
