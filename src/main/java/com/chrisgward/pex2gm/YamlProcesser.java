package com.chrisgward.pex2gm;

import lombok.Getter;
import lombok.Setter;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class YamlProcesser
{
	@Getter
	@Setter
	private PexGroups groups;
	public YamlProcesser(String pexFile)
	{
		Yaml yaml = new Yaml(new Constructor(PexGroups.class));
		groups = (PexGroups)yaml.load(pexFile);
	}
}
