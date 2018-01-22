package fr.istic.si2.test.checkpoint1

import org.junit.Test
import org.junit.Assert._
import util.Random
import fr.istic.si2.testerApp._
import fr.istic.si2.moreAssertions._
import fr.istic.si2.checkpoint1.ExercicesCP1._
import fr.istic.si2.checkpoint1.ExercicesCP1

class AExosBasiquesDefinitionTest {

  val _ = new AppInit(ExercicesCP1)
  val rand = new Random()

  /**
   * La fonction max est bien définie
   */
  @Test(timeout = 1000)
  def maxDefined {
    isDefined(max(rand.nextInt(), rand.nextInt()))
  }

  /**
   * La fonction estMultiple est bien définie
   */
  @Test(timeout = 1000)
  def estMultipleDefined {
    isDefined(estMultiple(rand.nextInt(), rand.nextInt()))
  }

  /**
   * La fonction xor1 est bien définie
   */
  @Test(timeout = 1000)
  def xor1Defined {
    isDefined(xor1(rand.nextBoolean(), rand.nextBoolean()))
  }

  /**
   * La fonction xor2 est bien définie
   */
  @Test(timeout = 1000)
  def xor2Defined {
    isDefined(xor2(rand.nextBoolean(), rand.nextBoolean()))
  }
  /**
   * La fonction signe est bien définie
   */
  @Test(timeout = 1000)
  def signeDefined {
    isDefined(signe(rand.nextInt(), rand.nextString(4)))
  }

}

class ExercicesCP1CorrectionTest {

  val rand = new Random()
  val _ = new AppInit(ExercicesCP1)

  /**
   * La fonction max ne provoque pas d'erreur à l'execution
   */
  @Test(timeout = 1000)
  def maxNoRTE {
    for (_ <- 1 to 10000) noRTE(max(rand.nextInt(), rand.nextInt()))
  }

  /**
   * La fonction max est correcte.
   * Test aléatoire sur 10000 paires d'entiers.
   */
  @Test(timeout = 1000)
  def maxOKfirst {
    for (_ <- 1 to 10000) {
      val incr = fr.istic.si2.math.nextStrictPositiveInt()
      val i = rand.nextInt(Int.MaxValue - incr)
      assertEquals(i + incr, max(i + incr, i))
    }
  }

  /**
   * La fonction max est correcte.
   * Test aléatoire sur 10000 paires d'entiers.
   */
  @Test(timeout = 1000)
  def maxOKsecond {
    for (_ <- 1 to 10000) {
      val incr = fr.istic.si2.math.nextStrictPositiveInt()
      val i = rand.nextInt(Int.MaxValue - incr)
      assertEquals(i + incr, max(i, i + incr))
    }
  }

  /**
   * La fonction estMultiple ne provoque pas d'erreurs à l'exécution
   */
  @Test(timeout = 1000)
  def estMultipleNoRTE {
    for (_ <- 1 to 10000) noRTE(estMultiple(rand.nextInt(), rand.nextInt()))
  }

  /**
   * La fonction estMultiple est correcte.
   * 10000 tests aléatoires sur des couples d'entiers qui sont multiples l'un de l'autre
   */
  @Test(timeout = 1000)
  def estMultipleOKzero {
    assertTrue(estMultiple(0, 0))
  }

  /**
   * La fonction estMultiple est correcte.
   * 10000 tests aléatoires : un entier est multiple de lui même.
   */
  @Test(timeout = 1000)
  def estMultipleOKtrueId {
    for (_ <- 1 to 10000) {
      val n = rand.nextInt()
      assertTrue(estMultiple(n, n))
    }
  }

  /**
   * La fonction estMultiple est correcte.
   * 10000 tests aléatoires sur des couples d'entiers qui sont multiples l'un de l'autre
   */
  @Test(timeout = 1000)
  def estMultipleOKtrue {
    for (_ <- 1 to 10000) {
      val n = rand.nextInt(math.sqrt(Int.MaxValue).toInt - 1)
      val p = rand.nextInt(math.sqrt(Int.MaxValue).toInt - 1)
      assertTrue(estMultiple(n * p, p))
    }
  }

  /**
   * La fonction estMultiple est correcte.
   * 100000 tests aléatoires sur des couples d'entiers qui ne sont pas multiples l'un de l'autre
   */
  @Test(timeout = 1000)
  def estMultipleOKfalse {
    for (_ <- 1 to 100000) {
      val n = rand.nextInt()
      assertFalse(estMultiple(n, n - 1))
    }
  }

  /**
   * La fonction estMultiple est correcte.
   * 100000 tests aléatoires sur des couples d'entiers qui sont multiples l'un de l'autre
   */
  @Test(timeout = 1000)
  def estMultipleOKfalseGT {
    for (_ <- 1 to 100000) {
      val n = rand.nextInt(math.sqrt(Int.MaxValue).toInt - 1)
      val p = rand.nextInt(math.sqrt(Int.MaxValue).toInt - 1)
      if (math.abs(n) > 1 && p != 0) assertFalse(estMultiple(p, n * p))
    }
  }

