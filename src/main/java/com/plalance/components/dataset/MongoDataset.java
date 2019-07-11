package com.plalance.components.dataset;

import java.io.Serializable;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.type.DataSet;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.meta.Documentation;

import com.plalance.components.datastore.MongoDatastore;

@DataSet("MongoDataset")
@GridLayout({
		// the generated layout put one configuration entry per line,
		// customize it as much as needed
		@GridLayout.Row({ "datastore" }), 
		@GridLayout.Row({ "query", "limit" }), 
		@GridLayout.Row({ "options" }) })
@Documentation("Configuration du set de données.")
public class MongoDataset implements Serializable {
	@Option
	@Documentation("Informations de connexion à la base de données.")
	private MongoDatastore datastore;

	@Option
	@Documentation("Requête mongo: équivalent à l'objet utilisé pour la méthode find();")
	private String query;

	@Option
	@Documentation("TODO fill the documentation for this parameter")
	private String options;

	@Option
	@Documentation("Contenu de la fonction limit() de MongoDb au format Json.")
	private String limit;

	public MongoDatastore getDatastore() {
		return datastore;
	}

	public MongoDataset setDatastore(MongoDatastore datastore) {
		this.datastore = datastore;
		return this;
	}

	public String getQuery() {
		return query;
	}

	public MongoDataset setQuery(String query) {
		this.query = query;
		return this;
	}

	public String getOptions() {
		return options;
	}

	public MongoDataset setOptions(String options) {
		this.options = options;
		return this;
	}
	
	public String getlimit() {
		return limit;
	}

	public MongoDataset setLimit(String limit) {
		this.limit = limit;
		return this;
	}
	
	
}