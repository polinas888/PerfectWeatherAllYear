package com.example.perfectweatherallyear

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit

fun Fragment.changeFragment(args: Bundle, fragmentManager: FragmentManager) {
        this.arguments = args

        fragmentManager.commit {
            addToBackStack(null)
            replace(R.id.nav_host_fragment, this@changeFragment)
        }
    }