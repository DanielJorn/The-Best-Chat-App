package com.danjorn.features.chat

import com.danjorn.core.interactor.UseCase
import com.danjorn.core.interactor.UseCase.None
import javax.inject.Inject

class GetChats
@Inject constructor(private val chatRepository: ChatRepository) : UseCase<List<Chat>, None>() {

    override suspend fun run(params: None) = chatRepository.chats()
}