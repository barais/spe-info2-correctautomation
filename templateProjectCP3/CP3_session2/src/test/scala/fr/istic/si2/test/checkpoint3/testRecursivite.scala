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
   * incremente est bien définie
   */
  @Test(timeout = 1000)
  def incrementeDefined {
    isDefined(incremente(Nil))
  }

  /**
   * incremente est récursive directe
   */
  @Test(timeout = 3000)
  def incrementeOKrec {
    val isRec = checkFunctionP("incremente", exos, isRecursive(_))
    if (!isRec) {
      fail("La fonction incremente doit être récursive directe.")
    }
  }

  
  /**
   * nbZeros est bien définie
   */
  @Test(timeout = 1000)
  def nbZerosDefined {
    isDefined(nbZeros(Nil, 4))
  }

  /**
   * nbZeros est récursive directe
   */
  @Test(timeout = 3000)
  def nbZerosOKrec {
    val isRec = checkFunctionP("nbZeros", exos, isRecursive(_))
    if (!isRec) {
      fail("La fonction nbZeros doit être récursive directe.")
    }
  }

  

}

class RecursiviteCorrectionTest {

  

  /**
   * incremente ne provoque pas d'erreur à l'execution
   */
  @Test(timeout = 2000)
  def incrementeNoRTE {
    for (_ <- 1 to 10) {
      noRTE(incremente(Seq.fill(Random.nextInt(100))(Random.nextInt(2000)).toList))
    }
  }

  /**
   * incremente est correcte - cas de base
   */
  @Test(timeout = 3000)
  def incrementeOKbase {
    assertEquals(o3(List[Int]()), incremente(Nil))
  }

  /**
   * incremente est correcte - cas récursifs
   */
  @Test(timeout = 3000)
  def incrementeOKother {
    for (_ <- 1 to 100) {
      val s = Random.nextInt(10)
      val l = s :: Seq.fill(Random.nextInt(100))(Random.nextInt(2000)).toList
      assertEquals(o3(l), incremente(l))
    }
  }

  

  /**
   * nbZeros ne provoque pas d'erreur à l'execution
   */
  @Test(timeout = 2000)
  def nbZerosNoRTE {
    for (_ <- 1 to 10) {
      noRTE(nbZeros(Seq.fill(Random.nextInt(100))(Random.nextInt(200)).toList, Random.nextInt(30)))
      noRTE(nbZeros(Seq.fill(Random.nextInt(100))(0).toList, Random.nextInt(2)))
    }
  }

  /**
   * nbZeros est correcte - cas de base
   */
  @Test(timeout = 3000)
  def nbZerosOKbase {
    for (_ <- 1 to 1000) {
      assertTrue(nbZeros(Nil, o9(0)(List[Int]())))
      assertFalse(nbZeros(Nil, 1 + Random.nextInt(40)))
    }
  }

  /**
   * nbZeros est correcte - cas récursifs
   */
  @Test(timeout = 3000)
  def nbZerosOKother {
    for (_ <- 1 to 100) {
      val s = Random.nextInt(50)
      val l = s :: Seq.fill(Random.nextInt(100))(Random.nextInt(10)).toList
      assertTrue(nbZeros(l, o9(0)(l)))
    }

    var i = 0
    while (i < 100) {
      val s = Random.nextInt(50)
      val l = s :: Seq.fill(Random.nextInt(100))(Random.nextInt(10)).toList
      val n = Random.nextInt(l.length)
      if (n != o9(0)(l)) {
        assertFalse(nbZeros(l, n))
        i += 1
      }
    }
  }

  
}
