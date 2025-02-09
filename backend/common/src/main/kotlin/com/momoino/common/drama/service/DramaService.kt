package com.momoino.common.drama.service

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.momoino.common.drama.controller.JsonSchemaWalker
import com.networknt.schema.ApplyDefaultsStrategy
import com.networknt.schema.JsonSchema
import com.networknt.schema.JsonSchemaFactory
import com.networknt.schema.SchemaValidatorsConfig
import com.networknt.schema.SpecVersion
import org.springframework.stereotype.Service

@Service
class DramaService {
  fun getJsonSchema(jsonSchema: Any): JsonSchema {
    val objectMapper = ObjectMapper()
    val factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012)
    val schema =
      factory.getSchema(
        objectMapper.valueToTree<JsonNode>(jsonSchema),
        SchemaValidatorsConfig
          .builder()
          .applyDefaultsStrategy(ApplyDefaultsStrategy(true, true, true))
          .build(),
      )

    return schema
  }

  fun generateJsonResponseFromJsonSchema(
    schema: JsonSchema,
    properties: Any,
  ): Map<String, Any?> {
    val objectMapper = ObjectMapper()
    val transformedObject = HashMap<String, Any?>()
    val customizedSchema =
      schema.withConfig(
        SchemaValidatorsConfig
          .builder()
          .applyDefaultsStrategy(ApplyDefaultsStrategy(true, true, true))
          .propertyWalkListener(JsonSchemaWalker(transformedObject))
          .build(),
      )

    customizedSchema.walk(objectMapper.valueToTree(properties), false)

    return transformedObject
  }
}
