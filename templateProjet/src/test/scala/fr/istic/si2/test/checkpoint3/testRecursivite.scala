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

class ARecursiviteDefinitionTest {

  val exos = parseFile("recursivite.scala", "fr.istic.si2.checkpoint3")

  /**
   * listeLongueurs est bien définie
   */
  @Test(timeout = 1000)
  def listeLongueursDefined {
    isDefined(listeLongueurs(Nil))
  }

  /**
   * listeLongueurs est récursive directe
   */
  @Test(timeout = 3000)
  def listeLongueursOKrec {
    val isRec = checkFunctionP("listeLongueurs", exos, isRecursive(_))
    if (!isRec) {
      fail("La fonction listeLongueurs doit être récursive directe.")
    }
  }

  /**
   * listeParite est bien définie
   */
  @Test(timeout = 1000)
  def listePariteDefined {
    isDefined(listeParites(Nil))
  }

  /**
   * listeParite est récursive directe
   */
  @Test(timeout = 3000)
  def listePariteOKrec {
    val isRec = checkFunctionP("listeParites", exos, isRecursive(_))
    if (!isRec) {
      fail("La fonction listeParites doit être récursive directe.")
    }
  }

  /**
   * incremente est bien définie
   */
  @Test(timeout = 1000)
  def incrementeDefined {
    isDefined(incremente(Nil))
  }

  /**
   * incremente est récursive directe
   */
  @Test(timeout = 3000)
  def incrementeOKrec {
    val isRec = checkFunctionP("incremente", exos, isRecursive(_))
    if (!isRec) {
      fail("La fonction incremente doit être récursive directe.")
    }
  }

  /**
   * flip est bien définie
   */
  @Test(timeout = 1000)
  def flipDefined {
    isDefined(flip(Nil))
  }

  /**
   * flip est récursive directe
   */
  @Test(timeout = 3000)
  def flipOKrec {
    val isRec = checkFunctionP("flip", exos, isRecursive(_))
    if (!isRec) {
      fail("La fonction flip doit être récursive directe.")
    }
  }

  /**
   * double est bien définie
   */
  @Test(timeout = 1000)
  def doubleDefined {
    isDefined(double(Nil))
  }

  /**
   * double est récursive directe
   */
  @Test(timeout = 3000)
  def doubleOKrec {
    val isRec = checkFunctionP("double", exos, isRecursive(_))
    if (!isRec) {
      fail("La fonction double doit être récursive directe.")
    }
  }

  /**
   * opposes est bien définie
   */
  @Test(timeout = 1000)
  def opposesDefined {
    isDefined(opposes(Nil))
  }

  /**
   * opposes est récursive directe
   */
  @Test(timeout = 3000)
  def opposesOKrec {
    val isRec = checkFunctionP("opposes", exos, isRecursive(_))
    if (!isRec) {
      fail("La fonction opposes doit être récursive directe.")
    }
  }

  /**
   * elementsPairs est bien définie
   */
  @Test(timeout = 1000)
  def elementsPairsDefined {
    isDefined(elementsPairs(Nil))
  }

  /**
   * elementsPairs est récursive directe
   */
  @Test(timeout = 3000)
  def elementsPairsOKrec {
    val isRec = checkFunctionP("elementsPairs", exos, isRecursive(_))
    if (!isRec) {
      fail("La fonction elementsPairs doit être récursive directe.")
    }
  }

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
   * nth est bien définie
   */
  @Test(timeout = 1000)
  def nthDefined {
    isDefined(nth(Nil, 0))
  }

  /**
   * nth est récursive directe
   */
  @Test(timeout = 3000)
  def nthOKrec {
    val isRec = checkFunctionP("nth", exos, isRecursive(_))
    if (!isRec) {
      fail("La fonction nth doit être récursive directe.")
    }
  }

  /**
   * compte est bien définie
   */
  @Test(timeout = 1000)
  def compteDefined {
    isDefined(compte(Nil, 0))
  }

  /**
   * compte est récursive directe
   */
  @Test(timeout = 3000)
  def compteOKrec {
    val isRec = checkFunctionP("compte", exos, isRecursive(_))
    if (!isRec) {
      fail("La fonction compte doit être récursive directe.")
    }
  }

  /**
   * nbZeros est bien définie
   */
  @Test(timeout = 1000)
  def nbZerosDefined {
    isDefined(nbZeros(Nil, 4))
  }

