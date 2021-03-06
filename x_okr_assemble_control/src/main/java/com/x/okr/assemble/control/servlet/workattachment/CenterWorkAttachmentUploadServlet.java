package com.x.okr.assemble.control.servlet.workattachment;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import com.x.base.core.application.servlet.AbstractServletAction;
import com.x.base.core.cache.ApplicationCache;
import com.x.base.core.container.EntityManagerContainer;
import com.x.base.core.container.factory.EntityManagerContainerFactory;
import com.x.base.core.entity.annotation.CheckPersistType;
import com.x.base.core.http.ActionResult;
import com.x.base.core.http.EffectivePerson;
import com.x.base.core.http.WrapOutId;
import com.x.base.core.http.annotation.HttpMethodDescribe;
import com.x.base.core.logger.Logger;
import com.x.base.core.logger.LoggerFactory;
import com.x.base.core.project.server.StorageMapping;
import com.x.okr.assemble.control.ThisApplication;
import com.x.okr.entity.OkrAttachmentFileInfo;
import com.x.okr.entity.OkrCenterWorkInfo;

/**
 * 附件上传服务
 * @author LIYI
 *
 */
@WebServlet(urlPatterns= "/servlet/upload/center/*" )
@MultipartConfig
public class CenterWorkAttachmentUploadServlet extends AbstractServletAction {

	private static final long serialVersionUID = 5628571943877405247L;
	private Logger logger = LoggerFactory.getLogger( CenterWorkAttachmentUploadServlet.class );
	
	@HttpMethodDescribe(value = "上传附件 servlet/upload/center/{id}", response = WrapOutId.class)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ActionResult<List<WrapOutId>> result = new ActionResult< List<WrapOutId> >();
		List<WrapOutId> wraps = new ArrayList<>();
		OkrAttachmentFileInfo okrAttachmentFileInfo = null;
		WrapOutId wrap = null;
		OkrCenterWorkInfo okrCenterWorkInfo = null;
		EffectivePerson effectivePerson = null;
		ServletFileUpload upload = null;
		FileItemIterator fileItemIterator = null;
		FileItemStream item = null;
		String centerId = null;
		String name = null;
		String site = null;
		boolean check = true;
		InputStream input = null;
		request.setCharacterEncoding( "UTF-8" );
		
