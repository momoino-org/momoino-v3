package com.momoino.common.drama.controller

import com.momoino.common.drama.entity.DramaType
import com.momoino.common.drama.entity.DramaTypeTranslation
import com.momoino.common.drama.repository.DramaTypeRepository
import com.momoino.common.shared.SingleResponse
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

class CreateDramaTypeRequest(
  @field:NotBlank
  @field:Size(min = 1, max = 64)
  val id: String,
  @field:NotEmpty
  val translations: Map<String, DramaTypeTranslation>,
  @field:NotEmpty
  val schema: Map<String, Any>,
)

@RestController
class DramaTypeController(
  private val dramaTypeRepository: DramaTypeRepository,
) {
  @PostMapping("/api/v1/drama/types")
  @ResponseStatus(HttpStatus.CREATED)
  fun createDramaType(
    @Valid @RequestBody body: CreateDramaTypeRequest,
  ): SingleResponse<DramaType> =
    SingleResponse(
      data =
        dramaTypeRepository.saveAndFlush(
          DramaType(
            id = body.id,
            translations = body.translations,
            schema = body.schema,
          ),
        ),
    )
}
