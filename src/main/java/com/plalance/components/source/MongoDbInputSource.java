package com.plalance.components.source;

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
import com.plalance.components.service.MongoComponentService;

import org.bson.Document;
import org.talend.sdk.component.api.base.BufferizedProducerSupport;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.input.Producer;
import org.talend.sdk.component.api.meta.Documentation;

@Documentation("TODO fill the documentation for this source")
public class MongoDbInputSource implements Serializable {
	private final MongoDbInputMapperConfiguration configuration;
	private final MongoComponentService service;
	private final JsonBuilderFactory builderFactory;

	private transient BufferizedProducerSupport<JsonObject> buffer;

	public MongoDbInputSource(@Option("configuration") final MongoDbInputMapperConfiguration configuration,
			final MongoComponentService service, final JsonBuilderFactory builderFactory) {
		this.configuration = configuration;
		this.service = service;
		this.builderFactory = builderFactory;
	}

	@PostConstruct
	public void init() {

		System.out.println("------< MongoInput Component >------");
		System.out.println("__ Configuration :");
		System.out.println("__ Host : " + configuration.getDatabase().getDatastore().getHost());
		System.out.println("__ Username : " + configuration.getDatabase().getDatastore().getUsername());
		System.out.println("__ Password : " + configuration.getDatabase().getDatastore().getPassword());
		System.out.println("__ Database Used : " + configuration.getDatabase().getDatastore().getDatabase());
		System.out.println("__ Collection Used : " + configuration.getDatabase().getDatastore().getCollection());
		System.out.println("__ Query : " + configuration.getDatabase().getQuery());
		System.out.println("------< >------");

		// MongoDb Client
		MongoClient client = MongoClients.create(configuration.getDatabase().getDatastore().getHost());

		// Connect to NFE204 base & get movies collection
		final MongoDatabase db = client.getDatabase(configuration.getDatabase().getDatastore().getDatabase());
		final MongoCollection<Document> coll = db
				.getCollection(configuration.getDatabase().getDatastore().getCollection());

		// Bson Document List, target of coll.find() results.
		List<Document> docs = new ArrayList<>();

		System.out.println("query is null ? " + (configuration.getDatabase().getQuery() == null));

		String query = Optional.ofNullable(configuration.getDatabase().getQuery()) // Value tested
				.filter(s -> !s.isEmpty()) // Filter : string must not be emtpy
				.orElse("{}"); // other cases : return empty json object.

		System.out.println("Query prepared :" + query);

		// Generate Bson Doc of query
		Document queryDoc = Document.parse(query);

		// get data
		coll.find(queryDoc).into(docs);

		docs = Optional.ofNullable(docs).orElse(Collections.emptyList());

		Iterator<JsonObject> bufferList = docs.stream() //
				.map(doc -> {

					JsonObjectBuilder builder = Json.createObjectBuilder();

					HashMap<String, Object> keys = new HashMap<>();
					keys.put("id", doc.get("_id"));
					keys.put("title", doc.get("title"));
					keys.put("year", doc.get("year"));
					keys.put("genre", doc.get("genre"));
					keys.put("summary", doc.get("summary"));

					for (Map.Entry<String, Object> e : keys.entrySet()) {
						String key = e.getKey();
						Object value = e.getValue();

						if (value == null)
							builder.add(key, JsonValue.NULL);
						else
							builder.add(key, value.toString());
					}

					return builder.build();
				}) //
				.collect(Collectors.toList()) //
				.iterator();

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