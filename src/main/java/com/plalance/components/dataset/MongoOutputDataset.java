package com.plalance.components.dataset;

import java.io.Serializable;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.type.DataSet;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.meta.Documentation;

import com.plalance.components.datastore.MongoDatastore;

@DataSet("MongoOutputDataset")
@GridLayout({
		// the generated layout put one configuration entry per line,
		// customize it as much as needed
		@GridLayout.Row({ "datastore" }), 
		@GridLayout.Row({ "requestDb", "requestCollection" }), 
})
@Documentation("Configuration du set de données.")
public class MongoOutputDataset implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Option
	@Documentation("Configuration de la source de donnée (hôte, authentification...).")
	private MongoDatastore datastore;

	@Option
	@Documentation("Database to insert data in.")
	private String requestDb;
	
	@Option
	@Documentation("Collection to insert data in.")
	private String requestCollection;
	
	/**
	 * @return the datastore
	 */
	public MongoDatastore getDatastore() {
		return datastore;
	}

	/**
	 * @param datastore the datastore to set
	 */
	public MongoOutputDataset setDatastore(MongoDatastore datastore) {
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
	public MongoOutputDataset setRequestDb(String requestDb) {
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
	public MongoOutputDataset setRequestCollection(String requestCollection) {
		this.requestCollection = requestCollection;
		return this;
	}	
}