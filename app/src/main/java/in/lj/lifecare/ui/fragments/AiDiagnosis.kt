package `in`.lj.lifecare.ui.fragments

import `in`.lj.lifecare.R
import `in`.lj.lifecare.api.RetrofitInstance
import `in`.lj.lifecare.data.Dept
import `in`.lj.lifecare.data.Features
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.ai_suggestion_card.*
import kotlinx.android.synthetic.main.fragment_ai_diagnosis.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AiDiagnosis : Fragment() {


    val chips  = mutableListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ai_diagnosis, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startLoading()
        Handler(Looper.getMainLooper()).postDelayed({
            stopLoading()
        }, 4000)

        val arr = emptyArray<String>()
        var adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireContext(), android.R.layout.select_dialog_item, arr)

        etSearch.threshold = 2
        etSearch.setAdapter(adapter)
        etSearch.setOnItemClickListener { adapterView, view, i, l ->
            addChipToGroup(adapterView.getItemAtPosition(i).toString())
            etSearch.text.clear()
        }

        btnSearch.setOnClickListener {
            val item  = Dept(
                "physician",
                R.drawable.physician
            )
            val bundle = Bundle().apply {
                putSerializable("Category",item)
            }
            findNavController().navigate(R.id.action_aiDiagnosis_to_doctorsList, bundle)
        }

        btnDiagnose.setOnClickListener {
            startLoadingAnimation()
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    getDisease()
                }, 2000)

        }
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitInstance.api.getFeatureList().isSuccessful
            if(response){
                val featureList = RetrofitInstance.api.getFeatureList().body()!!.features
                adapter = ArrayAdapter<String>(requireContext(), android.R.layout.select_dialog_item, featureList)
                withContext(Dispatchers.Main){
                    etSearch.setAdapter(adapter)
                }
            }
        }
    }

    private fun getDisease(){
        val body = Features(chips)
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitInstance.api.getDisease(body)
            if(response.isSuccessful){
                val result = response.body()
                withContext(Dispatchers.Main){
                    tvPossibilityInner.text = result?.result
                    stopLoadingAnimation()
                }
            }
        }
    }

    private fun addChipToGroup(symptom: String) {
        val chip = Chip(context)
        chip.text = symptom
        chip.chipIcon = ContextCompat.getDrawable(requireContext(), R.drawable.landing_bg)
        chip.background = ContextCompat.getDrawable(requireContext(), R.drawable.landing_bg)
        chip.isChipIconVisible = false
        chip.isCloseIconVisible = true
        // necessary to get single selection working
        chips.add(symptom)
        chip.isClickable = true
        chip.isCheckable = false
        chip_group.addView(chip as View)
        chip.setOnCloseIconClickListener {
            chip_group.removeView(chip as View)
            chips.remove(symptom)
        }
    }

    private fun startLoading(){
        innerCL.visibility = View.INVISIBLE
        tvDiagnosis.visibility = View.VISIBLE
        tvLoading.visibility = View.VISIBLE
        animationView.visibility = View.VISIBLE
        animationView.alpha = 1f
        animationView.playAnimation()

    }

    private fun startLoadingAnimation(){
        btnDiagnose.visibility = View.INVISIBLE
        animationView2.visibility = View.VISIBLE
        animationView2.alpha = 1f
        animationView2.playAnimation()
    }

    private fun stopLoadingAnimation(){
        tvSuggestions.visibility = View.VISIBLE
        tvPossibility.visibility = View.VISIBLE
        cvPossibility.visibility = View.VISIBLE
        aiSuggestionsCard.visibility = View.VISIBLE
        btnDiagnose.visibility = View.INVISIBLE
        animationView2.visibility = View.INVISIBLE
        animationView2.alpha = 0f
        animationView2.pauseAnimation()
    }

    private fun stopLoading(){
        innerCL.visibility = View.VISIBLE
        tvDiagnosis.visibility = View.INVISIBLE
        tvLoading.visibility = View.INVISIBLE
        animationView.pauseAnimation()
        animationView.visibility = View.INVISIBLE
        animationView.alpha = 0f
    }
}