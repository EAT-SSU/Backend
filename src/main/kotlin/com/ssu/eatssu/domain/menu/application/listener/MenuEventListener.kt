package com.ssu.eatssu.domain.menu.application.listener

import com.ssu.eatssu.domain.menu.persistence.MenuRepository
import com.ssu.eatssu.domain.review.event.CreateReviewEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
class MenuEventListener (
    private val menuRepository: MenuRepository
){

    @EventListener
    fun handleCreateReviewEvent(event: CreateReviewEvent) {
        val menu = menuRepository.findById(event.menuId).get()
        menu.addRating(event.mainRating)
    }
}