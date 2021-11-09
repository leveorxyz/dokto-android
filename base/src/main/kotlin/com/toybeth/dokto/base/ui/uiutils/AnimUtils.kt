package com.toybeth.dokto.base.ui.uiutils

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

enum class AnimState {
    ENTER, EXIT, POPENTER, POPEXIT
}

@ExperimentalAnimationApi
@Composable
fun getEnterAnimation(animState: AnimState?): EnterTransition {
    return if(animState == AnimState.POPENTER) {
        getPopEnterAnimation()
    } else {
        getEnterAnimation()
    }
}

@ExperimentalAnimationApi
@Composable
fun getExitAnimation(animState: AnimState?): ExitTransition {
    return if(animState == AnimState.POPEXIT) {
        getPopExitAnimation()
    } else {
        getExitAnimation()
    }
}

@ExperimentalAnimationApi
@Composable
private fun getEnterAnimation(): EnterTransition {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    return slideInHorizontally(
        initialOffsetX = {
            screenWidth.value.toInt()
        }
    ) + fadeIn(initialAlpha = .3f)
}

@ExperimentalAnimationApi
@Composable
private fun getPopEnterAnimation(): EnterTransition {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    return slideInHorizontally(
        initialOffsetX = {
            -screenWidth.value.toInt()
        }
    ) + fadeIn(initialAlpha = .3f)
}

@ExperimentalAnimationApi
@Composable
private fun getExitAnimation(): ExitTransition {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    return slideOutHorizontally(
        targetOffsetX = {
            -screenWidth.value.toInt()
        }
    ) + fadeOut(targetAlpha = .3f)
}

@ExperimentalAnimationApi
@Composable
private fun getPopExitAnimation(): ExitTransition {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    return slideOutHorizontally(
        targetOffsetX = {
            screenWidth.value.toInt()
        }
    ) + fadeOut(targetAlpha = .3f)
}