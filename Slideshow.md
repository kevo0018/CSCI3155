# Iterators

Presented by:
Brady Auen, Paul Kirilchuk, Kevin Vo

# Why Propose

This proposal proposes an iteration interface that objects can
offer to control the behavior of 'for' loops.

# Reasons for Propose:
1. __Gives an extensible iterator interface.__ 
2. __Performance improvements to list iteration.__ 
3. __Massive performance improvements to dictionary iteration.__ 
4. __Provides an interface for iterating without pretending to offer random access to elements.__ 
5. __Backward compatible with all existing user-defined classes and extension objects__
6. __Code iterating over non-sequence collections more brief and clear.__ 

# How it Works: 

* The iterator gives a 'get next value' operation that makes the next item in the sequence each time it is called. 
* The operation gives an exception when no more things are accessible. 

# How it Works: 

* The iterator gives a 'get next value' operation that makes
the next item in the sequence each time it is called. 
* The operation gives an exception when no more things are accessible. 
* There is only one required method, __next()__, which takes no arguments and returns the next value. 

# How it Works Cont.: 

* The iterator gives a 'get next value' operation that makes
the next item in the sequence each time it is called. 
* The operation gives an exception when no more things are accessible. 
* There is only one required method, __next()__, which takes no arguments and returns the next value. 
* When no values are left to be returned, calling next() should give the __StopIteration__ exception.

# How it Worked (Pseudo Iterator) Before Iterators:      

Before this update there wasn't a clear way for the user to iterate through the contents of objects in Python.  If the user wanted to create a "for item in object" sort of function, the method would look sort of like this:


# How it Worked (Pseudo Iterator) Before Iterators Code Example:

    def __getitem__(self, index):
    return <next item>

# How it Works Now (With Iterator):      

We have two built in functions that we can use to get iterators, the first is:
    iter(obj)

# How it Works Now (With Iterator):      

We have two built in functions that we can use to get iterators, the first is:
     iter(obj)

and the second is: 

     iter(C, sentinel)

# What Does the Iterator Feature Add?:

- A new exception is defined, __StopIteration__, which signals the end of an iteration.
- A new slot called __tp_iter__, that adds an iterator to the type object structure.

# Iterating in Dictionaries:

    dictionary = {'Age': 21, 'Weight': 200, 'Name': 'Brady'}

    for k in dict: 
    for key in dict.iterkeys():
    for value in dict.itervalues():
    
# Iterating in Dictionaries (example Code):

__CODE EXAMPLE: http://structure.usc.edu/python/whatsnew/node4.html __
	
    >>> m = {'Jan': 1, 'Feb': 2, 'Mar': 3, 'Apr': 4, 'May': 5, 'Jun': 6, 'Jul': 7, 'Aug': 8, 'Sep': 9, 'Oct': 10, 'Nov': 11, 'Dec': 12}
    >>> for key in m: print key, m[key]

# Iterating in Dictionaries (example Code):

CODE EXAMPLE:      http://structure.usc.edu/python/whatsnew/node4.html 
	
    >>> m = {'Jan': 1, 'Feb': 2, 'Mar': 3, 'Apr': 4, 'May': 5, 'Jun': 6, 'Jul': 7, 'Aug': 8, 'Sep': 9, 'Oct': 10, 'Nov': 11, 'Dec': 12}
    >>> for key in m: print key, m[key]

Mar 3
Feb 2
Aug 8
Sep 9
May 5
Jun 6
Jul 7
Jan 1
Apr 4
Nov 11
Dec 12
Oct 10

# Iterating in Files:

Files also provide an iterator, which calls the __readline( )__ method until there are no more lines in the file. This offers us with a good answer to the problem of iterate over the lines in a slow and nasty fashion. Ultimately, using an iterator is faster and more clear.
   

#Iterating in Files Example:

Files implement a tp_iter slot that is equivalent to
    iter(f.readline, "").  
    
#Iterating in Files Example:

Files implement a tp_iter slot that is equivalent to
    iter(f.readline, "").  

This means that we can write for line in file, which is equivalent to, but faster than
    while 1:
                 line = file.readline()
                 if not line:
                 break

# How This Applies to Concepts Learned in CSCI 3155

_"syntax that makes certain common tasks easier or less error prone in the language, perhaps describe the syntax in the context of allowed grammar productions.”_ -pdf

# Why the Community Passed this Proposal:

A clearer, faster and more user friendly method to traverse a list, dictionary or file.

