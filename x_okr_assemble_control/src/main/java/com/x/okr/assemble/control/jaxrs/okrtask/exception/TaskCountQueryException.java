package com.x.okr.assemble.control.jaxrs.okrtask.exception;

import com.x.base.core.exception.PromptException;

public class TaskCountQueryException extends PromptException {

	private static final long serialVersionUID = 1859164370743532895L;

	public TaskCountQueryException( Throwable e, String flag ) {
		super("系统在根据用户姓名获取待办总数时发生异常!Flag:" + flag );
	}
}
