package com.onetwothree.paging3sample

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PagingRepository {
    suspend fun getPagingData(page: Int): Pair<List<String>, Int?> {
        return withContext(Dispatchers.IO) {
            Pair(
                listOf(
                    "A $page",
                    "B $page",
                    "C $page"
                ), page + 1
            )
        }
    }
}