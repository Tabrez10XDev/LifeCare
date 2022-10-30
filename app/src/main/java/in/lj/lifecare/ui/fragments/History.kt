package `in`.lj.lifecare.ui.fragments

import `in`.lj.lifecare.DateBindingModel_
import `in`.lj.lifecare.DateHistoryBindingModel_
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import `in`.lj.lifecare.R
import `in`.lj.lifecare.dateHistory
import com.airbnb.epoxy.CarouselModel_
import kotlinx.android.synthetic.main.fragment_history.*
import java.text.SimpleDateFormat
import java.util.*


class History : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvController()
    }

    private fun rvController(){
        rvHistory.withModels {
            val nextWeek = getNextWeek()
            val dateHistoryModel = mutableListOf<DateHistoryBindingModel_>()

            nextWeek.forEachIndexed { index, s ->
                val dd = s.split(" ")

                val cardColour = if(index==0) R.color.history_date_selected else R.color.history_date_unselected
                val dotColour = if(index==0) R.color.white else R.color.history_date_selected
                dateHistoryModel.add(
                    DateHistoryBindingModel_()
                        .id(index)
                        .cardColour(cardColour)
                        .dotColour(dotColour)
                        .day(dd[0].substring(0,3))
                        .date(dd[1].substring(0,2))
                )

            }

            CarouselModel_()
                .id("carousel")
                .models(dateHistoryModel)
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
}