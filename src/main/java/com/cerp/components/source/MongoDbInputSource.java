package com.cerp.components.source;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import javax.json.*;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.talend.sdk.component.api.base.BufferizedProducerSupport;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.input.Producer;
import org.talend.sdk.component.api.meta.Documentation;

import com.cerp.components.service.MongoComponentService;

@Documentation("TODO fill the documentation for this source")
public class MongoDbInputSource implements Serializable {
    private final MongoDbInputMapperConfiguration configuration;
    private final MongoComponentService service;
    private final JsonBuilderFactory builderFactory;

    private MongoClient client;
    private MongoDatabase db;

    private transient BufferizedProducerSupport<JsonObject> buffer;

    public MongoDbInputSource(@Option("configuration") final MongoDbInputMapperConfiguration configuration,
                              final MongoComponentService service,
                              final JsonBuilderFactory builderFactory) {
        this.configuration = configuration;
        this.service = service;
        this.builderFactory = builderFactory;
    }

    @PostConstruct
    public void init() {
        // this method will be executed once for the whole component execution,
        // this is where you can establish a connection for instance


        // MongoDb Client
        this.client = MongoClients.create("mongodb://127.0.0.1:27018");
        // Connect to NFE204 base & get movies collection
        this.db = client.getDatabase("nfe204");

        System.out.println("Client connecté : " + this.client.toString());

        // MongoDb Client
        MongoClient client = MongoClients.create("mongodb://127.0.0.1:27018");

        // Connect to NFE204 base & get movies collection
        final MongoDatabase db = client.getDatabase("nfe204");
        final MongoCollection<Document> coll = db.getCollection("movies");

        List<Document> docs = new ArrayList<Document>();
        coll.find().into(docs);
        docs = Optional.ofNullable(docs).orElse(Collections.emptyList());


        Iterator<JsonObject> bufferList = docs.stream() //
                .map(doc -> {
                            System.out.println(" --> create doc : " + doc);
                            return Json.createObjectBuilder() //
                                    .add("titre", doc.getString("title"))//
                                    .build(); //
                        }
                ) //
                .collect(Collectors.toList()) //
                .iterator();


        buffer = new BufferizedProducerSupport<>(() -> bufferList);
    }

    @Producer
    public JsonObject next() {
/*
        System.out.println("--- Producer ---");

        JsonArrayBuilder builder = Json.createBuilderFactory(null).createArrayBuilder();

        // MongoDb Client
        MongoClient client = MongoClients.create("mongodb://127.0.0.1:27018");

        // Connect to NFE204 base & get movies collection
        MongoDatabase db = client.getDatabase("nfe204");
        MongoCollection<Document> coll = db.getCollection("movies");

        ArrayList<Document> docs = new ArrayList<Document>();

        coll.find().into(docs);

        for (Document doc : docs) {
            System.out.println("Titre : " + doc.getString("title"));
            if(doc.getString("title") != null)
                builder.add(doc.getString("title"));
        }

        JsonArray response = builder.build();

        JsonObject jo = Json.createObjectBuilder().add("titre", "yolooo super titre").build();
        System.out.println("Objet retourné : " + jo);

        return jo;*/

        JsonObject o = buffer.next();

        System.out.println("Next Buffer : " + o);

        return o;
    }

    @PreDestroy
    public void release() {
        // this is the symmetric method of the init() one,
        // release potential connections you created or data you cached
        System.out.println(">> Release");

    }
}