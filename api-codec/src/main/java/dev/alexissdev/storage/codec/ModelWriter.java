package dev.alexissdev.storage.codec;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * An interface for writing data into a model representation. This allows the construction
 * of a model by serializing various types of fields and values.
 *
 * @param <R>
 *        The type of the result produced after completing the model writing process.
 */

public interface ModelWriter<R> {

    /**
     * Writes a UUID value to the specified field.
     *
     * @param field
     *        The name of the field where the UUID will be written.
     * @param uuid
     *        The UUID value to be written.
     * @return The current instance of {@code ModelWriter<R>} for method chaining.
     */

    ModelWriter<R> write(String field, UUID uuid);

    /**
     * Writes a Date value to the specified field by converting it to its epoch time representation.
     *
     * @param field
     *        The name of the field where the Date value will be written.
     * @param date
     *        The Date value to be written.
     * @return The current instance of {@code ModelWriter<R>} for method chaining.
     */

    default ModelWriter<R> write(String field, Date date) {
        return write(field, date.getTime());
    }

    ModelWriter<R> write(String field, String value);

    /**
     * Writes a value from an Enum to the specified field.
     *
     * @param field
     *        The name of the field where the Enum value will be written.
     * @param value
     *        The Enum whose name will be written as the field value.
     * @return The current instance of {@code ModelWriter<R>} for method chaining.
     */

    default ModelWriter<R> write(String field, Enum<?> value) {
        return write(field, value.name());
    }

    /**
     * Writes an integer value to the specified field.
     *
     * @param field
     *        The name of the field where the integer value will be written.
     * @param value
     *        The integer value to be written.
     * @return The current instance of {@code ModelWriter<R>} for method chaining.
     */

    ModelWriter<R> write(String field, int value);

    /**
     * Writes a long value to the specified field.
     *
     * @param field
     *        The name of the field where the long value will be written.
     * @param value
     *        The long value to be written.
     * @return The current instance of {@code ModelWriter<R>} for method chaining.
     */

    ModelWriter<R> write(String field, long value);

    /**
     * Writes a double value to the specified field.
     *
     * @param field
     *        The name of the field where the double value will be written.
     * @param value
     *        The double value to be written.
     * @return The current instance of {@code ModelWriter<R>} for method chaining.
     */

    ModelWriter<R> write(String field, double value);

    /**
     * Writes a boolean value to the specified field.
     *
     * @param field
     *        The name of the field where the boolean value will be written.
     * @param value
     *        The boolean value to be written.
     * @return The current instance of {@code ModelWriter<R>} for method chaining.
     */

    ModelWriter<R> write(String field, boolean value);

    /**
     * Writes an object value to the specified field.
     *
     * @param field
     *        The name of the field where the object value will be written.
     * @param value
     *        The object value to be written. This can be any type that represents
     *        the data to serialize into the model.
     * @return The current instance of {@code ModelWriter<R>} for method chaining.
     */

    ModelWriter<R> writeObject(String field, Object value);

    /**
     * Writes a child model codec to the specified field. The child model codec
     * represents a nested or complex structure within the model.
     *
     * @param field
     *        The name of the field where the child model codec will be written.
     * @param child
     *        The child {@code ModelCodec} that will be serialized and written to the field.
     * @return The current instance of {@code ModelWriter<R>} for method chaining.
     */

    ModelWriter<R> write(String field, ModelCodec<R> child);

    /**
     * Writes a collection of child model codecs to the specified field.
     * Each {@code ModelCodec} in the collection represents a nested or complex structure
     * to be serialized and written to the model.
     *
     * @param field
     *        The name of the field where the collection of child model codecs will be written.
     * @param children
     *        The collection of {@code ModelCodec} instances to be serialized and written.
     * @return The current instance of {@code ModelWriter<R>} for method chaining.
     */

    ModelWriter<R> write(String field, Collection<? extends ModelCodec<R>> children);

    /**
     * Writes a mapping of child model codecs to the specified field. The keys of the map are ignored,
     * and only the values (a collection of {@code ModelCodec} instances) are serialized and written to the model.
     *
     * @param field
     *        The name of the field where the collection of child model codecs will be written.
     * @param children
     *        A map containing child {@code ModelCodec} instances. The keys in the map are ignored,
     *        and only the values are used for writing to the specified field.
     * @return The current instance of {@code ModelWriter<R>} for method chaining.
     */

    default ModelWriter<R> write(String field, Map<?, ? extends ModelCodec<R>> children) {
        return write(field, children.values());
    }

    /**
     * Completes the writing process and finalizes the model serialization.
     *
     * @return An instance of type {@code R} representing the result of the completed writing operation.
     */

    R end();
}