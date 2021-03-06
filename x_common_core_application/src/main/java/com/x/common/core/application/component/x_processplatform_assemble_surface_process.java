package com.x.common.core.application.component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang3.StringUtils;

import com.x.base.core.gson.XGsonBuilder;

public class x_processplatform_assemble_surface_process extends Assemble {

	public static List<String> containerEntities = new ArrayList<>();

	static {
		containerEntities.add("com.x.processplatform.core.entity.element.End");
		containerEntities.add("com.x.processplatform.core.entity.element.Application");
		containerEntities.add("com.x.processplatform.core.entity.element.Script");
		containerEntities.add("com.x.processplatform.core.entity.element.Cancel");
		containerEntities.add("com.x.processplatform.core.entity.element.Merge");
		containerEntities.add("com.x.processplatform.core.entity.element.Route");
		containerEntities.add("com.x.processplatform.core.entity.element.Choice");
		containerEntities.add("com.x.processplatform.core.entity.element.Invoke");
		containerEntities.add("com.x.processplatform.core.entity.element.Manual");
		containerEntities.add("com.x.processplatform.core.entity.element.Parallel");
		containerEntities.add("com.x.processplatform.core.entity.element.Begin");
		containerEntities.add("com.x.processplatform.core.entity.element.Split");
		containerEntities.add("com.x.processplatform.core.entity.element.Condition");
		containerEntities.add("com.x.processplatform.core.entity.element.Message");
		containerEntities.add("com.x.processplatform.core.entity.element.Process");
		containerEntities.add("com.x.processplatform.core.entity.element.Service");
		containerEntities.add("com.x.processplatform.core.entity.element.Agent");
		containerEntities.add("com.x.processplatform.core.entity.element.Delay");
		containerEntities.add("com.x.processplatform.core.entity.element.Form");
		containerEntities.add("com.x.processplatform.core.entity.element.Embed");
	}

	protected void custom(File dir, String repositoryPath) throws Exception {
		File repository = new File(repositoryPath);
		File lib = new File(dir, "WEB-INF/lib");
		FileUtils.copyDirectory(repository, lib, new WildcardFileFilter("x_organization_core_entity*.jar"));
		FileUtils.copyDirectory(repository, lib, new WildcardFileFilter("x_organization_core_express*.jar"));
		FileUtils.copyDirectory(repository, lib, new WildcardFileFilter("x_processplatform_core_surface*.jar"));
		FileUtils.copyDirectory(repository, lib, new WildcardFileFilter("x_processplatform_core_entity*.jar"));
	}

	public static void main(String[] args) {
		try {
			String str = args[0];
			str = StringUtils.replace(str, "\\", "/");
			Argument arg = XGsonBuilder.instance().fromJson(str, Argument.class);
			x_processplatform_assemble_surface_process o = new x_processplatform_assemble_surface_process();
			o.pack(arg.getDistPath(), arg.getRepositoryPath(), arg.getCenterHost(), arg.getCenterPort(),
					arg.getCenterCipher(), arg.getConfigApplicationServer());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
