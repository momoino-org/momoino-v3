package com.momoino.common.drama.entity

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.Type
import java.util.UUID

@Entity
@Table(name = "multilingual_dramas", schema = "public")
class MultilingualDramaEntity(
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  val id: UUID? = null,
  @Column(name = "lang_code", nullable = false)
  val langCode: String,
  @ManyToOne
  @JoinColumn(name = "drama_id")
  val drama: DramaEntity,
  @Type(JsonBinaryType::class)
  @Column(name = "properties", columnDefinition = "jsonb", nullable = false)
  val properties: Map<String, Any> = emptyMap(),
)
