package com.danjorn.core.permission

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.danjorn.core.exception.Failure
import com.danjorn.core.extension.inTransaction
import com.danjorn.core.functional.Either
import com.danjorn.core.functional.Either.*
import com.danjorn.core.interactor.UseCase.*
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PermissionManager
@Inject constructor(private val context: Context){

    suspend fun requestPermissions(vararg permissions: String, parentFragment: Fragment): Either<Failure, None> {
        val permissionFragment = PermissionFragment()
        parentFragment.fragmentManager?.inTransaction { add(permissionFragment, PermissionFragment.TAG) }
        val result = permissionFragment.requestPermissions(*permissions)
        parentFragment.fragmentManager?.inTransaction { remove(permissionFragment) }
        return result
    }

    fun isPermissionGranted(permission: String): Boolean{
        return when (ContextCompat.checkSelfPermission(context, permission)) {
            PackageManager.PERMISSION_GRANTED -> true
            else -> false
        }
    }
}