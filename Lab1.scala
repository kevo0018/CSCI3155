//Absolute value function
def abs(n: Double) : Double = {
  if (n < 0) (-n) else (n)
}

//XOR function
def xor(a: Boolean, b: Boolean) Boolean = {
	if (a==b) true else false
}

//Repeat a string "n" number of times recursively
def repeats(s: String, n: Int): String = {
	if (n < 0) “Error invlaid int"
	else if (n ==1) s
	else s + repeats(s, (n-1))
}

//Implement a function sqrtStep
def sqrtStep(c: Double, xn: Double): Double = {
  (xn - (((xn*xn)-c)/(2*xn)))
}

//Implement a function sqrtN
def sqrtN(c: Double, x0: Double, n: Int): Double = {
  require( c>0 && x0>0 && n>0)
  val x = sqrtStep(c, x0)
  if (n <=1) x
  else sqrtN(c,x,(n-1))
}

//Now, implement a function sqrtErr (computes approximations xn until the approximation error is within ε (epsilon))
def sqrtErr(c: Double, x0: Double, epsilon: Double): Double ={
  if (abs((x0*x0)-c) < epsilon) x0
  else sqrtErr(c, (sqrtStep(c,x0)), epsilon)
}

