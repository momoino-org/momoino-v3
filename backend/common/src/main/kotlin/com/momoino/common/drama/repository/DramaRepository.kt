package com.momoino.common.drama.repository

import com.momoino.common.drama.entity.DramaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface DramaRepository : JpaRepository<DramaEntity, UUID>
