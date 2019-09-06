package com.cerp.components.source;

import java.io.Serializable;
import java.util.List;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.configuration.ui.widget.TextArea;
import org.talend.sdk.component.api.meta.Documentation;

import com.cerp.components.dataset.MongoOutputDataset;

@GridLayout({
		@GridLayout.Row({ "database", "bulkMode" }),
		@GridLayout.Row({ "dateFields" }),
		@GridLayout.Row({ "commentaires" })
})
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
    @Documentation("")
    private List<DateField> dateFields;

    public static class DateField {

        @Option
        @Documentation("Champs à convertir en date")
        public String field;

        @Option
        @Documentation("Format de date de ce champs")
        public String format;
    }

	
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

	public Boolean getBulkMode() {
		return bulkMode;
	}

	public MongoDbOutputMapperConfiguration setBulkMode(Boolean bulkMode) {
		this.bulkMode = bulkMode;
		return this;
	}

	/**
	 * @return the dateFields
	 */
	public List<DateField> getDateFields() {
		return dateFields;
	}

	/**
	 * @param dateFields the dateFields to set
	 */
	public MongoDbOutputMapperConfiguration setDateFields(List<DateField> dateFields) {
		this.dateFields = dateFields;
		return this;
	}
}