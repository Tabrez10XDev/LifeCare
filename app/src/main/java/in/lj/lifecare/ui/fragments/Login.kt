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
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Login : Fragment() {


    lateinit var auth: FirebaseAuth

    override fun onStart() {
        super.onStart()
        checkLoggedInState()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        tvSignup.setOnClickListener {
            findNavController().navigate(R.id.signup)
        }
        btnLogin.setOnClickListener{
            hideKeyboard()
            loginUser()
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
        if(auth.currentUser != null){
            val intent = Intent(activity, DashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun loginUser() {
        //showbar()
        val email = editTextMail.text.toString()
        val password = editTextPassword.text.toString()
        if( email.isNotEmpty() && password.isNotEmpty()){
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                        //  hidebar()
                        checkLoggedInState()
                    }.addOnFailureListener {
                        // hidebar()
                        Toast.makeText(activity, "Invalid Credentials", Toast.LENGTH_SHORT).show()

                    }.addOnCanceledListener {
                        // hidebar()
                        Toast.makeText(activity, "Error!", Toast.LENGTH_SHORT).show()
                    }
                }
                catch (e : Exception){
                    withContext(Dispatchers.Main){
                        //hidebar()
                        Toast.makeText(activity,e.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
        else{
            Toast.makeText(activity,"Invalid Credentials", Toast.LENGTH_SHORT).show()
            //  hidebar()
        }
    }

}