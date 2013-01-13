package com.chrisgward.pex2gm;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class PexGroups implements Converter
{
	@Override
	public Map<String, GM.Users> generateUsers()
	{
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public GM.Config generateConfig()
	{
		GM.Config config = new GM.Config();

		List<String> mirrorList = new ArrayList<String>();
		mirrorList.add("users");
		mirrorList.add("groups");

		Map<String, Map<String, List<String>>> worldMirrors = new HashMap<String, Map<String, List<String>>>();

		for(String worldName : this.getWorlds().keySet()) {
			PexGroups.World world = this.getWorlds().get(worldName);
			if(world.getInheritance() == null)
				continue;
			if(!worldMirrors.containsKey(world.getInheritance()))
			{
				Map<String, List<String>> newworld = new HashMap<String, List<String>>();
				newworld.put(worldName, mirrorList);
				worldMirrors.put(world.getInheritance().get(0), newworld);
			}
			else
			{
				worldMirrors.get(world.getInheritance()).put(worldName, mirrorList);
			}
		}
		config.getSettings().setMirrors(worldMirrors);
		return config;
	}

	@Override
	public GM.GlobalGroups generateGlobalGroups()
	{
		GM.GlobalGroups groups = new GM.GlobalGroups();
		return null;
	}

	@Override
	public Map<String, GM.Groups> generateGroups()
	{
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public static class Group {
		public static class World {
			@Getter @Setter List<String> permissions;
		}

		@Getter @Setter List<String> permissions;
		@Getter @Setter List<String> inheritance;
		@Getter @Setter Map<String, World> worlds;
		@Getter @Setter Map<String, Object> options;
		@Getter @Setter String prefix;
		@Getter @Setter String suffix;
		Boolean dfault;
		public void setDefault(boolean dfault) {
			this.dfault = dfault;
		}

		public boolean getDefault() {
			return dfault;
		}
	}

	public static class User {
		@Getter @Setter List<String> permissions;
		@Getter @Setter String prefix;
		@Getter @Setter String suffix;
		@Getter @Setter List<String> groups;

		public static class World {
			@Getter @Setter List<String> permissions;
			@Getter @Setter List<String> groups;
		}
	}

	public static class World {
		@Getter @Setter public List<String> inheritance;
	}

	@Getter @Setter	Map<String, Group> groups;
	@Getter @Setter	Map<String, User> users;
	@Getter @Setter Map<String, World> worlds;

}
