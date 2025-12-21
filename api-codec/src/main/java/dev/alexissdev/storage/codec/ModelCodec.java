package dev.alexissdev.storage.codec;

public interface ModelCodec<R> {

    /**
     * Serializes the current object into a representation of type {@code R}.
     *
     * @return The serialized representation of the current object.
     */

    R serialize();
}
