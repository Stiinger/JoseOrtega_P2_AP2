package edu.ucne.joseortega_p2_ap2.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute

@Composable
fun ParcialNavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.EntidadList
    ) {
        composable<Screen.EntidadList> {
//            EntidadListScreen(
//                createEntidad = { navHostController.navigate(Screen.Entidad(0)) },
//                goToEntidad = { navHostController.navigate(Screen.Entidad(it)) }
//            )
        }
        composable<Screen.Entidad> {
            val entidadId = it.toRoute<Screen.Entidad>().entidadId
//            EntidadScreen(
//                entidadId = entidadId,
//                goBackToList = { navHostController.navigateUp() }
//            )
        }
    }
}