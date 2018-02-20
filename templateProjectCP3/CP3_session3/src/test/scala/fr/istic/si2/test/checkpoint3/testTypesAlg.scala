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
   * v1 est bien définie
   */
  @Test(timeout = 3000)
  def v1Defined {
    val b = exos.find(valWithName("v1"))
    b match {
      case None    => fail()
      case Some(v) => assertTrue(isNotQmark(v))
    }
  }

  /**
   * v2 est bien définie
   */
  @Test(timeout = 3000)
  def v2Defined {
    val b = exos.find(valWithName("v2"))
    b match {
      case None    => fail()
      case Some(v) => assertTrue(isNotQmark(v))
    }
  }

  /**
   * v3 est bien définie
   */
  @Test(timeout = 3000)
  def v3Defined {
    val b = exos.find(valWithName("v3"))
    b match {
      case None    => fail()
      case Some(v) => assertTrue(isNotQmark(v))
    }
  }

  /**
   * estToutTerrain est bien définie
   */
  @Test(timeout = 3000)
  def estToutTerrainDefined {
    val b = exos.find(methodWithName("estToutTerrain"))
    b match {
      case None    => fail()
      case Some(m) => assertTrue(isNotQmark(m))
    }
  }

  /**
   * prixTotal est bien définie
   */
  @Test(timeout = 3000)
  def prixTotalDefined {
    val b = exos.find(methodWithName("prixTotal"))
    b match {
      case None    => fail()
      case Some(m) => assertTrue(isNotQmark(m))
    }
  }

}

class TypesAlgebriquesCorrectionTest {

  val _ = new AppInit(QuestionTypesAlgebriques)

  /**
   * v1 est correcte.
   */
  @Test(timeout = 100)
  def v1OK {
    v1 match {
      case v: Libre => assertEquals(Ville, v.model)
      case _        => fail()
    }
  }

  /**
   * v2 est correcte.
   */
  @Test(timeout = 100)
  def v2OK {
    v2 match {
      case Libre(_, _, _)   => fail()
      case Loue(_, _, _, _) => fail()
      case c: Casse         => assertEquals(Vtt, c.model)
    }
  }

  /**
   * v3 est correcte.
   */
  @Test(timeout = 100)
  def v3OK {
    v3 match {
      case Casse(_, _)    => fail()
      case Libre(_, _, _) => fail()
      case i: Loue =>
        assertEquals(Course, i.model)
        assertEquals(4, i.dureeNbHeures)
    }
  }

  /**
   * estToutTerrain est correcte - cas positif
   */
  @Test(timeout = 3000)
  def estToutTerrainOKyes {
    var i = 0
    while (i < 1000) {
      (genVelo suchThat ((v: Velo) => model(v) == Vtt)).sample match {
        case None    => ()
        case Some(r) => i += 1; assertTrue(estToutTerrain(r))
      }
    }
  }

  /**
   * estToutTerrain est correcte - cas negatif
   */
  @Test(timeout = 1000)
  def estToutTerrainOKno {
    var i = 0
    while (i < 1000) {
      (genVelo suchThat ((v: Velo) => model(v) != Vtt)).sample match {
        case None    => ()
        case Some(r) => i += 1; assertFalse(estToutTerrain(r))
      }
    }
  }

  /**
   * prixTotal est correcte - cas sans prix
   */
  @Test(timeout = 1000)
  def prixTotalOKpasDePrix {
    var i = 0
    while (i < 1000) {
      oneOf(genCasse, genLibre).sample match {
        case None    => ()
        case Some(t) => i += 1; assertEquals(None, prixTotal(t))
      }
    }
  }

  /**
   * prixTotal est correcte - cas avec prix
   */
  @Test(timeout = 1000)
  def prixTotalOKprix {
    var i = 0
    while (i < 1000) {
      genLoue.sample match {
        case None => ()
        case Some(v) => {
          i += 1
          assertEquals(v.dureeNbHeures * v.prixLocHeure, prixTotal(v).get)
        }
      }
    }
  }

}

object UtilsGen {

  val genModel = oneOf(Ville, Course, Vtt)
  val genCasse = {
    for {
      id <- arbitrary[Int]
      m <- genModel
    } yield Casse(id, m)
  }
  val genLibre = {
    for {
      id <- arbitrary[Int]
      m <- genModel
      p <- arbitrary[Int] suchThat (_ > 0)
    } yield Libre(id, m, p)
  }
  val genLoue = {
    for {
      id <- arbitrary[Int]
      m <- genModel
      p <- arbitrary[Int] suchThat (_ > 0)
      d <- arbitrary[Int] suchThat (_ > 0)
    } yield Loue(id, m, p, d)
  }
  val genVelo = oneOf(genCasse, genLibre, genLoue)
  val model: Velo => Modele = v => {
    v match {
      case Casse(_, m)      => m
      case Libre(_, m, _)   => m
      case Loue(_, m, _, _) => m
    }
  }

}
