package com.plalance.components.service;

import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;
import org.talend.sdk.component.api.service.Service;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.MongoClientSettings.Builder;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.plalance.components.dataset.MongoDataset;
import com.plalance.components.dataset.MongoOutputDataset;
import com.plalance.components.datastore.MongoDatastore;

@Service
public class MongoComponentService {

	public Stream<JsonObject> getDataAsStream(List<Document> list) {

		System.out.println("------< MongoComponentservice : getDataStream() >------");
		
		/* JsonWriter Settings */
		JsonWriterSettings relaxed = JsonWriterSettings.builder().outputMode(JsonMode.RELAXED).build();
		
		return list.stream() //
				.map(doc -> {
					
					String jsonString = doc.toJson(relaxed);
					
					return jsonFromString(jsonString);
					
				});
	}

	public Stream<JsonObject> getDataAsStreamRawJsonObject(List<Document> list) {

		/** Json Writer Settings **/
		JsonWriterSettings relaxed = JsonWriterSettings.builder().outputMode(JsonMode.RELAXED).build();

		return list.stream() //
				.map(doc -> {

					JsonObjectBuilder builder = Json.createObjectBuilder();

					builder.add("string", doc.toJson(relaxed));

					return builder.build();

				});
	}
	
	private static JsonObject jsonFromString(String jsonObjectStr) {

	    JsonReader jsonReader = Json.createReader(new StringReader(jsonObjectStr));
	    JsonObject object = jsonReader.readObject();
	    jsonReader.close();

	    return object;
	}
	
	/**
	 * Creates MongoClient instance based on DataStore configuration and returns it.
	 * @param dset
	 * @return
	 */
	public MongoClient initMongoClient(MongoDataset dset) {
		
		System.out.println("------< Mongo Input Component >------");	
		System.out.println("__ Host : " + dset.getDatastore().getDbHost());
		System.out.println("__ Username : " + dset.getDatastore().getDbAuthUser());
		System.out.println("__ Password : " + dset.getDatastore().getDbAuthPassword());
		System.out.println("----");		
		System.out.println("__ Database Used : " + dset.getRequestDb());
		System.out.println("__ Collection Used : " + dset.getRequestCollection());
		System.out.println("__ Query : " + dset.getRequestQuery());
		System.out.println("__ Limit : " + dset.getQueryLimit());
		System.out.println("------------");
		
		MongoDatastore dstore = dset.getDatastore();
		
		Builder connectionBuilder = MongoClientSettings.builder()
				.applyToClusterSettings(builder -> builder.hosts(
						Arrays.asList(new ServerAddress(dstore.getDbHost(), dstore.getDbPort()))));

		if (dset.getDatastore().getDbAuthEnabled()) {
			MongoCredential credential = MongoCredential
					.createCredential(dstore.getDbAuthUser(), dstore.getDbAuthSource(), dstore.getDbAuthPassword().toCharArray());

			System.out.println("Authentication credential : " + credential);
			
			connectionBuilder.credential(credential);
		}

		MongoClient client = MongoClients.create(connectionBuilder.build());
		
		return client;
	}
	
	/**
	 * Creates MongoClient instance based on DataStore configuration and returns it.
	 * @param dset
	 * @return
	 */
	public MongoClient initMongoClientForOutput(MongoOutputDataset dset) {
		
		System.out.println("------< Mongo Output Component >------");	
		System.out.println("__ Host : " + dset.getDatastore().getDbHost());
		System.out.println("__ Username : " + dset.getDatastore().getDbAuthUser());
		System.out.println("__ Password : " + dset.getDatastore().getDbAuthPassword());
		System.out.println("----");		
		System.out.println("__ Database Used : " + dset.getRequestDb());
		System.out.println("__ Collection Used : " + dset.getRequestCollection());
		System.out.println("------------");
		
		MongoDatastore dstore = dset.getDatastore();
		
		Builder connectionBuilder = MongoClientSettings.builder()
				.applyToClusterSettings(builder -> builder.hosts(
						Arrays.asList(new ServerAddress(dstore.getDbHost(), dstore.getDbPort()))));

		if (dset.getDatastore().getDbAuthEnabled()) {
			MongoCredential credential = MongoCredential
					.createCredential(dstore.getDbAuthUser(), dstore.getDbAuthSource(), dstore.getDbAuthPassword().toCharArray());

			System.out.println("Authentication credential : " + credential);
			
			connectionBuilder.credential(credential);
		}

		MongoClient client = MongoClients.create(connectionBuilder.build());
		
		return client;
	}
}