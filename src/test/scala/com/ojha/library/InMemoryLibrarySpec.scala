package com.ojha.library

import com.ojha.library.BookApi.BookInfo
import com.ojha.library.Library.LibraryItem
import org.scalatest.{Matchers, FunSpec}

/**
 * Created by alexandra on 13/08/15.
 */
class InMemoryLibrarySpec extends FunSpec with Matchers {

  describe("A library") {

    it("should have no books to begin with")  {
      val library = InMemoryLibrary()
      library.getAllBooks should be ('empty)
    }

    it("should allow retrieval of stored book ") {
      val library = InMemoryLibrary()
      val libraryItem = LibraryItem("1234", BookInfo("Harry Potter and the Philosopher's Stone", "JK Rowling"), "Fiction")
      library.addBook(libraryItem)

      val allBooks = library.getAllBooks
      allBooks should equal(Set(libraryItem))
    }
  }

  describe("Looking up in library by title") {

    it ("should be case insensitive") {
      val library = InMemoryLibrary()
      val libraryItem = LibraryItem("1234", BookInfo("Harry Potter and the Philosopher's Stone", "JK Rowling"), "Fiction")
      library.addBook(libraryItem)

      library.getBooksByTitleSegment("harry potter") should equal(Set(libraryItem))
    }

    it ("should work for one book") {
      val library = InMemoryLibrary()
      val libraryItem = LibraryItem("1234", BookInfo("Harry Potter and the Philosopher's Stone", "JK Rowling"), "Fiction")
      library.addBook(libraryItem)

      library.getBooksByTitleSegment("Harry Potter") should equal(Set(libraryItem))
    }

    it ("should work for several books") {
        val library = InMemoryLibrary()
        val hp1 = LibraryItem("1234", BookInfo("Harry Potter and the Philosopher's Stone", "JK Rowling"), "Fiction")
        val hp2 = LibraryItem("1235", BookInfo("Harry Potter and the Chamber of Secrets", "JK Rowling"), "Fiction")
        val learningHindi = LibraryItem("1236", BookInfo("Learning Hindi", "Some author"), "Learning")
        library.addBook(hp1, hp2, learningHindi)

        library.getBooksByTitleSegment("Harry Potter") should equal(Set(hp1, hp2))
    }
  }

  describe("Looking up in library by author") {

    it ("should be case insensitive") {
      val library = InMemoryLibrary()
      val libraryItem = LibraryItem("1234", BookInfo("Harry Potter and the Philosopher's Stone", "JK Rowling"), "Fiction")
      library.addBook(libraryItem)

      library.getBooksByAuthor("jk") should equal(Set(libraryItem))
      library.getBooksByAuthor("rowling") should equal(Set(libraryItem))
      library.getBooksByAuthor("jk rowling") should equal(Set(libraryItem))
    }

    it ("should work for one book") {
      val library = InMemoryLibrary()
      val libraryItem = LibraryItem("1234", BookInfo("Harry Potter and the Philosopher's Stone", "JK Rowling"), "Fiction")
      library.addBook(libraryItem)

      library.getBooksByAuthor("JK") should equal(Set(libraryItem))
      library.getBooksByAuthor("Rowling") should equal(Set(libraryItem))
      library.getBooksByAuthor("JK Rowling") should equal(Set(libraryItem))
    }

    it ("should work for several books") {
      val library = InMemoryLibrary()
      val hp1 = LibraryItem("1234", BookInfo("Harry Potter and the Philosopher's Stone", "JK Rowling"), "Fiction")
      val hp2 = LibraryItem("1234", BookInfo("Harry Potter and the Chamber of Secrets", "JK Rowling"), "Fiction")
      library.addBook(hp1)
      library.addBook(hp2)

      library.getBooksByAuthor("Rowling") should equal(Set(hp1, hp2))
    }
  }

  describe("Looking up in library by category") {

    it ("should be case insensitive") {
      val library = InMemoryLibrary()
      val libraryItem = LibraryItem("1234", BookInfo("Harry Potter and the Philosopher's Stone", "JK Rowling"), "Fiction")
      library.addBook(libraryItem)

      library.getBooksByCategory("fiction") should equal(Set(libraryItem))
    }

    it ("should work for one book") {
      val library = InMemoryLibrary()
      val libraryItem = LibraryItem("1234", BookInfo("Harry Potter and the Philosopher's Stone", "JK Rowling"), "Fiction")
      library.addBook(libraryItem)

      library.getBooksByCategory("Fiction")
    }

    it ("should work for several books") {
      val library = InMemoryLibrary()
      val hp1 = LibraryItem("1234", BookInfo("Harry Potter and the Philosopher's Stone", "JK Rowling"), "Fiction")
      val hp2 = LibraryItem("1235", BookInfo("Harry Potter and the Chamber of Secrets", "JK Rowling"), "Fiction")
      val learningHindi = LibraryItem("1236", BookInfo("Learning Hindi", "Some author"), "Learning")
      library.addBook(hp1, hp2, learningHindi)

      library.getBooksByCategory("Fiction") should equal(Set(hp1, hp2))
    }
  }

  describe("Getting categories") {

    it ("should get all categories from stored books") {
      val library = InMemoryLibrary()
      val hp1 = LibraryItem("1234", BookInfo("Harry Potter and the Philosopher's Stone", "JK Rowling"), "Fiction")
      val hp2 = LibraryItem("1235", BookInfo("Harry Potter and the Chamber of Secrets", "JK Rowling"), "Fiction")
      val learningHindi = LibraryItem("1236", BookInfo("Learning Hindi", "Some author"), "Learning")
      library.addBook(hp1, hp2, learningHindi)

      library.getAllCategories should equal(Set("Fiction", "Learning"))
    }
  }

}
