/*
 * Copyright 2021 Monun
 *
 * Licensed under the Apache License, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://opensource.org/licenses/gpl-3.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.monun.tap.collection

import java.util.AbstractList
import java.util.Collections

@Suppress("unused")
class SortedList<E> : AbstractList<E>, RandomAccess, Cloneable {
    private val list: ArrayList<E>
    private val comparator: Comparator<E>?

    constructor() {
        list = ArrayList()
        comparator = null
    }

    constructor(comparator: Comparator<E>? = null) {
        list = ArrayList()
        this.comparator = comparator
    }

    constructor(initialCapacity: Int, comparator: Comparator<E>? = null) {
        list = ArrayList(initialCapacity)
        this.comparator = comparator
    }

    constructor(elements: Collection<E>, comparator: Comparator<E>? = null) {
        list = ArrayList(elements)
        this.comparator = comparator
        sort()
    }

    override fun add(element: E): Boolean {
        sort()
        binaryAdd(element)
        return true
    }

    fun sort() {
        Collections.sort(list, comparator)
    }

    fun binaryAdd(element: E): Int {
        modCount++
        val size = list.size
        if (size == 0) {
            list.add(element)
            return 0
        }
        var index = binarySearch(element)
        if (index < 0) index = -(index + 1)
        list.add(index, element)
        return index
    }

    fun binaryRemove(element: E): Boolean {
        val index = binarySearch(element)
        if (index >= 0) {
            list.removeAt(index)
            return true
        }
        return false
    }

    override fun addAll(elements: Collection<E>): Boolean {
        list.ensureCapacity(elements.size)
        for (element in elements) add(element)
        return true
    }

    override fun clear() {
        list.clear()
    }

    override operator fun contains(element: E): Boolean {
        return list.contains(element)
    }

    override fun containsAll(elements: Collection<E>): Boolean {
        return list.containsAll(elements)
    }

    override fun equals(other: Any?): Boolean {
        return other is SortedList<*> && list == other.list
    }

    private fun binarySearch(element: E): Int {
        return Collections.binarySearch(list, element, comparator)
    }

    override fun get(index: Int): E {
        return list[index]
    }

    override fun hashCode(): Int {
        return list.hashCode()
    }

    override fun indexOf(element: E): Int {
        return list.indexOf(element)
    }

    override fun isEmpty(): Boolean {
        return list.isEmpty()
    }

    override fun lastIndexOf(element: E): Int {
        return list.lastIndexOf(element)
    }

    override fun listIterator(index: Int): MutableListIterator<E> {
        return object : MutableListIterator<E> {
            private val iterator = list.listIterator(index)
            override fun add(element: E) {
                throw UnsupportedOperationException()
            }

            override fun hasNext(): Boolean {
                return iterator.hasNext()
            }

            override fun hasPrevious(): Boolean {
                return iterator.hasPrevious()
            }

            override fun next(): E {
                return iterator.next()
            }

            override fun nextIndex(): Int {
                return iterator.nextIndex()
            }

            override fun previous(): E {
                return iterator.previous()
            }

            override fun previousIndex(): Int {
                return iterator.previousIndex()
            }

            override fun remove() {
                iterator.remove()
            }

            override fun set(element: E) {
                throw UnsupportedOperationException()
            }
        }
    }

    override fun removeAt(index: Int): E {
        return list.removeAt(index)
    }

    override fun remove(element: E): Boolean {
        return list.remove(element)
    }

    override fun removeAll(elements: Collection<E>): Boolean {
        return list.removeAll(elements)
    }

    override fun retainAll(elements: Collection<E>): Boolean {
        return list.retainAll(elements)
    }

    override val size: Int
        get() {
            return list.size
        }

    override fun toArray(): Array<Any?> {
        return list.toTypedArray()
    }

    override fun <T> toArray(a: Array<T>): Array<T> {
        return list.toArray(a)
    }

    override fun toString(): String {
        return list.toString()
    }
}