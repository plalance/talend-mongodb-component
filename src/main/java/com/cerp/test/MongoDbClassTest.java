package com.cerp.test;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;

import com.cerp.components.dataset.MongoDataset;
import com.cerp.components.dataset.MongoOutputDataset;
import com.cerp.components.datastore.MongoDatastore;
import com.cerp.components.service.MongoComponentService;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

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
//		this.insertDataBatch();
	}

	@SuppressWarnings("unused")
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

	@SuppressWarnings("unused")
	private void insertData() {
		MongoDatastore dstore = new MongoDatastore();
		dstore.setDbHost("10.0.4.53");
		dstore.setDbPort(27017);
		dstore.setDbAuthEnabled(true);
		dstore.setDbAuthPassword("OoXuhai8aith4AiR7o");
		dstore.setDbAuthSource("mercure");
		dstore.setDbAuthUser("mercure");
		
		MongoOutputDataset dset = new MongoOutputDataset();
		dset.setDatastore(dstore);
		dset.setRequestDb("mercure");
		dset.setRequestCollection("calendar");
		
		MongoClient client = service.initMongoClientForOutput(dset);
		
		MongoDatabase db = client.getDatabase(dset.getRequestDb());
		MongoCollection<Document> collection = db.getCollection(dset.getRequestCollection());
		
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse("2019-09-12");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Document doc = new Document();
		
		doc.append("_id", UUID.randomUUID().toString());
		doc.append("date", date);
		doc.append("exported", null);
		doc.append("toggle", true);
		
		System.out.println(doc);
		
//		String jsonString = "{\"_id\": \"id5\", \"value\":\"Super insertion !!\"}";
//		System.out.println("String JSON : "+ jsonString);
//		System.out.println("BSON Doc : "+ Document.parse(jsonString));
//		Document doc = Document.parse(jsonString);
		
		
		
		
		collection.insertOne(doc);
	}
	
	private void insertDataBatch() {
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
		
		List<Document> docs = new ArrayList<>();
		
		MongoClient client = service.initMongoClientForOutput(dset);
		
		MongoDatabase db = client.getDatabase(dset.getRequestDb());
		MongoCollection<Document> collection = db.getCollection(dset.getRequestCollection());
		
		String jsonString1 = "{\"_id\": \"id:1\", \"value\":\"Super insertion 1\"}";
		String jsonString2 = "{\"_id\": \"id:2\", \"value\":\"Super insertion 2\"}";
		
		Document doc1 = Document.parse(jsonString1);
		Document doc2 = Document.parse(jsonString2);
		
		docs.add(doc1);
		docs.add(doc2);
		
		collection.insertMany(docs);
	}
	
	@SuppressWarnings("unused")
	private static JsonObject jsonFromString(String jsonObjectStr) {

	    JsonReader jsonReader = Json.createReader(new StringReader(jsonObjectStr));
	    JsonObject object = jsonReader.readObject();
	    jsonReader.close();

	    return object;
	}
}
