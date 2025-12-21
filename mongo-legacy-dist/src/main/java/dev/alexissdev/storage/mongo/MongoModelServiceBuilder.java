package dev.alexissdev.storage.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dev.alexissdev.storage.ModelService;
import dev.alexissdev.storage.builder.LayoutModelServiceBuilder;
import dev.alexissdev.storage.dist.DelegatedCachedModelService;
import dev.alexissdev.storage.model.Model;
import dev.alexissdev.storage.mongo.codec.DocumentCodec;
import dev.alexissdev.storage.mongo.codec.MongoModelParser;
import dev.alexissdev.storage.util.Validate;
import org.bson.Document;

/**
 * Builder class for creating a MongoDB-backed {@code ModelService} instance for managing models
 * of type {@code T}. This builder extends the functionality of {@code LayoutModelServiceBuilder}
 * and adds MongoDB-specific configurations such as database, collection name, and model parser.
 *
 * <p>The main purpose of this class is to provide a fluent API to set up and create a
 * {@code ModelService} configured to work with MongoDB collections.
 *
 * <p>Type parameters:
 * <ul>
 *     <li>{@code T} - The type of the model, which extends {@code Model} and {@code DocumentCodec}.
 * </ul>
 *
 * @param <T> the type of the model being managed by the service
 */

public class MongoModelServiceBuilder<T extends Model & DocumentCodec>
        extends LayoutModelServiceBuilder<T, MongoModelServiceBuilder<T>> {

    private MongoDatabase database;
    private String collectionName;
    private MongoModelParser<T> modelParser;

    protected MongoModelServiceBuilder(Class<T> type) {
        super(type);
    }

    public MongoModelServiceBuilder<T> database(MongoDatabase database) {
        this.database = database;
        return this;
    }

    public MongoModelServiceBuilder<T> modelParser(MongoModelParser<T> modelParser) {
        this.modelParser = modelParser;
        return this;
    }

    public MongoModelServiceBuilder<T> collection(String collection) {
        this.collectionName = collection;
        return this;
    }

    @Override
    public ModelService<T> build() {
        check();
        Validate.notNull(modelParser, "modelParser");
        Validate.notNull(database, "database");
        Validate.notNull(collectionName, "collectionName");

        MongoCollection<Document> collection = database.getCollection(collectionName);
        MongoModelService<T> modelService = new MongoModelService<>(executor, collection, modelParser);
        if (cacheModelService == null) {
            return modelService;
        }

        return new DelegatedCachedModelService<>(executor, cacheModelService, resolverRegistry, modelService);

    }

    @Override
    protected MongoModelServiceBuilder<T> back() {
        return this;
    }
}