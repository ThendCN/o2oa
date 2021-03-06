package com.x.attendance.assemble.control.jaxrs.attendanceappealinfo;

import com.x.base.core.exception.PromptException;

class AttendanceAppealArchiveException extends PromptException {

	private static final long serialVersionUID = 1859164370743532895L;

	AttendanceAppealArchiveException( Throwable e, String id ) {
		super("用户在根据ID归档申诉信息时发生异常！ID:" + id, e );
	}
}
