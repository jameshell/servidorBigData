/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigdataserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A server program which accepts requests from clients to
 * capitalize strings.  When clients connect, a new thread is
 * started to handle an interactive dialog in which the client
 * sends in a string and the server thread sends back the
 * capitalized version of the string.
 *
 * The program is runs in an infinite loop, so shutdown in platform
 * dependent.  If you ran it from a console window with the "java"
 * interpreter, Ctrl+C generally will shut it down.
 */
public class BigDataServer {

    /**
     * Application method to run the server runs in an infinite loop
     * listening on port 9898.  When a connection is requested, it
     * spawns a new thread to do the servicing and immediately returns
     * to listening.  The server keeps a unique client number for each
     * client that connects just to show interesting logging
     * messages.  It is certainly not necessary to do this.
     */
    
    public static void main(String[] args) throws Exception {
        
        System.out.println("Servidor corriendo!");
         List<Integer> listaDeDatos = new ArrayList<>();
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(9898);
        
        try {
            while (true) {
                new Capitalizer(listener.accept(), clientNumber++).start();
                if(clientNumber==4){
                    processPrimeNumbers();
                }
            }
     
            
        } finally {
            listener.close();
        }
    }
    
    
    
    public static void processPrimeNumbers(){
        
        
        System.out.println("ENVIANDO NUMEROS PRIMOS");
    }

    /**
     * A private thread to handle capitalization requests on a particular
     * socket.  The client terminates the dialogue by sending a single line
     * containing only a period.
     */
    private static class Capitalizer extends Thread {
        private Socket socket;
        private int clientNumber;

        public Capitalizer(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            log("Nueva conexion con el cliente# " + clientNumber + " en " + socket);  
        }

        /**
         * Services this thread's client by first sending the
         * client a welcome message then repeatedly reading strings
         * and sending back the capitalized version of the string.
         */
    
        
        public void run() {
            try {

                // Decorate the streams so we can send characters
                // and not just bytes.  Ensure output is flushed
                // after every newline.
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // Send a welcome message to the client.
                out.println("Hola!, Eres el cliente #" + clientNumber + ".");
                 
                int numeroAComputar=300;
                int partesPorCliente;
                double num;
                if(numeroAComputar%3==0){
                    partesPorCliente=numeroAComputar/3;
                } else{
                 num=numeroAComputar/3;
                    partesPorCliente= (int) num;
                    
                }
                
                
                String payload="Payload sin asignar...";
                int limiteInferior=0;
               
                if(clientNumber==0){
                  //  out.println("100");
                    limiteInferior=0;
                    payload=String.valueOf(partesPorCliente);
                    out.println("Este cliente realizara el proceso de numeros primos de "+limiteInferior+" a "+partesPorCliente+"!\n");
              
                }
                if(clientNumber==1){
                   // out.println("200");
                    limiteInferior=partesPorCliente;
                    int partesPorCliente2=partesPorCliente*2;
                    payload=String.valueOf(partesPorCliente2);
                    out.println("Este cliente realizara el proceso de numeros primos de "+limiteInferior+" a "+partesPorCliente2+"!\n");
     
                }
                if(clientNumber==2){
                   // out.println("300");
                    limiteInferior=partesPorCliente*2;
                    int partesPorCliente3=partesPorCliente*3;
                    payload=String.valueOf(partesPorCliente3);
                    out.println("Este cliente realizara el proceso de numeros primos de "+limiteInferior+" a "+partesPorCliente3+"!\n");
          
                }
                
                
                 List<Integer> listaDeDatos = new ArrayList<>();
            
                 


                // Get messages from the client, line by line; return them
                // capitalized
                while (true) {
                    String input = in.readLine();
                    
                    if(input.equals("askprimo")){
                        out.println(String.valueOf(limiteInferior)+" "+payload);
                        String data = in.readLine();
                        
                        listaDeDatos.add(Integer.parseInt(data));
                        System.out.println("-------- Se almacena en la lista el dato ------ ");
                        System.out.println(Arrays.toString(listaDeDatos.toArray()));
                       
                    } else if (input == null || input.equals(".")) {
                        break;
                    }
                    out.println(input.toUpperCase());
                }
            } catch (IOException e) {
                log("Error con el cliente# " + clientNumber + ": " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    log("No se pudo cerrar el socket... que raios?");
                }
                log("Connexion con cliente# " + clientNumber + " cerrado");
            }
        }

        /**
         * Logs a simple message.  In this case we just write the
         * message to the server applications standard output.
         */
        private void log(String message) {
            System.out.println(message);
        }
    }
}