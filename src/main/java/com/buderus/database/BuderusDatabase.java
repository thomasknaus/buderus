package com.buderus.database;

import org.dizitart.no2.Cursor;
import org.dizitart.no2.Document;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.NitriteCollection;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import static org.dizitart.no2.filters.Filters.eq;

@Service
public class BuderusDatabase {

    Nitrite database = null;

    //@PostConstruct
    public void initDB() {
        if (this.database == null) {
            setDatabase(Nitrite.builder().compressed().filePath("/tmp/buderus.db").openOrCreate());
        } else if (this.database.isClosed()) {
            setDatabase(Nitrite.builder().compressed().filePath("/tmp/buderus.db").openOrCreate());
        }
        for (String collection : getDatabase().listCollectionNames()) {
            getDatabase().getCollection(collection).dropAllIndices();
            getDatabase().getContext().dropCollection(collection);
            getDatabase().commit();
        }
    }

    private NitriteCollection createCollection(final String name) {
        // Create a Nitrite Collection
        return getDatabase().getCollection(name);
    }

    public Document createDocument(String key, Object value) {
        // create a document to populate data
        Document doc = new Document();
        doc.put("topic", key);
        doc.put("data", value);
        return doc;
    }

    public void insertDocument(final String collection, final String key, final Object value) {
        NitriteCollection nitriteCollection = createCollection(collection);
        Object result = find(collection, key);
        if (result != null) {
            nitriteCollection.update(eq("topic", key), createDocument(key, value));
        } else {
            nitriteCollection.insert(createDocument(key, value));
        }
        getDatabase().commit();
    }

    public Object find(final String collection, final String key) {
        NitriteCollection databaseCollection = getDatabase().getCollection(collection);
        Cursor cursor = databaseCollection.find(eq("topic", key));
        for (Document document : cursor) {
            return document.get("data");
        }
        return null;
    }

    public Nitrite getDatabase() {
        return this.database;
    }

    public void setDatabase(Nitrite database) {
        this.database = database;
    }
}
