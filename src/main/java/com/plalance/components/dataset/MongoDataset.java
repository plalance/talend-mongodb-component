package com.plalance.components.dataset;

import java.io.Serializable;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.type.DataSet;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.configuration.ui.widget.TextArea;
import org.talend.sdk.component.api.meta.Documentation;

import com.plalance.components.datastore.MongoDatastore;

@DataSet("MongoDataset")
@GridLayout({
		// the generated layout put one configuration entry per line,
		// customize it as much as needed
		@GridLayout.Row({ "datastore" }), 
		@GridLayout.Row({ "requestDb", "requestCollection" }), 
		@GridLayout.Row({ "requestQuery" }), 
		@GridLayout.Row({ "queryLimit", "rawMode" })
})
@Documentation("Configuration du set de données.")
public class MongoDataset implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Option
	@Documentation("Configuration de la source de donnée (hôte, authentification...).")
	private MongoDatastore datastore;

	@Option
	@Documentation("Database to query in.")
	private String requestDb;
	
	@Option
	@Documentation("Collection to apply query on.")
	private String requestCollection;
	
	@Option
	@TextArea
	@Documentation("Query to apply == Json argument of find() method.")
	private String requestQuery;
	
	@Option
	@Documentation("Limit query results.")
	private Integer queryLimit;
	
	@Option
	@Documentation("Le contenu doit être renvoyé sous sa forme brute (Json)")
	private Boolean rawMode;

	/**
	 * @return the datastore
	 */
	public MongoDatastore getDatastore() {
		return datastore;
	}

	/**
	 * @param datastore the datastore to set
	 */
	public MongoDataset setDatastore(MongoDatastore datastore) {
		this.datastore = datastore;
		return this;
	}

	/**
	 * @return the requestDb
	 */
	public String getRequestDb() {
		return requestDb;
	}

	/**
	 * @param requestDb the requestDb to set
	 */
	public MongoDataset setRequestDb(String requestDb) {
		this.requestDb = requestDb;
		return this;
	}

	/**
	 * @return the requestCollection
	 */
	public String getRequestCollection() {
		return requestCollection;
	}

	/**
	 * @param requestCollection the requestCollection to set
	 */
	public MongoDataset setRequestCollection(String requestCollection) {
		this.requestCollection = requestCollection;
		return this;
	}

	/**
	 * @return the requestQuery
	 */
	public String getRequestQuery() {
		return requestQuery;
	}

	/**
	 * @param requestQuery the requestQuery to set
	 */
	public MongoDataset setRequestQuery(String requestQuery) {
		this.requestQuery = requestQuery;
		return this;
	}

	/**
	 * @return the queryLimit
	 */
	public Integer getQueryLimit() {
		return queryLimit;
	}

	/**
	 * @param queryLimit the queryLimit to set
	 */
	public MongoDataset setQueryLimit(Integer queryLimit) {
		this.queryLimit = queryLimit;
		return this;
	}

	/**
	 * @return the rawMode
	 */
	public Boolean getRawMode() {
		return rawMode;
	}

	/**
	 * @param rawMode the rawMode to set
	 */
	public MongoDataset setRawMode(Boolean rawMode) {
		this.rawMode = rawMode;
		return this;
	}
	
}