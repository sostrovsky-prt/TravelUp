package com.sostrovsky.travelup.repository.ticket.ticket_search_params

import com.sostrovsky.travelup.TravelUpApp
import com.sostrovsky.travelup.database.TravelUpDatabase
import com.sostrovsky.travelup.database.entities.ticket.TicketSearchParams
import com.sostrovsky.travelup.repository.ticket.TicketRepository
import com.sostrovsky.travelup.repository.ticket.market_place.MarketPlaceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * Author: Sergey Ostrovsky
 * Date: 16.09.20
 * Email: sergey.ostrovsky.it.dev@gmail.com
 */
object TicketSearchParamsRepo {
    val database = TravelUpDatabase.getInstance(TravelUpApp.applicationContext())

    suspend fun getTicketSearchParams(
        placeFrom: String, placeTo: String,
        departureDate: String
    ): Int {
        val result = mutableListOf(0)

        withContext(Dispatchers.IO) {
            val isMarketPlaceDBEmpty = MarketPlaceRepository.hasNoItems()

            if (!isMarketPlaceDBEmpty) {
                var canContinue = true

                val marketPlaceIdFrom =
                    MarketPlaceRepository.getIdByName(placeFrom) // getMarketPlaceId(placeFrom)
                var marketPlaceIdTo = 0

                if (marketPlaceIdFrom <= 0) {
                    canContinue = false
                }

                if (canContinue) {
                    marketPlaceIdTo =
                        MarketPlaceRepository.getIdByName(placeTo) // getMarketPlaceId(placeTo)

                    if (marketPlaceIdTo <= 0) {
                        canContinue = false
                    }
                }

                if (canContinue) {
                    result[0] = TicketRepository.database.ticketSearchParamsDao.getId(
                        marketPlaceIdFrom, marketPlaceIdTo, departureDate
                    )
                }
            }
        }

        return result[0]
    }

    suspend fun getTicketSearchParamsId(
        marketPlaceIdFrom: Int, marketPlaceIdTo: Int,
        departureDate: String
    ): Int {
        var result = -1

        withContext(Dispatchers.IO) {
            result = database.ticketSearchParamsDao.getId(
                marketPlaceIdFrom, marketPlaceIdTo,
                departureDate
            )

            if (result == 0) {
                result = database.ticketSearchParamsDao.insert(
                    generateModel(
                        marketPlaceIdFrom, marketPlaceIdTo, departureDate
                    )
                ).toInt()
            }
        }

        return result
    }

    private fun generateModel(
        marketPlaceIdFrom: Int, marketPlaceIdTo: Int,
        departureDate: String
    ): TicketSearchParams {
        return TicketSearchParams(
            id = 0,
            marketPlaceIdFrom = marketPlaceIdFrom,
            marketPlaceIdTo = marketPlaceIdTo,
            departureDate = departureDate
        )
    }
}