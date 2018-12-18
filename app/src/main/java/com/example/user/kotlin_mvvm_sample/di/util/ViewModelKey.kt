package com.example.user.kotlin_mvvm_sample.di.util

import android.arch.lifecycle.ViewModel
import dagger.MapKey
import java.lang.annotation.*
import java.lang.annotation.Retention
import java.lang.annotation.Target
import kotlin.reflect.KClass

@Documented
@MapKey
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
annotation class ViewModelKey(val value: KClass<out ViewModel>)