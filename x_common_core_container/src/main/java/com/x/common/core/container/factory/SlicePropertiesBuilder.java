package com.x.common.core.container.factory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.x.base.core.entity.FactorDistributionPolicy;
import com.x.common.core.container.DataMapping;

public class SlicePropertiesBuilder {

	// 20160127要做的事情，删除应用要删除数据字典和work，目前已经删除了信泰的数据字典重复部分，没有来得及删除数据

	private static String driver_db2 = "com.ibm.db2.jcc.DB2Driver";

	private static String driver_mysql = "com.mysql.jdbc.Driver";

	private static String driver_postgresql = "org.postgresql.Driver";

	private static String driver_informix = "com.informix.jdbc.IfxDriver";

	private static String dictionary_db2 = "db2(CreatePrimaryKeys=false)";

	private static String dictionary_mysql = "mysql(clobTypeName=MEDIUMTEXT, blobTypeName=LONGBLOB, CreatePrimaryKeys=false)";

	private static String dictionary_postgresql = "postgres";

	private static String dictionary_informix = "informix";

	// public static Map<String, String> getPropertiesDBCP(List<DataMapping>
	// list) throws Exception {
	// try {
	// if (list.isEmpty()) {
	// throw new Exception("parameter data list is empty.");
	// }
	// Map<String, String> properties = new LinkedHashMap<String, String>();
	// properties.put("openjpa.BrokerFactory", "slice");
	// properties.put("openjpa.jdbc.DBDictionary",
	// determineDBDictionary(list.get(0)));
	// /* 如果是DB2 添加 Schema,mysql 不需要Schema */
	// if (StringUtils.equals(determineDBDictionary(list.get(0)),
	// dictionary_db2)) {
	// properties.put("openjpa.jdbc.Schema", "x");
	// }
	// if (StringUtils.equals(determineDBDictionary(list.get(0)),
	// dictionary_informix)) {
	// properties.put("openjpa.jdbc.Schema", "x");
	// }
	// properties.put("openjpa.slice.Lenient", "true");
	// // properties.put("openjpa.Multithreaded", "true");
	// properties.put("openjpa.slice.DistributionPolicy",
	// FactorDistributionPolicy.class.getName());
	// properties.put("openjpa.slice.Names", getSliceNames(list));
	// properties.put("openjpa.ConnectionDriverName",
	// org.apache.commons.dbcp2.BasicDataSource.class.getCanonicalName());
	// // properties.put("openjpa.ConnectionDriverName",
	// //
	// org.apache.commons.dbcp2.managed.BasicManagedDataSource.class.getCanonicalName());
	// properties.put("openjpa.QueryCompilationCache", "false");
	// properties.put("openjpa.IgnoreChanges", "true");
	// properties.put("openjpa.jdbc.ResultSetType", "scroll-insensitive");
	// // properties.put("openjpa.DynamicEnhancementAgent", "true");
	// properties.put("openjpa.jdbc.SynchronizeMappings",
	// "buildSchema(ForeignKeys=false)");
	// // properties.put("openjpa.jdbc.SchemaFactory",
	// // "native(ForeignKeys=false)");
	// // properties.put("openjpa.Compatibility",
	// // "StrictIdentityValues=true");
	// /* 事务管理 */
	// // properties.put("openjpa.ManagedRuntime",
	// // "jndi(TransactionManagerName=java:/TransactionManager)");
	// /* 锁 */
	// properties.put("openjpa.LockManager", "none");
	// for (int i = 0; i < list.size(); i++) {
	// String slice = getName(i);
	// DataMapping dataMapping = list.get(i);
	// properties.put("openjpa.slice." + slice + ".ConnectionProperties",
	// getConnectionPropertiesDBCP2(dataMapping));
	// // properties.put("openjpa.slice." + slice +
	// // ".ConnectionDriverName",
	// // org.apache.commons.dbcp.BasicDataSource.class.getCanonicalName());
	// properties.put("openjpa.slice." + slice + ".ConnectionDriverName",
	// org.apache.commons.dbcp2.BasicDataSource.class.getName());
	// // properties.put("openjpa.slice." + slice +
	// // ".ConnectionDriverName",
	// //
	// org.apache.commons.dbcp2.managed.BasicManagedDataSource.class.getName());
	// properties.put("openjpa.slice." + slice + ".Log", getLog(dataMapping));
	// properties.put("openjpa.slice." + slice + ".IgnoreChanges", "true");
	// properties.put("openjpa.slice." + slice + ".QueryCompilationCache",
	// "false");
	// properties.put("openjpa.slice." + slice + ".LockManager", "none");
	// properties.put("openjpa.slice." + slice + ".ConnectionFactoryProperties",
	// "PrettyPrint=true, PrettyPrintLineLength=72");
	// }
	// // for (int i = 0; i < list.size(); i++) {
	// // String slice = getName(i);
	// // DataMapping dataMapping = list.get(i);
	// // properties.put("openjpa.slice." + slice +
	// // ".ConnectionProperties",
	// // getConnectionPropertiesDBCP(dataMapping));
	// // properties.put("openjpa.slice." + slice +
	// // ".ConnectionDriverName",
	// // org.apache.commons.dbcp.BasicDataSource.class.getCanonicalName());
	// // // properties.put("openjpa.slice." + slice +
	// // // ".ConnectionDriverName",
	// // //
	// // org.apache.commons.dbcp2.BasicDataSource.class.getCanonicalName());
	// // properties.put("openjpa.slice." + slice + ".Log",
	// // getLog(dataMapping));
	// // properties.put("openjpa.slice." + slice + ".IgnoreChanges",
	// // "true");
	// // properties.put("openjpa.slice." + slice +
	// // ".QueryCompilationCache", "false");
	// // properties.put("openjpa.slice." + slice + ".LockManager",
	// // "none");
	// // properties.put("openjpa.slice." + slice +
	// // ".ConnectionFactoryProperties",
	// // "PrettyPrint=true, PrettyPrintLineLength=72");
	// // }
	// return properties;
	// } catch (Exception e) {
	// throw new Exception("can not convert connection to slice properties", e);
	// }
	// }

