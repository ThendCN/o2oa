package com.x.cms.assemble.control.service;

import java.util.List;

import com.x.base.core.container.EntityManagerContainer;
import com.x.base.core.entity.annotation.CheckPersistType;
import com.x.base.core.entity.annotation.CheckRemoveType;
import com.x.cms.assemble.control.Business;
import com.x.cms.core.entity.element.Form;

public class FormService {

	public List<Form> list(EntityManagerContainer emc, List<String> ids) throws Exception {
		if( ids == null || ids.isEmpty() ){
			return null;
		}
		Business business = new Business( emc );
		return business.getFormFactory().list( ids );
	}

	public List<String> listByAppId(EntityManagerContainer emc, String appId) throws Exception {
		if( appId == null || appId.isEmpty() ){
			return null;
		}
		Business business = new Business( emc );
		return business.getFormFactory().listByAppId( appId );
	}

	public Form id(EntityManagerContainer emc, String id) throws Exception {
		Business business = new Business( emc );
		return business.getFormFactory().get( id );
	}

	public Form save( EntityManagerContainer emc, Form form ) throws Exception {
		if( form == null ){
			throw new Exception( "form is null!" );
		}
		Form form_old = null;
		form_old = emc.find( form.getId(), Form.class );
		emc.beginTransaction( Form.class );
		if( form_old != null ){//update
			form.copyTo( form_old );
			emc.check( form_old, CheckPersistType.all );
		}else{
			emc.persist( form, CheckPersistType.all );
		}
		emc.commit();
		return form;
	}

	public void delete( EntityManagerContainer emc, String id ) throws Exception {
		Form form = emc.find( id, Form.class );
		if( form != null ){
			emc.beginTransaction(Form.class);
			emc.remove(form, CheckRemoveType.all);
			emc.commit();
		}else{
			throw new Exception("form is not exists!");
		}
	}
}
