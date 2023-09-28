package org.kasun.opprotector.Configs;


import org.kasun.opprotector.OPProtector;
import org.kasun.opprotector.Utils.Encryption;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;

public class OperatorConfig {

    private OPProtector plugin = OPProtector.getInstance();

    private final String FILE_PATH = plugin.getDataFolder() + "/operators.yml";

    private Map<String, Operator> operatorDataMap;
    String EncryptedPrefix = "Encrypted_";
    String key = "1234567890123456";



    public OperatorConfig() {
        operatorDataMap = new HashMap<>();
        loadOperators();
    }

    public void loadOperators() {
        try (InputStream inputStream = new FileInputStream(FILE_PATH)) {
            Yaml yaml = new Yaml();
            Map<String, Map<String, Object>> data = yaml.load(inputStream);

            for (Map.Entry<String, Map<String, Object>> entry : data.entrySet()) {
                String operatorName = entry.getKey();
                Map<String, Object> operatorData = entry.getValue();

                Operator operator = new Operator();
                operator.setName(operatorName);
                if (operatorData.get("password").toString().startsWith(EncryptedPrefix)) {
                    String encryptedPassword = operatorData.get("password").toString().substring(EncryptedPrefix.length());
                    try{
                        operator.setPassword(Encryption.decrypt(encryptedPassword, key));
                    }catch (Exception ignored){}
                } else {
                    operator.setPassword(operatorData.get("password").toString());

                }
                operator.setCommandBlacklist((List<String>) operatorData.get("commandBlacklist"));

                operatorDataMap.put(operatorName, operator);
            }
            saveOperators();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void saveOperators() {
        try (OutputStream outputStream = new FileOutputStream(FILE_PATH)) {
            Yaml yaml = new Yaml();
            Map<String, Map<String, Object>> data = new HashMap<>();
            MainConfig mainConfig = new MainConfig();

            for (Map.Entry<String, Operator> entry : operatorDataMap.entrySet()) {
                String operatorName = entry.getKey();
                Operator operator = entry.getValue();

                Map<String, Object> operatorData = new HashMap<>();
                if (mainConfig.encrypt_passwords){
                    try{
                        operatorData.put("password", EncryptedPrefix + Encryption.encrypt(operator.getPassword(), key));
                    }catch (Exception ignored){}
                }else {
                    operatorData.put("password", operator.getPassword());
                }
                operatorData.put("commandBlacklist", operator.getCommandBlacklist());

                data.put(operatorName, operatorData);
            }

            yaml.dump(data, new OutputStreamWriter(outputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Operator getOperator(String operatorName) {
        return operatorDataMap.get(operatorName);
    }

    public Map<String, Operator> getOperatorDataMap() {
        return operatorDataMap;
    }

    public boolean isContains(String operatorName) {
        if (operatorDataMap.containsKey(operatorName)) {
            return true;
        }else{
            return false;
        }
    }


}

