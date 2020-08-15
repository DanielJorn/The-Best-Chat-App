package com.danjorn.core.di

import com.danjorn.AndroidApplication
import com.danjorn.core.di.viewmodel.ViewModelModule
import com.danjorn.core.navigation.RouteActivity
import com.danjorn.features.chat.ChatListFragment
import com.danjorn.features.login.SignInFragment
import com.danjorn.features.login.SignUpFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ViewModelModule::class, ApplicationModule::class])
@Singleton
interface ApplicationComponent {
    fun inject(application: AndroidApplication)
    fun inject(routeActivity: RouteActivity)

    fun inject(chatListFragment: ChatListFragment)
    fun inject(signInFragment: SignInFragment)
    fun inject(signUpFragment: SignUpFragment)
}