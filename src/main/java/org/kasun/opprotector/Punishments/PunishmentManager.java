package org.kasun.opprotector.Punishments;

public class PunishmentManager {
    Lockdown lockdown;
    public PunishmentManager() {
        lockdown = new Lockdown();
    }
    public Lockdown getLockdown() {
        return lockdown;
    }
}
