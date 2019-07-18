package com.plalance.components.source;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.json.JsonObject;

import org.bson.Document;
import org.talend.sdk.component.api.base.BufferizedProducerSupport;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.input.Producer;
import org.talend.sdk.component.api.meta.Documentation;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.plalance.components.service.MongoComponentService;

@Documentation("TODO fill the documentation for this source")
public class MongoDbInputSource implements Serializable {
	
	private final MongoDbInputMapperConfiguration configuration;
	
	private final MongoComponentService service;

	private transient BufferizedProducerSupport<JsonObject> buffer;

	public MongoDbInputSource(@Option("configuration") final MongoDbInputMapperConfiguration configuration,
			final MongoComponentService service) {
		this.configuration = configuration;
		this.service = service;
	}

	@PostConstruct
	public void init() {
		// MongoDb Client
		MongoClient client = service.initMongoClient(configuration.getDatabase());

		// Connect to NFE204 base & get movies collection
		final MongoDatabase db = client.getDatabase(configuration.getDatabase().getRequestDb());
		final MongoCollection<Document> coll = db
				.getCollection(configuration.getDatabase().getRequestCollection());

		// Bson Document List, target of coll.find() results.
		List<Document> docs = new ArrayList<>();

		System.out.println("query is null ? " + (configuration.getDatabase().getRequestQuery() == null));

		String query = Optional.ofNullable(configuration.getDatabase().getRequestQuery()) // Value tested
				.filter(s -> !s.isEmpty()) // Filter : string must not be emtpy
				.orElse("{}"); // other cases : return empty json object.

		System.out.println("Query prepared :" + query);

		// Generate Bson Doc of query
		Document queryDoc = Document.parse(query);

		
		if(Optional.ofNullable(configuration.getDatabase().getQueryLimit()).isPresent()) {
			coll.find(queryDoc).limit(configuration.getDatabase().getQueryLimit()).into(docs);	
		}else {
			coll.find(queryDoc).into(docs);
		}

		docs = Optional.ofNullable(docs).orElse(Collections.emptyList());
		
		Iterator<JsonObject> bufferList;
		
		if(configuration.getDatabase().getRawMode().equals(Boolean.TRUE)) {
			bufferList = service.getDataAsStreamRawJsonObject(docs) //
					.collect(Collectors.toList()) //
					.iterator();
		}else{
			bufferList = service.getDataAsStream(docs) //
			.collect(Collectors.toList()) //
			.iterator();	
		}
		
		buffer = new BufferizedProducerSupport<>(() -> bufferList);

	}

	@Producer
	public JsonObject next() {

		JsonObject o = buffer.next();

		return o;
	}

	@PreDestroy
	public void release() {
		// this is the symmetric method of the init() one,
		// release potential connections you created or data you cached
		System.out.println(">> Release");

	}
}