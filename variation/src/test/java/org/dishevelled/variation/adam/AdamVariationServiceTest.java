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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import com.google.common.io.Files;

import org.apache.commons.io.FileUtils;

import org.bdgenomics.adam.avro.ADAMContig;
import org.bdgenomics.adam.avro.ADAMVariant;

import org.dishevelled.variation.Feature;
import org.dishevelled.variation.Variation;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit test for AdamVariationService.
 */
public final class AdamVariationServiceTest
{
    private String species;
    private String reference;
    private File file;
    private String filePath;
    private ADAMVariant variant;
    private AdamVariationService variationService;

    @Before
    public void setUp() throws Exception
    {
        species = "human";
        reference = "GRCh37";
        file = Files.createTempDir();
        filePath = file.getPath();
        variant = new ADAMVariant();
        variationService = new AdamVariationService(species, reference, filePath);
    }

    @After
    public void tearDown() throws Exception
    {
        file.delete();
    }


    @Test(expected=NullPointerException.class)
    public void testConstructorNullSpecies()
    {
        new AdamVariationService(null, reference, filePath);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullReference()
    {
        new AdamVariationService(species, null, filePath);
    }

    @Test(expected=NullPointerException.class)
    public void testConstructorNullFilePath()
    {
        new AdamVariationService(species, reference, null);
    }

    @Test
    public void testConstructor()
    {
        assertNotNull(variationService);
    }

    @Test(expected=NullPointerException.class)
    public void testVariationsNullFeature()
    {
        variationService.variations(null);
    }


    // todo:  add a package-private method that converts ADAMVariant to Variation, assume the ADAMVariant already overlaps a Feature

    @Test(expected=NullPointerException.class)
    public void testConvertNullADAMVariant()
    {
        variationService.convert(null);
    }


    // todo:  what should happen if the ADAMVariant is missing data?

    @Test
    public void testConvertEmptyADAMVariant()
    {
        variationService.convert(variant);
    }

    @Test
    public void testConvertMissingContig()
    {
        variant.setPosition(16162219L);
        variant.setExclusiveEnd(16162219L + 1L);
        variant.setReferenceAllele("C");
        variant.setVariantAllele("A");

        variationService.convert(variant);
    }


    // todo:  implement convert method such that this passes

    @Test
    public void testConvertADAMVariant()
    {
        ADAMContig contig = new ADAMContig();
        contig.setContigName("22");
        contig.setContigLength(1L);
        contig.setAssembly(reference);
        contig.setSpecies(species);

        variant.setContig(contig);
        variant.setPosition(16162219L);  // todo (mlh): confirm; ADAMVariant coordinate system is zero-based, closed-open range
        variant.setExclusiveEnd(16162219L + 1L);
        variant.setReferenceAllele("C");
        variant.setVariantAllele("A");

        Variation variation = variationService.convert(variant);
        assertEquals(species, variation.getSpecies());
        assertEquals(reference, variation.getReference());
        assertEquals("C", variation.getReferenceAllele());
        assertEquals(1, variation.getAlternateAlleles().size());
        assertEquals("A", variation.getAlternateAlleles().get(0));
        assertEquals("22", variation.getRegion());
        assertEquals(16162219 - 1, variation.getStart());
        assertEquals(16162219, variation.getEnd());
    }


    // todo:  implement reading from parquet directory such that this passes

    @Test
    public void testVariations() throws Exception
    {
        copyResources("ALL.chr22.phase1_release_v3.20101123.snps_indels_svs.genotypes-2-indv-thin-20000bp-trim.vcf.adam");

        Feature feature = new Feature(species, reference, "ENSG00000206195", "22", 16147979, 16193004, -1);
        boolean found = false;
        for (Variation variation : variationService.variations(feature))
        {
            if (variation.getIdentifiers().contains("rs139448371"))
            {
                assertEquals(species, variation.getSpecies());
                assertEquals(reference, variation.getReference());
                assertEquals("C", variation.getReferenceAllele());
                assertEquals(1, variation.getAlternateAlleles().size());
                assertEquals("A", variation.getAlternateAlleles().get(0));
                assertEquals("22", variation.getRegion());
                assertEquals(16162219 - 1, variation.getStart());
                assertEquals(16162219, variation.getEnd());

                found = true;
            }
        }
        assertTrue(found);
    }


    // internal test utility code

    private void copyResources(final String resourceName) throws Exception {
        URL resourceUrl = getClass().getResource(resourceName);
        File resourceFile = FileUtils.toFile(resourceUrl);
        FileUtils.copyDirectory(resourceFile, file);
    }

    @Test
    public void testCopyResources() throws Exception {
        copyResources("ALL.chr22.phase1_release_v3.20101123.snps_indels_svs.genotypes-2-indv-thin-20000bp-trim.adam");

        assertNotNull(file);

        File parquetFile = new File(file, "part-r-00000.gz.parquet");
        assertTrue(parquetFile.exists());

        File parquetCrcFile = new File(file, ".part-r-00000.gz.parquet.crc");
        assertTrue(parquetCrcFile.exists());

        File metadataFile = new File(file, "_metadata");
        assertTrue(metadataFile.exists());

        File metadataCrcFile = new File(file, "._metadata.crc");
        // todo (mlh):  hope this isn't a problem
        //assertTrue(metadataCrcFile.exists());

        File successFile = new File(file, "_SUCCESS");
        assertTrue(successFile.exists());

        File successCrcFile = new File(file, "._SUCCESS.crc");
        //assertTrue(successCrcFile.exists());
    }
}
