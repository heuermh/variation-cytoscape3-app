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
package org.dishevelled.variation.adam;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;


import org.dishevelled.variation.Feature;
import org.dishevelled.variation.Variation;
import org.dishevelled.variation.VariationService;

//import org.apache.spark.SparkContext // add to Maven buld

/**
 * ADAM file variation service.
 */

public final class AdamVariationService implements VariationService
{
	private String species;
    private String reference;
    private File file;
    private String filePath;
    private AdamVariant variant;

    public AdamVariationService(final String species, final String reference, final File file, final String filePath, AdamVariant variant)
    {
	    checkNotNull(species);
	    checkNotNull(reference);
	    checkNotNull(file);
	    checkNotNull(filePath);
	    checkNotNull(variant);
	    
	    this.species = species;
	    this.reference = reference;
	    this.file = file;
	    this.filePath = filePath;
	    this.variant = variant;
	}

    @Override
    public List<Variation> variations(final Feature feature)
    {
        checkNotNull(feature);
        checkArgument(species.equals(feature.getSpecies()));
        checkArgument(reference.equals(feature.getReference()));

        
        final List<Variation> variations = new ArrayList<Variation>(); // ArrayList of Variation to be returned 
        try
        {
            // Implementation 1
            //AdamReader.stream(Files.newReaderSupplier); // to-do
            
            
            
            
        }
        catch (Exception e)
        {
            // todo
        }
        return variations;
    }
    
    public AdamVariant ConvertNullADAMVariant()
    {
        AdamVariant variant = null;
        return variant;
    }
    
    public AdamVariant convertEmptyAdamVariant()
    {
        AdamVariant variant = null;
        return variant;
    	
    }
    
    public Variation convertAdamVariant() // return type correct?
    {
        AdamContig contig = null; // fix constructor parameters.
        return null;
    }

    public Variation convert(AdamVariant variant)
    {
    	
    	
       // use Adam Parser from Matt  
       // todo -- use AdamContig and AdamVariant to create a Variation

        Variation variationtoReturn = null;

        String species = variant.getContig().getSpecies();
        String reference = variant.getContig().getAssembly(); // in AdamContig, there is a mis-match between getter methond name and field value
        List<String> identifiers; // TO-DO
        String referenceAllele = variant.getReferenceAllele();
        List<String> alternateAlleles; // TO-DO
        String region;
        String start;
        String end;    
      
        
        /*
        private final String species;
        private final String reference;
        private final List<String> identifiers; // e.g. dbSNP id
        private final String referenceAllele;
        private final List<String> alternateAlleles;
        private final String region;
        private final int start;
        private final int end;
        */

        return variationtoReturn;
    }
    
    
    
    public AdamContig convertMissingContig()
    {
        AdamContig contig = null; // fix constructor parameters.
        return null;

    }
    
   
}
