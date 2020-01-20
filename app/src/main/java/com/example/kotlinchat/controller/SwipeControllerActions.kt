package com.example.kotlinchat.controller

abstract class SwipeControllerActions {

    open fun onLeftClicked(position: Int) {}

    open fun onRightClicked(position: Int) {}

}