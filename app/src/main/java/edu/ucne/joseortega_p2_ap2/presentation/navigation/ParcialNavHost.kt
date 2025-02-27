package edu.ucne.joseortega_p2_ap2.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.joseortega_p2_ap2.presentation.depositos.DepositoListScreen
import edu.ucne.joseortega_p2_ap2.presentation.depositos.DepositoScreen

@Composable
fun ParcialNavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.DepositoList
    ) {
        composable<Screen.DepositoList> {
            DepositoListScreen(
                createDeposito = { navHostController.navigate(Screen.Deposito(0)) },
                goToDeposito = { navHostController.navigate(Screen.Deposito(it)) }
            )
        }
        composable<Screen.Deposito> {
            val depositoId = it.toRoute<Screen.Deposito>().depositoId
            DepositoScreen(
                depositoId = depositoId,
                goBackToList = { navHostController.navigateUp() }
            )
        }
    }
}