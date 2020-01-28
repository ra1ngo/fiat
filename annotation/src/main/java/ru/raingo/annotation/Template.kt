package ru.raingo.annotation

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FIELD)
annotation class Template(val path: Int)
