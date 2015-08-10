package com.ojha.library.library

import com.ojha.library.dataModel.LibraryItem

/**
 * Created by alexandra on 05/05/15.
 */
trait Library {

  def addBook(item: LibraryItem)

  def getAllCategories: List[String]

}
