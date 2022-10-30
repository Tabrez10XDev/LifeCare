package `in`.lj.lifecare.ui.fragments

import `in`.lj.lifecare.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import `in`.lj.lifecare.data.Booking
import android.widget.Toast
import com.airbnb.epoxy.CarouselModel_
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_history.*
import java.text.SimpleDateFormat
import java.util.*


class History : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var fireStore : FirebaseFirestore = FirebaseFirestore.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        getBookings()
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

    private fun rvController2(bookings : MutableList<Booking>){
        rvHistory2.withModels {
            bookings.forEachIndexed { index, booking ->
                var status = "Completed"
                if(booking.status)
                    status = "Currently Ongoing"
                historyCard {
                    id(index)
                    name(booking.doctorName)
                    dept(booking.doctorDept)
                    status(status)
                    date(booking.ddmmyy)
                }
            }
        }
    }

    private fun getBookings(){
        val itemList : MutableList<Booking> = arrayListOf()
        fireStore.collection("users")
            .document(auth.uid!!)
            .collection("appointments")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    itemList.add(
                        document.toObject(Booking::class.java)
                    )

                }
                rvController2(itemList)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(requireContext(), exception.localizedMessage, Toast.LENGTH_SHORT).show()
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