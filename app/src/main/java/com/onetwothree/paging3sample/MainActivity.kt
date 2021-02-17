package com.onetwothree.paging3sample

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.paging.*
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PagingViewModel>()

    private val adapter = PagingAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            adapter.refresh()
        }

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter =
            adapter.withLoadStateHeaderAndFooter(DooolLoadStateAdapter { adapter.retry() },
                DooolLoadStateAdapter { adapter.retry() })

        adapter.addLoadStateListener {
            println("pre ${it.prepend}")
            println("ap ${it.append}")
            println("re ${it.refresh}")
            if (it.refresh is LoadState.Error) {
                adapter.retry()
            }
        }

        viewModel.data.observe(this@MainActivity, Observer {
            lifecycleScope.launch {
                adapter.submitData(it)
            }
        })

        viewModel.init()
    }
}

