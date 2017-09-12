/**
 * Unlocks a circular bit device that is 4 bits with 2 bit disclosure.
 * @author Hunter Hobbs
 * @version 1.0.1
 */
public class FourBitTwoDisclosureDeviceUnlocker extends DeviceUnlocker {

    /**
     * First peek pattern used to unlock resource.
     */
    private static final CharSequence PEEK_ONE = "??--";

    /**
     * Second peek pattern used to unlock resource.
     */
    private static final CharSequence PEEK_TWO = "?-?-";

    /**
     * Third peek pattern used to unlock resource.
     */
    private static final CharSequence PEEK_THREE = "?--?";

    /**
     * First poke pattern used with first peek pattern.
     */
    private static final CharSequence POKE_ONE = "TT--";

    /**
     * Second poke pattern used with second peek pattern.
     */
    private static final CharSequence POKE_TWO = "T-T-";

    /**
     * Third poke pattern used with third peek pattern.
     */
    private static final CharSequence POKE_THREE = "T--T";

    /**
     * Maximum number of iterations allowed before considered failure.
     */
    private static final int MAX_ATTEMPTS = 1000;

    /**
     * Runs the unlock resource algorithm on some device.
     * @param args not currently used for anything
     */
    public static void main(final String[] args) {

        Device device = new Device();   // Instance of a new Device object
        int counter = 0;                // var to keep track of unlock attempts

        while (!device.spin() && counter < MAX_ATTEMPTS) {  // spin()
            log("spin()");

            if (unlockResource(device, PEEK_ONE, POKE_ONE)) {
                break;                  // exit loop if unlocked
            } else if (unlockResource(device, PEEK_TWO, POKE_TWO)) {
                break;                  // exit loop if unlocked
            } else if (unlockResource(device, PEEK_THREE, POKE_THREE)) {
                break;                  // exit loop if unlocked
            }

            counter++;
        }

        String s = showTrace();
        System.out.println(s);          // outputs call trace

        if (counter >= MAX_ATTEMPTS) {
            System.out.println("Unlock unsuccessful");
        } else {
            System.out.println("Resource unlocked");
        }

    }

    /**
     * Attempts to unlock a device with a series of device method calls.
     * @param device device to be unlocked
     * @param peek char pattern used for peek method
     * @param poke char pattern useed for poke method
     * @return {@code true} if the device is unlocked,
     *  {@code false} if the device is still locked
     */
    private static boolean unlockResource(final Device device,
                                  final CharSequence peek,
                                  final CharSequence poke) {

        log("peek(" + peek + ")\npoke(" + poke + ")\nspin()");
        device.peek(peek);
        device.poke(poke);
        return device.spin();
    } // end unlockResource
}
