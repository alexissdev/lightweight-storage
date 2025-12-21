package dev.alexissdev.storage.mongo.codec;

import dev.alexissdev.storage.codec.ModelCodec;
import org.bson.Document;

/**
 * Represents a codec specifically for serializing and deserializing {@code Document} objects.
 *
 * <p>This interface extends {@code ModelCodec<Document>} to inherit serialization behavior
 * and provides a specialized contract for encoding and decoding {@code Document} domain models.
 *
 * <p>The serialization method inherited from {@code ModelCodec<Document>} is expected
 * to transform a {@code Document} instance into a platform-specific representation
 * while retaining the model's structure and data integrity.
 */

public interface DocumentCodec
        extends ModelCodec<Document> {
}