package com.plalance.components.dataset;

import java.io.Serializable;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.type.DataSet;
import org.talend.sdk.component.api.configuration.type.meta.ConfigurationType;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.configuration.ui.widget.TextArea;
import org.talend.sdk.component.api.meta.Documentation;
import org.talend.sdk.component.api.service.configuration.Configuration;

import com.plalance.components.datastore.MongoDatastore;

@DataSet("MongoDataset")
@GridLayout({
		// the generated layout put one configuration entry per line,
		// customize it as much as needed
		@GridLayout.Row({ "datastore" }), 
		@GridLayout.Row({ "query" }), 
		@GridLayout.Row({ "limit", "rawMode" }), 
		@GridLayout.Row({ "options" }) })
@Documentation("Configuration du set de données.")
public class MongoDataset implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Option
	@Documentation("Informations de connexion à la base de données.")
	private MongoDatastore datastore;

	@Option
	@TextArea
	@Documentation("Requête mongo: équivalent à l'objet utilisé pour la méthode find();")
	private String query;

	@Option
	@Documentation("TODO fill the documentation for this parameter")
	private String options;

	@Option
	@Documentation("Contenu de la fonction limit() de MongoDb au format Json.")
	private Integer limit;
	
	@Option
	@Documentation("Le contenu doit être renvoyé sous sa forme brute (Json)")
	private Boolean rawMode;

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
	
	public Integer getLimit() {
		return limit;
	}

	public MongoDataset setLimit(Integer limit) {
		this.limit = limit;
		return this;
	}
	
	public Boolean getRawMode() {
		return rawMode;
	}

	public MongoDataset setRawMode(Boolean rawMode) {
		this.rawMode = rawMode;
		return this;
	}
	
}