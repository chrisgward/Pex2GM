package com.chrisgward.pex2gm;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class PexGroups implements Converter
{
	public Map<String, GM.Users> generateUsers()
	{
		Map<String, GM.Users> users = new HashMap<String, GM.Users>();
		ArrayList<String> worldList = new ArrayList<String>();
		String dfault = null;
		for(Map.Entry<String, Group> pexgroup : getGroups().entrySet())
		{
			if(pexgroup.getValue().getDefault())
			{
				dfault = pexgroup.getKey();
			}
			for(String s : pexgroup.getValue().getWorlds().keySet())
				if(!worldList.contains(s))
					worldList.add(s);
		}
		for(Map.Entry<String, Map<String, String[]>> world : generateConfig().getSettings().getMirrors().entrySet())
		{
			if(!worldList.contains(world.getKey()))
				worldList.add(world.getKey());
		}

		for(String world : worldList)
		{
			GM.Users gmusers = new GM.Users();

			Map<String, GM.Users.User> groupList = new HashMap<String, GM.Users.User>();
			for(Map.Entry<String, User> pexuser : getUsers().entrySet())
			{
				GM.Users.User user = new GM.Users.User();
				if(pexuser.getValue().getWorlds() != null && pexuser.getValue().getWorlds().get(world) != null && pexuser.getValue().getWorlds().get(world).getPermissions() != null)
					user.setPermissions(pexuser.getValue().getWorlds().get(world).getPermissions());
				user.setGroup(pexuser.getValue().getGroup() == null ? dfault : pexuser.getValue().getGroup()[0]);
				if(pexuser.getValue().getGroup() != null)
					user.setSubgroups(Arrays.copyOfRange(pexuser.getValue().getGroup(), 1, pexuser.getValue().getGroup().length));
				if(pexuser.getValue().getPrefix() != null)
					user.getInfo().put("prefix", pexuser.getValue().getPrefix());
				if(pexuser.getValue().getSuffix() != null)
					user.getInfo().put("suffix", pexuser.getValue().getSuffix());
				ArrayList<String> inheritance = new ArrayList<String>();
				groupList.put(pexuser.getKey(), user);
			}
			gmusers.setUsers(groupList);
			users.put(world, gmusers);
		}
		return users;
	}

	public GM.Config generateConfig()
	{
		GM.Config config = new GM.Config();

		List<String> mirrorList = new ArrayList<String>();
		mirrorList.add("users");
		mirrorList.add("groups");

		Map<String, Map<String, String[]>> worldMirrors = config.getSettings().getMirrors();

		for(String worldName : this.getWorlds().keySet()) {
			PexGroups.World world = this.getWorlds().get(worldName);
			if(world.getInheritance() == null)
				continue;
			if(!worldMirrors.containsKey(world.getInheritance()))
			{
				Map<String, String[]> newworld = new HashMap<String, String[]>();
				newworld.put(worldName, mirrorList.toArray(new String[mirrorList.size()]));
				worldMirrors.put(world.getInheritance()[0], newworld);
			}
			else
			{
				worldMirrors.get(world.getInheritance()).put(worldName, mirrorList.toArray(new String[mirrorList.size()]));
			}
		}
		config.getSettings().setMirrors(worldMirrors);
		return config;
	}

	public GM.GlobalGroups generateGlobalGroups()
	{
		GM.GlobalGroups groups = new GM.GlobalGroups();
		Map<String, GM.GlobalGroups.Group> groupList = new HashMap<String, GM.GlobalGroups.Group>();
		for(Map.Entry<String, Group> pexgroup : getGroups().entrySet())
		{
			GM.GlobalGroups.Group group = new GM.GlobalGroups.Group();
			group.setPermissions(pexgroup.getValue().getPermissions());
			groupList.put("g:" + pexgroup.getKey(), group);
		}
		groups.setGroups(groupList);
		return groups;
	}

	public Map<String, GM.Groups> generateGroups()
	{

		Map<String, GM.Groups> groups = new HashMap<String, GM.Groups>();
		ArrayList<String> worldList = new ArrayList<String>();
		for(Map.Entry<String, Group> pexgroup : getGroups().entrySet())
			for(String s : pexgroup.getValue().getWorlds().keySet())
				if(!worldList.contains(s))
					worldList.add(s);
		for(Map.Entry<String, Map<String, String[]>> world : generateConfig().getSettings().getMirrors().entrySet())
		{
			if(!worldList.contains(world.getKey()))
				worldList.add(world.getKey());
		}

		for(String world : worldList)
		{
			GM.Groups gmgroups = new GM.Groups();

			Map<String, GM.Groups.Group> groupList = new HashMap<String, GM.Groups.Group>();
			for(Map.Entry<String, Group> pexgroup : getGroups().entrySet())
			{
				GM.Groups.Group group = new GM.Groups.Group();
				if(pexgroup.getValue().getWorlds().get(world) != null) {
					group.setPermissions(pexgroup.getValue().getWorlds().get(world).getPermissions());
				}
				if(pexgroup.getValue().getPrefix() != null)
					group.getInfo().put("prefix", pexgroup.getValue().getPrefix());
				if(pexgroup.getValue().getSuffix() != null)
					group.getInfo().put("suffix", pexgroup.getValue().getSuffix());
				ArrayList<String> inheritance = new ArrayList<String>();
				if(pexgroup.getValue().getInheritance() != null)
					for(String s : pexgroup.getValue().getInheritance())
						inheritance.add(s);
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

	public static class Group {
		public static class World {
			@Getter @Setter String[] permissions;
		}

		@Getter @Setter String[] permissions;
		@Getter @Setter String[] inheritance;
		@Getter @Setter Map<String, World> worlds = new HashMap<String, World>();
		@Getter @Setter Map<String, Object> options;
		@Getter @Setter String prefix;
		@Getter @Setter String suffix;
		boolean dfault = false;

		public void setInfo(Map<String, Object> info)
		{
			throw new RuntimeException("The info: block is not part of a PermissionsEx file. It belongs to GroupManager. This means your file should already be compatible with GroupManager. ");
		}

		public void setDefault(boolean dfault) {
			this.dfault = dfault;
		}

		public boolean getDefault() {
			return dfault;
		}
	}

	public static class User {
		@Getter @Setter String[] permissions;
		@Getter @Setter String prefix;
		@Getter @Setter String suffix;
		@Getter @Setter String[] group;
		@Getter @Setter Map<String, World> worlds;
		@Getter @Setter Map<String, Object> options;
		public void setInfo(Map<String, Object> info)
		{
			throw new RuntimeException("The info: block is not part of a PermissionsEx file. It belongs to GroupManager. This means your file should already be compatible with GroupManager. ");
		}

		public static class World {
			@Getter @Setter String[] permissions;
			@Getter @Setter String[] groups;
		}
	}

	public static class World {
		@Getter @Setter public String[] inheritance;
	}

	@Getter @Setter	Map<String, Group> groups = new HashMap<String, Group>();
	@Getter @Setter	Map<String, User> users = new HashMap<String, User>();
	@Getter @Setter Map<String, World> worlds = new HashMap<String, World>();

}
