package `in`.lj.lifecare.ui.fragments

import `in`.lj.lifecare.DateBindingModel_
import `in`.lj.lifecare.R
import `in`.lj.lifecare.databinding.FragmentDoctorDetailsBinding
import `in`.lj.lifecare.helper.BaseBottomSheet
import `in`.lj.lifecare.helper.BottomSheetLevelInterface
import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import com.airbnb.epoxy.CarouselModel_
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_doctor_details.*
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class DoctorDetails
    : BaseBottomSheet<FragmentDoctorDetailsBinding>(R.layout.fragment_doctor_details),
    BottomSheetLevelInterface {


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
        val imgUrl = "https://api.mapbox.com/styles/v1/mapbox/streets-v11/static/80.2602,13.0973,16,0,0/400x400?access_token=pk.eyJ1IjoibG93anVua2llIiwiYSI6ImNsMmhlajNuZDBjY3gzY256eGt0ZGpncTIifQ.wPCLsXXPdlP2dY365C-x7A"

        Glide
            .with(requireContext())
            .load(imgUrl)
            .into(ivLocation)

        btnDrop.setOnClickListener {
            dialog?.dismiss()
        }

        binding.rvDetails.withModels {
            val lis = listOf<Int>(1,2,3,4,5,6,7,8,9,0)
            val dateModels = mutableListOf<DateBindingModel_>()
            lis.forEachIndexed { index, i ->
                dateModels.add(
                    DateBindingModel_()
                        .id(index)
                )

            }

            CarouselModel_()
                .id("carousel")
                .models(dateModels)
                .addTo(this);
        }




    }


    fun getBitmapFromURL(src: String?): Bitmap? {
        return try {
            Log.e("src", src!!)
            val url = URL(src)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input: InputStream = connection.getInputStream()
            val myBitmap = BitmapFactory.decodeStream(input)
            Log.e("Bitmap", "returned")
            myBitmap
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("Exception", e.localizedMessage)
            null
        }
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