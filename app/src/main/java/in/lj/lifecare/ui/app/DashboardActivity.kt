package `in`.lj.lifecare.ui.app

import `in`.lj.lifecare.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.dashboard_screen.*

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.dashboard_screen)
        nav_view.setItemSelected(R.id.home2)
    }
}