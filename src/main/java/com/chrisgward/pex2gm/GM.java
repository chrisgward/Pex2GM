package com.chrisgward.pex2gm;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class GM {
	@Data
    public static class GlobalGroups {
		@Data
        public static class Group {
            String[] permissions = new String[0];
        }

        Map<String, Group> groups = new HashMap<>();
    }

	@Data
    public static class Groups {
		@Data
        public static class Group {
            Map<String, Object> info = new HashMap<>();

            public Group() {
                info.put("prefix", "");
                info.put("build", true);
                info.put("suffix", "");
            }

			@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE) boolean dfault = false;
            public boolean getDefault() {
                return dfault;
            }
            public void setDefault(boolean dfault) {
                this.dfault = dfault;
            }

            String[] permissions = new String[0];
            String[] inheritance = new String[0];
        }

        Map<String, Group> groups = new HashMap<>();
    }

	@Data
    public static class Users {
		@Data
        public static class User {
            public User() {
                info.put("prefix", "");
                info.put("build", true);
                info.put("suffix", "");
            }

            String[] permissions = new String[0];
            String group = "";
            String[] subgroups = new String[0];
            Map<String, Object> info = new HashMap<>();
        }

        public Map<String, User> users = new HashMap<>();
    }

	@Data
    public static class Config {
	    @Data
        public static class Settings {

		    @lombok.Data
            public static class Config_ {
                boolean opOverrides = true;
                boolean validate_toggle = true;
                boolean allow_commandblocks = false;
            }

		    @lombok.Data
            public static class Data {
			    @lombok.Data
                public static class Save {
                    int minutes = 10;
                    int hours = 24;
                }

                Save save = new Save();
            }

		    @lombok.Data
            public static class Logging {
                String level = "INFO";
            }

            Config_ config = new Config_();
            Data data = new Data();
            Logging logging = new Logging();
            Map<String, Map<String, String[]>> mirrors = new HashMap<>();

            public Settings() {
                Map<String, String[]> mirrorlist = new HashMap<>();
                mirrorlist.put("world_nether", new String[]{"users", "groups"});
                mirrorlist.put("world_the_end", new String[]{"users", "groups"});
                mirrors.put("world", mirrorlist);
            }
        }

        Settings settings = new Settings();
    }
}
