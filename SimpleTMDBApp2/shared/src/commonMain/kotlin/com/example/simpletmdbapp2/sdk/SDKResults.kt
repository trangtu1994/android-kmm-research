package com.example.simpletmdbapp2.sdk


interface Result
class SuccessResult<T> (val data: T): Result
class ErrorResult (val exception: Exception): Result

typealias OnCompletionListener = (Result) -> Unit