  /**
   * nbZeros est récursive directe
   */
  @Test(timeout = 3000)
  def nbZerosOKrec {
    val isRec = checkFunctionP("nbZeros", exos, isRecursive(_))
    if (!isRec) {
      fail("La fonction nbZeros doit être récursive directe.")
    }
  }

  /**
   * sommeEgalA est bien définie
   */
  @Test(timeout = 1000)
  def sommeEgalADefined {
    isDefined(sommeEgalA(Nil, 4))
  }

  /**
   * sommeEgalA est récursive directe
   */
  @Test(timeout = 3000)
  def sommeEgalAOKrec {
    val isRec = checkFunctionP("sommeEgalA", exos, isRecursive(_))
    if (!isRec) {
      fail("La fonction sommeEgalA doit être récursive directe.")
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

  /**
   * renverse est bien définie
   */
  @Test(timeout = 1000)
  def renverseDefined {
    isDefined(renverse(Nil))
  }

  /**
   * renverse est récursive directe
   */
  @Test(timeout = 3000)
  def renverseOKrec {
    val isRec = checkFunctionP("renverse", exos, isRecursive(_))
    if (!isRec) {
      fail("La fonction renverse doit être récursive directe.")
    }
  }

}

class RecursiviteCorrectionTest {

  /**
   * listeLongueurs ne provoque pas d'erreur à l'execution
   */
  @Test(timeout = 2000)
  def listeLongueursNoRTE {
    for (_ <- 1 to 10) {
      noRTE(listeLongueurs(Seq.fill(Random.nextInt(100))(Random.nextString(Random.nextInt(10))).toList))
    }
  }

  /**
   * listeLongueurs est correcte - cas de base
   */
  @Test(timeout = 3000)
  def listeLongueursOKbase {
    assertEquals(o1(List[String]()), listeLongueurs(Nil))
  }

  /**
   * listeLongueurs est correcte - cas récursifs
   */
  @Test(timeout = 3000)
  def listeLongueursOKother {
    for (_ <- 1 to 100) {
      val s = Random.nextString(Random.nextInt(10))
      val l = s :: Seq.fill(Random.nextInt(100))(Random.nextString(Random.nextInt(10))).toList
      assertEquals(o1(l), listeLongueurs(l))
    }
  }

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
   * incremente ne provoque pas d'erreur à l'execution
   */
  @Test(timeout = 2000)
  def incrementeNoRTE {
    for (_ <- 1 to 10) {
      noRTE(incremente(Seq.fill(Random.nextInt(100))(Random.nextInt(2000)).toList))
    }
  }

  /**
   * incremente est correcte - cas de base
   */
  @Test(timeout = 3000)
  def incrementeOKbase {
    assertEquals(o3(List[Int]()), incremente(Nil))
  }

  /**
   * incremente est correcte - cas récursifs
   */
  @Test(timeout = 3000)
  def incrementeOKother {
    for (_ <- 1 to 100) {
      val s = Random.nextInt(10)
      val l = s :: Seq.fill(Random.nextInt(100))(Random.nextInt(2000)).toList
      assertEquals(o3(l), incremente(l))
    }
  }

  /**
   * flip ne provoque pas d'erreur à l'execution
   */
  @Test(timeout = 2000)
  def flipNoRTE {
    for (_ <- 1 to 10) {
      noRTE(flip(Seq.fill(Random.nextInt(100))(Random.nextBoolean).toList))
    }
  }

  /**
   * flip est correcte - cas de base
   */
  @Test(timeout = 3000)
  def flipOKbase {
    assertEquals(o4(List[Boolean]()), flip(Nil))
  }

  /**
   * flip est correcte - cas récursifs
   */
  @Test(timeout = 3000)
  def flipOKother {
    for (_ <- 1 to 100) {
      val s = Random.nextBoolean
      val l = s :: Seq.fill(Random.nextInt(100))(Random.nextBoolean).toList
      assertEquals(o4(l), flip(l))
    }
  }

  /**
   * double ne provoque pas d'erreur à l'execution
   */
  @Test(timeout = 2000)
  def doubleNoRTE {
    for (_ <- 1 to 10) {
      noRTE(double(Seq.fill(Random.nextInt(100))(Random.nextInt(200)).toList))
    }
  }

  /**
   * double est correcte - cas de base
   */
  @Test(timeout = 3000)
  def doubleOKbase {
    assertEquals(o5(List[Int]()), double(Nil))
  }

  /**
   * double est correcte - cas récursifs
   */
  @Test(timeout = 3000)
  def doubleOKother {
    for (_ <- 1 to 100) {
      val s = Random.nextInt(2000)
      val l = s :: Seq.fill(Random.nextInt(100))(Random.nextInt(2000)).toList
      assertEquals(o5(l), double(l))
    }
  }

  /**
   * opposes ne provoque pas d'erreur à l'execution
   */
  @Test(timeout = 2000)
  def opposesNoRTE {
    for (_ <- 1 to 10) {
      noRTE(opposes(Seq.fill(Random.nextInt(100))(Random.nextInt(200)).toList))
    }
  }

  /**
   * opposes est correcte - cas de base
   */
  @Test(timeout = 3000)
  def opposesOKbase {
    assertEquals(o6(List[Int]()), opposes(Nil))
  }

  /**
   * opposes est correcte - cas récursifs
   */
  @Test(timeout = 3000)
  def opposesOKother {
    for (_ <- 1 to 100) {
      val s = Random.nextInt(2000)
      val l = s :: Seq.fill(Random.nextInt(50))(Random.nextInt(2000)).toList
      assertEquals(o6(l), opposes(l))
    }
  }

  /**
   * elementsPairs ne provoque pas d'erreur à l'execution
   */
  @Test(timeout = 2000)
  def elementsPairsNoRTE {
    for (_ <- 1 to 10) {
      noRTE(elementsPairs(Seq.fill(Random.nextInt(100))(Random.nextInt(200)).toList))
    }
  }

  /**
   * elementsPairs est correcte - cas de base
   */
  @Test(timeout = 3000)
  def elementsPairsOKbase {
    assertEquals(o7(List[Int]()), elementsPairs(Nil))
  }

  /**
   * elementsPairs est correcte - cas récursifs
   */
  @Test(timeout = 3000)
  def elementsPairsOKother {
    for (_ <- 1 to 100) {
      val s = Random.nextInt(2000)
      val l = s :: Seq.fill(Random.nextInt(100))(Random.nextInt(2000)).toList
      assertEquals(o7(l), elementsPairs(l))
    }
  }

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
   * nth ne provoque pas d'erreur à l'execution
   */
  @Test(timeout = 2000)
  def nthNoRTE {
    for (_ <- 1 to 10) {
      noRTE(nth(Seq.fill(Random.nextInt(100))(Random.nextString(Random.nextInt(8))).toList, Random.nextInt(30)))
      noRTE(nth(Seq.fill(Random.nextInt(80))(Random.nextString(Random.nextInt(8))).toList, 80 + Random.nextInt(30)))
    }
  }

  /**
   * nth est correcte - cas de base
   */
  @Test(timeout = 3000)
  def nthOKbase {
    for (_ <- 1 to 1000) {
      val n = Random.nextInt(100)
      assertEquals(None, nth(Nil, n))
    }
  }

  /**
   * nth est correcte - cas récursifs
   */
  @Test(timeout = 3000)
  def nthOKother {
    for (_ <- 1 to 1000) {
      val n = 1 + Random.nextInt(100)
      val s = Random.nextString(5)
      val l = s :: Seq.fill(n)(Random.nextString(Random.nextInt(8))).toList
      val m = Random.nextInt(l.size)
      assertEquals(Some(l(m)), nth(l, m))
      assertEquals(None, nth(l, math.max(l.size, Random.nextInt(100))))
    }
  }

  /**
   * compte ne provoque pas d'erreur à l'execution
   */
  @Test(timeout = 2000)
  def compteNoRTE {
    for (_ <- 1 to 10) {
      noRTE(compte(Seq.fill(Random.nextInt(100))(Random.nextInt(200)).toList, Random.nextInt(30)))
    }
  }

  /**
   * compte est correcte - cas de base
   */
  @Test(timeout = 3000)
  def compteOKbase {
    for (_ <- 1 to 1000) {
      val n = Random.nextInt(100)
      assertEquals(o9(n)(List[Int]()), compte(Nil, n))
    }
  }

  /**
   * compte est correcte - cas récursifs
   */
  @Test(timeout = 3000)
  def compteOKother {
    for (_ <- 1 to 100) {
      val n = Random.nextInt(10)
      val s = Random.nextInt(50)
      val l = s :: Seq.fill(Random.nextInt(100))(Random.nextInt(10)).toList
      assertEquals(o9(n)(l), compte(l, n))
    }
  }

  /**
   * nbZeros ne provoque pas d'erreur à l'execution
   */
  @Test(timeout = 2000)
  def nbZerosNoRTE {
    for (_ <- 1 to 10) {
      noRTE(nbZeros(Seq.fill(Random.nextInt(100))(Random.nextInt(200)).toList, Random.nextInt(30)))
      noRTE(nbZeros(Seq.fill(Random.nextInt(100))(0).toList, Random.nextInt(2)))
    }
  }

  /**
   * nbZeros est correcte - cas de base
   */
  @Test(timeout = 3000)
  def nbZerosOKbase {
    for (_ <- 1 to 1000) {
      assertTrue(nbZeros(Nil, o9(0)(List[Int]())))
      assertFalse(nbZeros(Nil, 1 + Random.nextInt(40)))
    }
  }

  /**
   * nbZeros est correcte - cas récursifs
   */
  @Test(timeout = 3000)
  def nbZerosOKother {
    for (_ <- 1 to 100) {
      val s = Random.nextInt(50)
      val l = s :: Seq.fill(Random.nextInt(100))(Random.nextInt(10)).toList
      assertTrue(nbZeros(l, o9(0)(l)))
    }

    var i = 0
    while (i < 100) {
      val s = Random.nextInt(50)
      val l = s :: Seq.fill(Random.nextInt(100))(Random.nextInt(10)).toList
      val n = Random.nextInt(l.length)
      if (n != o9(0)(l)) {
        assertFalse(nbZeros(l, n))
        i += 1
      }
    }
  }

  /**
   * sommeEgalA ne provoque pas d'erreur à l'execution
   */
  @Test(timeout = 2000)
  def sommeEgalANoRTE {
    for (_ <- 1 to 10) {
      noRTE(sommeEgalA(Seq.fill(Random.nextInt(100))(Random.nextInt(200)).toList, Random.nextInt(30)))
      noRTE(sommeEgalA(Seq.fill(Random.nextInt(100))(0).toList, Random.nextInt(100)))
    }
  }

  /**
   * sommeEgalA est correcte - cas de base
   */
  @Test(timeout = 3000)
  def sommeEgalAOKbase {
    for (_ <- 1 to 1000) {
      assertTrue(sommeEgalA(Nil, 0))
      assertFalse(sommeEgalA(Nil, 1 + math.abs(Random.nextInt(50))))
    }
  }

  /**
   * sommeEgalA est correcte - cas récursifs
   */
  @Test(timeout = 3000)
  def sommeEgalAOKother {
    for (_ <- 1 to 100) {
      val s = Random.nextInt(50)
      val l = s :: Seq.fill(Random.nextInt(100))(Random.nextInt(10)).toList
      assertTrue(sommeEgalA(l, o10(l)))

    }

    var i = 0
    while (i < 100) {
      val s = Random.nextInt(50)
      val l = s :: Seq.fill(Random.nextInt(100))(Random.nextInt(10)).toList
      val n = Random.nextInt(l.length * 2)
      if (n != o10(l)) {
        assertFalse(sommeEgalA(l, n))
        i += 1
      }
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

  /**
   * renverse ne provoque pas d'erreur à l'execution
   */
  @Test(timeout = 2000)
  def renverseNoRTE {
    for (_ <- 1 to 10) {
      noRTE(renverse(Seq.fill(Random.nextInt(100))(Random.nextString(Random.nextInt(8))).toList))
    }
  }

  /**
   * renverse est correcte - cas de base
   */
  @Test(timeout = 3000)
  def renverseOKbase {
    assertTrue(renverse(Nil).length == 0)
  }

  /**
   * renverse est correcte - cas récursifs
   */
  @Test(timeout = 3000)
  def renverseOKother {
    for (_ <- 1 to 100) {
      val s = Random.nextString(Random.nextInt(10))
      val l = s :: Seq.fill(Random.nextInt(100))(Random.nextString(Random.nextInt(8))).toList
      assertEquals(o12(l),renverse(l))
    }
  }
}
