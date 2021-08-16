package io.github.foodie.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.foodie.R
import io.github.foodie.adapter.HomeRecyclerView
import io.github.foodie.api.MySingleton
import io.github.foodie.api.RestaurantsAPI
import io.github.foodie.util.ConnectionAlert

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var recyclerView: RecyclerView? = null
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var recyclerAdapter: HomeRecyclerView
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        progressBar = view.findViewById(R.id.progressBar)
        layoutManager = LinearLayoutManager(activity)
        recyclerView = view.findViewById(R.id.fragHomeRecyclerView)
        progressBar.visibility = View.VISIBLE
        activity?.let {
            if (ConnectionAlert(it as Context).start({
                    startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
                    it.finish()
                }, {
                    ActivityCompat.finishAffinity(it)
                })) {
                val helperApi = RestaurantsAPI(it as Context)
                val jsonObjectRequest = helperApi.getRequest {
                    progressBar.visibility = View.GONE
                    recyclerAdapter = HomeRecyclerView(activity as Context, helperApi.restaurants)
                    recyclerView?.adapter = recyclerAdapter
                    recyclerView?.layoutManager = layoutManager
                }
                MySingleton.getInstance(it as Context).addToRequestQueue(jsonObjectRequest)
            }
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onStop() {
        MySingleton.getInstance(activity as Context).cancelAll("list-restaurants")
        super.onStop()
    }
}