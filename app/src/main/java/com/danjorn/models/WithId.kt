package com.danjorn.models

/**
 * We use this abstract class to solve problem: we don't have any way to map POJOs with their IDs.
 * This class forces POJO to have the id field. All POJOs that have to have ID field must extend it.
 * */
abstract class WithId {
    abstract var id: String?
}