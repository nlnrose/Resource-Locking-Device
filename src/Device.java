import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

/**
 * Resource-locking device with circular bit storage.
 * @author Hunter Hobbs
 * @version 1.0.1
 */
public class Device {

    /**
     * Default number of bits to reveal per peek.
     */
    private static int DEFAULT_PEEKS = 2;

    /**
     * Default number of bits stored.
     */
    private static int DEFAULT_SIZE = 4;

    /**
     * Character indicator of false.
     */
    private static char VALUE_FALSE = 'F';

    /**
     * Character indicator of true.
     */
    private static char VALUE_TRUE = 'T';

    /**
     * Holds bit contents of lock
     */
    private static boolean[] lock;

    /**
     * Constructs device using defaults.
     */
    public Device() {

        // creates random lock and makes sure its initially locked
        do {
            lock = randomBooleanArray(DEFAULT_SIZE);
        } while (!isLocked(lock));     // lockCheck returns false when unlocked.
    }

    /**
     * Construct device with specified bits for testing.
     * @param initialBits the bit values for this test device
     * @param bitsPerPeek the number of bits to disclose via peek or set via poke
     */
    public Device(boolean[] initialBits, int bitsPerPeek) {
        lock = initialBits;
        DEFAULT_PEEKS = bitsPerPeek;
    }

    /**
     * Construct device with specivied size and number of peek/poke bits.
     * @param size the number of bits sotred in this device
     * @param bitsPerPeek the number of bits to disclose via peek or set via poke
     */
    public Device(final int size, final int bitsPerPeek) {
        lock = randomBooleanArray(size);
        DEFAULT_PEEKS = bitsPerPeek;
    }

//---------------------------------- CLASS METHODS -------------------------------//
    /**
     * Constructs a boolean array of random values. Used to implement the lock.
     * @param len length of array
     * @return randomized boolean array
     */
    private boolean[] randomBooleanArray(int len) {
        Random random = new Random();
        boolean[] arr = new boolean[len];
        for(int i = 0; i < len; i++) {
            arr[i] = random.nextBoolean();
        }
        return arr;
    } // end random

    /**
     * Check to see if all values in array are the same.  Used to check if
     * lock is unlocked.
     * @param spun boolean array to represent lock state
     * @return {@code true} if some values are different in lock - meaning
     * lock is locked. {@code flase} if
     * all values are the same - meaning lock is unlocked.
     */
    private boolean isLocked(boolean[] spun) {
        boolean first = spun[0];    // compares all values to first value
                                    // to check for difference.
        for (boolean b: spun) {
            if (b != first) {
                return true;
            }
        }
        return false;
    } // end isLocked


    /**
     * Initiate device rotation.
     * @return {@code true} if all bits have identical value; {@code false}
     * otherwise
     */
    public boolean spin() {
        int rando = 1 + (int)(Math.random() * DEFAULT_SIZE), i;
        for(i = 0; i < rando; i++) {
            rotate();
        }
        if (isLocked(lock)) {
            return false;   // lock is locked
        } else {
            return true;    // lock is unlocked
        }
    } // end spin


    /**
     * circularly rotate bits 1 bit to the right.
     * Used in spin method to rotate some unknown amount of times.
     */
    private static void rotate() {
        boolean x = lock[lock.length - 1];  // holds last value in array
        for (int i = lock.length - 1; i > 0; i--) {
            lock[i] = lock[i - 1];  // rotates 1 bit to the right
        }
        lock[0] = x;    // puts last value at index 0
    } // end rotate


    /**
     * Peek at bits of device.
     * @param pattern indicatin which bits to show as '?'
     * @return a pattern that discloses the values of the indicated bits
     */
    public CharSequence peek(CharSequence pattern) {
        StringBuilder s = new StringBuilder();  // holds string of shown values

        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == '?') {
                if (lock[i]) {
                    s.append(VALUE_TRUE);
                } else {
                    s.append(VALUE_FALSE);
                }
            } else {
                s.append(pattern.charAt(i));
            }
        }
        return s;
    } // end peek

    /**
     * Poke bits into device.
     * @param pattern indicator of values of bits to poke
     */
    public void poke(CharSequence pattern) {
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == 'T') {
                lock[i] = true;
            } else if (pattern.charAt(i) == 'F') {
                lock[i] = false;
            }
        }
    } // end poke

    /**
     * String representation of device object
     * @return device object as a string
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("[");
        for (int i = 0; i < lock.length; i++) {
            s.append(lock[i]);
        }
        s.append("]");
        return s.toString().replaceAll("false", Character.toString(VALUE_FALSE))
                .replaceAll("true", Character.toString(VALUE_TRUE))
                .replaceAll(" ]", "]");
        
    } // end toString
}
