package com.chrisgward.pex2gm;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class PexGroups
{
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
