/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jlab.clas12.tracking.tests;

import java.util.ArrayList;
import java.util.List;
import org.jlab.jnp.detector.CollectionIterator;
import org.jlab.jnp.detector.HitCollection;
import org.jlab.jnp.hipo4.data.Bank;
import org.jlab.jnp.hipo4.data.Event;
import org.jlab.jnp.hipo4.io.HipoReader;

/**
 *
 * @author gavalian
 */
public class CollectionTest {
    
    public static HitCollection  readCollection(Bank bank){
        HitCollection hits = new HitCollection(bank.getRows());
        for(int i = 0; i < bank.getRows(); i++){
            hits.sector(i, bank.getInt("sector", i));
            hits.layer(i, bank.getInt("layer", i));
            hits.component(i, bank.getInt("component", i));
            hits.association(i, 0);
        }
        return hits;
    }
    
    public static void showSorted(HitCollection hits){
        System.out.println("BEFORE THE SORT \n" + hits);
        hits.sort();
        System.out.println("AFTER  THE SORT \n" + hits);
    }
    // SELECT hits from sector 1 and print them
    public static void show(HitCollection hits){
        
        CollectionIterator iter = new CollectionIterator(500);
        // Select events that have sector 1, layer and component do not matter (-1)
        // and the association is not important as well. 
        iter.setMatch(new int[]{1,-1,-1,-1});
        
        System.out.println("hits Size before = " + hits.count());
        
        hits.iterator(iter);
        System.out.println(iter);
        
        hits.setIterator(iter);
        
        System.out.println("hits Size after  = " + hits.count());
    }
    //---
    // Select hits for each layer in sector one and print out how many hits are in each layer
    //---
    public static void showLayers(HitCollection hits){
        List<CollectionIterator>  layers = new ArrayList<CollectionIterator>();
        for(int i = 0; i < 36; i++){
            CollectionIterator iter = new CollectionIterator(500);
            iter.setMatch(new int[]{1,i+1,-1,-1});
            layers.add(iter);
        }
        
        hits.iterator(layers);
        
        for(int i = 0; i < layers.size(); i++){
            System.out.printf(" LAYER %4d : hits = %5d\n",i,layers.get(i).count());
        }
    }
    

    
    //---
    // Select hits for each layer in sector one and print out how many hits are in each layer
    //---
    public static void showLayersData(HitCollection hits){
        List<CollectionIterator>  layers = new ArrayList<CollectionIterator>();
        for(int i = 0; i < 36; i++){
            CollectionIterator iter = new CollectionIterator(500);
            iter.setMatch(new int[]{1,i+1,-1,-1});
            layers.add(iter);
        }
        
        hits.iterator(layers);
        System.out.println("DATA");
        System.out.println(hits);
        // print components from hits in layer 1
        hits.setIterator(layers.get(0));
        System.out.println("DATA SELECTED");
        System.out.println(hits);
        /*
        for(int i = 0; i < hits.count(); i++){
            System.out.printf(" hit = %4d , component = %5d cluster = %5d\n",
                    i,hits.component(i),hits.association(i));
        }
        // print components from hits in layer 2
        hits.setIterator(layers.get(1));
        
        for(int i = 0; i < hits.count(); i++){
            System.out.printf(" hit = %4d , component = %5d cluster = %5d\n",
                    i,hits.component(i),hits.association(i));
        }*/
        
    }
    
       //---
    // Select hits for each layer in sector one and print out how many hits are in each layer
    //---
    public static void setLayerClusters(HitCollection hits){
        List<CollectionIterator>  layers = new ArrayList<CollectionIterator>();
        for(int i = 0; i < 36; i++){
            CollectionIterator iter = new CollectionIterator(500);
            iter.setMatch(new int[]{1,i+1,-1,-1});
            layers.add(iter);
        }
        
        hits.iterator(layers);
        System.out.println("DATA");
        System.out.println(hits);
        // print components from hits in layer 1
        hits.setIterator(layers.get(0));
        System.out.println(hits);
        /*
        int clusterID = 2;
        for(int i = 0; i < hits.count(); i++){
            hits.association(i, clusterID);
            System.out.printf(" hit = %4d , component = %5d cluster = %5d\n",
                    i,hits.component(i), hits.association(i));
        }*/
        
    }
    
    public static void main(String[] args){
        String filename = "/Users/gavalian/Work/DataSpace/clas12/ai/out_clas_004013.evio.00000-00009.hipo";
        HipoReader reader = new HipoReader();
        reader.open(filename);
        Event event = new Event();
        
        Bank  bank  = new Bank(reader.getSchemaFactory().getSchema("DC::tdc"));
        
        for(int i = 0; i < 5; i++){
            reader.nextEvent(event);
            event.read(bank);            
            HitCollection hits = CollectionTest.readCollection(bank);
            //CollectionTest.show(hits);
            //CollectionTest.showLayers(hits);
            //CollectionTest.showLayersData(hits);
            //CollectionTest.setLayerClusters(hits);
            CollectionTest.showSorted(hits);
        }
    }
}
