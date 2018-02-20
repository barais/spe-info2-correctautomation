package fr.istic.si2.test.checkpoint2

import org.junit.Test
import org.junit.Assert._
import util.Random
import fr.istic.si2.testerApp._
import fr.istic.si2.moreAssertions._
import fr.istic.si2.checkpoint2.QuestionTypesAlgebriques
import fr.istic.si2.checkpoint2.QuestionTypesAlgebriques._
import fr.istic.si2.checkpoint2._
import UtilsGen._

import Consignes._

class ARadioMusiqueDefinitionTest {

  val exos = parseFile("radioMusique.scala", "fr.istic.si2.checkpoint2")

  /**
   * bestOfBaker est bien définie
   */
  @Test(timeout = 3000)
  def bestOfBakerDefined {
    val b = exos.find(valWithName("bestOfBaker"))
    b match {
      case None    => fail()
      case Some(v) => assertTrue(isNotQmark(v))
    }
  }

  /**
   * theWall est bien définie
   */
  @Test(timeout = 3000)
  def theWallDefined {
    val b = exos.find(valWithName("theWall"))
    b match {
      case None    => fail()
      case Some(v) => assertTrue(isNotQmark(v))
    }
  }

  /**
   * Fonction aPropos est bien définie
   */
  @Test(timeout = 3000)
  def aProposDefined {
    val b = exos.find(methodWithName("aPropos"))
    b match {
      case None    => fail()
      case Some(m) => assertTrue(isNotQmark(m))
    }
  }

  /**
   * Fonction genre est bien définie
   */
  @Test(timeout = 3000)
  def genreDefined {
    val b = exos.find(methodWithName("genre"))
    b match {
      case None    => fail()
      case Some(m) => assertTrue(isNotQmark(m))
    }
  }

}

class RadioMusiqueCorrectionTest {

  val rand = new Random()
  val _ = new AppInit(QuestionTypesAlgebriques)

  /**
   * bestOfBaker pas d'erreur à l'execution
   */
  @Test(timeout = 100)
  def bestOfBakerNoRTE {
    noRTE(bestOfBaker)
  }

  /**
   * bestOfBaker est correcte.
   */
  @Test(timeout = 100)
  def bestOfBakerOK {
    bestOfBaker match {
      case BestOf(_, g) =>
        assertNotEquals(g, Classique)
        assertNotEquals(g, Rock)
      case _ => fail()
    }
  }

  /**
   * theWall pas d'erreur à l'execution
   */
  @Test(timeout = 100)
  def theWallNoRTE {
    noRTE(theWall)
  }

  /**
   * theWall est correcte.
   */
  @Test(timeout = 100)
  def theWallOK {
    theWall match {
      case Concert(_, g, c, a) =>
        assertNotEquals(g, Classique)
        assertNotEquals(g, Jazz)
        assertEquals(a, 2010)
        assertEquals(c.toLowerCase(), "chicago")
      case _ => fail()
    }
  }

  /**
   * La fonction aPropos ne provoque pas d'erreur à l'execution
   */
  @Test(timeout = 1000)
  def aProposNoRTE {
    for (_ <- 1 to 1000) {
      noRTE(aPropos(randomEmission(), rand.nextString(10)))
      val artiste = rand.nextString(10)
      noRTE(aPropos(randomEmission(artiste), artiste))
    }
  }

  /**
   * La fonction aPropos est correcte - cas négatif simple
   */
  @Test(timeout = 1000)
  def aProposOKnuit {
    for (_ <- 1 to 1000) {
      assertFalse(aPropos(Nuit, rand.nextString(10)))
    }
  }

  /**
   * La fonction aPropos est correcte - cas négatif
   */
  @Test(timeout = 1000)
  def aProposOKfalse {
    for (_ <- 1 to 1000) {
      val artiste = rand.nextString(10)
      val a_emission = rand.nextString(8)
      assertFalse(aPropos(randomEmission(artiste), a_emission))
    }
  }

  /**
   * La fonction aPropos est correcte - cas positif
   */
  @Test(timeout = 1000)
  def aProposOKtrue {
    for (_ <- 1 to 1000) {
      val artiste = rand.nextString(10)
      assertTrue(aPropos(randomEmission(artiste), artiste))
    }
  }

  /**
   * La fonction genre ne provoque pas d'erreur à l'execution
   */
  @Test(timeout = 1000)
  def genreNoRTE {
    for (_ <- 1 to 1000) noRTE(genre(randomEmission()))
  }

  /**
   * La fonction genre est correcte - cas aucun genre
   */
  @Test(timeout = 1000)
  def genreOKnone {
    genre(Nuit) match {
      case Some(_) => fail()
      case _       => ()
    }
  }

  /**
   * La fonction genre est correcte - cas genre défini
   */
  @Test(timeout = 1000)
  def genreOKresSome {
    for (_ <- 1 to 10000) {
      randomEmission() match {
        case Nuit        => ()
        case e: Emission => assertNotEquals(genre(e), None)
      }
    }
  }

  /**
   * La fonction genre est correcte - cas genre défini correct
   */
  @Test(timeout = 1000)
  def genreOKresOK {
    for (_ <- 1 to 10000) {
      randomEmission() match {
        case Nuit       => ()
        case e: Concert => assertEquals(genre(e).get, e.g)
        case e: BestOf  => assertEquals(genre(e).get, e.g)
      }
    }
  }

}

object UtilsGen {

  val rand = new Random()

  /**
   * @return a random Genre
   */
  def randomGenre(): Genre = {
    rand.nextInt(3) match {
      case 0 => Rock
      case 1 => Classique
      case 2 => Jazz
    }
  }

  /**
   * @param art an artist
   * @return a random Emission whose artist is art
   */
  def randomEmission(art: String): Emission = {
    rand.nextInt(2) match {
      case 0 => Concert(art, randomGenre(), rand.nextString(4), rand.nextInt(2018))
      case 1 => BestOf(art, randomGenre())
    }
  }

  /**
   * @return a random Emission
   */
  def randomEmission(): Emission = {
    rand.nextInt(3) match {
      case 0 => Nuit
      case 1 => Concert(rand.nextString(10), randomGenre(), rand.nextString(4), rand.nextInt(2018))
      case 2 => BestOf(rand.nextString(10), randomGenre())
    }
  }

}
