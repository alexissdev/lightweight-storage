package dev.alexissdev.storage.bukkit.codec;

import dev.alexissdev.storage.codec.ModelCodec;

import java.util.Map;

/**
 * Interface representing a codec for serializing and deserializing YAML data
 * into a map-based object structure.
 *
 * The primary role of this interface is to define a mechanism for encoding
 * and decoding YAML content into a standard Java {@code Map<String, Object>}
 * format. It extends the {@code ModelCodec} interface, which provides a generic
 * representation for model serialization.
 *
 * This interface is expected to be implemented by classes that handle YAML data
 * processing. Implementations should define the logic for reading and writing
 * YAML-formatted content while maintaining the structure of the map-based object.
 *
 * @see ModelCodec
 */

public interface YamlCodec
        extends ModelCodec<Map<String, Object>> {
}