	public static Map<String, String> getPropertiesDBCP(List<DataMapping> list) throws Exception {
		try {
			if (list.isEmpty()) {
				throw new Exception("parameter data list is empty.");
			}
			Map<String, String> properties = new LinkedHashMap<String, String>();
			properties.put("openjpa.BrokerFactory", "slice");
			properties.put("openjpa.jdbc.DBDictionary", determineDBDictionary(list.get(0)));
			/* 如果是DB2 添加 Schema,mysql 不需要Schema */
			if (StringUtils.equals(determineDBDictionary(list.get(0)), dictionary_db2)) {
				properties.put("openjpa.jdbc.Schema", "x");
			}
			if (StringUtils.equals(determineDBDictionary(list.get(0)), dictionary_informix)) {
				properties.put("openjpa.jdbc.Schema", "x");
			}
			properties.put("openjpa.slice.Lenient", "true");
			// properties.put("openjpa.Multithreaded", "true");
			properties.put("openjpa.slice.DistributionPolicy", FactorDistributionPolicy.class.getName());
			properties.put("openjpa.slice.Names", getSliceNames(list));
			// properties.put("openjpa.ConnectionDriverName",
			// org.apache.commons.dbcp2.BasicDataSource.class.getName());
			properties.put("openjpa.ConnectionDriverName", com.alibaba.druid.pool.DruidDataSource.class.getName());
			// properties.put("openjpa.ConnectionDriverName",
			// org.apache.commons.dbcp2.managed.BasicManagedDataSource.class.getCanonicalName());
			properties.put("openjpa.QueryCompilationCache", "false");
			properties.put("openjpa.IgnoreChanges", "true");
			properties.put("openjpa.jdbc.ResultSetType", "scroll-insensitive");
			// properties.put("openjpa.DynamicEnhancementAgent", "true");
			properties.put("openjpa.jdbc.SynchronizeMappings", "buildSchema(ForeignKeys=false)");
			// properties.put("openjpa.jdbc.SchemaFactory",
			// "native(ForeignKeys=false)");
			// properties.put("openjpa.Compatibility",
			// "StrictIdentityValues=true");
			/* 事务管理 */
			// properties.put("openjpa.ManagedRuntime",
			// "jndi(TransactionManagerName=java:/TransactionManager)");
			/* 锁 */
			properties.put("openjpa.LockManager", "none");
			for (int i = 0; i < list.size(); i++) {
				String slice = getName(i);
				DataMapping dataMapping = list.get(i);
				properties.put("openjpa.slice." + slice + ".ConnectionProperties",
						getConnectionPropertiesDruid(dataMapping));
				// properties.put("openjpa.slice." + slice +
				// ".ConnectionDriverName",
				// org.apache.commons.dbcp.BasicDataSource.class.getCanonicalName());
				properties.put("openjpa.slice." + slice + ".ConnectionDriverName",
						com.alibaba.druid.pool.DruidDataSource.class.getName());
				// properties.put("openjpa.slice." + slice +
				// ".ConnectionDriverName",
				// org.apache.commons.dbcp2.managed.BasicManagedDataSource.class.getCanonicalName());
				properties.put("openjpa.slice." + slice + ".Log", getLog(dataMapping));
				properties.put("openjpa.slice." + slice + ".IgnoreChanges", "true");
				properties.put("openjpa.slice." + slice + ".QueryCompilationCache", "false");
				properties.put("openjpa.slice." + slice + ".LockManager", "none");
				properties.put("openjpa.slice." + slice + ".ConnectionFactoryProperties",
						"PrettyPrint=true, PrettyPrintLineLength=72");
			}
			return properties;
		} catch (Exception e) {
			throw new Exception("can not convert connection to slice properties", e);
		}
	}

