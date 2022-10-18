package io.github.tuguzt.flexibleproject.view.navigation

sealed class RootNavigationDestinations(override val route: String) : Destination {
    object Main : RootNavigationDestinations(route = "main")

    sealed class Auth(override val route: String) : RootNavigationDestinations(route) {
        companion object Root : Auth(route = "auth")

        object SignIn : Auth(route = "signIn")

        object SignUp : Auth(route = "signUp")
    }
}
