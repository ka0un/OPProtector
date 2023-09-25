package org.kasun.opprotector.Configs;

import java.util.List;

public class Operator {
    private String name;
    private String password;
    private String email;
    private List<String> commandBlacklist;

    public Operator() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public List<String> getCommandBlacklist() {
        return commandBlacklist;
    }

    public void setCommandBlacklist(List<String> commandBlacklist) {
        this.commandBlacklist = commandBlacklist;
    }
}
