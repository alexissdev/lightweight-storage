package dev.alexissdev.storage.mongo.codec;

import dev.alexissdev.storage.codec.DelegateObjectModelWriter;
import dev.alexissdev.storage.codec.ModelWriter;
import dev.alexissdev.storage.model.Model;
import org.bson.Document;

/**
 * The DocumentWriter class is a concrete implementation of a ModelWriter
 * tailored for handling Document objects. It provides methods to construct
 * and populate a Document by writing key-value pairs.
 *
 * This class serves as a bridge between the abstract DelegateObjectModelWriter
 * and the specific use case for Document objects. The DocumentWriter allows
 * users to create new Document instances, populate them with data, and
 * finalize their construction.
 *
 * The primary focus of this class is to offer a fluent and flexible API
 * for building documents in applications that require structured data models.
 */

public class DocumentWriter
        extends DelegateObjectModelWriter<Document> {

    private final Document document;

    private DocumentWriter() {
        document = new Document();
    }

    public static ModelWriter<Document> create() {
        return new DocumentWriter();
    }

    public static ModelWriter<Document> create(Model model) {
        return new DocumentWriter()
                .write("_id", model.getId());
    }

    /**
     * It adds a field to the document.
     *
     * @param field
     * 	The name of the field to be added to the document.
     * @param value
     * 	The value to be written.
     *
     * @return Nothing.
     */
    @Override
    public DocumentWriter writeObject(String field, Object value) {
        document.append(field, value);
        return this;
    }

    @Override
    public Document end() {
        return document;
    }
}