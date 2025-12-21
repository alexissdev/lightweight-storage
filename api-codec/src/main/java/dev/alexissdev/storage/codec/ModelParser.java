package dev.alexissdev.storage.codec;

import dev.alexissdev.storage.model.Model;

public interface ModelParser<T extends Model, R> {

    /**
     * Parses a model using the provided reader.
     *
     * @param reader
     *        The {@code ModelReader} instance used to read and parse the model data.
     * @return The parsed model of type {@code T}.
     */

    T parse(ModelReader<R> reader);
}