package dev.alexissdev.storage.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import dev.alexissdev.storage.dist.RemoteModelService;
import dev.alexissdev.storage.model.Model;
import dev.alexissdev.storage.mongo.codec.DocumentCodec;
import dev.alexissdev.storage.mongo.codec.DocumentReader;
import dev.alexissdev.storage.mongo.codec.MongoModelParser;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/**
 * Service implementation for handling MongoDB-backed models of type {@code T}.
 *
 * <p>This class provides synchronous operations for CRUD functionality in conjunction with
 * MongoDB collections. It extends {@code RemoteModelService<T>} to use its asynchronous
 * execution capabilities while focusing on MongoDB-specific data operations.
 *
 * <p>The service leverages a {@code MongoCollection<Document>} for direct interaction with
 * MongoDB and uses a {@code MongoModelParser<T>} to transform MongoDB documents into domain
 * model instances or encode domain models back into MongoDB documents.
 *
 * @param <T>
 *        The type of the model managed by this service. The type must extend {@code Model}
 *        and implement {@code DocumentCodec}.
 */

public class MongoModelService<T extends Model & DocumentCodec>
        extends RemoteModelService<T> {

    private final MongoCollection<Document> mongoCollection;
    private final MongoModelParser<T> mongoModelParser;

    protected MongoModelService(Executor executor, MongoCollection<Document> mongoCollection,
                                MongoModelParser<T> mongoModelParser) {
        super(executor);

        this.mongoCollection = mongoCollection;
        this.mongoModelParser = mongoModelParser;
    }

    public static <T extends Model & DocumentCodec> MongoModelServiceBuilder<T> builder(Class<T> type) {
        return new MongoModelServiceBuilder<>(type);
    }

    @Override
    public @Nullable T findSync(@NotNull String id) {
        Document document = mongoCollection
                .find(Filters.eq("_id", id))
                .first();

        if (document == null) {
            return null;
        }

        return mongoModelParser.parse(DocumentReader.create(document));
    }

    @Override
    public List<T> findSync(@NotNull String field, @NotNull String value) {
        List<T> models = new ArrayList<>();
        for (Document document : mongoCollection.find(Filters.eq(field, value))) {
            models.add(mongoModelParser.parse(DocumentReader.create(document)));
        }

        return models;
    }

    @Override
    public List<T> findAllSync(@NotNull Consumer<T> postLoadAction) {
        List<Document> documents = mongoCollection.find().into(new ArrayList<>());
        List<T> models = new ArrayList<>();
        for (Document document : documents) {
            T model = mongoModelParser.parse(DocumentReader.create(document));

            postLoadAction.accept(model);
            models.add(model);
        }

        return models;
    }

    @Override
    public void saveSync(@NotNull T model) {
        mongoCollection.replaceOne(
                Filters.eq("_id", model.getId()),
                model.serialize(),
                new ReplaceOptions().upsert(true)
        );
    }

    @Override
    public void deleteSync(@NotNull T model) {
        mongoCollection.deleteOne(Filters.eq("_id", model.getId()));
    }
}