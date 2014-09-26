object Lab1 extends jsy.util.JsyApplication {
  import jsy.lab1.ast._
  import jsy.lab1.Parser
  
  /*
   * CSCI 3155: Lab 1
   * <Kevin Vo>
   * 
   * Partner: <Justin Tang>
   * Collaborators: <Paul, Brady>
   */

  /*
   * Fill in the appropriate portions above by replacing things delimited
   * by '<'... '>'.
   * 
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
  
  /*
   * Example with a Unit Test
   * 
   * A convenient, quick-and-dirty way to experiment, especially with small code
   * fragments, is to use the interactive Scala interpreter.
   * 
   * To run a selection in the interpreter in Eclipse, highlight the code of interest
   * and type Ctrl+Shift+X (on Windows) or Cmd+Shift+X (on Mac).
   * 
   * Highlight the next few lines below to try it out.  The assertion passes, so
   * it appears that nothing happens.  You can uncomment the "bad test specification"
   * and see that a failed assert throws an exception.
   * 
   * You can try calling 'plus' with some arguments, for example, plus(1,2).  You
   * should get a result something like 'res0: Int = 3'.
   * 
   * As an alternative, the testPlus2 function takes an argument that has the form
   * of a plus function, so we can try it with different implementations.  For example,
   * uncomment the "testPlus2(badplus)" line, and you will see an assertion failure.
   * 
   * Our convention is that these "test" functions are testing code that are not part
   * of the "production" code.
   * 
   * While writing such testing snippets are convenient, it is not ideal.  For example,
   * the 'testPlus1()' call is run whenever this object is loaded, so in practice,
   * it should probably be deleted for "release".  A more robust way to maintain
   * unit tests is in a separate file.  For us, we use the convention of writing
   * tests in a file called LabXSpec.scala (i.e., Lab1Spec.scala for Lab 1).
   */
  
  def plus(x: Int, y: Int): Int = x + y
  def testPlus1() {
    assert(plus(1,1) == 2)
    //assert(plus(1,1) == 3) // bad test specification
  }
  testPlus1()

  def badplus(x: Int, y: Int): Int = x - y
  def testPlus2(plus: (Int, Int) => Int) {
    assert(plus(1,1) == 2)
  }
  //testPlus2(badplus)

  /* Exercises */

//Absolute Value Function

  def abs(n: Double): Double = {
	if (n < 0) (-n)
	else n
}

//XOR Function

  def xor(a: Boolean, b: Boolean): Boolean = {
	if(a==b) false
	else true
}

//Repeat a string "n" times recursively

  def repeat(s: String, n: Int): String = {
	require(n >= 0)	
	if(n==0) ""
	else if (n ==1) s
	else s + repeat(s,(n-1))
}
  
//Square root step
  
  def sqrtStep(c: Double, xn: Double): Double = {
	(xn - (((xn*xn)-c)/(2*xn)))
}

//Squre root

  def sqrtN(c: Double, x0: Double, n: Int): Double = {
	require( c>0 && x0>0 && n>= 0)
	val x = sqrtStep(c,x0)
	if(n==0) x0
	else if (n<=1) x
	else sqrtN(c,x,(n-1))
}

  def sqrtErr(c: Double, x0: Double, epsilon: Double): Double = {
	require(epsilon>0 && c>0 && x0>0)	
	if (abs((x0*x0)-c)< epsilon) x0
	else sqrtErr(c,(sqrtStep(c,x0)),epsilon)
}

  def sqrt(c: Double): Double = {
    require(c >= 0)
    if (c == 0) 0 
    else sqrtErr(c, 1.0, 0.0001)
  }
  
  /* Search Tree */
  
  sealed abstract class SearchTree
  case object Empty extends SearchTree
  case class Node(l: SearchTree, d: Int, r: SearchTree) extends SearchTree
  
// Checks to see if the binarytree is valid
// Uses a recursive helper function to check when it is Empty or a Node
// If it is empty, we have searched to the end of that branch without an issue
// If it is a node, check that the value of the node is less than our max value and greater than or equal to our min value
// If it is, call check again on the left with the same min, and the new max being d. Then do the vice versa on the right tree
// If it doesn't fulfill those expectations that make that node valid based it's parent node, return false.  
  
  def repOk(t: SearchTree): Boolean = {
    def check(t: SearchTree, min: Int, max: Int): Boolean = t match {
      case Empty => true
      case Node(l, d, r) => {
        if (d >= min && d <= max) {
         (check (l, min, (d-1)) && check (r, d, max))
        } 
	else false
       }
    }
    check(t, Int.MinValue, Int.MaxValue)
  }
  
  def insert(t: SearchTree, n: Int): SearchTree = t match {
	case Empty => Node(Empty, n, Empty)
	case Node(l,d,r) => {
	  if(n<d) Node(insert(l,n),d,r)  
	  else Node(l,d,insert(r,n))
	// check the left tree, l, with max value d since all values in r must be less than d.
	// check the right tree, r, with min value d since all values in r must be greater than or equal to d
	
	}
  }
	

// Can't delete the min from an empty tree
// If the left of the node is empty, return the remaining right tree cause it's all the greater values
// If the left isn't empty, there is a lesser value in the left subtree so call deleteMin on the left and store the returned tree and deleted value
// This allows us to recreate the initial tree with the left subtree having the min value deleted.

  
  def deleteMin(t: SearchTree): (SearchTree, Int) = {
    require(t != Empty)
    (t: @unchecked) match {
      case Node(Empty, d, r) => (r, d)
      case Node(l, d, r) =>
        val (l1, m) = deleteMin(l)
        (Node(l1,d,r),m)
    }
  }
 
// Four different cases
	// Empty Tree: Return t
	// Not Empty:
		// If the value to be deleted is less than the node, create a new tree with calling delete on the left subtree (as the left subtree since it returns a tree)
		// If the value to be deleted is greater than the node, create a new tree with calling delete on the right subtree (as the right subtree since it returns a tree)
	// Note: This ignores if they are equal
	// If they are equal
		// One or no child subtree:
			// Make only subtree the new tree (if both empty, return will be Empty)
			// Else (two subtrees) 
 
 
  def delete(t: SearchTree, n: Int): SearchTree = t match {
      case Empty => Empty
      case Node(l, d, r) =>
	if(n > d) Node(l, d, delete(r, n)) 
	else if(n < d) Node(delete(l, n), d, r)
	else {
		if(l == Empty) r
		else if(r == Empty) l
	        else {
		 val tree = deleteMin(r)
	         Node(l, tree._2, tree._1) 
	      	}
	}
  }
  /* JavaScripty */
  
  def eval(e: Expr): Double = e match {
    case N(n) => n
    case Unary(uop,e1) => uop match {
	case Neg => -eval(e1)
    }
    case Binary(bop,e1,e2) => bop match {
	case Plus => eval(e1) + eval(e2)
	case Minus => eval(e1) - eval(e2)
	case Times => eval(e1) * eval(e2)
	case Div => eval(e1)/eval(e2)
    }
  }
  
 // Interface to run your interpreter from a string.  This is convenient
 // for unit testing.
 def eval(s: String): Double = eval(Parser.parse(s))



 /* Interface to run your interpreter from the command-line.  You can ignore the code below. */ 
  
 def processFile(file: java.io.File) {
    if (debug) { println("Parsing ...") }
    
    val expr = Parser.parseFile(file)
    
    if (debug) {
      println("\nExpression AST:\n  " + expr)
      println("------------------------------------------------------------")
    }
    
    if (debug) { println("Evaluating ...") }
    
    val v = eval(expr)
    
    println(v)
  }

}
