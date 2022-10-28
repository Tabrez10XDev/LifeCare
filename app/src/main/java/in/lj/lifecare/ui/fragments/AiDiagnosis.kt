package `in`.lj.lifecare.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import `in`.lj.lifecare.R
import kotlinx.android.synthetic.main.fragment_ai_diagnosis.*

class AiDiagnosis : Fragment() {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ai_diagnosis, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startLoading()


    }


    private fun startLoading(){

        animationView.visibility = View.VISIBLE
        animationView.alpha = 1f
        animationView.playAnimation()

    }



    private fun stopLoading(){
        animationView.pauseAnimation()
        animationView.visibility = View.INVISIBLE
        animationView.alpha = 0f
    }
}