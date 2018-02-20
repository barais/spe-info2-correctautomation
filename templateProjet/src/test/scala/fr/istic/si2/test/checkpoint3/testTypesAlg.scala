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

class TypesAlgebriquesDefinitionTest {

  val exos = parseFile("typesAlg.scala", "fr.istic.si2.checkpoint3")

  // XXX Ticket
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
  def ticketValideisDefined {
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
  def prixTicketisDefined {
    val b = exos.find(methodWithName("prixTicket"))
    b match {
      case None    => fail()
      case Some(m) => assertTrue(isNotQmark(m))
    }
  }

  // XXX Rendu CP
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
  def sonRenduisDefined {
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
  def noteisDefined {
    val b = exos.find(methodWithName("note"))
    b match {
      case None    => fail()
      case Some(m) => assertTrue(isNotQmark(m))
    }
  }

  // XXX Velos
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
  def estToutTerrainisDefined {
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
  def prixTotalisDefined {
    val b = exos.find(methodWithName("prixTotal"))
    b match {
      case None    => fail()
      case Some(m) => assertTrue(isNotQmark(m))
    }
  }

  // XXX Labyrinthe
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
  def aGagneisDefined {
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
  def niveauSupisDefined {
    val b = exos.find(methodWithName("niveauSup"))
    b match {
      case None    => fail()
      case Some(m) => assertTrue(isNotQmark(m))
    }
  }

  // XXX Posts
  /**
   * p1 est bien définie
   */
  @Test(timeout = 3000)
  def p1Defined {
    val b = exos.find(valWithName("p1"))
    b match {
      case None    => fail()
      case Some(v) => assertTrue(isNotQmark(v))
    }
  }

  /**
   * p2 est bien définie
   */
  @Test(timeout = 3000)
  def p2Defined {
    val b = exos.find(valWithName("p2"))
    b match {
      case None    => fail()
      case Some(v) => assertTrue(isNotQmark(v))
    }
  }

  /**
   * p3 est bien définie
   */
  @Test(timeout = 3000)
  def p3Defined {
    val b = exos.find(valWithName("p3"))
    b match {
      case None    => fail()
      case Some(v) => assertTrue(isNotQmark(v))
    }
  }

  /**
   * sujetDuPost est bien définie
   */
  @Test(timeout = 3000)
  def sujetDuPostisDefined {
    val b = exos.find(methodWithName("sujetDuPost"))
    b match {
      case None    => fail()
      case Some(m) => assertTrue(isNotQmark(m))
    }
  }

  /**
   * trending est bien définie
   */
  @Test(timeout = 3000)
  def trendingisDefined {
    val b = exos.find(methodWithName("trending"))
    b match {
      case None    => fail()
      case Some(m) => assertTrue(isNotQmark(m))
    }
  }

  // XXX Deplacements
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
  def referenceisDefined {
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
  def voyageProisDefined {
    val b = exos.find(methodWithName("voyagePro"))
    b match {
      case None    => fail()
      case Some(m) => assertTrue(isNotQmark(m))
    }
  }

}

class TypesAlgebriquesCorrectionTest {

  val _ = new AppInit(QuestionTypesAlgebriques)

  // XXX Tickets
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

  // XXX Rendus CP
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
        assertEquals(8, i.totalPoint)
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
  @Test(timeout = 1000)
  def noteOKnote {
    var i = 0
    while (i < 1000) {
      oneOf(genInvalide, genOK).sample match {
        case None => ()
        case Some(s: Invalide) => {
          i += 1
          assertEquals((s.etu, Some(0)), note(s))
        }
        case Some(std: OK) => {
          i += 1
          assertEquals(std.etu, note(std)._1)
          assertTrue(note(std)._2.get >= 0)
          assertTrue(note(std)._2.get <= std.totalPoint)
          assertEquals(std.totalPoint, math.min(std.totalPoint, note(std)._2.get + std.nbWarn + std.nbTestFail))
        }
        case _ => ()
      }
    }
  }

  // XXX Velos

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

  // XXX Labyrinthe

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

  // XXX Deplacements
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

  // XXX Posts
  /**
   * p1 est correcte.
   */
  @Test(timeout = 100)
  def p1OK {
    p1 match {
      case v: Publicite => assertEquals("http://www.giveMeYourMoney.com", v.url)
      case _            => fail()
    }
  }

  /**
   * p2 est correcte.
   */
  @Test(timeout = 100)
  def p2OK {
    p2 match {
      case v: Article =>
        assertEquals("Le pouvoir du Min Max", v.titre)
        assertEquals("John von Neumann".toLowerCase, v.auteur.toLowerCase())
        assertEquals(Economie, v.s)
      case _ => fail()
    }
  }

  /**
   * p3 est correcte.
   */
  @Test(timeout = 100)
  def p3OK {
    p3 match {
      case v: FakeNews =>
        assertEquals(Politique, v.s)
      case _ => fail()
    }
  }

  /**
   * sujetDuPost est correcte - cas defini
   */
  @Test(timeout = 3000)
  def sujetDuPostOKsome {
    var i = 0
    while (i < 1000) {
      oneOf(genFakeNews, genArticle).sample match {
        case Some(r: FakeNews) =>
          i += 1; assertEquals(r.s, sujetDuPost(r).get)
        case Some(r: Article) =>
          i += 1; assertEquals(r.s, sujetDuPost(r).get)
        case _ => ()
      }
    }
  }

  /**
   * sujetDuPost est correcte - cas non def
   */
  @Test(timeout = 1000)
  def sujetDuPostOKno {
    var i = 0
    while (i < 1000) {
      genPublicite.sample match {
        case None    => ()
        case Some(r) => i += 1; assertEquals(None, sujetDuPost(r))
      }
    }
  }

  /**
   * trending est correcte - cas negatif
   */
  @Test(timeout = 1000)
  def trendingOKnegatif {
    var i = 0
    while (i < 1000) {
      (arbitrary[Int] suchThat (_ > 0)).sample match {
        case None => ()
        case Some(n) =>
          (genPost suchThat (p => nbclics(p) < n)).sample match {
            case None    => ()
            case Some(t) => i += 1; assertFalse(trending(t, n))
          }
      }
    }
  }

  /**
   * trending est correcte - cas positif
   */
  @Test(timeout = 1000)
  def trendingOKpositif {
    var i = 0
    while (i < 1000) {
      (arbitrary[Int] suchThat (_ > 0)).sample match {
        case None => ()
        case Some(n) =>
          (genPost suchThat (p => nbclics(p) > n)).sample match {
            case None    => ()
            case Some(t) => i += 1; assertTrue(trending(t, n))
          }
      }
    }
  }

}

object UtilsGen { // XXX

  val genSujet = oneOf(Economie, Politique, Culture)
  val genPublicite = {
    for {
      u <- arbitrary[String]
      c <- arbitrary[Int] suchThat (_ >= 0)
    } yield Publicite(u, c)
  }
  val genArticle = {
    for {
      s <- genSujet
      a <- arbitrary[String]
      t <- arbitrary[String]
      c <- arbitrary[Int] suchThat (_ >= 0)
    } yield Article(s, a, t, c)
  }
  val genFakeNews = {
    for {
      s <- genSujet
      c <- arbitrary[Int] suchThat (_ >= 0)
    } yield FakeNews(s, c)
  }
  val genPost = oneOf(genArticle, genPublicite, genFakeNews)

  val nbclics: Post => Int = { p =>
    p match {
      case FakeNews(_, n)      => n
      case Article(_, _, _, n) => n
      case Publicite(_, n)     => n
    }
  }

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
  val genOK = {
    for {
      id <- arbitrary[String]
      tot <- arbitrary[Int] suchThat (_ >= 0) suchThat (_ <= 20)
      fl <- arbitrary[Int] suchThat (_ >= 0) suchThat (_ <= 20)
      wrn <- arbitrary[Int] suchThat (_ >= 0) suchThat (_ <= 20)
    } yield OK(id, tot, wrn, fl)
  }
  val genRendu = oneOf(genAbsent, genInvalide, genOK)

  val id: RenduCP => String = { r =>
    r match {
      case Absent(id)      => id
      case Invalide(id, _) => id
      case OK(id, _, _, _) => id
    }
  }

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
