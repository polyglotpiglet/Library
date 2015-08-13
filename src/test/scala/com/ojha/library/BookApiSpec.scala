package com.ojha.library

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Seconds, Span}

/**
 * Created by alexandra on 06/05/15.
 */
class BookApiSpec extends UnitSpec with BookApi with ScalaFutures {

  implicit val defaultPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(2, Seconds))

  it should "retrieve book info from book id" taggedAs ExternalTest in {
    val bookInfo = getBookInfo("9780099800200")

    whenReady(bookInfo) { bookInfo =>
      bookInfo.title should equal("Slaughterhouse-five")
      bookInfo.author should equal("Vonnegut, Kurt")
    }
  }

  it should "fail when book doesnt exist" taggedAs ExternalTest in {
    val bookInfo = getBookInfo("0000")

    whenReady(bookInfo.failed) { e =>
        e.getMessage should equal("Unable to retrieve book info for book id = 0000")
    }
  }

}
