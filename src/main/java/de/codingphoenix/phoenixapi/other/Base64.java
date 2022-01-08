package de.codingphoenix.phoenixapi.other;

import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;

public class Base64 {
    public static Object[] objectsArrayFromBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            ObjectInputStream dataInput = new ObjectInputStream(inputStream);
            Object[] objects = new Object[dataInput.readInt()];

            for (int i = 0; i < objects.length; i++) {
                objects[i] = dataInput.readObject();
            }

            dataInput.close();
            return objects;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    public static Object objectFromBase64(String data) throws IOException {
        if (data.equals(" ")) {
            return null;
        }
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            ObjectInputStream dataInput = new ObjectInputStream(inputStream);
            Object object = dataInput.readObject();
            dataInput.close();
            return object;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    public static String objectsArrayToBase64(Object[] object) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream dataOutput = new ObjectOutputStream(outputStream);

            dataOutput.writeInt(object.length);

            for (int i = 0; i < object.length; i++) {
                dataOutput.writeObject(object[i]);
            }

            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    public static String objectToBase64(Object object) throws IllegalStateException {
        if (!(object instanceof Serializable)) {
            return " ";
        }
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream dataOutput = new ObjectOutputStream(outputStream);
            dataOutput.writeObject(object);
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }
}
