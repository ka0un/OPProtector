package org.kasun.opprotector.inventories;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;

public class FactorsGuI {

    public FactorsGuI() {
        ChestGui chestGui = new ChestGui(3, "Auth Login");
        chestGui.setOnGlobalClick(event -> event.setCancelled(true));
    }
}
