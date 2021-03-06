package com.x.cms.assemble.control.jaxrs.appcategorypermission;

import java.util.ArrayList;
import java.util.List;

import com.x.base.core.entity.JpaObject;
import com.x.base.core.http.annotation.Wrap;
import com.x.cms.core.entity.AppCategoryPermission;

@Wrap(AppCategoryPermission.class)
public class WrapInAppCategoryPermission extends AppCategoryPermission {
	private static final long serialVersionUID = -5076990764713538973L;
	public static List<String> Excludes = new ArrayList<String>(JpaObject.FieldsUnmodifies);
}