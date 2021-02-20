package com.example.myapplication

interface IDelegate<T> {
    fun<T> delegate(vararg params:T)
}