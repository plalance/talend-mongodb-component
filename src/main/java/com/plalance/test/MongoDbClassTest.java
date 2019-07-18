package com.plalance.test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.plalance.components.dataset.MongoDataset;
import com.plalance.components.dataset.MongoOutputDataset;
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
//		this.recuperationData();
		this.insertData();
	}

	private void recuperationData() {
		
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

	private void insertData() {
		MongoDatastore dstore = new MongoDatastore();
		dstore.setDbHost("127.0.0.1");
		dstore.setDbPort(27018);
		dstore.setDbAuthEnabled(false);
		dstore.setDbAuthPassword("");
		dstore.setDbAuthSource("");
		dstore.setDbAuthUser("");
		
		MongoOutputDataset dset = new MongoOutputDataset();
		dset.setDatastore(dstore);
		dset.setRequestDb("nfe204");
		dset.setRequestCollection("talend");
		
		MongoClient client = service.initMongoClientForOutput(dset);
		
		MongoDatabase db = client.getDatabase(dset.getRequestDb());
		MongoCollection<Document> collection = db.getCollection(dset.getRequestCollection());
		
		String jsonString = "{\"_id\": \"id3\", \"value\":\"Super insertion !!\"}";
		
		System.out.println("String JSON : "+ jsonString);
		System.out.println("BSON Doc : "+ Document.parse(jsonString));
		
		Document doc = Document.parse(jsonString);
		collection.insertOne(doc);
	}
	
	private static JsonObject jsonFromString(String jsonObjectStr) {

	    JsonReader jsonReader = Json.createReader(new StringReader(jsonObjectStr));
	    JsonObject object = jsonReader.readObject();
	    jsonReader.close();

	    return object;
	}
}
