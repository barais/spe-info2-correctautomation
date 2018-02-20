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
   * listeParites est bien définie
   */
  @Test(timeout = 1000)
  def listeParitesDefined {
    isDefined(listeParites(Nil))
  }

  /**
   * listeParites est récursive directe
   */
  @Test(timeout = 3000)
  def listeParitesOKrec {
    val isRec = checkFunctionP("listeParites", exos, isRecursive(_))
    if (!isRec) {
      fail("La fonction listeParites doit être récursive directe.")
    }
  }

  /**
   * queLesVrais est bien définie
   */
  @Test(timeout = 1000)
  def queLesVraDefined {
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
   * listeParites ne provoque pas d'erreur à l'execution
   */
  @Test(timeout = 2000)
  def listeParitesNoRTE {
    for (_ <- 1 to 10) {
      noRTE(listeParites(Seq.fill(Random.nextInt(100))(Random.nextInt(2000)).toList))
    }
  }

  /**
   * listeParites est correcte - cas de base
   */
  @Test(timeout = 3000)
  def listeParitesOKbase {
    assertEquals(o2(List[Int]()), listeParites(Nil))
  }

  /**
   * listeParite est correcte - cas récursifs
   */
  @Test(timeout = 3000)
  def listePariteOKother {
    for (_ <- 1 to 100) {
      val s = Random.nextInt(10)
      val l = s :: Seq.fill(Random.nextInt(100))(Random.nextInt(2000)).toList
      assertEquals(o2(l), listeParites(l))
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
