package com.ssu.eatssu.domain.review.entity

import com.ssu.eatssu.domain.menu.entity.Menu
import com.ssu.eatssu.domain.user.entity.User
import com.ssu.eatssu.global.entity.BaseEntity
import jakarta.persistence.*

@Entity
class Review(
    @Column(name = "review_content")
    var content: String,

    @Column(name = "main_rating")
    var mainRating: Int,

    @Column(name = "amount_rating")
    var amountRating: Int,

    @Column(name = "taste_rating")
    var tasteRating: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    val menu: Menu,

    @Column(name = "review_image_url")
    @ElementCollection
    val imageUrls: List<String>,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    val id: Long,
) : BaseEntity() {
    fun addImageUrl(imageUrl: String) {
        imageUrls.toMutableList().add(imageUrl)
    }

    fun isWrittenBy(user: User): Boolean {
        return this.user.equals(user)
    }

    fun update(
        content: String,
        mainRating: Int,
        amountRating: Int,
        tasteRating: Int
    ) {
        this.content = content
        this.mainRating = mainRating
        this.amountRating = amountRating
        this.tasteRating = tasteRating
    }

    companion object {
        fun of(
            content: String,
            mainRating: Int,
            amountRating: Int,
            tasteRating: Int,
            user: User,
            menu: Menu,
            imageUrl: List<String> = mutableListOf()
        ): Review {
            return Review(
                content = content,
                mainRating = mainRating,
                amountRating = amountRating,
                tasteRating = tasteRating,
                user = user,
                menu = menu,
                imageUrls = imageUrl,
                id = 0
            )
        }

        fun fixture(
            content: String = "맛있어요",
            mainRating: Int = 5,
            amountRating: Int = 5,
            tasteRating: Int = 5,
            user: User,
            menu: Menu,
            imageUrl: List<String> = mutableListOf()
        ): Review {
            return Review(
                content = content,
                mainRating = mainRating,
                amountRating = amountRating,
                tasteRating = tasteRating,
                user = user,
                menu = menu,
                imageUrls = imageUrl,
                id = 0
            )
        }
    }
}