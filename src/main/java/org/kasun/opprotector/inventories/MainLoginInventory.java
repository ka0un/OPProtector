package org.kasun.opprotector.inventories;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class MainLoginInventory {

    public MainLoginInventory() {
        ChestGui chestGui = new ChestGui(3, "Auth Login");
        chestGui.setOnGlobalClick(event -> event.setCancelled(true));
    }
}
