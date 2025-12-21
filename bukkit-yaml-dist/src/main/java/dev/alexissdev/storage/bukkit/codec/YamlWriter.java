package dev.alexissdev.storage.bukkit.codec;

import dev.alexissdev.storage.codec.DelegateObjectModelWriter;
import dev.alexissdev.storage.codec.ModelCodec;
import dev.alexissdev.storage.codec.ModelWriter;
import dev.alexissdev.storage.model.Model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A concrete implementation of {@code DelegateObjectModelWriter} that writes
 * model data in a format suitable for YAML serialization. The written data
 * is stored in a {@code Map<String, Object>} structure.
 *
 * This class provides methods to create a writer instance with an empty map,
 * a pre-populated map, or based on an existing {@code Model}. It supports
 * adding fields and nested collections while maintaining the YAML-like
 * structure in the underlying map representation.
 */

public class YamlWriter
        extends DelegateObjectModelWriter<Map<String, Object>> {

    private final Map<String, Object> values;

    private YamlWriter(Map<String, Object> values) {
        this.values = values;
    }

    public static ModelWriter<Map<String, Object>> create() {
        return new YamlWriter(new HashMap<>());
    }

    public static ModelWriter<Map<String, Object>> create(Map<String, Object> values) {
        return new YamlWriter(values);
    }

    public static ModelWriter<Map<String, Object>> create(Model model) {
        return create(model, new HashMap<>());
    }

    public static ModelWriter<Map<String, Object>> create(Model model, Map<String, Object> values) {
        return create(values).write("id", model.getId());
    }

    @Override
    public ModelWriter<Map<String, Object>> write(String field, Collection<? extends ModelCodec<Map<String, Object>>> children) {
        Map<String, Object> values = new HashMap<>();
        Iterator<? extends ModelCodec<Map<String, Object>>> iterator = children.iterator();

        int i = 0;
        while (iterator.hasNext()) {
            ModelCodec<Map<String, Object>> codec = iterator.next();
            values.put(String.valueOf(i++), codec.serialize());
        }

        this.values.put(field, values);
        return this;
    }

    @Override
    public YamlWriter writeObject(String field, Object value) {
        values.put(field, value);
        return this;
    }

    @Override
    public Map<String, Object> end() {
        return values;
    }
}