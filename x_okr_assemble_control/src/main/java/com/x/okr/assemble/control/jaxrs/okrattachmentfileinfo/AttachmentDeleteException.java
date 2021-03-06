package com.x.okr.assemble.control.jaxrs.okrattachmentfileinfo;

import com.x.base.core.exception.PromptException;

class AttachmentDeleteException extends PromptException {

	private static final long serialVersionUID = 1859164370743532895L;

	AttachmentDeleteException( Throwable e, String id ) {
		super("系统根据id删除附件信息时发生异常。ID:" + id, e );
	}
}
