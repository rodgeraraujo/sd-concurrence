package nf.co.rogerioaraujo.questao_01;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Client {
    public static void main(String[] args) {


        //generate a random number
        Random random = new Random();

        //received number
        int received;


        try {
            //instance the socket as null
            Socket client = null;

            client = new Socket("127.0.0.1", 9622);

            //loop
            do {
                List<Integer> numbers = new ArrayList<>();

                //generate first number - random
                int number1 = random.nextInt(10);

                //generate second number - random
                int number2 = random.nextInt(10);

                //add the numbers on arraylist
                numbers.add(number1);
                numbers.add(number2);

                //print the numbes
                System.out.printf("Numbers generated: %d and %d%n", number1, number2);

                //send numbers to Node2, with an output stream
                ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
                outputStream.writeObject(numbers);

                //receive the result from server, and print the message if is 0, with an input stream
                ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
                //received number from server
                received = inputStream.readInt();

                //instance the serverSocket and socket client
                ServerSocket serverSocket = new ServerSocket(9633);
                Socket socket = serverSocket.accept();

                //get values, with an output stream
                ObjectInputStream inputStream2 = new ObjectInputStream(socket.getInputStream());
                int result = (Integer) inputStream2.readObject();

                System.out.println("Resultado: " + result);


            } while (true);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
