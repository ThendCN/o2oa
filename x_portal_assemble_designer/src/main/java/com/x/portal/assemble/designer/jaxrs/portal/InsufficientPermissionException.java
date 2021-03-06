package com.x.portal.assemble.designer.jaxrs.portal;

import com.x.base.core.exception.PromptException;

class InsufficientPermissionException extends PromptException {

	private static final long serialVersionUID = -9089355008820123519L;

	InsufficientPermissionException(String person) {
		super("用户: {} 没有足够的权限.", person);
	}
}
