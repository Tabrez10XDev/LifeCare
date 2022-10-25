package `in`.lj.lifecare.ui.app

import `in`.lj.lifecare.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setTheme(R.style.Theme_LifeCare)
        setContentView(R.layout.landing_screen)
    }
}