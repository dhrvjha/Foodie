package io.github.foodie.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.foodie.R
import io.github.foodie.adapter.FoodsRecyclerView
import io.github.foodie.api.MySingleton
import io.github.foodie.api.RestaurantDetailsAPI
import io.github.foodie.database.Foods
import io.github.foodie.util.SwrToast

class RestaurantDetailsActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var progressBar: ProgressBar
    private var recyclerView: RecyclerView? = null
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var recyclerAdapter: FoodsRecyclerView
    private lateinit var placeOrder: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_details)
        progressBar = findViewById<ProgressBar>(R.id.progressBar)
        toolbar = findViewById<Toolbar>(R.id.toolbar)
        recyclerView = findViewById<RecyclerView>(R.id.fragRestaurantDetailsRecyclerView)
        layoutManager = LinearLayoutManager(this@RestaurantDetailsActivity)
        placeOrder = findViewById<Button>(R.id.btnRestaurantDetailsPlaceOrder)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setUpActionBar()
        val restaurantName = intent?.getStringExtra("restaurant_name")
        restaurantName?.let {
            title = it
        } ?: kotlin.run {
            title = "details"
        }
        val restaurantId = intent?.getStringExtra("restaurant_id")
        restaurantId?.let {
            val handler = RestaurantDetailsAPI(this@RestaurantDetailsActivity, it)
            val jsonObjectRequest = handler.getRequest {
                progressBar.visibility = View.GONE
                recyclerAdapter =
                    FoodsRecyclerView(
                        this@RestaurantDetailsActivity,
                        handler.list as List<Foods>,
                        placeOrder
                    )
                recyclerView?.adapter = recyclerAdapter
                recyclerView?.layoutManager = layoutManager
            }
            MySingleton.getInstance(this@RestaurantDetailsActivity)
                .addToRequestQueue(jsonObjectRequest)
        } ?: SwrToast.show(this@RestaurantDetailsActivity)
        placeOrder.setOnClickListener {

        }
    }

    override fun onStop() {
        MySingleton.getInstance(this@RestaurantDetailsActivity).cancelAll("restaurant-details")
        super.onStop()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
//        return super.onSupportNavigateUp()
    }

    private fun setUpActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}