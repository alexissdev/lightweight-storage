package dev.alexissdev.storage.builder;

import dev.alexissdev.storage.ModelService;
import dev.alexissdev.storage.model.Model;
import dev.alexissdev.storage.resolve.ResolverRegistry;

import java.util.concurrent.Executor;

public interface ModelServiceBuilder<T extends Model> {

    ModelServiceBuilder<T> executor(Executor executor);

    ModelServiceBuilder<T> cachedService(ModelService<T> cachedService);

    ModelServiceBuilder<T> resolverRegistry(ResolverRegistry<T> resolverRegistry);

    ModelService<T> build();
}