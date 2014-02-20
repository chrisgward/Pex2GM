package com.chrisgward.pex2gm.converters;

import com.chrisgward.pex2gm.GM;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Data
public class PexGroups implements Converter {
    public Map<String, GM.Users> generateUsers() {
        Map<String, GM.Users> users = new HashMap<>();
        ArrayList<String> worldList = new ArrayList<>();
        String dfault = null;
        for (Map.Entry<String, Group> pexgroup : getGroups().entrySet()) {
            if (pexgroup.getValue().getDefault()) {
                dfault = pexgroup.getKey();
            }
            for (String s : pexgroup.getValue().getWorlds().keySet())
                if (!worldList.contains(s))
                    worldList.add(s);
        }
        for (Map.Entry<String, Map<String, String[]>> world : generateConfig().getSettings().getMirrors().entrySet()) {
            if (!worldList.contains(world.getKey()))
                worldList.add(world.getKey());
        }

        for (String world : worldList) {
            GM.Users gmusers = new GM.Users();

            Map<String, GM.Users.User> groupList = new HashMap<>();
            for (Map.Entry<String, User> pexuser : getUsers().entrySet()) {
                GM.Users.User user = new GM.Users.User();
                if (pexuser.getValue().getWorlds() != null && pexuser.getValue().getWorlds().get(world) != null && pexuser.getValue().getWorlds().get(world).getPermissions() != null)
                    user.setPermissions(pexuser.getValue().getWorlds().get(world).getPermissions());
                user.setGroup(pexuser.getValue().getGroup() == null || pexuser.getValue().getGroup().length == 0 ? dfault : pexuser.getValue().getGroup()[0]);
                if (pexuser.getValue().getGroup() != null && pexuser.getValue().getGroup().length > 1)
                    user.setSubgroups(Arrays.copyOfRange(pexuser.getValue().getGroup(), 1, pexuser.getValue().getGroup().length));
                if (pexuser.getValue().getPrefix() != null)
                    user.getInfo().put("prefix", pexuser.getValue().getPrefix());
                if (pexuser.getValue().getSuffix() != null)
                    user.getInfo().put("suffix", pexuser.getValue().getSuffix());
                ArrayList<String> inheritance = new ArrayList<>();
                groupList.put(pexuser.getKey(), user);
            }
            gmusers.setUsers(groupList);
            users.put(world, gmusers);
        }
        return users;
    }

    public GM.Config generateConfig() {
        GM.Config config = new GM.Config();

        List<String> mirrorList = new ArrayList<>();
        mirrorList.add("users");
        mirrorList.add("groups");

        Map<String, Map<String, String[]>> worldMirrors = config.getSettings().getMirrors();

        for (String worldName : this.getWorlds().keySet()) {
            PexGroups.World world = this.getWorlds().get(worldName);
            if (world.getInheritance() == null)
                continue;
            if (!worldMirrors.containsKey(world.getInheritance())) {
                Map<String, String[]> newworld = new HashMap<>();
                newworld.put(worldName, mirrorList.toArray(new String[mirrorList.size()]));
                worldMirrors.put(world.getInheritance()[0], newworld);
            } else {
                worldMirrors.get(world.getInheritance()).put(worldName, mirrorList.toArray(new String[mirrorList.size()]));
            }
        }
        config.getSettings().setMirrors(worldMirrors);
        return config;
    }

    public GM.GlobalGroups generateGlobalGroups() {
        GM.GlobalGroups groups = new GM.GlobalGroups();
        Map<String, GM.GlobalGroups.Group> groupList = new HashMap<>();
        for (Map.Entry<String, Group> pexgroup : getGroups().entrySet()) {
            GM.GlobalGroups.Group group = new GM.GlobalGroups.Group();
            group.setPermissions(pexgroup.getValue().getPermissions());
            groupList.put("g:" + pexgroup.getKey(), group);
        }
        groups.setGroups(groupList);
        return groups;
    }

    public Map<String, GM.Groups> generateGroups() {

        Map<String, GM.Groups> groups = new HashMap<>();
        ArrayList<String> worldList = new ArrayList<>();
        for (Map.Entry<String, Group> pexgroup : getGroups().entrySet())
            for (String s : pexgroup.getValue().getWorlds().keySet())
                if (!worldList.contains(s))
                    worldList.add(s);
        for (Map.Entry<String, Map<String, String[]>> world : generateConfig().getSettings().getMirrors().entrySet()) {
            if (!worldList.contains(world.getKey()))
                worldList.add(world.getKey());
        }

        for (String world : worldList) {
            GM.Groups gmgroups = new GM.Groups();

            Map<String, GM.Groups.Group> groupList = new HashMap<>();
            for (Map.Entry<String, Group> pexgroup : getGroups().entrySet()) {
                GM.Groups.Group group = new GM.Groups.Group();
                if (pexgroup.getValue().getWorlds().get(world) != null) {
                    group.setPermissions(pexgroup.getValue().getWorlds().get(world).getPermissions());
                }
                if (pexgroup.getValue().getPrefix() != null)
                    group.getInfo().put("prefix", pexgroup.getValue().getPrefix());
                if (pexgroup.getValue().getSuffix() != null)
                    group.getInfo().put("suffix", pexgroup.getValue().getSuffix());
                group.getInfo().put("build", pexgroup.getValue().isBuild());
                ArrayList<String> inheritance = new ArrayList<>();
                if (pexgroup.getValue().getInheritance() != null)
	                Collections.addAll(inheritance, pexgroup.getValue().getInheritance());
                inheritance.add("g:" + pexgroup.getKey());
                group.setInheritance(inheritance.toArray(new String[inheritance.size()]));
                group.setDefault(pexgroup.getValue().getDefault());
                groupList.put(pexgroup.getKey(), group);
            }
            gmgroups.setGroups(groupList);
            groups.put(world, gmgroups);
        }
        return groups;
    }

	@Data
    public static class Group {
		@Data
        public static class World {
            String[] permissions;
        }

        String[] permissions;
        String[] inheritance;
        Map<String, World> worlds = new HashMap<>();
        Map<String, Object> options;
        String prefix;
        String suffix;
        boolean build = false;

        public void setInfo(Map<String, Object> info) {
            throw new RuntimeException("The info: block is not part of a PermissionsEx file. It belongs to GroupManager. This means your file is either seriously malformed or already a GroupManager file.");
        }

		@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE) boolean dfault = false;
        public void setDefault(boolean dfault) {
            this.dfault = dfault;
        }
        public boolean getDefault() {
            return dfault;
        }
    }

	@Data
    public static class User {
        String[] permissions;
        String prefix;
        String suffix;
        String[] group;
        Map<String, World> worlds;
        Map<String, Object> options;

        public void setInfo(Map<String, Object> info) {
	        throw new RuntimeException("The info: block is not part of a PermissionsEx file. It belongs to GroupManager. This means your file is either seriously malformed or already a GroupManager file.");
        }

		@Data
        public static class World {
            String[] permissions;
            String[] groups;
        }
    }

	@Data
    public static class World {
        public String[] inheritance;
    }

    Map<String, Group> groups = new HashMap<>();
    Map<String, User> users = new HashMap<>();
    Map<String, World> worlds = new HashMap<>();

}
