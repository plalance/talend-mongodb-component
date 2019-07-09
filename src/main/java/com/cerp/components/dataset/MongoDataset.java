package com.cerp.components.dataset;

import java.io.Serializable;

import com.cerp.components.datastore.MongoDatastore;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.type.DataSet;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.meta.Documentation;

@DataSet("MongoDataset")
@GridLayout({
    // the generated layout put one configuration entry per line,
    // customize it as much as needed
    @GridLayout.Row({ "datastore" }),
    @GridLayout.Row({ "query" }),
    @GridLayout.Row({ "options" })
})
@Documentation("TODO fill the documentation for this configuration")
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
}