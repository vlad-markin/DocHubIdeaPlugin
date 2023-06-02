package org.dochub.idea.arch.jsonschema;

import com.intellij.openapi.project.*;
import com.intellij.openapi.util.text.*;
import com.intellij.openapi.vfs.*;
import com.jetbrains.jsonSchema.extension.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class EntityJSONSchemaProviderFactory implements JsonSchemaProviderFactory, DumbAware {

    @Override
    public @NotNull List<JsonSchemaFileProvider> getProviders(@NotNull Project project) {
        return Collections.singletonList(new JsonSchemaFileProvider() {

            @Override
            public boolean isAvailable(@NotNull VirtualFile file) {
                return StringUtil.endsWithIgnoreCase(file.getName(), ".yaml");
            }

            @NotNull
            @Override
            public String getName() {
                return "DocHub.Entity.JSONSchema";
            }

            @Nullable
            @Override
            public VirtualFile getSchemaFile(){
                VirtualFile result = EntityManager.getSchema(project);
                if (result == null) {
                    result = JsonSchemaProviderFactory.getResourceFile(getClass(), "/schemas/empty.json");
                }
                return  result;
            }

            @NotNull
            @Override
            public SchemaType getSchemaType() {
                return SchemaType.embeddedSchema;
            }

        });
    }


}
