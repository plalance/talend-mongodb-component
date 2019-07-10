package com.plalance.components.source;

import java.io.Serializable;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.meta.Documentation;

import com.plalance.components.dataset.MongoDataset;

@GridLayout({
    // the generated layout put one configuration entry per line,
    // customize it as much as needed
    @GridLayout.Row({ "database" }),
    @GridLayout.Row({ "commentaires" })
})
@Documentation("TODO fill the documentation for this configuration")
public class MongoDbInputMapperConfiguration implements Serializable {
    @Option
    @Documentation("TODO fill the documentation for this parameter")
    private MongoDataset database;

    @Option
    @Documentation("TODO fill the documentation for this parameter")
    private String commentaires;

    public MongoDataset getDatabase() {
        return database;
    }

    public MongoDbInputMapperConfiguration setDatabase(MongoDataset database) {
        this.database = database;
        return this;
    }

    public String getCommentaires() {
        return commentaires;
    }

    public MongoDbInputMapperConfiguration setCommentaires(String commentaires) {
        this.commentaires = commentaires;
        return this;
    }
}