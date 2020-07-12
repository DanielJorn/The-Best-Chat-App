package com.danjorn.core.interactor

import com.danjorn.core.exception.Failure
import com.danjorn.core.functional.Either
import kotlinx.coroutines.*

/**
 * [Type] is a successful result returned by UseCase instance.
 * Pass any class you need as [Params],
 * and [None] if you don't need them.
 */
abstract class UseCase<out Type, in Params> where Type : Any{

    abstract suspend fun run(params: Params) : Either<Failure, Type>

    operator fun invoke(params: Params, onResult: (Either<Failure, Type>) -> Unit = {}){
        val job = GlobalScope.async(Dispatchers.Main) { run(params) }
        GlobalScope.launch(Dispatchers.Main) { onResult(job.await()) }
    }

    class None
}