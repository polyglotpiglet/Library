package com.ojha.library.dataModel

/**
 * Created by alexandra on 05/05/15.
 */
case class BookInfo(title: String, author: String)
case class LibraryItem(bookInfo: BookInfo, category: String)
