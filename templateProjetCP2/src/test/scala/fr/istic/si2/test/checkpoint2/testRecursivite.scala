package fr.istic.si2.test.checkpoint2

import org.junit.Test
import org.junit.Assert._
import util.Random
import fr.istic.si2.testerApp._
import fr.istic.si2.moreAssertions._
import fr.istic.si2.checkpoint2.QuestionRecursivite
import fr.istic.si2.checkpoint2.QuestionRecursivite._
import fr.istic.si2.checkpoint2._
import Oracles._

import Consignes._

class AARecursiviteDefinitionTest {

  val rand = new Random()
  val exos = parseFile("recursivite.scala", "fr.istic.si2.checkpoint2")

  /**
   * allerRetour est bien définie
   */
  @Test(timeout = 1000)
  def allerRetourDefined {
    isDefined(allerRetour(42))
  }

  /**
   * chiffreGauche est bien définie
   */
  @Test(timeout = 1000)
  def chiffreGaucheDefined {
    isDefined(chiffreGauche(90234))
  }

  /**
   * allerRetour est récursive
   */
  @Test(timeout = 3000)
  def allerRetourOKrec {
    val isRec = checkFunctionP("allerRetour", exos, isRecursive(_))
    if (!isRec) {
      fail("La fonction allerRetour doit être récursive directe.")
    }
  }

  /**
   * chiffreGauche est récursive
   */
  @Test(timeout = 3000)
  def chiffreGaucheOKrec {
    val isRec = checkFunctionP("chiffreGauche", exos, isRecursive(_))
    if (!isRec) {
      fail("La fonction chiffreGauche doit être récursive directe.")
    }
  }

}

class RecursiviteCorrectionTest {

  val rand = new Random()
  val exos = parseFile("recursivite.scala", "fr.istic.si2.checkpoint2")

  /**
   * allerRetour ne provoque pas d'erreur à l'execution
   */
  @Test(timeout = 2000)
  def allerRetourNoRTE {
    for (_ <- 1 to 10) noRTE(allerRetour(rand.nextInt(100)))
  }

  /**
   * allerRetour est correcte - cas de base
   */
  @Test(timeout = 3000)
  def allerRetourOKbase {
    assertEquals(allerRetourOracle(0),allerRetour(0))
  }

  /**
   * allerRetour est correcte - cas récursifs
   */
  @Test(timeout = 3000)
  def allerRetourOKother {
    for (_ <- 1 to 100) {
      val n = rand.nextInt(100)
      assertEquals(allerRetourOracle(n),allerRetour(n))
    }
  }

  /**
   * allerRetour calcule une chaîne de la bonne longueur
   */
  @Test(timeout = 3000)
  def allerRetourOKlength {
    for (_ <- 1 to 100) {
      val n = math.abs(rand.nextInt(100))
      assertEquals(allerRetourOracle(n).length,allerRetour(n).length)
    }
  }

  /**
   * chiffreGauche ne provoque pas d'erreur à l'execution
   */
  @Test(timeout = 1000)
  def chiffreGaucheNoRTE {
    noRTE(chiffreGauche(0))
    for (_ <- 1 to 1000) noRTE(chiffreGauche(math.abs(rand.nextInt())))
  }

  /**
   * La fonction chiffreGauche est correcte - cas de base
   */
  @Test(timeout = 1000)
  def chiffreGaucheOKbase {
    for (i <- 1 to 9)
      assertEquals(chiffreGaucheOracle(i),chiffreGauche(i))
  }

  /**
   * La fonction chiffreGauche est correcte - cas récursif
   */
  @Test(timeout = 1000)
  def chiffreGaucheOKres {
    var i = 1
    while (i <= 1000) {
      val n = math.abs(rand.nextInt())
      if (n > 9) {
        i += 1;
        assertEquals(chiffreGaucheOracle(n),chiffreGauche(n))
      } else ()
    }
  }

  /**
   * La fonction chiffreGauche calcule bien un chiffre
   */
  @Test(timeout = 1000)
  def chiffreGaucheOKresDigit {
    for (_ <- 1 to 1000) {
      assertTrue(chiffreGauche(rand.nextInt()) < 10)
    }
  }

}

object Oracles  {

  /**
   * @param n un entier positif ou nul
   * @return la chaîne de caractères composée d'une énumération
   *         des entiers de n à jusqu'à 0, puis de nouveau jusqu'à n.
   *         Les entiers sont séparés par des espaces.
   */
  def allerRetourOracle(n: Int): String = {
    if (n == 0) { "0" }
    else {
      var res : String = n.toString
      for (i <- n-1 to 0 by -1) { res = res + " " + i }
      for (i <- 1 to n) { res = res + " " + i }
      res
    }
  }
  
  /**
   * @param n un entier positif ou nul
   * @return le chiffre le plus à gauche dans l'écriture de n
   * @note on suppose que l'écriture de n n'a pas de 0 superflu à gauche
   */
  def chiffreGaucheOracle(n: Int): Int = {
    n.toString().charAt(0).toString().toInt
  }

}
