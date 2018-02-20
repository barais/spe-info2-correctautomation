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
   * elementsPositifs est bien définie
   */
  @Test(timeout = 1000)
  def elementsPositifsDefined {
    isDefined(elementsPositifs(Nil))
  }

  /**
   * elementsPositifs est récursive directe
   */
  @Test(timeout = 3000)
  def elementsPositifsOKrec {
    val isRec = checkFunctionP("elementsPositifs", exos, isRecursive(_))
    if (!isRec) {
      fail("La fonction elementsPositifs doit être récursive directe.")
    }
  }

  /**
   * queLesVrais est bien définie
   */
  @Test(timeout = 1000)
  def queLesVraisDefined {
    isDefined(queLesVrais(Nil))
  }

  /**
   * queLesVrais est récursive directe
   */
  @Test(timeout = 3000)
  def queLesVraisOKrec {
    val isRec = checkFunctionP("queLesVrais", exos, isRecursive(_))
    if (!isRec) {
      fail("La fonction queLesVrais doit être récursive directe.")
    }
  }

}

class RecursiviteCorrectionTest {

  /**
   * elementsPositifs ne provoque pas d'erreur à l'execution
   */
  @Test(timeout = 2000)
  def elementsPositifsNoRTE {
    for (_ <- 1 to 10) {
      noRTE(elementsPositifs(Seq.fill(Random.nextInt(100))(Random.nextInt(200)).toList))
    }
  }

  /**
   * elementsPositifs est correcte - cas de base
   */
  @Test(timeout = 3000)
  def elementsPositifsOKbase {
    assertEquals(o8(List[Int]()), elementsPositifs(Nil))
  }

  /**
   * elementsPositifs est correcte - cas récursifs
   */
  @Test(timeout = 3000)
  def elementsPositifsOKother {
    for (_ <- 1 to 100) {
      val s = Random.nextInt(2000)
      val signe = if (Random.nextBoolean) 1 else -1
      val l = s :: Seq.fill(Random.nextInt(100))(signe * Random.nextInt(2000)).toList
      assertEquals(o8(l), elementsPositifs(l))
    }
  }

  /**
   * queLesVrais ne provoque pas d'erreur à l'execution
   */
  @Test(timeout = 2000)
  def queLesVraisNoRTE {
    for (_ <- 1 to 100) {
      noRTE(queLesVrais(Seq.fill(Random.nextInt(40))((Random.nextBoolean(), Random.nextInt(200))).toList))
    }
  }

  /**
   * queLesVrais est correcte - cas de base
   */
  @Test(timeout = 3000)
  def queLesVraisOKbase {
    for (_ <- 1 to 1000) {
      assertTrue(queLesVrais(Nil).isEmpty)
    }
  }

  /**
   * queLesVrais est correcte - cas récursifs
   */
  @Test(timeout = 3000)
  def queLesVraisOKother {
    for (_ <- 1 to 100) {
      val s = (Random.nextBoolean(), Random.nextInt(50))
      val l = s :: Seq.fill(Random.nextInt(100))((Random.nextBoolean(), Random.nextInt(10))).toList
      assertEquals(o11(l), queLesVrais(l))
    }
  }

}
