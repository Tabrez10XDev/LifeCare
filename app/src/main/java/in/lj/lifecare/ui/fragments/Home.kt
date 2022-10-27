package `in`.lj.lifecare.ui.fragments

import `in`.lj.lifecare.DateBindingModel_
import `in`.lj.lifecare.DeptBindingModel_
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import `in`.lj.lifecare.R
import `in`.lj.lifecare.databinding.ItemDeptBinding
import `in`.lj.lifecare.helper.Constants.DEPARTMENTS
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.CarouselModel_
import kotlinx.android.synthetic.main.fragment_home.*


class Home : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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