package com.cerp.components.datastore;

import java.io.Serializable;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.type.DataStore;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.configuration.ui.widget.Credential;
import org.talend.sdk.component.api.meta.Documentation;

@DataStore("MongoDatastore")
@GridLayout({
    // the generated layout put one configuration entry per line,
    // customize it as much as needed
    @GridLayout.Row({ "host" }),
    @GridLayout.Row({ "username" }),
    @GridLayout.Row({ "password" }),
    @GridLayout.Row({ "database" }),
    @GridLayout.Row({ "collection" })
})
@Documentation("TODO fill the documentation for this configuration")
public class MongoDatastore implements Serializable {
    @Option
    @Documentation("TODO fill the documentation for this parameter")
    private String host;

    @Option
    @Documentation("TODO fill the documentation for this parameter")
    private String username;

    @Credential
    @Option
    @Documentation("TODO fill the documentation for this parameter")
    private String password;

    @Option
    @Documentation("TODO fill the documentation for this parameter")
    private String database;

    @Option
    @Documentation("TODO fill the documentation for this parameter")
    private String collection;

    public String getHost() {
        return host;
    }

    public MongoDatastore setHost(String host) {
        this.host = host;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public MongoDatastore setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public MongoDatastore setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getDatabase() {
        return database;
    }

    public MongoDatastore setDatabase(String database) {
        this.database = database;
        return this;
    }

    public String getCollection() {
        return collection;
    }

    public MongoDatastore setCollection(String collection) {
        this.collection = collection;
        return this;
    }
}