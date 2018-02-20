package fr.istic.si2.test.checkpoint3

import org.junit.Test
import org.junit.Assert._
import util.Random
import fr.istic.si2.testerApp._
import fr.istic.si2.moreAssertions._
import fr.istic.si2.checkpoint3.QuestionRecursivite
import fr.istic.si2.checkpoint3.QuestionRecursivite._
import fr.istic.si2.checkpoint3._
import fr.istic.si2.checkpoint3.oracles.Oracles._

import Consignes._

class AARecursiviteDefinitionTest {

  val exos = parseFile("recursivite.scala", "fr.istic.si2.checkpoint3")

  /**
   * listeLongueurs est bien définie
   */
  @Test(timeout = 1000)
  def listeLongueursDefined {
    isDefined(listeLongueurs(Nil))
  }

  /**
   * listeLongueurs est récursive directe
   */
  @Test(timeout = 3000)
  def listeLongueursOKrec {
    val isRec = checkFunctionP("listeLongueurs", exos, isRecursive(_))
    if (!isRec) {
      fail("La fonction listeLongueurs doit être récursive directe.")
    }
  }

  /**
   * nth est bien définie
   */
  @Test(timeout = 1000)
  def nthDefined {
    isDefined(nth(Nil, 0))
  }

  /**
   * nth est récursive directe
   */
  @Test(timeout = 3000)
  def nthOKrec {
    val isRec = checkFunctionP("nth", exos, isRecursive(_))
    if (!isRec) {
      fail("La fonction nth doit être récursive directe.")
    }
  }

}

class RecursiviteCorrectionTest {

  /**
   * listeLongueurs ne provoque pas d'erreur à l'execution
   */
  @Test(timeout = 2000)
  def listeLongueursNoRTE {
    for (_ <- 1 to 10) {
      noRTE(listeLongueurs(Seq.fill(Random.nextInt(100))(Random.nextString(Random.nextInt(10))).toList))
    }
  }

  /**
   * listeLongueurs est correcte - cas de base
   */
  @Test(timeout = 3000)
  def listeLongueursOKbase {
    assertEquals(o1(List[String]()), listeLongueurs(Nil))
  }

  /**
   * listeLongueurs est correcte - cas récursifs
   */
  @Test(timeout = 3000)
  def listeLongueursOKother {
    for (_ <- 1 to 100) {
      val s = Random.nextString(Random.nextInt(10))
      val l = s :: Seq.fill(Random.nextInt(100))(Random.nextString(Random.nextInt(10))).toList
      assertEquals(o1(l), listeLongueurs(l))
    }
  }

  /**
   * nth ne provoque pas d'erreur à l'execution
   */
  @Test(timeout = 2000)
  def nthNoRTE {
    for (_ <- 1 to 10) {
      noRTE(nth(Seq.fill(Random.nextInt(100))(Random.nextString(Random.nextInt(8))).toList, Random.nextInt(30)))
      noRTE(nth(Seq.fill(Random.nextInt(80))(Random.nextString(Random.nextInt(8))).toList, 80 + Random.nextInt(30)))
    }
  }

  /**
   * nth est correcte - cas de base
   */
  @Test(timeout = 3000)
  def nthOKbase {
    for (_ <- 1 to 1000) {
      val n = Random.nextInt(100)
      assertEquals(None, nth(Nil, n))
    }
  }

  /**
   * nth est correcte - cas récursifs
   */
  @Test(timeout = 3000)
  def nthOKother {
    for (_ <- 1 to 1000) {
      val n = 1 + Random.nextInt(100)
      val s = Random.nextString(5)
      val l = s :: Seq.fill(n)(Random.nextString(Random.nextInt(8))).toList
      val m = Random.nextInt(l.size)
      assertEquals(Some(l(m)), nth(l, m))
      assertEquals(None, nth(l, math.max(l.size, Random.nextInt(100))))
    }
  }

}
