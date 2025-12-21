package dev.alexissdev.storage.bukkit;

import dev.alexissdev.storage.ModelService;
import dev.alexissdev.storage.builder.LayoutModelServiceBuilder;
import dev.alexissdev.storage.bukkit.codec.YamlCodec;
import dev.alexissdev.storage.bukkit.codec.YamlModelParser;
import dev.alexissdev.storage.dist.DelegatedCachedModelService;
import dev.alexissdev.storage.model.Model;
import dev.alexissdev.storage.util.Validate;
import org.bukkit.plugin.Plugin;

import java.io.File;

/**
 * Builder class for creating instances of {@code ModelService} specifically tailored
 * for handling YAML-encoded models. This builder supports configuration of YAML model
 * parsing and model storage within a designated folder.
 *
 * This class extends {@code LayoutModelServiceBuilder}, inheriting core functionality
 * for managing executors, caching, and resolver registries while adding YAML-specific
 * configurations.
 *
 * @param <T>
 *        The type of the model that this builder handles. The model must extend the
 *        {@code Model} interface and implement the {@code YamlCodec} for YAML
 *        serialization and deserialization support.
 *
 * @see LayoutModelServiceBuilder
 * @see Model
 * @see YamlCodec
 * @see YamlModelParser
 */

public class YamlModelServiceBuilder<T extends Model & YamlCodec>
        extends LayoutModelServiceBuilder<T, YamlModelServiceBuilder<T>> {

    private YamlModelParser<T> modelParser;
    private File folder;

    protected YamlModelServiceBuilder(Class<T> type) {
        super(type);
    }

    public YamlModelServiceBuilder<T> folder(File folder) {
        this.folder = folder;
        return this;
    }

    public YamlModelServiceBuilder<T> dataFolder(Plugin plugin, String child) {
        return folder(new File(plugin.getDataFolder(), child));
    }

    public YamlModelServiceBuilder<T> dataFolder(Plugin plugin) {
        return folder(plugin.getDataFolder());
    }

    public YamlModelServiceBuilder<T> modelParser(YamlModelParser<T> modelParser) {
        this.modelParser = modelParser;
        return this;
    }

    @Override
    protected YamlModelServiceBuilder<T> back() {
        return this;
    }

    @Override
    public ModelService<T> build() {
        check();
        Validate.notNull(modelParser, "modelParser");
        Validate.notNull(folder, "folder");
        if (!folder.exists()) {
            Validate.state(folder.mkdirs(), "Failed to create folder: " + folder.getName());
        }

        YamlModelService<T> modelService = new YamlModelService<>(executor, folder, modelParser);

        if (cacheModelService == null) {
            return modelService;
        }

        return new DelegatedCachedModelService<>(executor, cacheModelService, resolverRegistry, modelService);
    }
}