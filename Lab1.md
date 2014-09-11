Kevin Vo
CSCI3155
Lab 1
========

1.
  a) The use of pi at line 4 is bound at line 3 because it is in the scope of def circumference. 
     The pi at line 7 is bound at line 1 because it is outside def circumference, which leaves pi to only be bound at line 1.
  b) The use of x at line 3 is bound at line 2 because that is the def function. 
     The use of x at line 6 is bound at line 5 because it is in its scope and whatever x is in line 5 will be passed into x at line 6. 
     The use of x at line 10 would also be bounded by line 5 for the same reason. 
     X at line 13 would be bound at line 1 because it is completely out of the function itself.

2. Yes, the body of g is well typed.
   the Return type of g: (x: Int) ((Int, Int), Int) 

    b: (Int, Int)
    1: (Int)
    a + 2: (Int)
