package com.danjorn.core.extension

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

fun FragmentManager.inTransaction(body: FragmentTransaction.() -> FragmentTransaction) =
        beginTransaction().body().commitNow()
