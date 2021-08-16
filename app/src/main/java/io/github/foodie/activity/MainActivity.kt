package io.github.foodie.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import io.github.foodie.R
import io.github.foodie.fragment.*
import io.github.foodie.util.SharedPreferencesManager

class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var frameLayout: FrameLayout
    private lateinit var coordinatorLayout: CoordinatorLayout
    private lateinit var userName: TextView
    private lateinit var mobileNumber: TextView
    private var previousMenuItemSelected: MenuItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawerLyMain)
        navigationView = findViewById(R.id.navViewMain)
        frameLayout = findViewById(R.id.frameLyMain)
        coordinatorLayout = findViewById(R.id.coordLyMain)
        val headerView = navigationView.getHeaderView(0)
        userName = headerView.findViewById(R.id.txtDrawHeaderUserName)
        mobileNumber = headerView.findViewById(R.id.txtDrawHeaderMobileNumber)
        openFragmentActivity(HomeFragment(), getString(R.string.allRestaurant))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        setUpActionBar()
        //region Setting up drawer layout
        val actionBarDrawerToolbar = ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToolbar)
        actionBarDrawerToolbar.syncState()
        //endregion

        val sharedPM = SharedPreferencesManager(
            getSharedPreferences(
                getString(R.string.sharedLoginFile),
                MODE_PRIVATE
            )
        )
        userName.text = sharedPM.getUserName()
        mobileNumber.text = sharedPM.getMobileNumber()
        navigationView.setCheckedItem(R.id.menuHome)
        navigationView.setNavigationItemSelectedListener {

            previousMenuItemSelected?.isChecked = false
            it.isChecked = true
            previousMenuItemSelected = it
            when (it.itemId) {
                R.id.menuProfile -> {
                    openFragmentActivity(ProfileFragment(), "Profile")
                }
                R.id.menuFavourites -> {
                    openFragmentActivity(FavouritesFragment(), "Favourites")
                }
                R.id.menuHsitory -> {
                    openFragmentActivity(OrderHistoryFragment(), "Order History")
                }
                R.id.menuHome -> {
                    openFragmentActivity(HomeFragment(), getString(R.string.allRestaurant))
                }
                R.id.menuFaqs -> {
                    openFragmentActivity(FaqsFragment(), "FAQs")
                }
                R.id.menuLogout -> {
                    val alertDialog = AlertDialog.Builder(this@MainActivity)
                    alertDialog.setTitle(R.string.confirmation)
                    alertDialog.setMessage(R.string.areYouSureYouWantToLogOut)
                    alertDialog.setPositiveButton(R.string.yes) { _, _ ->
                        Toast.makeText(this@MainActivity, "Logging out", Toast.LENGTH_SHORT).show()
                        getSharedPreferences(
                            getString(R.string.sharedLoginFile),
                            MODE_PRIVATE
                        ).edit().clear().apply()
                        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                        finish()
                    }
                    alertDialog.setNegativeButton(R.string.no) { _, _ ->
                        // Do nothing
                    }
                    alertDialog.create().show()
                }
            }
            true
        }

        super.onPostCreate(savedInstanceState)
    }

    private fun openFragmentActivity(fragment: Fragment, title: String) {
        supportFragmentManager.beginTransaction().replace(R.id.frameLyMain, fragment).commit()
        supportActionBar?.title = title
        drawerLayout.closeDrawers()
    }


    private fun setUpActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.allRestaurant)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}