		if (!ServletFileUpload.isMultipartContent(request)) {
			check = false;
			logger.warn("not mulit part request." ); 
			result.error( new Exception( "请求不是Multipart，无法获取文件信息。" ) );
		}
		if ( check ) {
			try {
				effectivePerson = this.effectivePerson( request );
			} catch (Exception e) {
				check = false;
				result.error(e);
				logger.warn("system get effectivePerson from request url got an exception." ); 
				logger.error(e);
			}
		}
		if( check ){
			try {
				centerId = this.getURIPart( request.getRequestURI(), "center" );
			}catch(Exception e){
				check = false;
				result.error(e);
				logger.warn("system get centerId from request url got an exception." );
				logger.error(e);
			}
		}
		if( check ){
			try {
				try (EntityManagerContainer emc = EntityManagerContainerFactory.instance().create()) {
					okrCenterWorkInfo = emc.find( centerId, OkrCenterWorkInfo.class );
					if ( null == okrCenterWorkInfo ) {
						check = false;
						Exception exception = new CenterWorkNotExistsException( centerId );
						result.error( exception );
						//logger.error( e, effectivePerson, request, null);
					}
				}
			}catch(Exception e){
				check = false;
				Exception exception = new CenterWorkQueryByIdException( e, centerId );
				result.error( exception );
				logger.error( e, effectivePerson, request, null);
			}
		}				
		if( check ){
			try {
				upload = new ServletFileUpload();
				fileItemIterator = upload.getItemIterator( request );
				while ( fileItemIterator.hasNext() ) {
					item = fileItemIterator.next();
					name = item.getFieldName();
					try {
						input = item.openStream();
						if (item.isFormField()) {
							String str = Streams.asString(input);
							if ( StringUtils.equals( name, "site" ) ) {
								site = str;
							}
						} else {
							
							wrap = saveAttachmetFile( effectivePerson.getName(), okrCenterWorkInfo, item, site, input );
							wraps.add( wrap );
						}
					}finally{
						input.close();
					}
				}
				if( wraps != null && !wraps.isEmpty() && site!=null && !site.isEmpty() ){
					for( WrapOutId _wrap : wraps ){
						try( EntityManagerContainer emc = EntityManagerContainerFactory.instance().create();){
							okrAttachmentFileInfo = emc.find( _wrap.getId(), OkrAttachmentFileInfo.class);
							emc.beginTransaction( OkrAttachmentFileInfo.class );
							okrAttachmentFileInfo.setSite(site);
							emc.check( okrAttachmentFileInfo, CheckPersistType.all);
							emc.commit();
						}
					}
				}
				result.setData(wraps);
			}catch(Exception e){
				check = false;
				result.error( e );
				logger.warn( "[UploadServlet]system try to save okrAttachmentFileInfo to Storage got an exception." );
				logger.error(e);
			}
		}
		this.result( response, result );
	}
	
	private WrapOutId saveAttachmetFile( String personName, OkrCenterWorkInfo okrCenterWorkInfo, FileItemStream item, String site, InputStream input ) throws Exception {
		WrapOutId wrap = null;
		OkrAttachmentFileInfo fileInfo = null;
		EntityManagerContainer emc = null;
		StorageMapping mapping = null;
	
		if( item != null ){
			
			emc = EntityManagerContainerFactory.instance().create();
			
			okrCenterWorkInfo = emc.find( okrCenterWorkInfo.getId(), OkrCenterWorkInfo.class);

			emc.beginTransaction( OkrAttachmentFileInfo.class );
			emc.beginTransaction( OkrCenterWorkInfo.class );
			mapping = ThisApplication.context().storageMappings().random( OkrAttachmentFileInfo.class );
			
			fileInfo = concreteAttachment( personName, okrCenterWorkInfo, mapping, this.getFileName( item.getName() ), site );
			
			//先检查对象是否能够被保存，如果能保存，再进行文件存储
			emc.check( fileInfo, CheckPersistType.all);
			
			fileInfo.saveContent( mapping, input, item.getName() );					
			
			if( okrCenterWorkInfo.getAttachmentList() == null ){
				okrCenterWorkInfo.setAttachmentList( new ArrayList<>() );
			}
			if( !okrCenterWorkInfo.getAttachmentList().contains( fileInfo.getId() ) ){
				okrCenterWorkInfo.getAttachmentList().add( fileInfo.getId() );
			}
			emc.check( okrCenterWorkInfo, CheckPersistType.all);
			emc.persist( fileInfo, CheckPersistType.all );
			
			wrap = new WrapOutId( fileInfo.getId());
			
			emc.commit();
			ApplicationCache.notify( OkrAttachmentFileInfo.class );
			ApplicationCache.notify( OkrCenterWorkInfo.class );
		}
		return wrap;
	}
	
	private OkrAttachmentFileInfo concreteAttachment( String person, OkrCenterWorkInfo okrCenterWorkInfo, StorageMapping storage, String name, String site ) throws Exception {
		String fileName = UUID.randomUUID().toString();
		String extension = FilenameUtils.getExtension( name );
		OkrAttachmentFileInfo attachment = new OkrAttachmentFileInfo();
		if ( StringUtils.isEmpty(extension) ) {
			throw new Exception("file extension is empty.");
		}else{
			fileName = fileName + "." + extension;
		}
		if (name.indexOf("\\") > 0) {
			name = StringUtils.substringAfterLast(name, "\\");
		}
		if (name.indexOf("/") > 0) {
			name = StringUtils.substringAfterLast(name, "/");
		}
		attachment.setCreateTime( new Date() );
		attachment.setLastUpdateTime( new Date() );
		attachment.setExtension( extension );
		attachment.setName( name );
		attachment.setFileName( fileName );
		attachment.setStorage( storage.getName() );
		attachment.setWorkInfoId( null );
		attachment.setCenterId( okrCenterWorkInfo.getId() );
		attachment.setStatus( "正常" );
		attachment.setParentType( "中心工作" );
		attachment.setCreatorUid( person );
		attachment.setSite( site );
		attachment.setFileHost( "" );
		attachment.setFilePath( "" );
		attachment.setKey( okrCenterWorkInfo.getId() );
		
		return attachment;
	}
	
}