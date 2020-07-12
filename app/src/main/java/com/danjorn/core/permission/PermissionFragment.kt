package com.danjorn.core.permission

import android.content.pm.PackageManager
import android.util.Log
import androidx.fragment.app.Fragment
import com.danjorn.core.exception.Failure
import com.danjorn.core.functional.Either
import com.danjorn.core.functional.Either.Left
import com.danjorn.core.functional.Either.Right
import com.danjorn.core.interactor.UseCase.None
import com.danjorn.core.permission.PermissionFailure.*
import kotlinx.coroutines.CompletableDeferred

class PermissionFragment : Fragment() {
    companion object {
        const val TAG = "PermissionFragment"
    }

    private val permissionDeferred = CompletableDeferred<Either<Failure, None>>()

    suspend fun requestPermissions(vararg permissions: String): Either<Failure, None> {
        requestPermissions(arrayOf(*permissions), 0)
        val result = permissionDeferred.await()
        Log.d("TEST", "$result")
        return result
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when {
            permissions.any { shouldShowRequestPermissionRationale(it) } -> permissionDeferred.complete(Left(Denied))
            grantResults.any { it == PackageManager.PERMISSION_DENIED } -> permissionDeferred.complete(Left(PermanentlyDenied))
            grantResults.all { it == PackageManager.PERMISSION_GRANTED } -> permissionDeferred.complete(Right(None()))
        }
    }
}