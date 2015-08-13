package com.ojha.library

import com.ojha.library.Library.LibraryItem

/**
 * Created by alexandra on 12/08/15.
 */
object InMemoryLibrary {
  def apply(): InMemoryLibrary = {
    new InMemoryLibrary
  }
}

class InMemoryLibrary extends Library {

  private var allLibraryItems = Set[LibraryItem]()

  override def addBook(item: LibraryItem*): Unit = {
    allLibraryItems = allLibraryItems ++ item
  }

  override def getAllBooks: Set[LibraryItem] = allLibraryItems

  override def getAllCategories: Set[String] = {
   allLibraryItems.map(i => i.category)
  }


  /*
    Look up books by title.
    Case insensitive lookup and can search by substring ('har' string will match 'Harry Potter')
  */
  override def getBooksByTitleSegment(titleText: String): Set[LibraryItem] = {
    val searchstring = titleText.toLowerCase
    allLibraryItems.filter(i => i.bookInfo.title.toLowerCase.contains(searchstring))
  }

  /*
     Look up books by author.
     Case insensitive lookup and can search by substring ('fi' string will match 'Smithfield' and 'Fielding')
   */
  override def getBooksByAuthor(authorText: String): Set[LibraryItem] = {
    val searchString = authorText.toLowerCase
    allLibraryItems.filter(i => i.bookInfo.author.toLowerCase.contains(searchString))
  }

  /*
     Books are stored against a category, which is just a string.
     We lookup by the exact string.
   */
  override def getBooksByCategory(category: String): Set[LibraryItem] = {
    val searchstring = category.toLowerCase
    allLibraryItems.filter(i => i.category.toLowerCase == searchstring)
  }
}
