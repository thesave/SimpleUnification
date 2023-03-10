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

public class Equation {
    Expression left;
    Expression right;

    private Equation( Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public static Equation of( Expression left, Expression right ){
        return new Equation( left, right );
    }

    public Equation replace( Rewrite r ) {
        return new Equation( left.replace( r ), right.replace( r ) );
    }

    @Override
    public String toString() {
        return left + "=" + right;
    }

}
