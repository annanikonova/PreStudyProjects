package com.nixsolutions.project8;

import interfaces.task8.SerializableUtils;

import java.io.*;

/**
 * Created by annnikon on 09.02.17.
 */
public class SerializableUtilsImpl implements SerializableUtils, Serializable {

    @Override

    public void serialize(OutputStream outputStream, Object object) {
        if(outputStream == null || object == null) {
            throw new NullPointerException("Null parameter given");
        }
        if(!(object instanceof Serializable)) {
            NotSerializableException cause =
                    new NotSerializableException("Object should implement Serializable");
            throw new RuntimeException("Wrong argument", cause);
        }
        try (ObjectOutputStream objStream  =new ObjectOutputStream(outputStream)) {
            objStream.writeObject(object);
        }
        catch (IOException e) {
            throw new RuntimeException("Cannot create object output stream", e);
        }


    }

    @Override
    public Object deserialize(InputStream inputStream) {
        if(inputStream == null) {
            throw new NullPointerException("Null input stream given");
        }
         try (ObjectInputStream objStream = new ObjectInputStream(inputStream)) {
            Object readed = objStream.readObject();
            return readed;

         }
         catch (IOException e) {
             throw new RuntimeException("Cannot create object input stream", e);
         }
         catch (ClassNotFoundException e) {
             throw new RuntimeException("Class not found", e);
         }


    }


}
