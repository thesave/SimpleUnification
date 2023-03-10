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

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Unification {

  public static List< Rewrite > unify( Set< Equation > C ) {
    if ( C.isEmpty() ) {
//            System.out.println( "0. Done" );
      return Collections.emptyList();
    } else {
      Equation e = C.stream().findAny().get();
//            System.out.println( "Taking " + e );
      C = C.stream().filter( _e -> !_e.equals( e ) ).collect( Collectors.toSet() );
//            System.out.println( "Keeping " + C );
      if ( e.left.equals( e.right ) ) {
//                System.out.println( "1. Removing " + e.left + " " + e.right );
        return unify( C );
      }
      if ( e.left instanceof Variable left ) {
        Rewrite r = new Rewrite( left, e.right );
//                System.out.println( "2. Rewriting " + left + " " + e.right );
        return compose( unify( replace( r, C ) ), Collections.singletonList( r ) );
      }
      if ( e.right instanceof Variable right ) {
        Rewrite r = new Rewrite( right, e.left );
//                System.out.println( "3. Rewriting " + right + " " + e.left );
        return compose( unify( replace( r, C ) ), Collections.singletonList( r ) );
      }
      if ( e.right instanceof Function f1 && e.left instanceof Function f2 ) {
//                System.out.println( "4. Destructuring " + e.right + " " + e.left );
        return unify( Stream.concat(
            C.stream(),
            Stream.of( Equation.of( f1.in, f2.in ), Equation.of( f1.out, f2.out ) )
        ).collect( Collectors.toSet() ) );
      } else {
        throw new RuntimeException( "FAIL!" );
      }
    }
  }

  public static Set< Equation > replace( Rewrite r, Set< Equation > C ) {
    return C.stream().map( c -> c.replace( r ) ).collect( Collectors.toSet() );
  }

  public static List< Rewrite > compose( List< Rewrite > left, List< Rewrite > right ) {
    return Stream.concat(
        right.stream().map( r -> new Rewrite( r.left, replace( left, r.right ) ) ),
        left.stream().filter( r -> ! inDomain( right, r.left ) )
    ).toList();
  }

  public static Expression replace( List< Rewrite > r, Expression e ) {
    return r.stream().filter( _r -> _r.left.equals( e ) ).map( _r -> _r.right ).findFirst().orElse( e );
  }

  public static boolean inDomain( List< Rewrite > r, Variable v ){
      return r.stream().anyMatch( _r -> _r.left.equals( v ) );
  }


}
