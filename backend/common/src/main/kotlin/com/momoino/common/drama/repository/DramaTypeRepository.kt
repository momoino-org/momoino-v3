package com.momoino.common.drama.repository

import com.momoino.common.drama.entity.DramaType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DramaTypeRepository : JpaRepository<DramaType, String>
