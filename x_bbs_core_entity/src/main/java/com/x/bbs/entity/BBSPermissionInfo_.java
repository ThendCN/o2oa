/** 
 *  Generated by OpenJPA MetaModel Generator Tool.
**/

package com.x.bbs.entity;

import com.x.base.core.entity.SliceJpaObject_;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;

@javax.persistence.metamodel.StaticMetamodel
(value=com.x.bbs.entity.BBSPermissionInfo.class)
@javax.annotation.Generated
(value="org.apache.openjpa.persistence.meta.AnnotationProcessor6",date="Sat May 06 19:34:23 CST 2017")
public class BBSPermissionInfo_ extends SliceJpaObject_  {
    public static volatile SingularAttribute<BBSPermissionInfo,Date> createTime;
    public static volatile SingularAttribute<BBSPermissionInfo,String> description;
    public static volatile SingularAttribute<BBSPermissionInfo,String> forumId;
    public static volatile SingularAttribute<BBSPermissionInfo,String> forumName;
    public static volatile SingularAttribute<BBSPermissionInfo,String> id;
    public static volatile SingularAttribute<BBSPermissionInfo,String> mainSectionId;
    public static volatile SingularAttribute<BBSPermissionInfo,String> mainSectionName;
    public static volatile SingularAttribute<BBSPermissionInfo,Integer> orderNumber;
    public static volatile SingularAttribute<BBSPermissionInfo,String> permissionCode;
    public static volatile SingularAttribute<BBSPermissionInfo,String> permissionFunction;
    public static volatile SingularAttribute<BBSPermissionInfo,String> permissionName;
    public static volatile SingularAttribute<BBSPermissionInfo,String> permissionType;
    public static volatile SingularAttribute<BBSPermissionInfo,String> sectionId;
    public static volatile SingularAttribute<BBSPermissionInfo,String> sectionName;
    public static volatile SingularAttribute<BBSPermissionInfo,String> sequence;
    public static volatile SingularAttribute<BBSPermissionInfo,Date> updateTime;
}
