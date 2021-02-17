package com.onetwothree.paging3sample

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PagingViewModel : ViewModel() {

    val flow = Pager(PagingConfig(pageSize = 10)) {
        DooolPagingSource(PagingRepository())
    }.flow.map {
        it.map<DataModel> { DataModel.Item(it) }
            .insertHeaderItem(DataModel.Header("HEADER"))
            .insertFooterItem(DataModel.Header("FOOTER"))
            .insertSeparators { before, after ->
                when {
                    before is DataModel.Item && after is DataModel.Item -> {
                        if (before.title.startsWith("C") && after.title.startsWith("A")) DataModel.Separator
                        else null
                    }
                    else -> null
                }
            }
    }.cachedIn(viewModelScope)

    val data: MutableLiveData<PagingData<DataModel>> = MutableLiveData()

    fun init() {
        viewModelScope.launch {
            flow.collectLatest { pagingData ->
                data.postValue(pagingData)
            }
        }
    }
}