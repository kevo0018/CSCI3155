object Lab2 extends jsy.util.JsyApplication {
  import jsy.lab2.Parser
  import jsy.lab2.ast._
  
  /*
   * Kevin Vo & Justin Tang
   * Collaborators: Brady Auen, Paul Kirilchuk
   * CSCI 3155: Lab 2
   */

  /*
   * Replace the 'throw new UnsupportedOperationException' expression with
   * your code in each function.
   * 
   * Do not make other modifications to this template, such as
   * - adding "extends App" or "extends Application" to your Lab object,
   * - adding a "main" method, and
   * - leaving any failing asserts.
   * 
   * Your lab will not be graded if it does not compile.
   * 
   * This template compiles without error. Before you submit comment out any
   * code that does not compile or causes a failing assert.  Simply put in a
   * 'throws new UnsupportedOperationException' as needed to get something
   * that compiles without error.
   */
  
  /* We represent a variable environment is as a map from a string of the
   * variable name to the value to which it is bound.
   * 
   * You may use the following provided helper functions to manipulate
   * environments, which are just thin wrappers around the Map type
   * in the Scala standard library.  You can use the Scala standard
   * library directly, but these are the only interfaces that you
   * need.
   */
  
  
  type Env = Map[String, Expr]
  val emp: Env = Map()
  def get(env: Env, x: String): Expr = env(x)
  def extend(env: Env, x: String, v: Expr): Env = {
    require(isValue(v))
    env + (x -> v)
  }
  
  /* Some useful Scala methods for working with Scala values include:
   * - Double.NaN
   * - s.toDouble (for s: String)
   * - n.isNaN (for n: Double)
   * - n.isWhole (for n: Double)
   * - s (for n: Double)
   * - s format n (for s: String [a format string like for printf], n: Double)
   */




  def AreAllNumbers(x: String) = x forall Character.isDigit
//This is from http://stackoverflow.com/questions/9938098/
  
  
  def toNumber(v: Expr): Double = {
    require(isValue(v))
    (v: @unchecked) match 
    {
      case N(n) => n                              					    //we have a number
      case B(b) => if (b) 1 else 0				   					    //boolean case
      case S(s) => if( AreAllNumbers(s)) s.toDouble else Double.NaN     //string case 
      case Undefined => return Double.NaN
      case _ => throw new UnsupportedOperationException
		}
	}
  
 def toBoolean(v: Expr): Boolean = 
 {
    require(isValue(v))
    (v: @unchecked) match 
    {
      case B(b) => b							 //Boolean case returns boolean
      case N(n) => if(n==0) false else true      //number case
      case Undefined => false
    }
  }
  
  def toStr(v: Expr): String = 
  {
    require(isValue(v))
    (v: @unchecked) match 
    {
      case S(s) => s        					//string to string			
      case N(n) => n.toString					//all numbers turned into a string 
      case B(b) => if (b) "true" else "false"   //if true then "true"
      case Undefined => "undefined"
      case _ => throw new UnsupportedOperationException
    }
  }
  
  def eval(env: Env, e: Expr): Expr = 
  {
    /* Some helper functions for convenience. */
    def eToVal(e: Expr): Expr = eval(env, e)
    def eToNumber(e: Expr): Double = toNumber(eval(env, e))
    def eToBoolean(e: Expr): Boolean = toBoolean(eval(env, e))
    
    
    e match 
    {
      /* Base Cases */
      
      case _ if (isValue(e)) => e
      case Var(x) => get(env,x)
      case ConstDecl(x, e1, e2) => eval(extend(env, x, eToVal( e1)), e2)
      case Unary(Neg, e1) => N(- eToNumber(e1))                                 //case where the expression is turned into a # then multiplied by -1
      case Unary(Not, e1) => B(! eToBoolean(e1))							    //case where the expression is turned into a boolean then banged
	  case Binary(Plus, e1, e2) => N(toNumber(eval(env,e1)) + toNumber(eval(env,e2)))         //the rest of the case being worked on
	  case Binary(Minus, e1, e2) => N(toNumber(eval(env,e1)) - toNumber(eval(env,e2)))
	  case Binary(Times, e1, e2) => N(toNumber(eval(env,e1)) * toNumber(eval(env,e2)))
	  case Binary(Div, e1, e2) => N(toNumber(eval(env,e1)) / toNumber(eval(env,e2)))
	  case Binary(Eq, e1, e2) => B(toNumber(eval(env,e1)) == toNumber(eval(env,e2)))
	  case Binary(Ne, e1, e2) => B(toNumber(eval(env,e1)) != toNumber(eval(env,e2)))
	  case Binary(Lt, e1, e2) => B(toNumber(eval(env,e1)) < toNumber(eval(env,e2)))
	  case Binary(Le, e1, e2) => B(toNumber(eval(env,e1)) <= toNumber(eval(env,e2)))
	  case Binary(Gt, e1, e2) => B(toNumber(eval(env,e1)) > toNumber(eval(env,e2)))
	  case Binary(Ge, e1, e2) => B(toNumber(eval(env,e1)) >= toNumber(eval(env,e2)))
	  case Binary(And, e1, e2) => val (val1, val2) = (toBoolean(e1), toBoolean(e2))
		(val1, val2) match
		 {
			case (true, true) => eToVal(e1)
			case (false, true) => eToVal(e1)
			case (true, false) => eToVal(e2)
			case (false, false) => eToVal(e1)
		 }
	  case Binary(Or, e1, e2) => val (val1, val2) = (eToBoolean(e1), eToBoolean (e2))
		(val1, val2) match 
		{	
			case (true, _) => eToVal(e1)
			case (false, _) => eToVal(e2)
		}
	  case Binary(Seq, e1, e2) => eval(env, e1); eval(env, e2)
	  
	  
      
      
      /* Inductive Cases */
      case Print(e1) => println(pretty(eToVal(e1))); Undefined
      case If(e1, e2, e3) => if (eToBoolean(e1)) eToVal(e2) else eToVal(e3)      //Depending on what e1 is, we'll return e2 or e3 if its true or false

      case _ => throw new UnsupportedOperationException
    }
  }
    
  // Interface to run your interpreter starting from an empty environment.
  def eval(e: Expr): Expr = eval(emp, e)

  // Interface to run your interpreter from a string.  This is convenient
  // for unit testing.
  def eval(s: String): Expr = eval(Parser.parse(s))

 /* Interface to run your interpreter from the command-line.  You can ignore what's below. */ 
 def processFile(file: java.io.File) {
    if (debug) { println("Parsing ...") }
    
    val expr = Parser.parseFile(file)
    
    if (debug) {
      println("\nExpression AST:\n  " + expr)
      println("------------------------------------------------------------")
    }
    
    if (debug) { println("Evaluating ...") }
    
    val v = eval(expr)
    
    println(pretty(v))
  }

}
