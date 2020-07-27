package helpers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author UCGOSERVER.COM
 *
 * Denne klassen brukes til å utføre serialize og deserialize av objekter.
 */
public class Serializing {

    /**
     * Serialiserer oppgitt objekt og returnerer det som en string av hex
     * verdier laget vha byteArrayString
     *
     * @param obj Objekt som skal serialiseres.
     *
     * @return Objekt data som en string av hex verdier, null hvis feil.
     */
    public static String Serialize(Object obj) {

        ByteArrayOutputStream object_data = new ByteArrayOutputStream();

        try {
            ObjectOutputStream object_stream = new ObjectOutputStream(object_data);
            object_stream.writeObject(obj);
            object_stream.flush();
            object_stream.reset();
            object_stream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return byteArrayString.byteArrayToString(object_data.toByteArray());
    }

    /**
     * Deserialiserer et objekt lest fra en string av hex verdier.
     *
     * @param <T>
     * @param str String som inneholder objekt data.
     *
     * @return Objektet som har blitt deserialisert, eller null hvis feil.
     */
    @SuppressWarnings("unchecked")
    public static <T> T Deserialize(String str) {

        T obj;

        try {
            ByteArrayInputStream object_data = new ByteArrayInputStream(helpers.byteArrayString.stringToByteArray(str));
            ObjectInputStream object_stream = new ObjectInputStream(object_data);

            obj = (T) object_stream.readObject();

            object_stream.close();
            object_data.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return obj;

    }
}
