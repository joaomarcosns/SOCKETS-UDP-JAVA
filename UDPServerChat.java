import java.util.Scanner;
import java.net.*;
import java.io.*;

public class UDPServerChat {
    public static void main(String[] args){
        String nickname;
        int portaCliente;
        String msg;
        DatagramSocket aSocket = null;

        Scanner scanner = new Scanner(System.in, "CP850");
        System.out.println("Iniciando chat...");
        System.out.print("Digite seu nickname: ");
        nickname = scanner.nextLine();
        System.out.println("Bem vindo "+ nickname +", vamos iniciar o chat.");
        System.out.printf("Observação: para finalisar o chat digite -1\n");

        try {
            aSocket = new DatagramSocket(6789);
            while(true) {
                byte[] buffer = new byte[65507];

                // Receber
                DatagramPacket receber = new DatagramPacket(buffer, buffer.length);
                System.out.println("Aguardando mensagem");
                aSocket.receive(receber);
                String data = new String(receber.getData(), 0, receber.getLength());

                //Porta 
                portaCliente = receber.getPort();
                InetAddress direcao = receber.getAddress();

                if(data.equals("-1")) {
                    System.out.println("Seu amigo(a) finalizou o chat....");
                    aSocket.close();
                    break;
                }else {
                    System.out.println("Mensagem recebida: " + data);

                    // Mandar
                    System.out.print("Digite sua mensagem: ");
                    msg = scanner.nextLine();
                    DatagramPacket manda = new DatagramPacket(msg.getBytes(), msg.length(), direcao, portaCliente);
                    aSocket.send(manda);
                    if(msg.equals("-1")) {
                        System.out.println("Finalizando chat...");
                        aSocket.close();
                        break;
                    }
                }
            }
        } catch(SocketException e) {
            System.out.println("Socket " + e.getMessage());
        } catch(IOException e) {
            System.out.println("IO " + e.getMessage());
        } finally {
            if(aSocket == null) {
                System.out.println("Erro");
            }
        }

    }
}