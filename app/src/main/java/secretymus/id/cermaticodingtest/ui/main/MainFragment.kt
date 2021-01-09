package secretymus.id.cermaticodingtest.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.main_fragment.*
import secretymus.id.cermaticodingtest.R
import secretymus.id.cermaticodingtest.UserListAdapter

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
        const val CODE_FIRST_LOAD = 0
        const val CODE_LOAD_MORE = 1
    }

    lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var viewModel: MainViewModel
    private lateinit var userAdapter: UserListAdapter
    var currentPage: Int = 1
    var currentQuery: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        observeViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        userAdapter = context?.let { UserListAdapter(it, arrayListOf()) }!!
        setupRecyclerView()
        searchViewListener()
        addScrollerListener()
    }

    override fun onPause() {
        shimmerPlaceholder.stopShimmerAnimation()
        super.onPause()
    }

    private fun observeViewModel() {
        viewModel.users.observe(viewLifecycleOwner, { user ->
            userAdapter.load(user)
        })
        viewModel.isLoading.observe(viewLifecycleOwner, {
            if (it) {
                recyclerView?.visibility = View.GONE
                shimmerPlaceholder?.visibility = View.VISIBLE
            } else {
                recyclerView?.visibility = View.VISIBLE
                shimmerPlaceholder?.visibility = View.GONE
            }
        })
        viewModel.isLoadError.observe(viewLifecycleOwner, {
            //Toast.makeText(context, "Error when load data", Toast.LENGTH_SHORT).show()
        })
    }

    private fun setupRecyclerView() {
        recyclerView.apply {
            mLayoutManager = LinearLayoutManager(context)
            layoutManager = mLayoutManager
            adapter = userAdapter
        }
    }

    private fun searchViewListener() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                currentPage = 1
                if (query != null) {
                    currentQuery = query
                } else {
                    currentQuery = ""
                }
                Log.d("MainFragment", "Query : $query")
                query?.let {
                    shimmerPlaceholder.startShimmerAnimation()
                    userAdapter.masterList.clear()
                    viewModel.fetchFromRemote(CODE_FIRST_LOAD, query, currentPage)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun addScrollerListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastItemAt = userAdapter.masterList.lastIndex
                Log.d("NewsListFragment", "lastItemAt = $lastItemAt")
                if (mLayoutManager.findLastCompletelyVisibleItemPosition() == lastItemAt && lastItemAt >= 12) {
                    Log.d("NewsListFragment", "Is Last Row")
                    currentPage++
                    Log.d("MainFragment", "Querry : $currentQuery")
                    viewModel.fetchFromRemote(CODE_LOAD_MORE, currentQuery, currentPage)
                    userAdapter.notifyDataSetChanged()
                }
            }
        })
    }

}