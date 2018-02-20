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
   * elementsPairs est bien définie
   */
  @Test(timeout = 1000)
  def elementsPairsDefined {
    isDefined(elementsPairs(Nil))
  }

  /**
   * elementsPairs est récursive directe
   */
  @Test(timeout = 3000)
  def elementsPairsOKrec {
    val isRec = checkFunctionP("elementsPairs", exos, isRecursive(_))
    if (!isRec) {
      fail("La fonction elementsPairs doit être récursive directe.")
    }
  }

  /**
   * sommeEgalA est bien définie
   */
  @Test(timeout = 1000)
  def sommeEgalADefined {
    isDefined(sommeEgalA(Nil, 4))
  }

  /**
   * sommeEgalA est récursive directe
   */
  @Test(timeout = 3000)
  def sommeEgalAOKrec {
    val isRec = checkFunctionP("sommeEgalA", exos, isRecursive(_))
    if (!isRec) {
      fail("La fonction sommeEgalA doit être récursive directe.")
    }
  }

}

class RecursiviteCorrectionTest {

  /**
   * elementsPairs ne provoque pas d'erreur à l'execution
   */
  @Test(timeout = 2000)
  def elementsPairsNoRTE {
    for (_ <- 1 to 10) {
      noRTE(elementsPairs(Seq.fill(Random.nextInt(100))(Random.nextInt(200)).toList))
    }
  }

  /**
   * elementsPairs est correcte - cas de base
   */
  @Test(timeout = 3000)
  def elementsPairsOKbase {
    assertEquals(o7(List[Int]()), elementsPairs(Nil))
  }

  /**
   * elementsPairs est correcte - cas récursifs
   */
  @Test(timeout = 3000)
  def elementsPairsOKother {
    for (_ <- 1 to 100) {
      val s = Random.nextInt(2000)
      val l = s :: Seq.fill(Random.nextInt(100))(Random.nextInt(2000)).toList
      assertEquals(o7(l), elementsPairs(l))
    }
  }

  /**
   * sommeEgalA ne provoque pas d'erreur à l'execution
   */
  @Test(timeout = 2000)
  def sommeEgalANoRTE {
    for (_ <- 1 to 10) {
      noRTE(sommeEgalA(Seq.fill(Random.nextInt(100))(Random.nextInt(200)).toList, Random.nextInt(30)))
      noRTE(sommeEgalA(Seq.fill(Random.nextInt(100))(0).toList, Random.nextInt(100)))
    }
  }

  /**
   * sommeEgalA est correcte - cas de base
   */
  @Test(timeout = 3000)
  def sommeEgalAOKbase {
    for (_ <- 1 to 1000) {
      assertTrue(sommeEgalA(Nil, 0))
      assertFalse(sommeEgalA(Nil, 1 + math.abs(Random.nextInt(50))))
    }
  }

  /**
   * sommeEgalA est correcte - cas récursifs
   */
  @Test(timeout = 3000)
  def sommeEgalAOKother {
    for (_ <- 1 to 100) {
      val s = Random.nextInt(50)
      val l = s :: Seq.fill(Random.nextInt(100))(Random.nextInt(10)).toList
      assertTrue(sommeEgalA(l, o10(l)))

    }

    var i = 0
    while (i < 100) {
      val s = Random.nextInt(50)
      val l = s :: Seq.fill(Random.nextInt(100))(Random.nextInt(10)).toList
      val n = Random.nextInt(l.length * 2)
      if (n != o10(l)) {
        assertFalse(sommeEgalA(l, n))
        i += 1
      }
    }
  }

}
