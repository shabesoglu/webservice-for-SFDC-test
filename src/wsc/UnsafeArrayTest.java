package wsc;

import java.lang.reflect.Field;

public class UnsafeArrayTest {
	
    public void testUnsafe() {
    	sun.misc.Unsafe unsafe = null;

        try {
            Field field = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (sun.misc.Unsafe) field.get(null);
        } catch (Exception e) {
            throw new AssertionError(e);
        }

        int ten = 10;
        byte size = 1;
        long mem = unsafe.allocateMemory(size);
        unsafe.putAddress(mem, ten);
        long readValue = unsafe.getAddress(mem);
        System.out.println("Val: " + readValue);

    }

}
