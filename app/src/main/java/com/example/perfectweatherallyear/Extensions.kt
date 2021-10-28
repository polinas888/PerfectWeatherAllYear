package com.example.perfectweatherallyear

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

    fun Fragment.changeFragment(args: Bundle, fragmentManager: FragmentManager) {
        this.arguments = args

        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, this)
        transaction.addToBackStack(null)
        transaction.commit()
    }