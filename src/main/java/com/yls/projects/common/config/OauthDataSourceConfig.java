package com.yls.projects.common.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.yls.frame.common.mybatis.PackagesSqlSessionFactoryBean;

@Configuration
@MapperScan(basePackages = OauthDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "oauthSqlSessionFactory")
public class OauthDataSourceConfig {

	// 精确到 szqx 目录，以便跟其他数据源隔离
	static final String PACKAGE = "com.yls.projects.*.daodb";

	static final String MAPPER_LOCATION = "classpath:/mappings/oauth/*.xml";

	static final String TYPE_ALIASES_PACKAGE = "com.yls.projects.*.entity";

	@Value("${oauth.jdbc.url}")
	private String url;

	@Value("${oauth.jdbc.username}")
	private String user;

	@Value("${oauth.jdbc.password}")
	private String password;

	@Value("${oauth.jdbc.driver}")
	private String driverClass;

	@Value("${jdbc.pool.init}")
	private int initialSize;

	@Value("${jdbc.pool.minIdle}")
	private int minIdle;

	@Value("${jdbc.pool.maxActive}")
	private int maxActive;

	@Value("${jdbc.testSql}")
	private String validationQuery;

	@Bean(name = "oauthDataSource")
	public DataSource getDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		// 数据源驱动类
		dataSource.setDriverClassName(driverClass);

		// 基本属性 url、user、password
		dataSource.setUrl(url);
		dataSource.setUsername(user);
		dataSource.setPassword(password);

		// 配置初始化大小、最小、最大
		dataSource.setInitialSize(initialSize);
		dataSource.setMinIdle(minIdle);
		dataSource.setMaxActive(maxActive);

		// 配置获取连接等待超时的时间
		dataSource.setMaxWait(60000);

		// 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
		dataSource.setTimeBetweenConnectErrorMillis(60000);

		// 配置一个连接在池中最小生存的时间，单位是毫秒
		dataSource.setMinEvictableIdleTimeMillis(300000);

		dataSource.setValidationQuery(validationQuery);
		dataSource.setTestWhileIdle(true);
		dataSource.setTestOnBorrow(false);
		dataSource.setTestOnReturn(false);

		// 打开PSCache，并且指定每个连接上PSCache的大小（Oracle使用）
		// dataSource.setPoolPreparedStatements(true);
		// dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);

		return dataSource;
	}

	@Bean(name = "oauthTransactionManager")
	public DataSourceTransactionManager getTransactionManager() {
		return new DataSourceTransactionManager(getDataSource());
	}

	@Bean(name = "oauthSqlSessionFactory")
	public SqlSessionFactory getSqlSessionFactory(@Qualifier("oauthDataSource") DataSource dataSource)
			throws Exception {
		final SqlSessionFactoryBean sessionFactory = new PackagesSqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
		sessionFactory.setVfs(SpringBootVFS.class);
		sessionFactory.setTypeAliasesPackage(TYPE_ALIASES_PACKAGE);
		sessionFactory.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
		return sessionFactory.getObject();
	}

}
