package org.dishevelled.variation.adam;

/*

    dsh-variation  Variation.
    Copyright (c) 2013-2014 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 3 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.fsf.org/licensing/licenses/lgpl.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/

public class AdamContig{
    private String name;
    private long  contigLength;
    private String reference;
    private String species;


    public AdamContig(String name, long contigLength, String reference, String species)
    {
        this.name = name;
        this.contigLength = contigLength;
        this.reference = reference;
        this.species = species;
    }

    public String getContigName()
    {
        return name;
    }

    public long getContigLength()
    {
        return contigLength;
    }

    public String getAssembly()
    {
        return reference;
    }

    public String getSpecies()
    {
        return species;
    }

    public void setContigLength(long contigLength){
        this.contigLength = contigLength;
    }

    public void setContigName(String name){
        this.name = name;
    }

    public void setAssembly(String reference){
        this.reference = reference;
    }

    public void setSpecies(String species){
        this.species = species;
    }


}

