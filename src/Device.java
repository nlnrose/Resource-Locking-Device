import java.util.Arrays;
import java.util.Random;


public class Device {
    
    private static int DEFAULT_PEEKS = 2;
    private static int DEFAULT_SIZE = 4;
    private static char VALUE_FALSE = 'F';
    private static char VALUE_TRUE = 'T';

    static boolean[] lock;

    public Device() {

        do {src
            lock = randomBools(DEFAULT_SIZE);
        } while (!lockCheck(lock));

    }

    public Device(boolean[] initialBits, int bitsPerPeek) {
        lock = initialBits;
        DEFAULT_PEEKS = bitsPerPeek;

    }

    public Device(final int size, final int bitsPerPeek) {
        lock = randomBools(size);
        DEFAULT_PEEKS = bitsPerPeek;
    }

    private boolean[] randomBools(int len) {
        Random random = new Random();
        boolean[] arr = new boolean[len];
        for(int i = 0; i < len; i++) {
            arr[i] = random.nextBoolean();
        }
        return arr;
    }

    private boolean lockCheck(boolean[] spun) {
        boolean first = spun[0];
        for (boolean b: spun) {
            if (b != first) {
                return true;
            }
        }
        return false;
    }

    public boolean spin() {
        int rando = 1 + (int)(Math.random() * DEFAULT_SIZE), i;
        //System.err.println("number of spins = " + rando);
        for(i = 0; i < rando; i++) {
            rotate();
        }
	//Make sure it is not locked, then return it
        return !lockCheck(lock);
    }

    public void poke(CharSequence pattern) {
        pattern = pattern.toString().replaceAll("\\[", "")
                .replaceAll(" ", "").replaceAll("]", "");

        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == 'T') {
                lock[i] = true;
            } else if (pattern.charAt(i) == 'F') {
                lock[i] = false;
            }
        }
    }

    public CharSequence peek(CharSequence pattern) {
        pattern = pattern.toString().replaceAll("\\[", "")
                .replaceAll(" ", "").replaceAll("]", "");
        //System.err.println(pattern);
        StringBuilder s = new StringBuilder();
        
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == '?') {
                if (lock[i]) {
                    s.append(VALUE_TRUE);
                } else {
                    s.append(VALUE_TRUE);
                }
            } else {
                s.append(pattern.charAt(i));
            }
        }

        //System.err.println("PEEK: " + s);
        return s;
    }

    private static void rotate() {
        boolean x = lock[lock.length - 1];
        for (int i = lock.length - 1; i > 0; i--) {
            lock[i] = lock[i - 1];
        }
        lock[0] = x;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("[");

        return Arrays.toString(lock).replaceAll("false", Character.toString(VALUE_FALSE)).
                replaceAll("true", Character.toString(VALUE_TRUE));
        
    }
}
