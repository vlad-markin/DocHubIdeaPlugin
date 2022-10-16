package org.dochub.idea.arch.jsonschema;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.jetbrains.jsonSchema.extension.JsonSchemaFileProvider;
import com.jetbrains.jsonSchema.extension.JsonSchemaProviderFactory;
import com.jetbrains.jsonSchema.extension.SchemaType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class EntityJSONSchemaProviderFactory implements JsonSchemaProviderFactory {
    @Override
    public @NotNull List<JsonSchemaFileProvider> getProviders(@NotNull Project project) {
        return Collections.singletonList(new JsonSchemaFileProvider() {
            @Override
            public boolean isAvailable(@NotNull VirtualFile file) {
                return true;
            }

            @NotNull
            @Override
            public String getName() {
                return "DocHub.Entity.JSONSchema";
            }

            @Nullable
            @Override
            public VirtualFile getSchemaFile() {
                VirtualFile result = EntityManager.getSchema(project);
                if (result == null) {
                    result = JsonSchemaProviderFactory.getResourceFile(getClass(), "/schemas/empty.json");
                }
                return result;
            }

            @NotNull
            @Override
            public SchemaType getSchemaType() {
                return SchemaType.embeddedSchema;
            }
        });
    }
}
