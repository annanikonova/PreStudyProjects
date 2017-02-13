package com.nixsolutions.project8;

import java.io.*;

/**
 * Created by annnikon on 13.02.17.
 */
public class DemoTaskD {
    public static SerializableUtilsImpl utils = new SerializableUtilsImpl();
    private static final String SERIALIZE_FILE = "serializeItem.txt";

    public static void main(String[]args) {
        Item original = new Item(0, "original name");
        try (OutputStream stream = new FileOutputStream(SERIALIZE_FILE)){
            utils.serialize(stream,original);
            System.out.println("File created: "+(new File(SERIALIZE_FILE).length()>0) );

        }
        catch (IOException e) {
            System.out.println("Cannot create stream for writing:"
                    +e.getMessage() );
        }

        Item restored1 = null, restored2 = null;
        try (InputStream stream = new FileInputStream(SERIALIZE_FILE)){
            restored1 = (Item)utils.deserialize(stream);
             System.out.println("Desearialized 1st item: "+(restored1!=null) );

        }
        catch (IOException e) {
            System.out.println("Cannot create stream 1 for reading:"
                    +e.getMessage() );
        }

        try (InputStream stream = new FileInputStream(SERIALIZE_FILE)){
            restored2 = (Item)utils.deserialize(stream);
             System.out.println("Desearialized 2nd item: "+(restored2!=null) );

        }
        catch (IOException e) {
            System.out.println("Cannot create stream 1 for reading:"
                    +e.getMessage() );
        }
        if (restored1!=null && restored2!=null ) {
            System.out.println("Restored objects are different: " + (restored1!=restored2));
            System.out.println("Restored objects are not equal: " + !(restored1.equals(restored2)));
            System.out.println("Their String fields are the same: "+ restored1.getName().equals(restored2.getName()));
            System.out.println("Their Int fields are the same: "+ (restored1.getId()==restored2.getId()));
        }





    }

   static class Item implements Serializable {
        private int id;
        private String name;
        public Item(int id,String name) {
            this.id = id;
            this.name=name;
        }
        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }
    }
}