  /**
   * La fonction xor1 ne provoque pas d'erreur à l'exécution
   */
  @Test(timeout = 1000)
  def xor1NoRTE {
    noRTE(xor1(true, true))
    noRTE(xor1(true, false))
    noRTE(xor1(false, true))
    noRTE(xor1(false, false))
  }

  /**
   * La fonction xor1 est correcte.
   * Test exhaustif.
   */
  @Test(timeout = 1000)
  def xor1OKres1 {
    assertFalse(xor1(true, true))

  }

  /**
   * La fonction xor1 est correcte.
   * Test exhaustif.
   */
  @Test(timeout = 1000)
  def xor1OKres2 {
    assertTrue(xor1(true, false))

  }
  /**
   * La fonction xor1 est correcte.
   * Test exhaustif.
   */
  @Test(timeout = 1000)
  def xor1OKres3 {
    assertTrue(xor1(false, true))

  }
  /**
   * La fonction xor1 est correcte.
   * Test exhaustif.
   */
  @Test(timeout = 1000)
  def xor1OKres4 {
    assertFalse(xor1(false, false))
  }

  /**
   * La fonction xor2 ne provoque pas d'erreur à l'exécution
   */
  @Test(timeout = 1000)
  def xor2NoRTE {
    noRTE(xor2(true, true))
    noRTE(xor2(true, false))
    noRTE(xor2(false, true))
    noRTE(xor2(false, false))
  }

  /**
   * La fonction xor2 est correcte.
   * Test exhaustif.
   */
  @Test(timeout = 1000)
  def xor2OKres1 {
    assertFalse(xor2(true, true))
  }

  /**
   * La fonction xor2 est correcte.
   * Test exhaustif.
   */
  @Test(timeout = 1000)
  def xor2OKres2 {
    assertTrue(xor2(true, false))
  }

  /**
   * La fonction xor2 est correcte.
   * Test exhaustif.
   */
  @Test(timeout = 1000)
  def xor2OKres3 {
    assertTrue(xor2(false, true))
  }

  /**
   * La fonction xor2 est correcte.
   * Test exhaustif.
   */
  @Test(timeout = 1000)
  def xor2OKres4 {
    assertFalse(xor2(false, false))
  }

  /**
   * La fonction signe ne provoque pas d'erreur à l'exécution
   * pour les paramètres attendus.
   */
  @Test(timeout = 1000)
  def signeNoRTEnul {
    for (_ <- 1 to 1000) noRTE(signe(rand.nextInt(), "nul"))
  }

  /**
   * La fonction signe ne provoque pas d'erreur à l'exécution
   * pour les paramètres attendus.
   */
  @Test(timeout = 1000)
  def signeNoRTEstrictPos {
    for (_ <- 1 to 1000) noRTE(signe(rand.nextInt(), "strictement positif"))
  }

  /**
   * La fonction signe ne provoque pas d'erreur à l'exécution
   * pour les paramètres attendus.
   */
  @Test(timeout = 1000)
  def signeNoRTEstrictNeg {
    for (_ <- 1 to 1000) noRTE(signe(rand.nextInt(), "strictement negatif"))
  }

  /**
   * La fonction signe est correcte pour les paramètres attendus.
   * Répétion du test de correction sur 1000 exécutions.
   */
  @Test(timeout = 1000)
  def signeOKnulrand {
    for (_ <- 1 to 1000) {
      val n = rand.nextInt()
      if (n == 0) assertTrue(signe(n, "nul"))
      else assertFalse(signe(n, "nul"))
    }
  }

  /**
   * La fonction signe est correcte pour les paramètres attendus.
   * Test de correction pour zéro.
   */
  @Test(timeout = 1000)
  def signeOKnul {
    assertTrue(signe(0, "nul"))
  }

  /**
   * La fonction signe est correcte pour les paramètres attendus.
   * Répétion du test de correction sur 1000 exécutions.
   */
  @Test(timeout = 1000)
  def signeOKstrictPos {
    for (_ <- 1 to 1000) {
      val n = rand.nextInt()
      if (n > 0) assertTrue(signe(n, "strictement positif"))
      else assertFalse(signe(n, "strictement positif"))
    }
  }

  /**
   * La fonction signe est correcte pour les paramètres attendus.
   * Répétion du test de correction sur 1000 exécutions.
   */
  @Test(timeout = 1000)
  def signeOKstrictNeg {
    for (_ <- 1 to 1000) {
      val n = rand.nextInt()
      if (n < 0) assertTrue(signe(n, "strictement negatif"))
      else assertFalse(signe(n, "strictement negatif"))
    }
  }

}
