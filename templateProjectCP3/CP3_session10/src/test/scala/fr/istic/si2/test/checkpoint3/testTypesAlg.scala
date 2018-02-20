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
   * d1 est bien définie
   */
  @Test(timeout = 3000)
  def d1Defined {
    val b = exos.find(valWithName("d1"))
    b match {
      case None    => fail()
      case Some(v) => assertTrue(isNotQmark(v))
    }
  }

  /**
   * d2 est bien définie
   */
  @Test(timeout = 3000)
  def d2Defined {
    val b = exos.find(valWithName("d2"))
    b match {
      case None    => fail()
      case Some(v) => assertTrue(isNotQmark(v))
    }
  }

  /**
   * d3 est bien définie
   */
  @Test(timeout = 3000)
  def d3Defined {
    val b = exos.find(valWithName("d3"))
    b match {
      case None    => fail()
      case Some(v) => assertTrue(isNotQmark(v))
    }
  }

  /**
   * reference est bien définie
   */
  @Test(timeout = 3000)
  def referenceDefined {
    val b = exos.find(methodWithName("reference"))
    b match {
      case None    => fail()
      case Some(m) => assertTrue(isNotQmark(m))
    }
  }

  /**
   * voyagePro est bien définie
   */
  @Test(timeout = 3000)
  def voyageProDefined {
    val b = exos.find(methodWithName("voyagePro"))
    b match {
      case None    => fail()
      case Some(m) => assertTrue(isNotQmark(m))
    }
  }

}

class TypesAlgebriquesCorrectionTest {

  val _ = new AppInit(QuestionTypesAlgebriques)

  /**
   * d1 est correcte.
   */
  @Test(timeout = 100)
  def d1OK {
    d1 match {
      case v: Marche => assertEquals(Tourisme, v.r)
      case _         => fail()
    }
  }

  /**
   * d2 est correcte.
   */
  @Test(timeout = 100)
  def d2OK {
    d2 match {
      case v: Avion =>
        assertEquals("WQAGT", v.ref)
        assertTrue(v.bagage)
      case _ => fail()
    }
  }

  /**
   * d3 est correcte.
   */
  @Test(timeout = 100)
  def d3OK {
    d3 match {
      case v: Train =>
        assertEquals(75, v.prix)
        assertEquals(Autre, v.r)
      case _ => fail()
    }
  }

  /**
   * reference est correcte - cas defini
   */
  @Test(timeout = 3000)
  def referenceOKsome {
    var i = 0
    while (i < 1000) {
      oneOf(genTrain, genAvion).sample match {
        case Some(r: Train) =>
          i += 1; assertEquals(r.ref, reference(r).get)
        case Some(r: Avion) =>
          i += 1; assertEquals(r.ref, reference(r).get)
        case _ => ()
      }
    }
  }

  /**
   * reference est correcte - cas non def
   */
  @Test(timeout = 1000)
  def referenceOKno {
    var i = 0
    while (i < 1000) {
      genMarche.sample match {
        case None    => ()
        case Some(r) => i += 1; assertEquals(None, reference(r))
      }
    }
  }

  /**
   * voyagePro est correcte - cas negatif
   */
  @Test(timeout = 1000)
  def voyageProOKnegatif {
    var i = 0
    while (i < 1000) {
      arbitrary[Int].sample match {
        case None => ()
        case Some(n) =>
          genMarche.sample match {
            case None    => ()
            case Some(t) => i += 1; assertFalse(voyagePro(t, n))
          }
      }
    }
  }

  /**
   * voyagePro est correcte - cas peut-être
   */
  @Test(timeout = 1000)
  def voyageProOKmaybe {
    var i = 0
    while (i < 1000) {
      oneOf(genTrain, genAvion).sample match {
        case None => ()
        case Some(v) => {
          i += 1
          val p = arbitrary[Int].sample.get
          val b = arbitrary[Boolean].sample.get
          v match {
            case o: Train =>
              if (b) assertEquals(o.r == Travail, voyagePro(o, o.prix))
              else assertEquals(o.prix == p & o.r == Travail, voyagePro(o, p))
            case o: Avion =>
              if (b) assertEquals(o.r == Travail, voyagePro(o, o.prix))
              else assertEquals(o.prix == p & o.r == Travail, voyagePro(o, p))
            case _ => fail()
          }
        }
      }
    }
  }

}

object UtilsGen {

  val genRaison: Gen[Raison] = Gen.oneOf(Travail, Tourisme, Autre)
  val genMarche: Gen[Marche] = {
    for {
      r <- genRaison
    } yield Marche(r)
  }
  val genTrain: Gen[Train] = {
    for {
      r <- genRaison
      ref <- arbitrary[String]
      prix <- arbitrary[Int]
    } yield Train(r, ref, prix)
  }
  val genAvion: Gen[Avion] = {
    for {
      r <- genRaison
      ref <- arbitrary[String]
      prix <- arbitrary[Int]
      b <- arbitrary[Boolean]
    } yield Avion(r, ref, prix, b)
  }
  val genDeplacement: Gen[Deplacement] = oneOf(genMarche, genTrain, genAvion)

}
