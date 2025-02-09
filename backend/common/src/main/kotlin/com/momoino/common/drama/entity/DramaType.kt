package com.momoino.common.drama.entity

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.Type

data class DramaTypeTranslation(
  val label: String,
  val description: String?,
)

@Entity
@Table(name = "drama_types", schema = "public")
class DramaType(
  @Id
  @Column(name = "id", length = 64, nullable = false, unique = true, insertable = true, updatable = false)
  val id: String,
  @Type(JsonBinaryType::class)
  @Column(name = "translations", columnDefinition = "jsonb", nullable = false)
  val translations: Map<String, DramaTypeTranslation> = emptyMap(),
  @Type(JsonBinaryType::class)
  @Column(name = "schema", columnDefinition = "jsonb", nullable = false)
  val schema: Map<String, Any> = emptyMap(),
  @OneToMany(mappedBy = "type")
  val dramas: Set<DramaEntity> = emptySet(),
)
