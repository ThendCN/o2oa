/** 
 *  Generated by OpenJPA MetaModel Generator Tool.
**/

package com.x.mail.core.entity;

import com.x.base.core.entity.SliceJpaObject_;
import java.lang.String;
import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;

@javax.persistence.metamodel.StaticMetamodel
(value=com.x.mail.core.entity.Account.class)
@javax.annotation.Generated
(value="org.apache.openjpa.persistence.meta.AnnotationProcessor6",date="Fri Mar 10 10:09:22 CST 2017")
public class Account_ extends SliceJpaObject_  {
    public static volatile SingularAttribute<Account,String> account;
    public static volatile SingularAttribute<Account,Date> createTime;
    public static volatile SingularAttribute<Account,String> displayName;
    public static volatile SingularAttribute<Account,String> id;
    public static volatile SingularAttribute<Account,String> incomingMailPort;
    public static volatile SingularAttribute<Account,String> incomingMailServer;
    public static volatile SingularAttribute<Account,String> incomingMailUseSSL;
    public static volatile SingularAttribute<Account,String> mailAddress;
    public static volatile SingularAttribute<Account,String> ownerName;
    public static volatile SingularAttribute<Account,String> password;
    public static volatile SingularAttribute<Account,String> protocol;
    public static volatile SingularAttribute<Account,String> sendMailPort;
    public static volatile SingularAttribute<Account,String> sendMailServer;
    public static volatile SingularAttribute<Account,String> sendMailUseSSL;
    public static volatile SingularAttribute<Account,String> senderName;
    public static volatile SingularAttribute<Account,String> sequence;
    public static volatile SingularAttribute<Account,Date> updateTime;
}
