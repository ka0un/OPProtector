package org.kasun.opprotector.Configs;

import org.kasun.opprotector.OPProtector;
import org.yaml.snakeyaml.Yaml;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OperatorConfig {

    private static OPProtector plugin = OPProtector.getInstance();

    private static final String FILE_PATH = plugin.getDataFolder() + "/operators.yml";

    private static Map<String, OperatorConfig> operatorMap = new LinkedHashMap<>();
    private static List<String> operatorNames = new ArrayList<>();

    private String name;
    private String password;
    private String email;
    private String discord;
    private List<String> commandBlacklist;

    public OperatorConfig() {
    }

    public OperatorConfig(String name, String password, String email, String discord, List<String> commandBlacklist) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.discord = discord;
        this.commandBlacklist = commandBlacklist;
    }

    public static void loadOperators() {
        try (FileInputStream input = new FileInputStream(FILE_PATH)) {
            Yaml yaml = new Yaml();
            Iterable<Object> data = yaml.loadAll(input);

            operatorMap.clear();
            operatorNames.clear();

            for (Object object : data) {
                if (object instanceof Map) {
                    Map<String, Object> operatorData = (Map<String, Object>) object;
                    OperatorConfig operator = new OperatorConfig();

                    operator.setName((String) operatorData.get("name"));
                    operator.setPassword((String) operatorData.get("password"));
                    operator.setEmail((String) operatorData.get("email"));
                    operator.setDiscord((String) operatorData.get("discord"));
                    operator.setCommandBlacklist((List<String>) operatorData.get("commandBlacklist"));

                    operatorMap.put(operator.getName(), operator);
                    operatorNames.add(operator.getName());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveOperators() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            Yaml yaml = new Yaml();

            for (String operatorName : operatorNames) {
                OperatorConfig operator = operatorMap.get(operatorName);

                Map<String, Object> operatorData = new LinkedHashMap<>();
                operatorData.put("name", operator.getName());
                operatorData.put("password", operator.getPassword());
                operatorData.put("email", operator.getEmail());
                operatorData.put("discord", operator.getDiscord());
                operatorData.put("commandBlacklist", operator.getCommandBlacklist());

                yaml.dump(operatorData, writer);
                writer.write("---\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getOperatorNames() {
        return operatorNames;
    }

    public static OperatorConfig getOperatorConfig(String operatorName) {
        return operatorMap.get(operatorName);
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

    public String getDiscord() {
        return discord;
    }

    public void setDiscord(String discord) {
        this.discord = discord;
    }

    public List<String> getCommandBlacklist() {
        return commandBlacklist;
    }

    public void setCommandBlacklist(List<String> commandBlacklist) {
        this.commandBlacklist = commandBlacklist;
    }
}

