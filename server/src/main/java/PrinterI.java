import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.zeroc.Ice.InputStream;

import Demo.Response;

public class PrinterI implements Demo.Printer {

    public Response printString(String s, com.zeroc.Ice.Current current) {

        System.out.println("Executing the command: " + s);

        String[] splitMessage = s.split(" ");

        if (splitMessage.length == 1) {
            return new Response(0L, "You sended an void message, try again :(");
        }

        String actual_message = splitMessage[1];

        // 2.a - Fibonacci numbers - Primer factors

        if (isNumeric(actual_message)) {
            Long n = Long.parseLong(actual_message);
            System.out.println(splitMessage[0] + " " + calculateFibonacci(n));
            return new Response(0L, calculatePrimeFactors(n));
        } else if (actual_message.startsWith("listifs")) {

            // 2.b - listifs

            String ans = runCommand("ifconfig");

            System.out.println(splitMessage[0] + "\n" + ans);

            return new Response(0L, ans);

        } else if (actual_message.startsWith("listports")) {

            // listports

            String ans = runCommand("nmap " + splitMessage[2]);

            System.out.println(splitMessage[0] + "\n" + ans);

        } else if (actual_message.startsWith("!")) {

            // !

            String[] everyCommand = s.split("!");

            String ans = runCommand(everyCommand[1]);

            System.out.println(splitMessage[0] + "\n" + ans);

            return new Response(0L, ans);

        } else {

            return new Response(0L, "Submit a valid command to the program :(");

        }

        System.out.println(s);
        return new Response(0, "Server response: " + s);
    }

    public static boolean isNumeric(String strNum) {
        try {
            Long l = Long.parseLong(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static Long calculateFibonacci(Long n) {
        if (n <= 0) {
            return 0L;
        } else if (n == 1) {
            return 1L;
        }

        Long prev1 = 0L;
        Long prev2 = 1L;
        Long ans = 0L;

        for (int i = 2; i <= n; i++) {
            ans = prev1 + prev2;
            prev1 = prev2;
            prev2 = ans;
        }

        return ans;
    }

    public static String calculatePrimeFactors(Long n) {

        String ans = "";
        Long nCopy = n;

        for (Long i = 2L; i < n; i++) {
            while (nCopy % i == 0) {
                ans = ans + i + " ";
                nCopy /= i;
            }
        }

        return ans;
    }

    public static String runCommand(String m) {
        String str = null, output = "";

        InputStream s;
        BufferedReader r;

        try {
            Process p = Runtime.getRuntime().exec(m);

            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((str = br.readLine()) != null)
                output += str + System.getProperty("line.separator");
            br.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return output;
    }

}