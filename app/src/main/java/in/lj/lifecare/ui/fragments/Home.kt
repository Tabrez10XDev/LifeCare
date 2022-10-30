package `in`.lj.lifecare.ui.fragments

import `in`.lj.lifecare.DateBindingModel_
import `in`.lj.lifecare.DeptBindingModel_
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import `in`.lj.lifecare.R
import `in`.lj.lifecare.adapters.SliderAdapter
import `in`.lj.lifecare.data.User
import `in`.lj.lifecare.databinding.ItemDeptBinding
import `in`.lj.lifecare.helper.Constants.DEPARTMENTS
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.CarouselModel_
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.fragment_home.*


class Home : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var fireStore : FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    lateinit var sliderView: SliderView
    lateinit var imageUrl: ArrayList<Int>
    lateinit var sliderAdapter: SliderAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        auth.uid?.let { getUser(it) }
        rvHomeController()
        btnTest.setOnClickListener {
            findNavController().navigate(R.id.action_home2_to_aiDiagnosis)
        }

        setUpSlider()

    }


    private fun getUser(uid: String){
        var user: User ?= null
        fireStore.collection("users").document(uid).get()
            .addOnSuccessListener {
                user = it.toObject(User::class.java)
                tvName.text = "Welcome, ${user?.name}"

            }
    }

    private fun setUpSlider(){
        sliderView = imageSlider

        imageUrl = ArrayList()
        imageUrl.apply {
            add(R.drawable.home_card_1)
            add(R.drawable.home_card_2)
            add(R.drawable.home_card_3)
            add(R.drawable.home_card_4)
        }

        sliderAdapter = SliderAdapter( imageUrl)
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.scrollTimeInSec = 3
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()
    }

    private fun rvHomeController(){
        rvHome.withModels {
            val deptModels = mutableListOf<DeptBindingModel_>()
            DEPARTMENTS.forEachIndexed { index, item ->
                deptModels.add(
                    DeptBindingModel_()
                        .id(index)
                        .name(item.name)
                        .imgRes(item.img)
                        .onClickContent { _ ->
                            val bundle = Bundle().apply {
                                putSerializable("Category",item)
                            }
                            findNavController().navigate(R.id.action_home2_to_doctorsList,bundle)
                        }

                )
            }

            CarouselModel_()
                .id("carousel2")
                .models(deptModels)
                .addTo(this);
        }
    }


}