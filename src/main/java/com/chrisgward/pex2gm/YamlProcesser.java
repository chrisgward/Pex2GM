package com.chrisgward.pex2gm;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

public class YamlProcesser
{
	public YamlProcesser(String groupsfile, Class from)
	{
		Representer r = new Representer();
		Yaml yaml = new Yaml(new Constructor(from));

		Converter convert = (Converter)yaml.load(groupsfile);
		newConfig = yaml.dump(convert.generateConfig()).split("\n", 2)[1];
		newGlobalGroups = yaml.dump(convert.generateGlobalGroups()).split("\n", 2)[1];
		newGroups = yaml.dump(convert.generateGroups()).split("\n", 2)[1];
		newUsers = yaml.dump(convert.generateUsers()).split("\n", 2)[1];
	}

	public String newConfig;
	public String newGlobalGroups;
	public String newGroups;
	public String newUsers;
}
