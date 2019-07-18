package com.plalance.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.json.JsonObject;

import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoClientSettings.Builder;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.plalance.components.dataset.MongoDataset;
import com.plalance.components.datastore.MongoDatastore;
import com.plalance.components.service.MongoComponentService;

public class MongoDbClassTest {
	
	
	final MongoComponentService service = new MongoComponentService();

	public static void main(String[] args) {
		MongoDbClassTest instance = new MongoDbClassTest();
		instance.execute();
	}

	/*
	 * Instance Executor
	 */
	private void execute() {
		this.testMongoDb();
	}

	private void testMongoDb() {
		
		MongoDatastore dstore = new MongoDatastore();
		dstore.setDbHost("127.0.0.1");
		dstore.setDbPort(27018);
		dstore.setDbAuthEnabled(false);
		dstore.setDbAuthPassword("");
		dstore.setDbAuthSource("");
		dstore.setDbAuthUser("");
		
		MongoDataset dset = new MongoDataset();
		dset.setDatastore(dstore);
		dset.setRequestDb("nfe204");
		dset.setRequestCollection("movies");
		dset.setQueryLimit(3);
		dset.setRequestQuery("");
		
		MongoClient client = service.initMongoClient(dset);
		
		MongoDatabase db = client.getDatabase(dset.getRequestDb());
		MongoCollection<Document> coll = db.getCollection(dset.getRequestCollection());

		List<Document> docs = new ArrayList<>();
	
		Integer limit = dset.getQueryLimit();

		String requestQuery = Optional.ofNullable(dset.getRequestQuery()) // Value tested
				.filter(s -> !s.isEmpty()) // Filter : string must not be empty
				.orElse("{}"); // other cases : return empty Json object.

		Document queryDoc = Document.parse(requestQuery);

		Iterable<Document> collection;

		if (Optional.ofNullable(limit).isPresent()) {
			coll.find(queryDoc).limit(limit).into(docs);
		} else {
			coll.find(queryDoc).into(docs);
		}

		docs = Optional.ofNullable(docs).orElse(Collections.emptyList());

		List<JsonObject> response = new ArrayList<>();

		for (Document doc : docs) {	
			JsonWriterSettings relaxed = JsonWriterSettings.builder().outputMode(JsonMode.RELAXED).build();
			System.out.println("Json = " + doc.toJson(relaxed));
		}
	}
}
