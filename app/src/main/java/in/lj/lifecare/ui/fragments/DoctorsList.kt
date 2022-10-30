package `in`.lj.lifecare.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import `in`.lj.lifecare.R
import `in`.lj.lifecare.data.Doctor
import `in`.lj.lifecare.doctorsCard
import android.util.Log
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_doctors_list.*
import java.util.*


class DoctorsList : Fragment() {

    lateinit var auth: FirebaseAuth
    private var fireStore : FirebaseFirestore = FirebaseFirestore.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_doctors_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        var args: DoctorsListArgs = DoctorsListArgs.fromBundle(this.requireArguments())
        var item = args.category
        ivDept.setImageResource(item.img)
        tvDept.text = item.name
        getDoctors(item.name.lowercase())
        btnBack.setOnClickListener {
            findNavController().popBackStack()

        }

    }

    private fun rvController(doctors: List<Doctor>){
        recyclerView.withModels {
            doctors.forEachIndexed { index, doctor ->
                doctorsCard {
                    id(index)
                    onClickContent{ _ ->
                        val bundle = Bundle().apply {
                            putSerializable("Doctor",doctor)
                        }
                        findNavController().navigate(R.id.action_doctorsList_to_doctorDetails, bundle)
//                        val doctorDetails = DoctorDetails(doctor)
//                        doctorDetails.show(requireActivity().supportFragmentManager, DoctorDetails.TAG)
                    }
                    name("Dr. " + doctor.name)
                    specialization(doctor.specialization)
                    availability(if(doctor.availability) "Available" else "Unavailable")
                    location(doctor.location)
                    experience(doctor.experience + "Years")
                    val colour = if(doctor.availability) R.color.availability_green else R.color.availability_red
                    txtColor(colour)
                }
            }

        }
    }


    private fun getDoctors(dept: String){
        val itemList : MutableList<Doctor> = arrayListOf()
        fireStore.collection(dept)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    itemList.add(
                        document.toObject(Doctor::class.java)
                    )

                }
                rvController(itemList)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(requireContext(), exception.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }


}