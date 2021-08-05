package br.com.mfet.jmovie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.mfet.jmovie.adapter.RecyclerViewAdapter
import br.com.mfet.jmovie.models.RecyclerList
import br.com.mfet.jmovie.viewmodel.MainActivityViewModel

class RecyclerListFragment : Fragment() {

    private lateinit var recyclerAdapter : RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_recycler_list, container, false)

        initViewModel(view)
        initViewModel()
        return view
    }

    private fun initViewModel(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_movielistpopular)
        val recyclerViewLatest = view.findViewById<RecyclerView>(R.id.rv_movieupcoming)

        recyclerView.layoutManager = LinearLayoutManager(
            activity, LinearLayoutManager.HORIZONTAL,false
        )

        recyclerViewLatest.layoutManager = LinearLayoutManager(
            activity, LinearLayoutManager.HORIZONTAL, false
        )

        recyclerAdapter = RecyclerViewAdapter()
        recyclerView.adapter = recyclerAdapter
        recyclerViewLatest.adapter = recyclerAdapter

    }

    private fun initViewModel() {
        val viewModel = ViewModelProvider(this)
            .get(MainActivityViewModel::class.java)
        viewModel.getRecyclerListObserver().observe(this, Observer<RecyclerList> {
            if(it != null) {
                recyclerAdapter.setUpdateData(it.results)
            } else {
                Toast.makeText(activity, "Erro ao trazer dados", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeApiCall()
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            RecyclerListFragment()
    }
}