package `in`.lj.lifecare.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import `in`.lj.lifecare.R
import `in`.lj.lifecare.doctorsCard
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_doctors_list.*


class DoctorsList : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctors_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnBack.setOnClickListener {
         //   findNavController().popBackStack()
            val doctorDetails = DoctorDetails()

            doctorDetails.show(requireActivity().supportFragmentManager, DoctorDetails.TAG)

        }
        recyclerView.withModels {
            doctorsCard {
                id(1)
            }
            doctorsCard {
                id(2)
            }
            doctorsCard {
                id(3)
            }
        }
    }


}