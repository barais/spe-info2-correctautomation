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
   * flip est bien définie
   */
  @Test(timeout = 1000)
  def flipDefined {
    isDefined(flip(Nil))
  }

  /**
   * flip est récursive directe
   */
  @Test(timeout = 3000)
  def flipOKrec {
    val isRec = checkFunctionP("flip", exos, isRecursive(_))
    if (!isRec) {
      fail("La fonction flip doit être récursive directe.")
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
   * flip ne provoque pas d'erreur à l'execution
   */
  @Test(timeout = 2000)
  def flipNoRTE {
    for (_ <- 1 to 10) {
      noRTE(flip(Seq.fill(Random.nextInt(100))(Random.nextBoolean).toList))
    }
  }

  /**
   * flip est correcte - cas de base
   */
  @Test(timeout = 3000)
  def flipOKbase {
    assertEquals(o4(List[Boolean]()), flip(Nil))
  }

  /**
   * flip est correcte - cas récursifs
   */
  @Test(timeout = 3000)
  def flipOKother {
    for (_ <- 1 to 100) {
      val s = Random.nextBoolean
      val l = s :: Seq.fill(Random.nextInt(100))(Random.nextBoolean).toList
      assertEquals(o4(l), flip(l))
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
