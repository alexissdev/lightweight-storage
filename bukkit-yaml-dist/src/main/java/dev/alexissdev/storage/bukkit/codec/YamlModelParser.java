package dev.alexissdev.storage.bukkit.codec;


import dev.alexissdev.storage.codec.ModelParser;
import dev.alexissdev.storage.model.Model;

import java.util.Map;

/**
 * Interface for parsing YAML-formatted models.
 *
 * This interface extends the {@code ModelParser} interface and specifies the contract
 * for parsing models of type {@code T} from a YAML representation, where the YAML
 * representation is mapped as a {@code Map<String, Object>}.
 *
 * Implementations of this interface are expected to provide mechanisms for converting
 * YAML-formatted data into appropriate model objects by using a {@code ModelReader}
 * to read and interpret the YAML data.
 *
 * @param <T>
 *        The type of the model to be parsed. It must extend the {@code Model} interface,
 *        ensuring that each model has a unique identifier and can be stored or retrieved.
 *
 * @see ModelParser
 * @see Model
 * @see YamlCodec
 */

public interface YamlModelParser<T extends Model>
        extends ModelParser<T, Map<String, Object>> {
}