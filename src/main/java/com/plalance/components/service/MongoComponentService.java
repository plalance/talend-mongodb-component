package com.plalance.components.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;
import org.talend.sdk.component.api.service.Service;

@Service
public class MongoComponentService {

	public Stream<JsonObject> getDataAsStream(List<Document> list) {

		System.out.println("------< MongoComponentservice : getDataStream() >------");

		return list.stream() //
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
}