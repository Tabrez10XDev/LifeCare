package `in`.lj.lifecare.ui.fragments

import `in`.lj.lifecare.R
import `in`.lj.lifecare.data.Booking
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.constraintlayout.widget.Constraints
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_confirmation_dialog.*
import kotlinx.android.synthetic.main.fragment_doctor_details.*


class ConfirmationDialog(
    val booking: Booking
) : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        tvChangeDate.setOnClickListener {
            this.dialog?.dismiss()
        }
        btnClose.setOnClickListener {
            this.dialog?.dismiss()
        }
        tvDialogName.text = booking.doctorName
        tvDialogCategory.text = booking.doctorDept
        tvDate.text = booking.date
        tvDay.text = booking.day

    }

    companion object {
        const val TAG = "ConfirmationDialog"
    }

}