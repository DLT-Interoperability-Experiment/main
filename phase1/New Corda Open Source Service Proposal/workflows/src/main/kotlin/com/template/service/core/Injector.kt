package com.template.service.core

interface Injector {

    fun <T> getInstance(clazz: Class<T>): T

}