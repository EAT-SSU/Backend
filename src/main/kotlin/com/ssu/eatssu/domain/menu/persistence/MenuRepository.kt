package com.ssu.eatssu.domain.menu.persistence

import com.ssu.eatssu.domain.menu.entity.Menu
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MenuRepository : JpaRepository<Menu, Long>