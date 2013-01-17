package com.chrisgward.pex2gm;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class YamlProcesser implements Servlet
{
	public String newConfig;
	public String newGlobalGroups;


	@Override
	public void init(ServletConfig servletConfig) throws ServletException
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public ServletConfig getServletConfig()
	{
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException
	{
		HttpServletResponse response = (HttpServletResponse)servletResponse;
		response.setHeader("Content-Disposition", "attachment; filename=pex2gm.zip");
		response.setContentType("application/zip");

		DumperOptions options = new DumperOptions();
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		Yaml yaml = new Yaml(options);
		Converter convert = yaml.loadAs(servletRequest.getParameter("yaml"), PexGroups.class);

		newConfig = yaml.dump(convert.generateConfig()).split("\n", 2)[1];
		newGlobalGroups = yaml.dump(convert.generateGlobalGroups()).split("\n", 2)[1];

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);
		zos.putNextEntry(new ZipEntry("config.yml"));
		zos.write(newConfig.getBytes());
		zos.putNextEntry(new ZipEntry("globalgroups.yml"));
		zos.write(newGlobalGroups.getBytes());


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
		response.getOutputStream().write(baos.toByteArray(), 0, baos.toByteArray().length);
	}

	@Override
	public String getServletInfo()
	{
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void destroy()
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
