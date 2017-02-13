package com.nixsolutions.project8;

import com.nixsolutions.project4.CollectionUtilsImpl;
import interfaces.task8.CyclicCollection;
import interfaces.task8.CyclicItem;
import interfaces.task8.SerializableUtils;

import java.io.*;

/**
 * Created by annnikon on 10.02.17.
 */
public class DemoTaskC {
    private static final String SERIALIZE_FILE = "serialize.txt";
    private static SerializableUtils utils = new SerializableUtilsImpl();
    private static CyclicCollection collection = new CyclicCollectionImpl();
    private static CyclicItem item1, item2, item3;
    private static CyclicCollection restoredCollection;

    public static void main(String[]args) {

        System.out.println("Created empty collection with size: "+collection.size());
        createItems();
        addItemsToCollection();
        System.out.println("Serializing collection: ");
        serialize();
        System.out.println("Deserializing collection: ");
        restoredCollection = (CyclicCollectionImpl)deserialize();
        compareCollections();

    }

    private static void createItems() {
        item1 = new CyclicItemImpl();
        item2 = new CyclicItemImpl();
        item3 = new CyclicItemImpl();
        System.out.println("For single item next is itself: "
                + (item1.nextItem()==item1));

        System.out.println("Seted serializable objects for items 1, 2, 3. ");
        item1.setValue("Value of item1");
        item2.setValue(Math.PI);
        item3.setValue(new File("file.txt"));
        System.out.println("Seted temp for items 1, 2, 3. ");
        item1.setTemp("Temp of item1");
        item2.setTemp("Temp of item2");
        item3.setTemp("Temp of item3");
    }

    private static void addItemsToCollection() {
        collection.add(item1);
        System.out.println("After adding item 1 collection size is: "+collection.size() );
        System.out.println("Value of first item in collection: "+collection.getFirst().getValue());
        System.out.println("Value of next item in collection is value of first: "
                +collection.getFirst().nextItem().getValue());
        collection.add(item2);
        System.out.println("After adding item 2 collection size is: "+collection.size() );
        System.out.println("Value of second item in collection: "
                +collection.getFirst().nextItem().getValue());
        collection.add(item3);
        System.out.println("After adding item 3 collection size is: "+collection.size() );
        System.out.println("Value of the third item in collection: "
                +collection.getFirst().nextItem().nextItem().getValue());
        System.out.print("Try to add item3 one more time: ");
        try{
            collection.add(item3);
            System.out.println("Added.");}
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Collection size is: "+collection.size() );
    }

    private static void serialize() {

        try (OutputStream stream = new FileOutputStream(SERIALIZE_FILE)){
            utils.serialize(stream,collection);
            System.out.println("File created: "+(new File(SERIALIZE_FILE).length()>0) );

        }
        catch (IOException e) {
            System.out.println("Cannot create stream for writing:"
                    +e.getMessage() );
        }
    }


    private static Object deserialize() {
        Object result = null;
        try (InputStream stream = new FileInputStream(SERIALIZE_FILE)){
            result = utils.deserialize(stream);
            System.out.println("Desearialized: "+(result!=null) );
        }
        catch (IOException e) {
            System.out.println("Cannot create stream for reading:"
                    +e.getMessage() );
        }
        return result;

    }

    private static void compareCollections() {
        System.out.println("size of restored collection is: "
                +restoredCollection.size() );
        System.out.println("Value of first item: "
                +restoredCollection.getFirst().getValue());
        System.out.println("Value of the second item: "
                +restoredCollection.getFirst().nextItem().getValue());
        System.out.println("Value of the third item: "
                +restoredCollection.getFirst().nextItem().nextItem().getValue());
        System.out.println("Temp was not restored: "
                + !(collection.getFirst().getTemp().
                equals(restoredCollection.getFirst().getTemp())));
        System.out.println("Collections are different: "
        + !collection.equals(restoredCollection));
    }
}
