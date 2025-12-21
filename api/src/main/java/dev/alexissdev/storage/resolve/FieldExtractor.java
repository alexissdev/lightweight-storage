package dev.alexissdev.storage.resolve;

import dev.alexissdev.storage.model.Model;

import java.util.function.Function;

public interface FieldExtractor<T extends Model>
        extends Function<T, String> {
}