package dev.alexissdev.storage;

import dev.alexissdev.storage.model.Model;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public interface ModelService<T extends Model> {

    Consumer<? extends Model> NOOP = model -> { };
    String ID_FIELD = "id";


    @Nullable T findSync(@NotNull String id);

    @Nullable List<T> findSync(@NotNull String field, @NotNull String value);

    @SuppressWarnings("unchecked")
    default @Nullable List<T> findAllSync() {
        return findAllSync((Consumer<T>) NOOP);
    }

    @Nullable List<T> findAllSync(@NotNull Consumer<T> postLoadAction);

    void saveSync(@NotNull T model);

    void deleteSync(@NotNull T model);

    @Nullable T deleteSync(@NotNull String id);
}