	// openjpa.slice.Names 属性值
	protected static String getSliceNames(List<DataMapping> list) throws Exception {
		try {
			String[] arr = new String[list.size()];
			for (int j = 0; j < list.size(); j++) {
				arr[j] = getName(j);
			}
			return StringUtils.join(arr, ", ");
		} catch (Exception e) {
			throw new Exception("can not create slice names", e);
		}
	}

	// 单个slice名称
	protected static String getName(Integer i) throws Exception {
		try {
			return "s" + ((1001 + i) + "").substring(1);
		} catch (Exception e) {
			throw new Exception("can not create slice name property", e);
		}
	}

	/* 使用DBCP连接池时产生的属性 */
	protected static String getConnectionPropertiesDBCP(DataMapping dataMapping) throws Exception {
		try {
			String str = "MaxActive=50, MaxIdle=2, MinIdle=0, MaxWait=10000, Username=" + dataMapping.getUsername()
					+ ", Password=" + dataMapping.getPassword() + ", TestOnBorrow=true";
			if (StringUtils.equals(determineDBDictionary(dataMapping), dictionary_db2)) {
				str += ", driverClassName=" + driver_db2 + ", url=" + dataMapping.getUrl();
			} else if (StringUtils.equals(determineDBDictionary(dataMapping), dictionary_mysql)) {
				String url = dataMapping.getUrl();
				// url += "?autoReconnect=true";
				str += ", driverClassName=" + driver_mysql + ", url=" + url;
			} else if (StringUtils.equals(determineDBDictionary(dataMapping), dictionary_postgresql)) {
				String url = dataMapping.getUrl();
				str += ", driverClassName=" + driver_postgresql + ", url=" + url;
			} else if (StringUtils.equals(determineDBDictionary(dataMapping), dictionary_informix)) {
				String url = dataMapping.getUrl();
				str += ", driverClassName=" + driver_informix + ", url=" + url;
			}
			return str;
		} catch (Exception e) {
			throw new Exception("can not create connection properites", e);
		}
	}

	/* 使用DBCP2连接池时产生的属性 */
	protected static String getConnectionPropertiesDBCP2(DataMapping dataMapping) throws Exception {
		try {
			String str = "maxTotal=300, maxIdle=2, minIdle=0, maxWaitMillis=30000, timeBetweenEvictionRunsMillis=300000, minEvictableIdleTimeMillis=300000, maxConnLifetimeMillis=1200000, Username="
					+ dataMapping.getUsername() + ", Password=" + dataMapping.getPassword();
			if (StringUtils.equals(determineDBDictionary(dataMapping), dictionary_db2)) {
				str += ", driverClassName=" + driver_db2 + ", url=" + dataMapping.getUrl();
			} else if (StringUtils.equals(determineDBDictionary(dataMapping), dictionary_mysql)) {
				String url = dataMapping.getUrl();
				// url += "?autoReconnect=true";
				str += ", driverClassName=" + driver_mysql + ", url=" + url;
			} else if (StringUtils.equals(determineDBDictionary(dataMapping), dictionary_postgresql)) {
				String url = dataMapping.getUrl();
				str += ", driverClassName=" + driver_postgresql + ", url=" + url;
			} else if (StringUtils.equals(determineDBDictionary(dataMapping), dictionary_informix)) {
				String url = dataMapping.getUrl();
				str += ", driverClassName=" + driver_informix + ", url=" + url;
			}
			return str;
		} catch (Exception e) {
			throw new Exception("can not create connection properites", e);
		}
	}

