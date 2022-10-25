package `in`.lj.lifecare.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import `in`.lj.lifecare.R
import `in`.lj.lifecare.ui.app.DashboardActivity
import android.content.Context
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login.*


class Login : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvSignup.setOnClickListener {
            findNavController().navigate(R.id.signup)
        }
        btnLogin.setOnClickListener{
            checkLoggedInState()
        }
    }


    private fun hideKeyboard(){
        val view = activity?.currentFocus
        view?.let { v ->
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    private fun checkLoggedInState(){
            hideKeyboard()
//        if(auth.currentUser != null){
            val intent = Intent(activity, DashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            activity?.finish()

     //   }
    }

}