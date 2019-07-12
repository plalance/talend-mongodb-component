package com.plalance.test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDbClassTest {

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
		MongoClient client = MongoClients.create("mongodb://127.0.0.1:27018");
		MongoDatabase db = client.getDatabase("nfe204");
		MongoCollection<Document> coll = db.getCollection("movies");

		List<Document> docs = new ArrayList<>();
		String query = "";
		Integer limit = 3;

		query = Optional.ofNullable(query) // Value tested
				.filter(s -> !s.isEmpty()) // Filter : string must not be emtpy
				.orElse("{}"); // other cases : return empty json object.

		Document queryDoc = Document.parse(query);

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
