package `in`.lj.lifecare.ui.app

import `in`.lj.lifecare.R
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.dashboard_screen.*

class DashboardActivity : AppCompatActivity() {

    var doubleBackToExitPressedOnce: Boolean ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.dashboard_screen)
        nav_view.setItemSelected(R.id.home2)
        nav_view.setOnItemSelectedListener {
            when(it){
              R.id.home2 ->{
                  findNavController(R.id.dashboardFragment).popBackStack(R.id.dashboard_nav, true)
                  findNavController(R.id.dashboardFragment).navigate(it)

              }else->{
                findNavController(R.id.dashboardFragment).popBackStack(R.id.dashboard_nav, true)
                findNavController(R.id.dashboardFragment).navigate(it)

            }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(doubleBackToExitPressedOnce == true){ this?.finish() }

        doubleBackToExitPressedOnce = true
        nav_view.setItemSelected(R.id.home2)
        findNavController(R.id.dashboardFragment).popBackStack(R.id.dashboard_nav, true)
        findNavController(R.id.dashboardFragment).navigate(R.id.home2)
        Toast.makeText(applicationContext, "Press again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }
}