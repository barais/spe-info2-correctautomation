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
   * t1 est bien définie
   */
  @Test(timeout = 3000)
  def t1Defined {
    val b = exos.find(valWithName("t1"))
    b match {
      case None    => fail()
      case Some(v) => assertTrue(isNotQmark(v))
    }
  }

  /**
   * t2 est bien définie
   */
  @Test(timeout = 3000)
  def t2Defined {
    val b = exos.find(valWithName("t2"))
    b match {
      case None    => fail()
      case Some(v) => assertTrue(isNotQmark(v))
    }
  }

  /**
   * t3 est bien définie
   */
  @Test(timeout = 3000)
  def t3Defined {
    val b = exos.find(valWithName("t3"))
    b match {
      case None    => fail()
      case Some(v) => assertTrue(isNotQmark(v))
    }
  }

  /**
   * ticketValide est bien définie
   */
  @Test(timeout = 3000)
  def ticketValideDefined {
    val b = exos.find(methodWithName("ticketValide"))
    b match {
      case None    => fail()
      case Some(m) => assertTrue(isNotQmark(m))
    }
  }

  /**
   * prixTicket est bien définie
   */
  @Test(timeout = 3000)
  def prixTicketDefined {
    val b = exos.find(methodWithName("prixTicket"))
    b match {
      case None    => fail()
      case Some(m) => assertTrue(isNotQmark(m))
    }
  }

  


}

class TypesAlgebriquesCorrectionTest {

  val _ = new AppInit(QuestionTypesAlgebriques)

  /**
   * t1 est correcte.
   */
  @Test(timeout = 100)
  def t1OK {
    t1 match {
      case Scolaire(p, _) =>
        assertNotEquals(p, Soir)
        assertNotEquals(p, ApresMidi)
      case _ => fail()
    }
  }

  /**
   * t2 est correcte.
   */
  @Test(timeout = 100)
  def t2OK {
    t2 match {
      case Scolaire(_, _)    => fail()
      case Standard(_, _, _) => fail()
      case _                 => ()
    }
  }

  /**
   * t3 est correcte.
   */
  @Test(timeout = 100)
  def t3OK {
    t3 match {
      case Scolaire(_, _) => fail()
      case Visiteur(_)    => fail()
      case i: Standard =>
        assertEquals(ApresMidi, i.p)
        assertFalse(i.moitiePrix)
    }
  }

  /**
   * ticketValide est correcte - cas positif
   */
  @Test(timeout = 1000)
  def ticketValideOKvalide {
    var i = 0
    while (i < 1000) {
      genPeriode.sample match {
        case None => ()
        case Some(p) =>
          (genTicket suchThat ((t: Ticket) => periode(t) == p)).sample match {
            case None    => ()
            case Some(t) => i += 1; assertTrue(ticketValide(t, p))
          }
      }
    }

  }

  /**
   * ticketValide est correcte - cas negatif
   */
  @Test(timeout = 1000)
  def ticketValideOKinvalide {
    var i = 0
    while (i < 1000) {
      genPeriode.sample match {
        case None => ()
        case Some(p) =>
          (genTicket suchThat ((t: Ticket) => periode(t) != p)).sample match {
            case None    => ()
            case Some(t) => i += 1; assertFalse(ticketValide(t, p))
          }
      }
    }

  }

  /**
   * prixTicket est correcte - cas sans prix
   */
  @Test(timeout = 1000)
  def prixTicketOKvisiteur {
    var i = 0
    while (i < 1000) {
      genVisiteur.sample match {
        case None    => ()
        case Some(t) => i += 1; assertEquals(None, prixTicket(t))
      }
    }
  }

  /**
   * prixTicket est correcte - cas avec prix
   */
  @Test(timeout = 1000)
  def prixTicketOKpayant {
    var i = 0
    while (i < 1000) {
      oneOf(genScolaire, genStandard).sample match {
        case None => ()
        case Some(s: Scolaire) => {
          i += 1
          assertEquals(Some(s.prixEntree), prixTicket(s))
        }
        case Some(std: Standard) => {
          i += 1
          if (std.moitiePrix) {
            assertEquals(std.prixEntree / 2, prixTicket(std).get)
          } else {
            assertEquals(std.prixEntree, prixTicket(std).get)
          }
        }
        case _ => ()
      }
    }
  }

  
}

object UtilsGen {

  val periode: Ticket => Periode = { t =>
    t match {
      case Visiteur(p)       => p
      case Standard(p, _, _) => p
      case Scolaire(p, _)    => p
    }
  }

  val genPeriode: Gen[Periode] = Gen.oneOf(Matin, ApresMidi, Soir)
  val genVisiteur: Gen[Visiteur] = {
    for {
      p <- genPeriode
    } yield Visiteur(p)
  }
  val genScolaire: Gen[Scolaire] = {
    for {
      p <- genPeriode
      i <- arbitrary[Int] suchThat (_ >= 0)
    } yield Scolaire(p, i)
  }
  val genStandard: Gen[Standard] = {
    for {
      p <- genPeriode
      i <- arbitrary[Int] suchThat (_ >= 0)
      b <- arbitrary[Boolean]
    } yield Standard(p, i, b)
  }
  val genTicket: Gen[Ticket] = Gen.oneOf(genVisiteur, genScolaire, genStandard)

}
