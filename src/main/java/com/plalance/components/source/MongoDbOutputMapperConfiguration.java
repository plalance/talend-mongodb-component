package com.plalance.components.source;

import java.io.Serializable;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.configuration.ui.widget.TextArea;
import org.talend.sdk.component.api.meta.Documentation;

import com.plalance.components.dataset.MongoDataset;
import com.plalance.components.dataset.MongoOutputDataset;

@GridLayout({
    // the generated layout put one configuration entry per line,
    // customize it as much as needed
    @GridLayout.Row({ "database" }),
    @GridLayout.Row({ "commentaires" })
})
@Documentation("TODO fill the documentation for this configuration")
public class MongoDbOutputMapperConfiguration implements Serializable {
    @Option
    @Documentation("DataStore de ce champs.")
    private MongoOutputDataset database;

    @Option
	@TextArea
    @Documentation("Champs pour décrire l'utilsiation que l'on fait de ce composant.")
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
}