/*
 * Copyright (c) 2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.networknt.schema;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;

import com.networknt.schema.SpecVersion.VersionFlag;

public class Issue936Test {
    @Test
    void shouldThrowInvalidSchemaException() {
        String schema = "{\r\n" + "  \"$id\": \"0\",\r\n"
                + "  \"$schema\": \"https://json-schema.org/draft/2020-12/schema\"\r\n" + "}";
        SchemaValidatorsConfig config = new SchemaValidatorsConfig();
        config.setSchemaIdValidator(JsonSchemaIdValidator.DEFAULT);
        assertThrowsExactly(InvalidSchemaException.class,
                () -> JsonSchemaFactory.getInstance(VersionFlag.V202012).getSchema(schema, config));
        try {
            JsonSchemaFactory.getInstance(VersionFlag.V202012).getSchema(schema, config);
        } catch (InvalidSchemaException e) {
            assertEquals("/$id: '0' is not a valid $id", e.getMessage());
        }
    }
}
