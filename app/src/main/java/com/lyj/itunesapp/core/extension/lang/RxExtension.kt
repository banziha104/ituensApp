package com.lyj.itunesapp.core.extension.lang

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.schedulers.Schedulers

fun <T> Observable<T>.applyScheduler(subscribeOn : SchedulerType, observeOn : SchedulerType): Observable<T> =
    this.subscribeOn(subscribeOn.scheduler).observeOn(observeOn.scheduler)

fun <T> Single<T>.applyScheduler(subscribeOn : SchedulerType, observeOn : SchedulerType): Single<T> =
    this.subscribeOn(subscribeOn.scheduler).observeOn(observeOn.scheduler)


fun <T> Flowable<T>.applyScheduler(subscribeOn : SchedulerType, observeOn : SchedulerType): Flowable<T> =
    this.subscribeOn(subscribeOn.scheduler).observeOn(observeOn.scheduler)


fun <T> Maybe<T>.applyScheduler(subscribeOn : SchedulerType, observeOn : SchedulerType): Maybe<T> =
    this.subscribeOn(subscribeOn.scheduler).observeOn(observeOn.scheduler)

fun Completable.applyScheduler(subscribeOn : SchedulerType, observeOn : SchedulerType): Completable =
    this.subscribeOn(subscribeOn.scheduler).observeOn(observeOn.scheduler)


enum class SchedulerType(
    val scheduler: Scheduler
){
    MAIN(AndroidSchedulers.mainThread()),
    IO(Schedulers.io()),
    NEW(Schedulers.newThread()),
    TRAMPOLIN(Schedulers.trampoline()),
    COMPUTATION(Schedulers.computation())
}