package com.x.base.core.project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.x.base.core.entity.StorageType;
import com.x.base.core.gson.XGsonBuilder;

public class x_meeting_assemble_control extends Assemble {

	public static final String name = "会议管理";
	public static List<String> containerEntities = new ArrayList<>();
	public static List<StorageType> usedStorageTypes = new ArrayList<>();
	public static List<Class<? extends Compilable>> dependents = new ArrayList<>();

	static {
		containerEntities.add("com.x.meeting.core.entity.Building");
		containerEntities.add("com.x.meeting.core.entity.Room");
		containerEntities.add("com.x.meeting.core.entity.Meeting");
		containerEntities.add("com.x.meeting.core.entity.Attachment");
		dependents.add(x_base_core_project.class);
		dependents.add(x_organization_core_express.class);
		dependents.add(x_organization_core_entity.class);
		dependents.add(x_meeting_core_entity.class);
		dependents.add(x_collaboration_core_message.class);
	}

	protected void custom(File lib, String xLib) throws Exception {
		// File xLibDir = new File(xLib);
		// File libDir = new File(lib, "WEB-INF/lib");
		// for (Class<? extends Compilable> clz : dependents) {
		// FileUtils.copyDirectory(xLibDir, libDir, new
		// NameFileFilter(clz.getSimpleName() + "-" + VERSION + ".jar"));
		// }
	}

	public static void main(String[] args) {
		try {
			String str = args[0];
			str = StringUtils.replace(str, "\\", "/");
			Argument arg = XGsonBuilder.instance().fromJson(str, Argument.class);
			x_meeting_assemble_control o = new x_meeting_assemble_control();
			o.pack(arg.getDistPath(), arg.getRepositoryPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
