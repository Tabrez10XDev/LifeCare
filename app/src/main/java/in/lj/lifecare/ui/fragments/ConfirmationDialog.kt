package `in`.lj.lifecare.ui.fragments

import `in`.lj.lifecare.R
import `in`.lj.lifecare.data.Booking
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.constraintlayout.widget.Constraints
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_ai_diagnosis.*
import kotlinx.android.synthetic.main.fragment_confirmation_dialog.*
import kotlinx.android.synthetic.main.fragment_doctor_details.*


class ConfirmationDialog() : DialogFragment() {

    lateinit var auth: FirebaseAuth
    private lateinit var booking: Booking

    private var fireStore : FirebaseFirestore = FirebaseFirestore.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.fragment_confirmation_dialog, container, false)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(Constraints.LayoutParams.MATCH_PARENT, Constraints.LayoutParams.WRAP_CONTENT);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var args: ConfirmationDialogArgs = ConfirmationDialogArgs.fromBundle(this.requireArguments())
        booking = args.booking
        tvChangeDate.setOnClickListener {
            this.dialog?.dismiss()
        }
        btnClose.setOnClickListener {
            this.dialog?.dismiss()
        }
        initializeValues()

        btnConfirm.setOnClickListener {
            fireStore.collection("users").document(auth.uid!!).collection("appointments").document().set(booking)
            startLoading()
            Handler(Looper.getMainLooper()).postDelayed({
                findNavController().popBackStack(R.id.home2,false)
            }, 2000)
        }


    }

    private fun initializeValues(){
        tvDialogName.text = booking.doctorName
        tvDialogCategory.text = booking.doctorDept
        tvDate.text = booking.date
        tvDay.text = booking.day
    }

    private fun startLoading(){
        cvDialog.visibility = View.INVISIBLE
        avBooked.visibility = View.VISIBLE
        avBooked.alpha = 1f
        avBooked.playAnimation()

    }


    companion object {
        const val TAG = "ConfirmationDialog"
    }

}