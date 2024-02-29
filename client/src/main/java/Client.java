import java.net.Inet4Address;
import java.util.Scanner;

import Demo.Response;

public class Client {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        java.util.List<String> extraArgs = new java.util.ArrayList<>();

        try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args, "config.client",
                extraArgs)) {
            // com.zeroc.Ice.ObjectPrx base =
            // communicator.stringToProxy("SimplePrinter:default -p 10000");
            Response response = null;
            Demo.PrinterPrx service = Demo.PrinterPrx
                    .checkedCast(communicator.propertyToProxy("Printer.Proxy"));

            try {

                if (service == null) {
                    throw new Error("Invalid proxy");
                }

                String username = System.getProperty("user.name");
                String hostname = Inet4Address.getLocalHost().getHostName();
                String userInput;

                do {
                    userInput = sc.nextLine();

                    response = service.printString(username + ":" + hostname + " " + userInput);

                    if(userInput.equalsIgnoreCase("exit")) {
                        System.out.println("Thank you for usin the program :)");
                        break;
                    }

                    System.out.println("Server's response: " + response.value);

                } while (!userInput.equalsIgnoreCase("exit"));

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }
}