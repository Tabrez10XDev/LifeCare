package `in`.lj.lifecare.ui.fragments

import `in`.lj.lifecare.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment


class ConfirmationDialog : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_confirmation_dialog, container, false)
    }

    companion object {
        const val TAG = "ConfirmationDialog"
    }

}