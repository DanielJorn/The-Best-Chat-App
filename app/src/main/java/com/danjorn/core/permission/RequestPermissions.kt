package com.danjorn.core.permission

import androidx.fragment.app.Fragment
import com.danjorn.core.exception.Failure
import com.danjorn.core.functional.Either
import com.danjorn.core.interactor.UseCase
import com.danjorn.core.interactor.UseCase.None
import com.danjorn.core.permission.RequestPermissions.*
import javax.inject.Inject

class RequestPermissions
@Inject constructor(private val permissionManager: PermissionManager) : UseCase<None, PermissionParams>() {
    override suspend fun run(params: PermissionParams) =
            permissionManager.requestPermissions(*params.permissions, parentFragment = params.parentFragment)

    data class PermissionParams(
            val permissions: Array<String>,
            val parentFragment: Fragment
    )
}