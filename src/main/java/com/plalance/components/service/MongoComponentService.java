package com.plalance.components.service;

import java.io.StringReader;
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
}