package com.cerp.components.datastore;

import java.io.Serializable;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.condition.ActiveIf;
import org.talend.sdk.component.api.configuration.constraint.Required;
import org.talend.sdk.component.api.configuration.type.DataStore;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.configuration.ui.widget.Credential;
import org.talend.sdk.component.api.meta.Documentation;

@DataStore("MongoDatastore")
@GridLayout({
		@GridLayout.Row({ "dbHost", "dbPort", "dbAuthEnabled" }),
		@GridLayout.Row({ "dbAuthSource", "dbAuthUser", "dbAuthPassword" })
})
@Documentation("TODO fill the documentation for this configuration")
public class MongoDatastore implements Serializable {

	private static final long serialVersionUID = 1L;

	@Option
	@Required
	@Documentation("The database host")
	private String dbHost;

	@Option
	@Required
	@Documentation("The database port")
	private Integer dbPort;

	@Option
	@Documentation("Auth enabled or not")
	private Boolean dbAuthEnabled;

	@Option
	@ActiveIf(target="dbAuthEnabled", value="true")
	@Documentation("In which database is the user defined, for authentication")
	private String dbAuthSource;

	@Option
	@ActiveIf(target="dbAuthEnabled", value="true")
	@Documentation("The username for authentication")
	private String dbAuthUser;

	@Credential
	@Option
	@ActiveIf(target="dbAuthEnabled", value="true")
	@Documentation("The password for authentication")
	private String dbAuthPassword;

	
	
	public MongoDatastore setDbHost(String dbHost) {
		this.dbHost = dbHost;
		return this;
	}
	
	public MongoDatastore setDbHost(Integer dbPort) {
		this.dbPort = dbPort;
		return this;
	}
	
	public MongoDatastore setDbAuthEnabled(Boolean dbAuthEnabled) {
		this.dbAuthEnabled = dbAuthEnabled;
		return this;
	}
	
	public MongoDatastore setDbAuthSource(String dbAuthSource) {
		this.dbAuthSource = dbAuthSource;
		return this;
	}
	
	public MongoDatastore setDbAuthUser(String dbAuthUser) {
		this.dbAuthUser = dbAuthUser;
		return this;
	}
	
	public MongoDatastore setDbAuthPassword(String dbAuthPassword) {
		this.dbAuthPassword = dbAuthPassword;
		return this;
	}

	/**
	 * @return the dbPort
	 */
	public Integer getDbPort() {
		return dbPort;
	}

	/**
	 * @param dbPort the dbPort to set
	 */
	public void setDbPort(Integer dbPort) {
		this.dbPort = dbPort;
	}

	/**
	 * @return the dbHost
	 */
	public String getDbHost() {
		return dbHost;
	}

	/**
	 * @return the dbAuthEnabled
	 */
	public Boolean getDbAuthEnabled() {
		return dbAuthEnabled;
	}

	/**
	 * @return the dbAuthSource
	 */
	public String getDbAuthSource() {
		return dbAuthSource;
	}

	/**
	 * @return the dbAuthUser
	 */
	public String getDbAuthUser() {
		return dbAuthUser;
	}

	/**
	 * @return the dbAuthPassword
	 */
	public String getDbAuthPassword() {
		return dbAuthPassword;
	}
}