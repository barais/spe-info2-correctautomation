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
   * c1 est bien définie
   */
  @Test(timeout = 3000)
  def c1Defined {
    val b = exos.find(valWithName("c1"))
    b match {
      case None    => fail()
      case Some(v) => assertTrue(isNotQmark(v))
    }
  }

  /**
   * c2 est bien définie
   */
  @Test(timeout = 3000)
  def c2Defined {
    val b = exos.find(valWithName("c2"))
    b match {
      case None    => fail()
      case Some(v) => assertTrue(isNotQmark(v))
    }
  }

  /**
   * c3 est bien définie
   */
  @Test(timeout = 3000)
  def c3Defined {
    val b = exos.find(valWithName("c3"))
    b match {
      case None    => fail()
      case Some(v) => assertTrue(isNotQmark(v))
    }
  }

  /**
   * aGagne est bien définie
   */
  @Test(timeout = 3000)
  def aGagneDefined {
    val b = exos.find(methodWithName("aGagne"))
    b match {
      case None    => fail()
      case Some(m) => assertTrue(isNotQmark(m))
    }
  }

  /**
   * niveauSup est bien définie
   */
  @Test(timeout = 3000)
  def niveauSupDefined {
    val b = exos.find(methodWithName("niveauSup"))
    b match {
      case None    => fail()
      case Some(m) => assertTrue(isNotQmark(m))
    }
  }

}

class TypesAlgebriquesCorrectionTest {

  val _ = new AppInit(QuestionTypesAlgebriques)

  /**
   * c1 est correcte.
   */
  @Test(timeout = 100)
  def c1OK {
    c1 match {
      case v: Normale => assertEquals(Entree, v.typ.get)
      case _          => fail()
    }
  }

  /**
   * c2 est correcte.
   */
  @Test(timeout = 100)
  def c2OK {
    assertTrue(c2.isInstanceOf[Obstacle])
  }

  /**
   * c3 est correcte.
   */
  @Test(timeout = 100)
  def c3OK {
    assertTrue(c3.isInstanceOf[Mur])
  }

  /**
   * aGagne est correcte - cas potentiel
   */
  @Test(timeout = 3000)
  def aGagneOKyes {
    var i = 0
    while (i < 1000) {
      (genNormale suchThat (_.typ != None)).sample match {
        case None    => ()
        case Some(r) => i += 1; assertEquals(r.typ.get == Sortie, aGagne(r))
      }
    }
  }

  /**
   * aGagne est correcte - cas negatif
   */
  @Test(timeout = 1000)
  def aGagneOKno {
    var i = 0
    while (i < 1000) {
      (oneOf(genMur, genObstacle)).sample match {
        case None    => ()
        case Some(r) => i += 1; assertFalse(aGagne(r))
      }
    }
  }

  /**
   * niveauSup est correcte - inchangée
   */
  @Test(timeout = 1000)
  def niveauSupOKsame {
    var i = 0
    while (i < 1000) {
      oneOf(genMur, genNormale).sample match {
        case None    => ()
        case Some(t) => i += 1; assertEquals(t, niveauSup(t))
      }
    }
  }

  /**
   * niveauSup est correcte - penalité augmentee
   */
  @Test(timeout = 1000)
  def niveauSupOKpen {
    var i = 0
    while (i < 1000) {
      genObstacle.sample match {
        case None => ()
        case Some(v) => {
          i += 1
          niveauSup(v) match {
            case o: Obstacle => {
              assertEquals(v.x, o.x)
              assertEquals(v.y, o.y)
              assertEquals(2 * v.penalite, o.penalite)
            }
            case _ => fail()
          }
        }
      }
    }
  }

}

object UtilsGen {

  val genEntreeSortie = oneOf(Some(Entree), Some(Sortie), None)
  val genMur = {
    for {
      i <- arbitrary[Int] suchThat (_ >= 0)
      j <- arbitrary[Int] suchThat (_ >= 0)
    } yield Mur(i, j)
  }
  val genNormale = {
    for {
      i <- arbitrary[Int] suchThat (_ >= 0)
      j <- arbitrary[Int] suchThat (_ >= 0)
      t <- genEntreeSortie
    } yield Normale(i, j, t)
  }
  val genObstacle = {
    for {
      i <- arbitrary[Int] suchThat (_ >= 0)
      j <- arbitrary[Int] suchThat (_ >= 0)
      t <- arbitrary[Int] suchThat (_ >= 0) suchThat (_ < Int.MaxValue / 2)
    } yield Obstacle(i, j, t)
  }
  val genCase = oneOf(genMur, genNormale, genObstacle)

}
