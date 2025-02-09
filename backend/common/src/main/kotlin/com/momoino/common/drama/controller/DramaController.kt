package com.momoino.common.drama.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.momoino.common.drama.entity.DramaEntity
import com.momoino.common.drama.entity.MultilingualDramaEntity
import com.momoino.common.drama.repository.DramaRepository
import com.momoino.common.drama.repository.DramaTypeRepository
import com.momoino.common.drama.service.DramaService
import com.momoino.common.shared.SingleResponse
import com.networknt.schema.ValidationMessage
import com.networknt.schema.walk.JsonSchemaWalkListener
import com.networknt.schema.walk.WalkEvent
import com.networknt.schema.walk.WalkFlow
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

class CreateDramaRequest(
  @field:NotBlank
  val typeId: String,
  @field:NotEmpty
  val translations: Map<String, Map<String, Any>>,
)

data class CreateDramaResponse(
  val id: UUID,
  val typeId: String,
  val translations: MutableMap<String, Map<String, Any?>>,
)

class JsonSchemaWalker(
  private val obj: MutableMap<String, Any?>,
) : JsonSchemaWalkListener {
  override fun onWalkStart(p0: WalkEvent): WalkFlow {
    val key = p0.instanceLocation.getName(p0.instanceLocation.nameCount - 1)
    obj[key] = p0.instanceNode
    return WalkFlow.CONTINUE
  }

  override fun onWalkEnd(
    p0: WalkEvent,
    p1: MutableSet<ValidationMessage>,
  ) {
  }
}

@RestController
class DramaController(
  private val dramaTypeRepository: DramaTypeRepository,
  private val dramaRepository: DramaRepository,
  private val dramaService: DramaService,
) {
  @PostMapping("/api/v1/dramas")
  @ResponseStatus(HttpStatus.CREATED)
  fun createDrama(
    @Valid @RequestBody body: CreateDramaRequest,
  ): SingleResponse<CreateDramaResponse> {
    val dramaTypeSchema = dramaTypeRepository.findByIdOrNull(body.typeId) ?: throw Exception("Drama type \"${body.typeId}\" is not exist")
    val drama = DramaEntity(type = dramaTypeSchema)

    drama.translations.addAll(
      body.translations.map {
        MultilingualDramaEntity(
          langCode = it.key,
          properties = it.value,
          drama = drama,
        )
      },
    )

    val result = dramaRepository.saveAndFlush(drama)
    val resultDTO =
      CreateDramaResponse(
        id = requireNotNull(result.id),
        typeId = result.type.id,
        translations = mutableMapOf(),
      )

    val schema = dramaService.getJsonSchema(dramaTypeSchema.schema)
    result.translations.forEach {
      val generatedObject = dramaService.generateJsonResponseFromJsonSchema(schema, it.properties)
      val errs = schema.validate(ObjectMapper().valueToTree(generatedObject))

      if (errs.isNotEmpty()) {
        throw Exception("Oops! The data is not match with the defined json schema. Details: $errs")
      }

      resultDTO.translations[it.langCode] = generatedObject
    }

    return SingleResponse(data = resultDTO)
  }
}
