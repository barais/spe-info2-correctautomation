package fr.istic.si2.test.checkpoint3

import org.junit.Test
import org.junit.Assert._
import util.Random
import fr.istic.si2.testerApp._
import fr.istic.si2.moreAssertions._
import fr.istic.si2.checkpoint3.QuestionTypesAlgebriques
import fr.istic.si2.checkpoint3.QuestionTypesAlgebriques._
import fr.istic.si2.checkpoint3._
import UtilsGen._

import org.scalacheck._
import Gen.{ fail => _, _ }
import Arbitrary.arbitrary

import Consignes._

class ATypesAlgebriquesDefinitionTest {

  val exos = parseFile("typesAlg.scala", "fr.istic.si2.checkpoint3")

  /**
   * p1 est bien définie
   */
  @Test(timeout = 3000)
  def p1Defined {
    val b = exos.find(valWithName("p1"))
    b match {
      case None    => fail()
      case Some(v) => assertTrue(isNotQmark(v))
    }
  }

  /**
   * p2 est bien définie
   */
  @Test(timeout = 3000)
  def p2Defined {
    val b = exos.find(valWithName("p2"))
    b match {
      case None    => fail()
      case Some(v) => assertTrue(isNotQmark(v))
    }
  }

  /**
   * p3 est bien définie
   */
  @Test(timeout = 3000)
  def p3Defined {
    val b = exos.find(valWithName("p3"))
    b match {
      case None    => fail()
      case Some(v) => assertTrue(isNotQmark(v))
    }
  }

  /**
   * sujetDuPost est bien définie
   */
  @Test(timeout = 3000)
  def sujetDuPostDefined {
    val b = exos.find(methodWithName("sujetDuPost"))
    b match {
      case None    => fail()
      case Some(m) => assertTrue(isNotQmark(m))
    }
  }

  /**
   * trending est bien définie
   */
  @Test(timeout = 3000)
  def trendingDefined {
    val b = exos.find(methodWithName("trending"))
    b match {
      case None    => fail()
      case Some(m) => assertTrue(isNotQmark(m))
    }
  }

}

class TypesAlgebriquesCorrectionTest {

  val _ = new AppInit(QuestionTypesAlgebriques)

  /**
   * p1 est correcte.
   */
  @Test(timeout = 100)
  def p1OK {
    p1 match {
      case v: Publicite => assertEquals("http://www.giveMeYourMoney.com", v.url)
      case _            => fail()
    }
  }

  /**
   * p2 est correcte.
   */
  @Test(timeout = 100)
  def p2OK {
    p2 match {
      case v: Article =>
        assertEquals("Le pouvoir du Min Max", v.titre)
        assertEquals("John von Neumann".toLowerCase, v.auteur.toLowerCase())
        assertEquals(Economie, v.s)
      case _ => fail()
    }
  }

  /**
   * p3 est correcte.
   */
  @Test(timeout = 100)
  def p3OK {
    p3 match {
      case v: FakeNews =>
        assertEquals(Politique, v.s)
      case _ => fail()
    }
  }

  /**
   * sujetDuPost est correcte - cas defini
   */
  @Test(timeout = 3000)
  def sujetDuPostOKsome {
    var i = 0
    while (i < 1000) {
      oneOf(genFakeNews, genArticle).sample match {
        case Some(r: FakeNews) =>
          i += 1; assertEquals(r.s, sujetDuPost(r).get)
        case Some(r: Article) =>
          i += 1; assertEquals(r.s, sujetDuPost(r).get)
        case _ => ()
      }
    }
  }

  /**
   * sujetDuPost est correcte - cas non def
   */
  @Test(timeout = 1000)
  def sujetDuPostOKno {
    var i = 0
    while (i < 1000) {
      genPublicite.sample match {
        case None    => ()
        case Some(r) => i += 1; assertEquals(None, sujetDuPost(r))
      }
    }
  }

  /**
   * trending est correcte - cas negatif
   */
  @Test(timeout = 1000)
  def trendingOKnegatif {
    var i = 0
    while (i < 1000) {
      (arbitrary[Int] suchThat (_ > 0)).sample match {
        case None => ()
        case Some(n) =>
          (genPost suchThat (p => nbclics(p) < n)).sample match {
            case None    => ()
            case Some(t) => i += 1; assertFalse(trending(t, n))
          }
      }
    }
  }

  /**
   * trending est correcte - cas positif
   */
  @Test(timeout = 1000)
  def trendingOKpositif {
    var i = 0
    while (i < 1000) {
      (arbitrary[Int] suchThat (_ > 0)).sample match {
        case None => ()
        case Some(n) =>
          (genPost suchThat (p => nbclics(p) > n)).sample match {
            case None    => ()
            case Some(t) => i += 1; assertTrue(trending(t, n))
          }
      }
    }
  }

}

object UtilsGen {

  val genSujet = oneOf(Economie, Politique, Culture)
  val genPublicite = {
    for {
      u <- arbitrary[String]
      c <- arbitrary[Int] suchThat (_ >= 0)
    } yield Publicite(u, c)
  }
  val genArticle = {
    for {
      s <- genSujet
      a <- arbitrary[String]
      t <- arbitrary[String]
      c <- arbitrary[Int] suchThat (_ >= 0)
    } yield Article(s, a, t, c)
  }
  val genFakeNews = {
    for {
      s <- genSujet
      c <- arbitrary[Int] suchThat (_ >= 0)
    } yield FakeNews(s, c)
  }
  val genPost = oneOf(genArticle, genPublicite, genFakeNews)

  val nbclics: Post => Int = { p =>
    p match {
      case FakeNews(_, n)      => n
      case Article(_, _, _, n) => n
      case Publicite(_, n)     => n
    }
  }

}
