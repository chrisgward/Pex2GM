package com.chrisgward.pex2gm.converters;

import com.chrisgward.pex2gm.GM;

import java.util.Map;

public interface Converter {
    public Map<String, GM.Users> generateUsers();

    public GM.Config generateConfig();

    public GM.GlobalGroups generateGlobalGroups();

    public Map<String, GM.Groups> generateGroups();
}
