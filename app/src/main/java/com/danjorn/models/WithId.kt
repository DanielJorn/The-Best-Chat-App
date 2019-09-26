package com.danjorn.models

/**
 * We use this abstract class to solve problem: we don't have any way to map POJOs with their IDs.
 * This class forces POJO to have the id field. All POJOs that have to have ID field must extend it.
 *
 * Use cases: we can create ktx on WithId class. Or declare function with a generic type WithId.
 * */
abstract class WithId {
    abstract var id: String?
}