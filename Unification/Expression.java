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

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

interface Expression {
 Expression replace( Rewrite r );
}

class Variable implements Expression {
 String label;

 private Variable( String label ) {
  this.label = label;
 }

 public static Expression of( String x ) {
  return new Variable( x );
 }

 @Override
 public Expression replace( Rewrite r ) {
  if ( r.left.label.equals( label ) ) {
   return r.right;
  } else {
   return this;
  }
 }

 @Override
 public boolean equals( Object o ) {
  if ( o instanceof Variable v ) {
   return v.label.equals( this.label );
  } else {
   return false;
  }
 }

 @Override
 public String toString() {
  return label;
 }
}

class Literal implements Expression {
 String label;

 private Literal( String label ) {
  this.label = label;
 }

 public static Expression of( String s ) {
  return new Literal( s );
 }

 @Override
 public Expression replace( Rewrite r ) {
  return this;
 }

 @Override
 public String toString() {
  return label;
 }

 @Override
 public boolean equals( Object o ) {
  if ( o instanceof Literal v ) {
   return v.label.equals( this.label );
  } else {
   return false;
  }
 }
}

class Function implements Expression {
 Expression in;
 Expression out;

 private Function( Expression in, Expression out ) {
  this.in = in;
  this.out = out;
 }

 public static Expression of( Expression in, Expression out ) {
  return new Function( in, out );
 }

 @Override
 public Expression replace( Rewrite r ) {
  return new Function( in.replace( r ), out.replace( r ) );
 }

 @Override
 public String toString() {
  return in + "→" + out;
 }

 @Override
 public boolean equals( Object o ) {
  if ( o instanceof Function f ) {
   return f.in.equals( this.in ) && f.out.equals( this.out );
  } else {
   return false;
  }
 }
}

class Parser {

 public static Set< Equation > parse( String s ) {
  if ( s.contains( "," ) ) {
   return Arrays.stream( s.split( "," ) )
     .flatMap( c -> parse( c ).stream() ).collect( Collectors.toSet() );
  } else if( s.isEmpty() ){
   return Collections.emptySet();
  } else {
   String[] _s = s.split( "=" );
   Expression left = parseExpression( _s[ 0 ] );
   Expression right = parseExpression( _s[ 1 ] );
   return Stream.of( Equation.of( left, right ) ).collect( Collectors.toSet() );
  }
 }

 public static Expression parseExpression( String s ) {
  s = s.trim();
  if ( s.contains( "->" ) ) {
   return Function.of(
     parseExpression( s.substring( 0, s.indexOf( "->" ) ) ),
     parseExpression( s.substring( s.indexOf( "->" ) + 2 ) ) );
  }
  if ( s.matches( "L(.+)" ) ) {
   return Literal.of( s.substring( s.indexOf( "(" )+1, s.indexOf( ")" ) ) );
  }
  if ( s.matches( "V(.+)" ) ) {
   return Variable.of( s.substring( s.indexOf( "(" )+1, s.indexOf( ")" ) ) );
  }
  if ( s.matches( "." ) ) {
   return Variable.of( s );
  }
  throw new RuntimeException( "Unrecognised string '" + s + "'");
 }

}
