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

import parquet.avro.AvroParquetReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.hadoop.fs.Path;

//import org.apache.spark.SparkContext // add to Maven build

/**
 * ADAM file variation service.
 *
 * @author  Niranjan Padmanabhan
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

    // reads ADAMVariants from directory of Parquet-formatted files, converts them to Variations using the convert method, and returns a list of Variations.
    @Override
    public List<Variation> variations(final Feature feature)
    {
        /*checkNotNull(feature);
        checkArgument(species.equals(feature.getSpecies()));
        checkArgument(reference.equals(feature.getReference()));*/

        final List<Variation> variationsToReturn = new ArrayList<Variation>(); // ArrayList of Variation to be returned
        List<AdamVariant> adamList = new ArrayList<AdamVariant>();
        try
        {
            Path dataFilePath = new Path(filePath);
            AvroParquetReader<Genotype> parquetReader =  new AvroParquetReader<Genotype>(dataFilePath);

            Genotype tmpValue;

            while ((tmpValue = parquetReader.read()) != null)
            {
                AdamContig contig = tmpValue.variant.contig;
                long pos = tmpValue.variant.start;
                long exclusiveEnd = tmpValue.variant.end;
                String referenceAllele = tmpValue.variant.referenceAllele;
                String variantAllele = tmpValue.variant.alternateAllele;

                AdamVariant variant = new AdamVariant(contig, pos, exclusiveEnd, referenceAllele, variantAllele);
                adamList.add(variant);
                Variation v = convert(adamList);
                variationsToReturn.add(v);
                // what to do when multiple AdamVariants are present?
            }
            return variationsToReturn;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return variationsToReturn;
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

    public Variation convert(List<AdamVariant> variants)
    {
        // todo -- use AdamContig and AdamVariant to create a Variation
        List<String> listOfVariantAlleles = new ArrayList<String>();
        for(AdamVariant v:variants){
            String varAllele =v.getVariantAllele();
            listOfVariantAlleles.add(varAllele);
        }

        Variation variationtoReturn = null;

        String species = variant.getContig().getSpecies();
        String reference = variant.getContig().getAssembly(); // in AdamContig, there is a mis-match between getter methond name and field value
        List<String> identifiers = null; // TO-DO
        String referenceAllele = variant.getReferenceAllele();
        List<String> alternateAlleles = listOfVariantAlleles; // TO-DO
        String region = variant.getContig().getContigName();
        long start = variant.getPosition();
        long end = variant.getExclusiveEnd();

        variationtoReturn = new Variation(species, reference, identifiers, referenceAllele, alternateAlleles, region, start, end);
        return variationtoReturn;
    }

    public AdamContig convertMissingContig()
    {
        AdamContig contig = null; // fix constructor parameters.
        return null;

    }
}
