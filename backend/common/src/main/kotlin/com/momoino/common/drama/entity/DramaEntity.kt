package com.momoino.common.drama.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "dramas", schema = "public")
class DramaEntity(
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  val id: UUID? = null,
  @ManyToOne
  @JoinColumn(name = "type_id")
  val type: DramaType,
  @OneToMany(mappedBy = "drama", cascade = [CascadeType.ALL], orphanRemoval = true)
  val translations: MutableList<MultilingualDramaEntity> = mutableListOf(),
)
