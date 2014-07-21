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

public class AdamVariant{
    private AdamContig contig;
    private long position;
    private long exclusiveEnd;
    private String referenceAllele;
    private String variantAllele;


    public AdamVariant(AdamContig contig, long position, long exclusiveEnd, String referenceAllele, String variantAllele)
    {
        this.contig = contig;
        this.position = position;
        this.exclusiveEnd = exclusiveEnd;
        this.referenceAllele = referenceAllele;
        this.variantAllele = variantAllele;

    }

    public AdamContig getContig()
    {
        return contig;
    }

    public long getPosition()
    {
        return position;
    }

    public long getExclusiveEnd()
    {
        return exclusiveEnd;
    }

    public String getReferenceAllele()
    {
        return referenceAllele;
    }

    public String getVariantAllele()
    {
        return variantAllele;
    }

    public void setContig(AdamContig contig){
        this.contig = contig;
    }

    public void setPosition(long position){

    }

    public void setExclusiveEnd(long exclusiveEnd){

    }

    public void setReferenceAllele(String referenceAllele){

    }

    public void setVariantAllele(String variantAllele){

    }


}

