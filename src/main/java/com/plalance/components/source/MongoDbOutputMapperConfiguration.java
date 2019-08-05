package com.plalance.components.source;

import java.io.Serializable;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.configuration.ui.widget.TextArea;
import org.talend.sdk.component.api.meta.Documentation;

import com.plalance.components.dataset.MongoOutputDataset;

@GridLayout({
		// the generated layout put one configuration entry per line,
		// customize it as much as needed
		@GridLayout.Row({ "database", "bulkMode" }),
		@GridLayout.Row({ "commentaires" }) })
@Documentation("TODO fill the documentation for this configuration")
public class MongoDbOutputMapperConfiguration implements Serializable {

	private static final long serialVersionUID = 1L;

	@Option
	@Documentation("DataStore de ce champs.")
	private MongoOutputDataset database;

	@Option
	@Documentation("Mode Bulk, ce mode insère les données en une seule fois, sous forme de liste au lieu d'utiliser le insertOne(Document) du driver MongoDb")
	private Boolean bulkMode;

	@Option
	@TextArea
	@Documentation("Champs pour décrire l'utilisation que l'on fait de ce composant.")
	private String commentaires;

	public MongoOutputDataset getDatabase() {
		return database;
	}

	public MongoDbOutputMapperConfiguration setDatabase(MongoOutputDataset database) {
		this.database = database;
		return this;
	}

	public String getCommentaires() {
		return commentaires;
	}

	public MongoDbOutputMapperConfiguration setCommentaires(String commentaires) {
		this.commentaires = commentaires;
		return this;
	}

	public Boolean getBulkkMode() {
		return bulkMode;
	}

	public MongoDbOutputMapperConfiguration setBulkMode(Boolean bulkMode) {
		this.bulkMode = bulkMode;
		return this;
	}
}