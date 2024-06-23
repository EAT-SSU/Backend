package com.ssu.eatssu.domain.meal.persistence

import com.ssu.eatssu.domain.meal.entity.Meal
import org.springframework.data.jpa.repository.JpaRepository

interface MealRepository : JpaRepository<Meal, Long> {
}