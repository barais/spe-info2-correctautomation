package fr.istic.si2.test.checkpoint3

import scala.reflect.runtime.universe._
import scala.reflect.runtime._
import scala.tools.reflect.ToolBox
import scala.util.matching.Regex

object Consignes {

  val cm = universe.runtimeMirror(getClass.getClassLoader)
  val toolbox = cm.mkToolBox()

  /**
   * @param mn method name
   * @param t an AST
   * @param P a predicate
   * @return method n is defined in t, and satisfies P
   */
  def checkFunctionP(mn: String, t: Tree, P: Tree => Boolean): Boolean = {
    val m: Option[Tree] = t.find(methodWithName(mn))
    m match {
      case None     => false
      case Some(mt) => P(mt)
    }
  }

  /**
   * @param t an AST
   * @param sol an AST
   * @return t is a DefDef tree, and contains sol
   */
  def containsSol(t: Tree, sol: Tree) : Boolean = {
    t match {
      case d: DefDef => d.rhs.find(t => t.equalsStructure(sol)) != None
      case _ => false
    }
  }
  
  /**
   * @param t an AST
   * @return t is a DefDef tree, and is a recursive definition
   */
  def isRecursive(t: Tree): Boolean = {
    t match {
      case d: DefDef => d.rhs.find(t => t.equalsStructure(Ident(d.name))) != None
      case _         => false
    }
  }

  /**
   * @param n method name
   * @param t an AST
   * @return t is the AST of method n
   */
  def methodWithName(n: String)(t: Tree): Boolean = {
    t match {
      case d: DefDef => d.name == TermName(n)
      case _         => false
    }
  }

  /**
   * @param n val name
   * @param t an AST
   * @return t is the AST of val n
   */
  def valWithName(n: String)(t: Tree): Boolean = {
    t match {
      case d: ValDef => d.name == TermName(n)
      case _         => false
    }
  }

  /**
   * @param t an AST of a ValDef or a DefDef
   * @return the val or def defined with t doesn't contain Predef.???
   */
  def isNotQmark(t: Tree): Boolean = {
    val isQmark = Ident(TermName("$qmark$qmark$qmark")).equalsStructure(_)
    t match {
      case vd: ValDef => !(vd.rhs.exists(isQmark))
      case dd: DefDef => !(dd.rhs.exists(isQmark))
      case _          => true
    }
  }

  /**
   * @param fname name of file to parse
   * @param pkgname name of package containing fname
   * @return the parse tree of file fname inside package pkgname
   */
  def parseFile(fname: String, pkgname: String): Tree = {
    val sep: String = System.getProperty("file.separator")
    val whereami = System.getProperty("user.dir")
    val pkgpath = pkgname.split('.').foldLeft("")((acc, d) => acc + sep + d)
    val path = whereami + sep + "src" + sep + "main" + sep + "scala" + sep + pkgpath
    val verbatim = scala.io.Source.fromFile(path + sep + fname).mkString
    val fixedpkgdecl = verbatim.replaceFirst("package " + pkgname, "package " + pkgname + " { ") + "\n }"
    toolbox.parse(fixedpkgdecl)
  }

}

