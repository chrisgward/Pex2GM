package com.chrisgward.pex2gm.converters;

import com.chrisgward.pex2gm.GM;
import lombok.Data;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wardcg
 * Date: 10/02/13
 * Time: 6:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class PrivilegesConverter implements Converter {
	@Data
    public static class Groups {
	    @Data
        public static class Group {
            private int rank;
            private List<String> permissions;
            private List<String> inheritance;
            private Map<String, List<String>> worlds;
        }

        private Map<String, Group> groups;
    }

	@Data
    public static class Users {
	    @Data
        public static class User {
            private String group;
            private List<String> permissions;
            private Map<String, List<String>> worlds;
        }

        private Map<String, User> users;
    }

    private Groups groups;
    private Users users;

    public PrivilegesConverter(String users, String groups, Yaml yaml) {
        this.users = yaml.loadAs(users, Users.class);
        this.groups = yaml.loadAs(groups, Groups.class);
    }

    public Map<String, GM.Users> generateUsers() {
        Map<String, GM.Users> users = new HashMap<>();
        List<String> worldlist = new ArrayList<>();
        String dfault = null;
        for (Map.Entry<String, Groups.Group> group : groups.getGroups().entrySet()) {
            if (group.getValue().getRank() == 1)
                dfault = group.getKey();
            if (group.getValue().getWorlds() != null)
                for (String s : group.getValue().getWorlds().keySet())
                    if (!worldlist.contains(s))
                        worldlist.add(s);
        }
        for (Map.Entry<String, Map<String, String[]>> world : generateConfig().getSettings().getMirrors().entrySet())
            if (!worldlist.contains(world.getKey()))
                worldlist.add(world.getKey());
        for (String s : worldlist) {
            GM.Users gmusers = new GM.Users();
            for (Map.Entry<String, Users.User> user : this.users.getUsers().entrySet()) {
                GM.Users.User gmuser = new GM.Users.User();
                ArrayList<String> list = new ArrayList<>();
                if (user.getValue().getPermissions() != null)
                    list.addAll(user.getValue().getPermissions());
                if (user.getValue().getWorlds() != null && user.getValue().getWorlds().get(s) != null)
                    list.addAll(user.getValue().getWorlds().get(s));
                gmuser.setGroup(user.getValue().getGroup());
                gmusers.getUsers().put(user.getKey(), gmuser);
            }
            users.put(s, gmusers);
        }
        return users;
    }

	GM.Config config = new GM.Config();
    public GM.Config generateConfig() {
        return config;
    }

    public GM.GlobalGroups generateGlobalGroups() {
        GM.GlobalGroups globalGroups = new GM.GlobalGroups();
        for (Map.Entry<String, Groups.Group> group : groups.getGroups().entrySet()) {
            GM.GlobalGroups.Group gmgroup = new GM.GlobalGroups.Group();
            gmgroup.setPermissions(group.getValue().getPermissions().toArray(new String[group.getValue().getPermissions().size()]));
            globalGroups.getGroups().put("g:" + group.getKey(), gmgroup);
        }
        return globalGroups;
    }

    public Map<String, GM.Groups> generateGroups() {
        Map<String, GM.Groups> groups = new HashMap<>();
        List<String> worldlist = new ArrayList<>();
        String dfault = null;
        for (Map.Entry<String, Groups.Group> group : this.groups.getGroups().entrySet()) {
            if (group.getValue().getRank() == 1)
                dfault = group.getKey();
            if (group.getValue().getWorlds() != null)
                for (String s : group.getValue().getWorlds().keySet())
                    if (!worldlist.contains(s))
                        worldlist.add(s);
        }
        for (Map.Entry<String, Map<String, String[]>> world : generateConfig().getSettings().getMirrors().entrySet())
            if (!worldlist.contains(world.getKey()))
                worldlist.add(world.getKey());

        for (String world : worldlist) {
            GM.Groups gmgroups = new GM.Groups();
            for (Map.Entry<String, Groups.Group> group : this.groups.getGroups().entrySet()) {
                GM.Groups.Group gmgroup = new GM.Groups.Group();
                if (group.getKey().equalsIgnoreCase(dfault))
                    gmgroup.setDefault(true);
                ArrayList<String> inheritence = new ArrayList<>();
                if (group.getValue().getInheritance() != null)
                    inheritence.addAll(group.getValue().getInheritance());
                inheritence.add("g:" + group.getKey());
                gmgroup.setInheritance(inheritence.toArray(new String[inheritence.size()]));
                if (group.getValue().getWorlds() != null && group.getValue().getWorlds().get(world) != null)
                    gmgroup.setPermissions(group.getValue().getWorlds().get(world).toArray(new String[group.getValue().getWorlds().get(world).size()]));
                gmgroups.getGroups().put(group.getKey(), gmgroup);
            }
            groups.put(world, gmgroups);
        }
        return groups;
    }
}
