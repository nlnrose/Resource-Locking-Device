import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Device d = new Device();
        System.out.println("Initial Lock: " + d.toString().replaceAll(", ", " "));

        //String[] s = d.peek("[? ? - -]").toString().split("");
        //System.out.println(Arrays.toString(s).replaceAll(", ", " "));

        /*boolean[] initial = {true, false, false, true};
        int p = 1;
        d = new Device(initial, p);
        System.out.println(d.toString());

        d = new Device(10, p);
        System.out.println(d.toString());*/

        //d.spin();
        //System.out.println(d.toString().replaceAll(", ", " "));

        while(d.spin()) {
            System.out.println(d.toString());

            d.peek("[? ? - -]");
            d.poke("[T T - -]");
            d.spin();

            d.peek("[? - ? -]");
            d.poke("[T - T -]");
            d.spin();

            System.out.println(d.toString());
            d.peek("[? - - ?]");
            d.poke("[T - - T]");

        }

        System.out.println("UNLOCKED!");
        System.out.println(d.toString());
    }
}
