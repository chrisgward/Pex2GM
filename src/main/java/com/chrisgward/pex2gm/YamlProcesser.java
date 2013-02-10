package com.chrisgward.pex2gm;

import com.chrisgward.pex2gm.converters.BPermsGroups;
import com.chrisgward.pex2gm.converters.Converter;
import com.chrisgward.pex2gm.converters.PexGroups;
import com.chrisgward.pex2gm.converters.PrivilegesConverter;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class YamlProcesser implements Servlet
{
	public void init(ServletConfig servletConfig) throws ServletException
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public ServletConfig getServletConfig()
	{
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException
	{
		HttpServletResponse response = (HttpServletResponse)servletResponse;

		DumperOptions options = new DumperOptions();
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		Yaml yaml = new Yaml(options);
        String method = servletRequest.getParameter("method");
        Converter convert;

        if(method != null) {
            if(method.equalsIgnoreCase("bperms") || method.equalsIgnoreCase("bpermissions"))
                convert = yaml.loadAs(servletRequest.getParameter("yaml"), BPermsGroups.class);
            else if (method.equalsIgnoreCase("pex") || method.equalsIgnoreCase("permissionsex"))
                convert = yaml.loadAs(servletRequest.getParameter("yaml"), PexGroups.class);
            else if (method.equalsIgnoreCase("privs") || method.equalsIgnoreCase("privileges"))
                convert = new PrivilegesConverter(servletRequest.getParameter("users"), servletRequest.getParameter("groups"), yaml);
            else
                throw new IllegalArgumentException("You didn't specify a permissions file type!");
        }
        else
            throw new IllegalArgumentException("You didn't specify a permissions file type!");

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);
		zos.putNextEntry(new ZipEntry("config.yml"));
		zos.write(yaml.dump(convert.generateConfig()).split("\n", 2)[1].getBytes());
		zos.putNextEntry(new ZipEntry("globalgroups.yml"));
		zos.write(yaml.dump(convert.generateGlobalGroups()).split("\n", 2)[1].getBytes());


		Map<String, GM.Groups> groupsList = convert.generateGroups();
		for(Map.Entry<String, GM.Groups> groups : groupsList.entrySet())
		{
			zos.putNextEntry(new ZipEntry("worlds/" + groups.getKey() + "/groups.yml"));
			zos.write(yaml.dump(groups.getValue()).split("\n", 2)[1].getBytes());
		}

		Map<String, GM.Users> usersList = convert.generateUsers();
		for(Map.Entry<String, GM.Users> users : usersList.entrySet())
		{
			zos.putNextEntry(new ZipEntry("worlds/" + users.getKey() + "/users.yml"));
			zos.write(yaml.dump(users.getValue()).split("\n", 2)[1].getBytes());
		}

		zos.close();
		response.setHeader("Content-Disposition", "attachment; filename=convert2gm.zip");
		response.setContentType("application/zip");
		response.getOutputStream().write(baos.toByteArray(), 0, baos.toByteArray().length);
	}

	public String getServletInfo()
	{
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public void destroy()
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
