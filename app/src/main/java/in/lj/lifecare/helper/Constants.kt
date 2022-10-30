package `in`.lj.lifecare.helper

import `in`.lj.lifecare.R
import `in`.lj.lifecare.data.Dept

object Constants {
    val DEPARTMENTS = listOf<Dept>(
        Dept("Gynecology", R.drawable.gynecology),
        Dept("Dermatology",R.drawable.dermatology),
        Dept("Physician",R.drawable.physician),
        Dept("Sexology",R.drawable.sexology),
        Dept("Psychiatry",R.drawable.psychiatry),
        Dept("Stomach",R.drawable.stomach),
        Dept("Pediatrics",R.drawable.pediatrics)
    )
}