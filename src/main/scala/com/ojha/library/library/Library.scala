package com.ojha.library.library

import com.ojha.library.book.BookApi.BookInfo
import com.ojha.library.library.Library.LibraryItem

/**
 * Created by alexandra on 05/05/15.
 */

object Library {
  case class LibraryItem(uniqueBookId: String, bookInfo: BookInfo, category: String)
}

trait Library {

  def addBook(item: LibraryItem)

  def getAllCategories: Set[String]

  def getBookByAuthor(author: String): LibraryItem

  def getBookByTitleSegment(titleText: String): LibraryItem

  def getBooksByCategory(category: String): Set[LibraryItem]

  def getAllBooks: Set[LibraryItem]

}
