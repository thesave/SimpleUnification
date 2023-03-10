/*
 * Copyright (C) 2023 by Saverio Giallorenzo <saverio.giallorenzo@gmail.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Library General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this program; if not, write to the
 * Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package Unification;

import java.util.Set;

public class UnificationTester {

 /**
  * This implementation supports an elementary language for type expressions.
  * Elements of type expressions are:
  * - single-letter symbols are variables, otherwise use V(MyVar) for longer names
  * - literal symbols, like Nat, Int, Bool, use the syntax L(Int), L(Nat), L(Bool)
  * - the function type is element -> element
  * Equations have the form TypeExpression = TypeExpression, separated by a comma
  */
 public static void main( String[] args ) {
  String example1 = "X -> X -> X = L(Int)-> Y, X -> X = Y";
  String example2 = "X = L(Nat), Y = X -> X";
  String example3 = "X -> Y = Y -> Z, Z = U -> W";
  String example4 = "L(Nat) -> L(Nat) = X -> Y";
  String example5 = "L(Nat) = L(Nat) -> Y"; // SHOULD FAIL
  String example6 = "";

  Set< Equation > C = Parser.parse( example1 );
  System.out.println( "Equations: " +  C );
  System.out.println( "Possible solution: " + Unification.unify( C ) );
 }

}
