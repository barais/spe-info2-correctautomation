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
   * double est bien définie
   */
  @Test(timeout = 1000)
  def doubleDefined {
    isDefined(double(Nil))
  }

  /**
   * double est récursive directe
   */
  @Test(timeout = 3000)
  def doubleOKrec {
    val isRec = checkFunctionP("double", exos, isRecursive(_))
    if (!isRec) {
      fail("La fonction double doit être récursive directe.")
    }
  }

  /**
   * renverse est bien définie
   */
  @Test(timeout = 1000)
  def renverseDefined {
    isDefined(renverse(Nil))
  }

  /**
   * renverse est récursive directe
   */
  @Test(timeout = 3000)
  def renverseOKrec {
    val isRec = checkFunctionP("renverse", exos, isRecursive(_))
    if (!isRec) {
      fail("La fonction renverse doit être récursive directe.")
    }
  }

}

class RecursiviteCorrectionTest {

  /**
   * double ne provoque pas d'erreur à l'execution
   */
  @Test(timeout = 2000)
  def doubleNoRTE {
    for (_ <- 1 to 10) {
      noRTE(double(Seq.fill(Random.nextInt(100))(Random.nextInt(200)).toList))
    }
  }

  /**
   * double est correcte - cas de base
   */
  @Test(timeout = 3000)
  def doubleOKbase {
    assertEquals(o5(List[Int]()), double(Nil))
  }

  /**
   * double est correcte - cas récursifs
   */
  @Test(timeout = 3000)
  def doubleOKother {
    for (_ <- 1 to 100) {
      val s = Random.nextInt(2000)
      val l = s :: Seq.fill(Random.nextInt(100))(Random.nextInt(2000)).toList
      assertEquals(o5(l), double(l))
    }
  }

  /**
   * renverse ne provoque pas d'erreur à l'execution
   */
  @Test(timeout = 2000)
  def renverseNoRTE {
    for (_ <- 1 to 10) {
      noRTE(renverse(Seq.fill(Random.nextInt(100))(Random.nextString(Random.nextInt(8))).toList))
    }
  }

  /**
   * renverse est correcte - cas de base
   */
  @Test(timeout = 3000)
  def renverseOKbase {
    assertTrue(renverse(Nil).length == 0)
  }

  /**
   * renverse est correcte - cas récursifs
   */
  @Test(timeout = 3000)
  def renverseOKother {
    for (_ <- 1 to 100) {
      val s = Random.nextString(Random.nextInt(10))
      val l = s :: Seq.fill(Random.nextInt(100))(Random.nextString(Random.nextInt(8))).toList
      assertEquals(o12(l), renverse(l))
    }
  }
}
