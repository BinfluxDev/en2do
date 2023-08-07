package eu.koboo.en2do.mongodb.methods.predefined.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import eu.koboo.en2do.mongodb.RepositoryMeta;
import eu.koboo.en2do.mongodb.methods.predefined.PredefinedMethod;
import eu.koboo.en2do.repository.Repository;
import eu.koboo.en2do.repository.methods.fields.UpdateBatch;

import java.lang.reflect.Method;

public class MethodUpdateAllFields<E, ID, R extends Repository<E, ID>> extends PredefinedMethod<E, ID, R> {

    public MethodUpdateAllFields(RepositoryMeta<E, ID, R> meta, MongoCollection<E> entityCollection) {
        super("updateAllFields", meta, entityCollection);
    }

    @Override
    public Object handle(Method method, Object[] arguments) throws Exception {
        // Cast the first object of the array to the UpdateBatch object
        MongoCollection<E> collection = repositoryMeta.getCollection();
        UpdateBatch updateBatch = (UpdateBatch) arguments[0];

        // Call the UpdateBatch on all documents with the "id" field of the entity,
        // which could be a unique name or the "_id" field.
        UpdateResult result = collection.updateMany(repositoryMeta.createIdExistsFilter(),
            repositoryMeta.createUpdateDocument(updateBatch),
            new UpdateOptions().upsert(false));
        return result.wasAcknowledged();
    }
}