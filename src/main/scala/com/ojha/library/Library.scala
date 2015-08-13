package com.ojha.library

import com.ojha.library.BookApi.BookInfo
import com.ojha.library.Library.LibraryItem

/**
 * Created by alexandra on 05/05/15.
 */

object Library {
  case class LibraryItem(uniqueBookId: String, bookInfo: BookInfo, category: String)
}

trait Library {

  def addBook(item: LibraryItem*)

  def getAllCategories: Set[String]

  def getBooksByAuthor(author: String): Set[LibraryItem]

  def getBooksByTitleSegment(titleText: String): Set[LibraryItem]

  def getBooksByCategory(category: String): Set[LibraryItem]

  def getAllBooks: Set[LibraryItem]

}
