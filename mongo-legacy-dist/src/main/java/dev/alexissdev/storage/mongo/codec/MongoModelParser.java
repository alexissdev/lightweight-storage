package dev.alexissdev.storage.mongo.codec;


import dev.alexissdev.storage.codec.ModelParser;
import dev.alexissdev.storage.model.Model;
import org.bson.Document;

/**
 * Represents a parser for mapping MongoDB {@code Document} objects into models of type {@code T}.
 *
 * <p>This interface extends the {@code ModelParser} interface, specializing it for use with
 * MongoDB {@code Document} objects as the raw input type. Classes implementing this interface
 * are responsible for transforming raw MongoDB data into domain-specific model instances.
 *
 * @param <T>
 *        The type of the model that extends {@code Model} and will be parsed from a {@code Document}.
 */

public interface MongoModelParser<T extends Model>
        extends ModelParser<T, Document> {
}