	/* 使用Druid连接池时产生的属性 */
	protected static String getConnectionPropertiesDruid(DataMapping dataMapping) throws Exception {
		try {
			String str = "filters=stat, poolPreparedStatements=true, maxActive=20, minIdle=0, timeBetweenEvictionRunsMillis=100000, minEvictableIdleTimeMillis=300000, Username="
					+ dataMapping.getUsername() + ", Password=" + dataMapping.getPassword();
			if (StringUtils.equals(determineDBDictionary(dataMapping), dictionary_db2)) {
				str += ", driverClassName=" + driver_db2 + ", url=" + dataMapping.getUrl();
			} else if (StringUtils.equals(determineDBDictionary(dataMapping), dictionary_mysql)) {
				String url = dataMapping.getUrl();
				// url += "?autoReconnect=true";
				str += ", driverClassName=" + driver_mysql + ", url=" + url;
			} else if (StringUtils.equals(determineDBDictionary(dataMapping), dictionary_postgresql)) {
				String url = dataMapping.getUrl();
				str += ", driverClassName=" + driver_postgresql + ", url=" + url;
			} else if (StringUtils.equals(determineDBDictionary(dataMapping), dictionary_informix)) {
				String url = dataMapping.getUrl();
				str += ", driverClassName=" + driver_informix + ", url=" + url;
			}
			return str;
		} catch (Exception e) {
			throw new Exception("can not create connection properites", e);
		}
	}

	/* 获取驱动名称 */
	protected static String getConnectionDriverName(DataMapping dataMapping) throws Exception {
		try {
			if (StringUtils.equals(determineDBDictionary(dataMapping), dictionary_db2)) {
				return driver_db2;
			} else if (StringUtils.equals(determineDBDictionary(dataMapping), dictionary_mysql)) {
				return driver_mysql;
			} else if (StringUtils.equals(determineDBDictionary(dataMapping), dictionary_postgresql)) {
				return driver_postgresql;
			} else if (StringUtils.equals(determineDBDictionary(dataMapping), dictionary_informix)) {
				return driver_informix;
			}
			throw new Exception("database jdbc driver miss match:" + dataMapping.getUrl());
		} catch (Exception e) {
			throw new Exception("can not get driverClassName property", e);
		}
	}

	/* 获取日志属性 */
	protected static String getLog(DataMapping dataMapping) throws Exception {
		try {
			return "Tool=" + dataMapping.getToolLevel() + ", Enhance=" + dataMapping.getEnhanceLevel() + ", METADATA="
					+ dataMapping.getMetaDataLevel() + ", RUNTIME=" + dataMapping.getRuntimeLevel() + ", Query="
					+ dataMapping.getQueryLevel() + ", DataCache=" + dataMapping.getDataCacheLevel() + ", JDBC="
					+ dataMapping.getJdbcLevel() + ", SQL=" + dataMapping.getSqlLevel();
		} catch (Exception e) {
			throw new Exception("can not get log property.", e);
		}
	}

	protected static String determineDBDictionary(DataMapping dataMapping) throws Exception {
		try {
			if (StringUtils.containsIgnoreCase(dataMapping.getUrl(), "jdbc:db2://")) {
				return dictionary_db2;
			} else if (StringUtils.containsIgnoreCase(dataMapping.getUrl(), "jdbc:mysql://")) {
				return dictionary_mysql;
			} else if (StringUtils.containsIgnoreCase(dataMapping.getUrl(), "jdbc:postgresql://")) {
				return dictionary_postgresql;
			} else if (StringUtils.containsIgnoreCase(dataMapping.getUrl(), "jdbc:informix-sqli://")) {
				return dictionary_informix;
			}
			throw new Exception("database jdbc driver miss match:" + dataMapping.getUrl());
		} catch (Exception e) {
			throw new Exception("can not get driverClassName property", e);
		}
	}
}