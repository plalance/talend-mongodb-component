package com.cerp.components.source;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.json.JsonObject;

import org.bson.Document;
import org.talend.sdk.component.api.component.Icon;
import org.talend.sdk.component.api.component.Version;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.meta.Documentation;
import org.talend.sdk.component.api.processor.ElementListener;
import org.talend.sdk.component.api.processor.Processor;

import com.cerp.components.service.MongoComponentService;
import com.cerp.components.source.MongoDbOutputMapperConfiguration.DateField;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Version(1)
@Icon(Icon.IconType.STAR)
@Processor(name = "MongoDbOutput")
@Documentation("Super Composant...")
public class MongoDbOutputMapper implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final MongoDbOutputMapperConfiguration configuration;
	
	private final MongoComponentService service;

	// Name Of target database when inserting data.
	private MongoDatabase targetDb;

	// Target collection when inserting data.
	private MongoCollection<Document> targetCollection;

	// List of documents, used when bulk mode is active.
	private List<Document> docs = new ArrayList<>();


	/**
	 * Constructor
	 *
	 * @param configuration        MongoDb configuration
	 * @param service              MongoComponent Service
	 * @param recordBuilderFactory Record Builder
	 * @param jsonb                json Builder
	 */
	public MongoDbOutputMapper(@Option("configuration") final MongoDbOutputMapperConfiguration configuration,
			final MongoComponentService service) {
		this.configuration = configuration;
		this.service = service;

	}

	@PostConstruct
	public void init() {
		MongoClient client = service.initMongoClientForOutput(configuration.getDatabase());
		targetDb = client.getDatabase(configuration.getDatabase().getRequestDb());
		targetCollection = targetDb.getCollection(configuration.getDatabase().getRequestCollection());
	}

	// This method is used to pass the incoming data to the output.
	// Every object passed should be a JsonObject instance.
	// This method can include any logic required to write data to the data source.
	@ElementListener
	public void persist(final JsonObject row) {

		System.out.println("Object proccessed : " + row);

		String jsonString = row.toString();
		Document doc = Document.parse(jsonString);
		
		
		
		
		// TODO récup des champs de type date + formats depuis configuration mapper
			
	
		// Update key in Document, change String date with format defined, to Date object.
		for (DateField df : configuration.getDateFields()) {
			
			String docStringDate = doc.getString(df.field);
			doc.remove(df.field);
			
			Date date = null;
			try {
				date = new SimpleDateFormat(df.format).parse(docStringDate);
				doc.append(df.field, date);
			} catch (ParseException e) {
				doc.append(df.field, docStringDate);
				e.printStackTrace();
			}
			
		}
		
		
		// bulkMode = store document in List<Document> docs, docs will be inserted in
		// mongo in the release method.
		if (configuration.getBulkMode() != Boolean.TRUE) {
			targetCollection.insertOne(doc);
			System.out.println("------< Mongo Output Component >------");
			System.out.println("__Input Row: " + jsonString);
			System.out.println("__BSON Doc inserted: " + doc);
			System.out.println("\n");
		} else {
			docs.add(doc);
		}
	}

	@PreDestroy
	public void release() {
		if (configuration.getBulkMode() == Boolean.TRUE) {
			targetCollection.insertMany(docs);
			System.out.println("------< Mongo Output Component >------");
			System.out.println("List of document inserterd.");
		}
	}
}