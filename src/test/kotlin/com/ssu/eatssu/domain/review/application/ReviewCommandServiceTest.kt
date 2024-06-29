package com.ssu.eatssu.domain.review.application

import com.ssu.eatssu.domain.menu.entity.Menu
import com.ssu.eatssu.domain.menu.persistence.MenuRepository
import com.ssu.eatssu.domain.review.entity.Review
import com.ssu.eatssu.domain.review.persistence.ReviewRepository
import com.ssu.eatssu.domain.review.presentation.dto.CreateReviewRequest
import com.ssu.eatssu.domain.review.presentation.dto.UpdateReviewRequest
import com.ssu.eatssu.domain.user.entity.User
import com.ssu.eatssu.domain.user.persistence.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ReviewCommandServiceTest @Autowired constructor(
    private val reviewCommandService: ReviewCommandService,
    private val userRepository: UserRepository,
    private val menuRepository: MenuRepository,
    private val reviewRepository: ReviewRepository
) {

    @BeforeEach
    fun setUp() {
        reviewRepository.deleteAll()
        menuRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("리뷰를 생성한다")
    fun createReviewTest() {
        // given
        val userId: Long = userRepository.save(
            User.fixture()
        ).id!!

        val menuId: Long = menuRepository.save(
            Menu.fixture("떡볶이")
        ).id!!

        val reviewString = "맛있어요"

        // when
        val response = reviewCommandService.createReview(
            userId,
            CreateReviewRequest(
                menuId = menuId,
                content = reviewString,
                mainRating = 5,
                amountRating = 5,
                tasteRating = 5,
                images = emptyList()
            )
        )

        // then
        assertNotNull(response)
        assertEquals(response.userId, userId)
        assertEquals(response.content, reviewString)
        assertEquals(response.mainRating, 5)
        assertEquals(response.amountRating, 5)
        assertEquals(response.tasteRating, 5)
    }

    @Test
    @DisplayName("리뷰를 수정한다")
    fun updateReviewTest() {
        // given
        val user: User = userRepository.save(
            User.fixture()
        )

        val menu: Menu = menuRepository.save(
            Menu.fixture("떡볶이")
        )

        val review: Review = reviewRepository.save(
            Review.fixture(
                user = user,
                menu = menu
            )
        )

        // when
        reviewCommandService.updateReview(
            user.id!!,
            review.id!!,
            UpdateReviewRequest(
                content = "맛없어요",
                mainRating = 1,
                amountRating = 1,
                tasteRating = 1
            )
        )

        // then
        val updatedReview = reviewRepository.findById(review.id!!)
        assertTrue(updatedReview.isPresent)
        assertEquals(updatedReview.get().content, "맛없어요")
        assertEquals(updatedReview.get().mainRating, 1)
        assertEquals(updatedReview.get().amountRating, 1)
        assertEquals(updatedReview.get().tasteRating, 1)
    }

    @Test
    @DisplayName("리뷰를 삭제한다")
    fun deleteReviewTest() {
        // given
        val user: User = userRepository.save(
            User.fixture()
        )

        val menu: Menu = menuRepository.save(
            Menu.fixture("떡볶이")
        )

        val review: Review = reviewRepository.save(
            Review.fixture(
                user = user,
                menu = menu
            )
        )

        // when
        reviewCommandService.deleteReview(
            user.id!!,
            review.id!!
        )

        // then
        val deletedReview = reviewRepository.findById(review.id!!)
        assertFalse(deletedReview.isPresent)
    }
}
