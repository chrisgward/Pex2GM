package com.chrisgward.pex2gm;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class GM
{
	public static class GlobalGroups {
		public static class Group {
			@Getter @Setter String[] permissions = new String[0];
		}
		@Getter @Setter Map<String,Group> groups = new HashMap<String, Group>();
	}

	public static class Groups {
		public static class Group {
			@Getter @Setter Map<String, Object> info = new HashMap<String, Object>();

			public Group() {
				info.put("prefix", "");
				info.put("build", true);
				info.put("suffix", "");
			}

			Boolean dfault = false;
			public boolean getDefault() {
				return dfault;
			}

			public void setDefault(boolean dfault) {
				this.dfault = dfault;
			}
			@Getter @Setter String[] permissions = new String[0];
			@Getter @Setter String[] inheritance = new String[0];
		}
		@Getter @Setter Map<String, Group> groups = new HashMap<String, Group>();
	}

	public static class Users {
		public static class User {
            public User() {
                info.put("prefix", "");
                info.put("build", true);
                info.put("suffix", "");
            }
			@Getter @Setter String[] permissions = new String[0];
			@Getter @Setter String group = "";
			@Getter @Setter String[] subgroups = new String[0];
			@Getter @Setter Map<String, Object> info = new HashMap<String, Object>();
		}

		@Getter @Setter public Map<String, User> users = new HashMap<String, User>();
	}

	public static class Config {
		public static class Settings {
			public static class Config_ {
				@Getter @Setter Boolean opOverrides = true;
				@Getter @Setter Boolean validate_toggle = true;
			}

			public static class Data {
				public static class Save {
					@Getter @Setter Integer minutes = 10;
					@Getter @Setter Integer hours = 24;
				}

				@Getter @Setter Save save = new Save();
			}

			public static class Logging {
				@Getter @Setter String level = "INFO";
			}

			@Getter @Setter Config_ config = new Config_();
			@Getter @Setter Data data = new Data();
			@Getter @Setter Logging logging = new Logging();
			@Getter @Setter Map<String, Map<String, String[]>> mirrors = new HashMap<String, Map<String, String[]>>();
			public Settings()
			{
				Map<String, String[]> mirrorlist = new HashMap<String, String[]>();
				mirrorlist.put("world_nether", new String[]{"users", "groups"});
				mirrorlist.put("world_the_end", new String[]{"users", "groups"});
				mirrors.put("world", mirrorlist);
			}
		}

		@Getter @Setter Settings settings = new Settings();
	}
}
