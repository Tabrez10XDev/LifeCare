package `in`.lj.lifecare.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import `in`.lj.lifecare.R
import `in`.lj.lifecare.data.User
import `in`.lj.lifecare.ui.app.DashboardActivity
import android.content.Context
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_signup.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Signup : Fragment() {

    lateinit var auth: FirebaseAuth
    private var fireStore : FirebaseFirestore = FirebaseFirestore.getInstance()


    override fun onStart() {
        super.onStart()
        checkLoggedInState()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        btnSignup.setOnClickListener {
            hideKeyboard()
            registerUser()
        }
        tvLogin.setOnClickListener {
            findNavController().popBackStack()
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


    private fun registerUser() {
        //showbar()
        val name = editTextName.text.toString()
        val number = editTextPhone.text.toString()
        val password = editTextPassword.text.toString()
        val email = editTextMail.text.toString()
        val user = User(name, email, number)

        if( email.isNotEmpty() && password.isNotEmpty()){
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                        fireStore.collection("users").document(auth.uid!!).set(user)
                        Toast.makeText(activity,"Account created",Toast.LENGTH_SHORT).show()
                        checkLoggedInState()
                    }.addOnFailureListener(){
                        Toast.makeText(activity,it.message,Toast.LENGTH_SHORT).show()
                        //hidebar()
                    }


                }
                catch (e : Exception){
                    withContext(Dispatchers.Main){
                        //hidebar()
                        Toast.makeText(activity,e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}