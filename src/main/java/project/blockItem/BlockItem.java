package project.blockItem;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerItemMendEvent;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerEditBookEvent;

import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerItemMendEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import org.bukkit.event.player.PlayerQuitEvent;

import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import org.bukkit.event.inventory.InventoryClickEvent;

import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class BlockItem extends JavaPlugin implements Listener {

    private final List<Material> blockedItems = Arrays.asList(
            Material.SHULKER_BOX,
            Material.ENDER_CHEST,
            Material.SHIELD,
            Material.ELYTRA
    );

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    // 제작 금지
    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (blockedItems.contains(event.getRecipe().getResult().getType())) {
            event.setCancelled(true);
        }
    }

    // 인벤토리 클릭 시 제거
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item != null && blockedItems.contains(item.getType())) {
            event.getWhoClicked().getInventory().remove(item.getType());
        }
    }

    // 아이템 줍기 시 제거
    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        if (blockedItems.contains(event.getItem().getItemStack().getType())) {
            event.setCancelled(true);
            event.getItem().remove();
        }
    }

    // 인벤토리에 들어올 수 있는 주요 상황 대응
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        removeBlockedItems(event.getPlayer());
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        removeBlockedItems(event.getPlayer());
    }

    // 엔더 진주 던지기 금지
    @EventHandler
    public void onEnderPearlThrow(ProjectileLaunchEvent event) {
        if (event.getEntity().getType().toString().equals("ENDER_PEARL")) {
            event.setCancelled(true);
        }
    }

    // 아이템 들어오면 즉시 제거
    private void removeBlockedItems(Player player) {
        for (Material blocked : blockedItems) {
            player.getInventory().remove(blocked);
        }
    }
}
