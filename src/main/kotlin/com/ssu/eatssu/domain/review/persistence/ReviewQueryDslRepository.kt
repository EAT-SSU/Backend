package com.ssu.eatssu.domain.review.persistence

import com.querydsl.core.Tuple
import com.querydsl.jpa.impl.JPAQueryFactory
import com.ssu.eatssu.domain.meal.entity.Meal
import com.ssu.eatssu.domain.review.entity.QReview.review
import com.ssu.eatssu.domain.review.entity.Review
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Component

@Component
class ReviewQueryDslRepository(
    private val queryFactory: JPAQueryFactory
) {
    fun findAverageMainRatingByMenu(id: Long): Double? {
        return queryFactory
            .select(review.mainRating.avg())
            .from(review)
            .where(review.menu.id.eq(id))
            .fetchOne()
    }

    fun findAverageAmountRatingByMenu(id: Long): Double? {
        return queryFactory
            .select(review.amountRating.avg())
            .from(review)
            .where(review.menu.id.eq(id))
            .fetchOne()
    }

    fun findAverageTasteRatingByMenu(id: Long): Double? {
        return queryFactory
            .select(review.tasteRating.avg())
            .from(review)
            .where(review.menu.id.eq(id))
            .fetchOne()
    }

    fun findRatingCountMapByMenu(menuId: Long): Map<Int, Long?> {
        val results: List<Tuple> = queryFactory
            .select(review.mainRating, review.mainRating.count())
            .from(review)
            .where(review.menu.id.eq(menuId))
            .groupBy(review.mainRating)
            .fetch()

        return results.associate { tuple ->
            val rating = tuple.get(review.mainRating)!!.toInt()
            val count = tuple.get(review.mainRating.count()) ?: 0L
            rating to count
        }
    }

    fun findAverageMainRatingByMeal(mealId: Long): Double? {
        return queryFactory
            .select(review.mainRating.avg())
            .from(review)
            // TODO: 쿼리 날아가는거 보고 meal에서 menu 리스트를 조회하고 각각을 조회하는 것으로 성능 개선을 꾀할 수 있음
            .where(review.menu.meal.id.eq(mealId))
            .fetchOne()
    }

    fun findAverageAmountRatingByMeal(mealId: Long): Double? {
        return queryFactory
            .select(review.amountRating.avg())
            .from(review)
            .where(review.menu.meal.id.eq(mealId))
            .fetchOne()
    }

    fun findAverageTasteRatingByMeal(mealId: Long): Double? {
        return queryFactory
            .select(review.tasteRating.avg())
            .from(review)
            .where(review.menu.meal.id.eq(mealId))
            .fetchOne()
    }

    fun findRatingCountMapByMeal(mealId: Long): Map<Int, Long?> {
        val results: List<Tuple> = queryFactory
            .select(review.mainRating, review.mainRating.count())
            .from(review)
            .where(review.menu.meal.id.eq(mealId))
            .groupBy(review.mainRating)
            .fetch()

        return results.associate { tuple ->
            val rating = tuple.get(review.mainRating)!!.toInt()
            val count = tuple.get(review.mainRating.count()) ?: 0L
            rating to count
        }
    }

    fun findAllByMenuId(
        menuId: Long,
        pageable: Pageable,
        lastReviewId: Long?,
        isDesc: Boolean
    ): Slice<Review> {
        val reviews = queryFactory.selectFrom(review)
            .where(
                review.menu.id.eq(menuId),
                review.id.lt(lastReviewId ?: Long.MAX_VALUE)
            )
            .orderBy(
                if (isDesc) {
                    review.id.desc()
                } else {
                    review.id.asc()
                }
            )
            // 요청한 페이지 크기보다 1 더 많은 결과를 가져옴
            // 다음 페이지가 있는지 확인
            .limit(pageable.pageSize + 1L)
            .fetch()

        return checkLastPage(pageable, reviews)
    }

    fun findAllByMealId(meal: Meal, pageable: Pageable, lastReviewId: Long?, desc: Boolean): Slice<Review> {
        val menus = meal.menus.map { it }.toList()

        val reviews = queryFactory.selectFrom(review)
            .where(
                review.menu.`in`(menus),
                review.id.lt(lastReviewId ?: Long.MAX_VALUE)
            )
            .orderBy(
                if (desc) {
                    review.id.desc()
                } else {
                    review.id.asc()
                }
            )
            .limit(pageable.pageSize + 1L)
            .fetch()

        return checkLastPage(pageable, reviews)
    }

    fun checkLastPage(
        pageable: Pageable,
        reviews: MutableList<Review>
    ): Slice<Review> {
        var hasNext = false

        if (reviews.size > pageable.pageSize) {
            hasNext = true
            reviews.removeLast()
        }

        return SliceImpl(
            reviews,
            pageable,
            hasNext
        )
    }

    fun findAllByUserId(id: Long, pageable: Pageable, lastReviewId: Long?, desc: Boolean): Slice<Review> {
        val reviews = queryFactory.selectFrom(review)
            .where(
                review.user.id.eq(id),
                review.id.lt(lastReviewId ?: Long.MAX_VALUE)
            )
            .orderBy(
                if (desc) {
                    review.id.desc()
                } else {
                    review.id.asc()
                }
            )
            .limit(pageable.pageSize + 1L)
            .fetch()

        return checkLastPage(pageable, reviews)
    }

}