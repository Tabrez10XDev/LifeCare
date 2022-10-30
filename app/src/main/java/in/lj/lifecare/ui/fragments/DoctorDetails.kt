package `in`.lj.lifecare.ui.fragments

import `in`.lj.lifecare.DateBindingModel_
import `in`.lj.lifecare.R
import `in`.lj.lifecare.data.Booking
import `in`.lj.lifecare.data.Doctor
import `in`.lj.lifecare.databinding.FragmentDoctorDetailsBinding
import `in`.lj.lifecare.helper.BaseBottomSheet
import `in`.lj.lifecare.helper.BottomSheetLevelInterface
import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.CarouselModel_
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_doctor_details.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


class DoctorDetails()
    : BaseBottomSheet<FragmentDoctorDetailsBinding>(R.layout.fragment_doctor_details),
    BottomSheetLevelInterface {

    var selected: Int ?= null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            setUpFullScreen(bottomSheetDialog, 80)
        }

        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var args: DoctorDetailsArgs = DoctorDetailsArgs.fromBundle(this.requireArguments())
        var doctor = args.doctor
        val imgUrl = "https://api.mapbox.com/styles/v1/mapbox/streets-v11/static/${doctor.geoTag[0]},${doctor.geoTag[1]},16,0,0/400x400?access_token=pk.eyJ1IjoibG93anVua2llIiwiYSI6ImNsMmhlajNuZDBjY3gzY256eGt0ZGpncTIifQ.wPCLsXXPdlP2dY365C-x7A"

        Glide
            .with(requireContext())
            .load(imgUrl)
            .into(ivLocation)

        btnDrop.setOnClickListener {
            dialog?.dismiss()
        }
        btnBook.setOnClickListener {
            val lis = getNextWeek()
            if(selected == null)
                return@setOnClickListener
            val _selected = lis[selected!!]
            val dd = _selected.split(" ")

            val booking = Booking(
                doctorName = "Dr. " + doctor.name,
                doctorDept = doctor.specialization,
                day = dd[0].substring(0,3),
                date = dd[1].substring(0,2),
                visitingHours = doctor.visitingHours,
                ddmmyy = dd[1]
            )
            val bundle = Bundle().apply {
                putSerializable("Booking",booking)
            }
            findNavController().navigate(R.id.action_doctorDetails_to_confirmationDialog2,bundle)
        //    val confirmationDialog = ConfirmationDialog(booking)
        //    confirmationDialog.show(requireActivity().supportFragmentManager,ConfirmationDialog.TAG)

        }

        tvDoctorName.text = "Dr. " + doctor.name
        tvDoctorDept.text = doctor.specialization
        tvVisitingHours.text = doctor.visitingHours
        tvExperience.text = doctor.experience + " Years"
        tvConsultations.text = "${doctor.count}+"

        rvDetailsController()

    }

    private fun rvDetailsController(){
        val lis = getNextWeek()
        binding.rvDetails.withModels {

            val dateModels = mutableListOf<DateBindingModel_>()
            lis.forEachIndexed { index, i ->
                val dd = i.split(" ")
                val colour = if(selected==index) R.color.date_selected else R.color.date_unselected
                dateModels.add(
                    DateBindingModel_()
                        .id(index)
                        .day(dd[0].substring(0,3))
                        .date(dd[1].substring(0,2))
                        .onClickContent { _ ->
                            selected = index
                            this.requestModelBuild()
                        }
                        .cardColour(colour)
                )
            }

            CarouselModel_()
                .id("carousel")
                .models(dateModels)
                .addTo(this);
        }
    }

    private fun getNextWeek(): MutableList<String> {
        val lis = mutableListOf<String>()
        val sdf = SimpleDateFormat("EEEE dd-MMM-yyyy")
        for (i in 0..6) {
            val calendar: Calendar = GregorianCalendar()
            calendar.add(Calendar.DATE, i)
            val day: String = sdf.format(calendar.time)
            lis.add(day)
        }
        return lis
    }



    companion object {
        const val TAG = "DoctorDetails"
    }

    override fun onSheet2Dismissed() {
        setLevel(-1)
    }

    override fun onSheet2Created() {
        setLevel(-2)
    }

    override fun onSheet1Dismissed() {
        setLevel(0)

    }

    override fun getHeightOfBottomSheet(height: Int) {
        setLevel(-1)
    }

}