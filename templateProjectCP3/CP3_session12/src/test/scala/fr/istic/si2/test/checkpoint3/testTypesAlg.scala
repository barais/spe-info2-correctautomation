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
   * r1 est bien définie
   */
  @Test(timeout = 3000)
  def r1Defined {
    val b = exos.find(valWithName("r1"))
    b match {
      case None    => fail()
      case Some(v) => assertTrue(isNotQmark(v))
    }
  }

  /**
   * r2 est bien définie
   */
  @Test(timeout = 3000)
  def r2Defined {
    val b = exos.find(valWithName("r2"))
    b match {
      case None    => fail()
      case Some(v) => assertTrue(isNotQmark(v))
    }
  }

  /**
   * r3 est bien définie
   */
  @Test(timeout = 3000)
  def r3Defined {
    val b = exos.find(valWithName("r3"))
    b match {
      case None    => fail()
      case Some(v) => assertTrue(isNotQmark(v))
    }
  }

  /**
   * sonRendu est bien définie
   */
  @Test(timeout = 3000)
  def sonRenduDefined {
    val b = exos.find(methodWithName("sonRendu"))
    b match {
      case None    => fail()
      case Some(m) => assertTrue(isNotQmark(m))
    }
  }

  /**
   * note est bien définie
   */
  @Test(timeout = 3000)
  def noteDefined {
    val b = exos.find(methodWithName("note"))
    b match {
      case None    => fail()
      case Some(m) => assertTrue(isNotQmark(m))
    }
  }

  

}

class TypesAlgebriquesCorrectionTest {

  val _ = new AppInit(QuestionTypesAlgebriques)

  /**
   * r1 est correcte.
   */
  @Test(timeout = 100)
  def r1OK {
    r1 match {
      case Invalide(_, m) =>
        assertNotEquals(Retard, m)
        assertNotEquals(MauvaisFormat, m)
      case _ => fail()
    }
  }

  /**
   * r2 est correcte.
   */
  @Test(timeout = 100)
  def r2OK {
    r2 match {
      case Invalide(_, _) => fail()
      case OK(_, _, _, _) => fail()
      case _              => ()
    }
  }

  /**
   * r3 est correcte.
   */
  @Test(timeout = 100)
  def r3OK {
    r3 match {
      case Absent(_)      => fail()
      case Invalide(_, _) => fail()
      case i: OK =>
        assertEquals(4, i.nbTestFail)
        assertEquals(1, i.nbWarn)
        assertEquals(8, i.totalPointCP)
    }
  }

  /**
   * sonRendu est correcte - cas positif
   */
  @Test(timeout = 3000)
  def sonRenduOKlesien {
    var i = 0
    while (i < 1000) {
      genRendu.sample match {
        case None    => ()
        case Some(r) => i += 1; assertTrue(sonRendu(r, id(r)))
      }
    }
  }

  /**
   * sonRendu est correcte - cas negatif
   */
  @Test(timeout = 1000)
  def sonRenduOKunautre {
    var i = 0
    while (i < 1000) {
      arbitrary[String].sample match {
        case None => ()
        case Some(s) =>
          (genRendu suchThat ((r: RenduCP) => id(r) != s)).sample match {
            case None    => ()
            case Some(r) => i += 1; assertFalse(sonRendu(r, s))
          }
      }
    }
  }

  /**
   * note est correcte - cas sans note
   */
  @Test(timeout = 1000)
  def noteOKpasDeNote {
    var i = 0
    while (i < 1000) {
      genAbsent.sample match {
        case None    => ()
        case Some(t) => i += 1; assertEquals((t.etu, None), note(t))
      }
    }
  }

  /**
   * note est correcte - cas avec note
   */
  /**
   * note est correcte - cas avec note
   */
  @Test(timeout = 4000)
  def noteOKnote {
    var i = 0
    while (i < 1000) {
      oneOf(genOKpos,genOKneg).sample match {
        case None => ()
        case Some(std: OK) => {
          i += 1
          assertEquals(std.etu, note(std)._1)
          assertTrue(note(std)._2.get >= 0)
          assertTrue(note(std)._2.get <= std.totalPointCP)
          assertEquals(std.totalPointCP, math.min(std.totalPointCP, note(std)._2.get + std.nbWarn +  2 * std.nbTestFail))
        }
      }
    }
    i = 0
    while (i < 1000) {
      genInvalide.sample match {
        case None => ()
        case Some(s: Invalide) => {
          i += 1
          assertEquals((s.etu, Some(0)), note(s))
        }
      }
    }
  }

  

}

object UtilsGen {

  val genMotif: Gen[Motif] = Gen.oneOf(Error, MauvaisFormat, Retard)
  val genAbsent = {
    for {
      id <- arbitrary[String]
    } yield Absent(id)
  }
  val genInvalide = {
    for {
      id <- arbitrary[String]
      m <- genMotif
    } yield Invalide(id, m)
  }
  val genOKpos = {
    for {
      id <- arbitrary[String]
      fl <- arbitrary[Int] suchThat (_ >= 0) suchThat (_ <= 20)
      wrn <- arbitrary[Int] suchThat (_ >= 0) suchThat (_ <= 20)
    } yield OK(id, 3 * fl + 2 * wrn, wrn, fl)
  }
  val genOKneg = {
    for {
      id <- arbitrary[String]
      fl <- arbitrary[Int] suchThat (_ >= 0) suchThat (_ <= 20)
      wrn <- arbitrary[Int] suchThat (_ >= 0) suchThat (_ <= 20)
    } yield OK(id, fl + wrn, wrn, fl)
  }
  val genRendu = oneOf(genAbsent, genInvalide, genOKpos, genOKneg)

  val id: RenduCP => String = { r =>
    r match {
      case Absent(id)      => id
      case Invalide(id, _) => id
      case OK(id, _, _, _) => id
    }
  }

}
