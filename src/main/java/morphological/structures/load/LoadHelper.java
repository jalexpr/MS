package morphological.structures.load;

import morphological.structures.internal.Property;
import template.wrapper.hash.CityHash;

import static morphological.structures.internal.Property.KEY_OFFSET;

public class LoadHelper {

    public final static byte CONTROL_OFFSET = Property.CONTROL_OFFSET;

    public static int getHashCode(String str) {
        return (int)CityHash.cityHash64(str);
    }

    public static int getControlHashCode(String str) {
        return (str.hashCode() >> CONTROL_OFFSET) & 255;
    }

    public static int createKeyWithControlCode(int start, int oldKey, String str) throws Exception {
        return createKeyWithControlCode(start + (oldKey << KEY_OFFSET), getControlHashCode(str));
    }

    private static int createKeyWithControlCode(int oldKey, int controlHash) throws Exception {
//        Проверка, что последнии значения ноль
        if(((byte)oldKey) == 0) {
            return controlHash + oldKey;
        } else {
            throw new Exception(String.format("Ключ: %d имеет не пустые 8 последнии битов", oldKey));
        }
//        return oldKey;
    }

    public static byte getControlValue(int key) {
        return (byte)(key & 255);
    }

}
