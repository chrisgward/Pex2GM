package com.chrisgward.pex2gm.converters;

import com.chrisgward.pex2gm.GM;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class BPermsGroups implements Converter {
    public Map<String, GM.Users> generateUsers() {
        GM.Users users = new GM.Users();
        for (Map.Entry<String, List<String>> entry : getGroups().entrySet()) {
            GM.Users.User user = new GM.Users.User();
            user.setPermissions(entry.getValue().toArray(new String[entry.getValue().size()]));
            users.getUsers().put(entry.getKey(), user);
        }
        HashMap<String, GM.Users> output = new HashMap<>();
        output.put("world", users);
        return output;
    }

    public GM.Config generateConfig() {
        return new GM.Config();
    }

    public GM.GlobalGroups generateGlobalGroups() {
        GM.GlobalGroups globalGroups = new GM.GlobalGroups();
        for (Map.Entry<String, List<String>> entry : getGroups().entrySet()) {
            GM.GlobalGroups.Group group = new GM.GlobalGroups.Group();
            group.setPermissions(entry.getValue().toArray(new String[entry.getValue().size()]));
            globalGroups.getGroups().put(entry.getKey(), group);
        }
        return globalGroups;
    }

    public Map<String, GM.Groups> generateGroups() {
        GM.Groups groups = new GM.Groups();
        boolean defaultset = false;
        for (Map.Entry<String, List<String>> entry : getGroups().entrySet()) {
            GM.Groups.Group group = new GM.Groups.Group();
            if (!defaultset) {
                group.setDefault(true);
                defaultset = true;
            }
            group.setPermissions(new String[]{});
            group.setInheritance(new String[]{"g:" + entry.getKey()});
            groups.getGroups().put(entry.getKey(), group);
        }
        HashMap<String, GM.Groups> output = new HashMap<>();
        output.put("world", groups);
        return output;
    }

    Map<String, List<String>> groups;
    Map<String, List<String>> players;
}
