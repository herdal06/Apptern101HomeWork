package com.example.apptern101homework.base.listener

interface RvItemClickListener<T> {
    fun onClick(item: T?)
    fun onLongClick(item: T?)
}