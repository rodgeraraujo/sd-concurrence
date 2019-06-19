package nf.co.rogerioaraujo.questao_01;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        int number1 = 0, number2 = 0;

        //new server on port 9622
        ServerSocket serverSocket = new ServerSocket(9622);

        //instace socket as null
        Socket socket = null;
        List<Integer> numbers = new ArrayList<>();

        //instace de input and outpunt stream
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;

        //loop
        while(number1 == number2){

            if(socket != null){
                //get an output stream for this socket
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                //writes a int value to the to the underlying stream as four bytes
                outputStream.writeInt(0);
                //flushes the output stream and forces any buffered output bytes
                outputStream.flush();
            }

            //accept the socker from Node1, and get the values
            socket = serverSocket.accept();
            inputStream = new ObjectInputStream(socket.getInputStream());
            numbers = (List<Integer>) inputStream.readObject();

            //save the numbers from sending from Node1
            number1 = numbers.get(0);
            number2 = numbers.get(1);
        }

        //get an output stream for this socket
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeInt(1);
        outputStream.flush();

        //instace a new client from socket on port 9622
        Socket client = new Socket("127.0.0.1", 9622);

        //add the numbers on ArrayList
        int result = number1+number2;


        //get an output stream from client (socket)
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getOutputStream());
        objectOutputStream.writeObject(result);


        objectOutputStream.close();
    }
}
