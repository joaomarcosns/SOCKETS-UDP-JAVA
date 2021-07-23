import java.util.Scanner;
import java.net.*;
import java.io.*;

public class UDPClientChat {
    public static void main(String[] args) throws Exception {
        String nickname;
        Scanner scanner = new Scanner(System.in);
        String msg;
        DatagramSocket aSocket = null;

        System.out.println("Iniciando chat...");
        System.out.print("Digite seu nickname: ");
        nickname = scanner.nextLine();
        System.out.println("Bem vindo "+ nickname +", vamos iniciar o chat.");
        System.out.println("Observação: para finalisar o chat digite -1");

        
        final int porta = 6789;
        try{
            aSocket = new DatagramSocket();


            while(true) {
                byte[] buffer = new byte[65507];
                
                // Mandar
                InetAddress direcao = InetAddress.getByName("localhost");
                System.out.print("Digite a sua menssagem: ");
                msg = scanner.nextLine();

                DatagramPacket mandar = new DatagramPacket(msg.getBytes(), msg.length(), direcao, porta);

                aSocket.send(mandar);
                
                if(msg.equals("-1")) {
                    System.out.println("Fim do chat...");
                    aSocket.close();
                    break;
                }else{
                    System.out.println("Aguardando mensagem");

                    // Receber
                    DatagramPacket receber = new DatagramPacket(buffer, buffer.length);
                    aSocket.receive(receber);
                    String mensagem = new String(receber.getData(), 0, receber.getLength());

                    if(mensagem.equals("-1")) {
                        System.out.println("Seu amigo(a) finalizou o chat....");
                        aSocket.close();
                        break;
                    }else{
                        System.out.println("Mensagem recebida: " + mensagem);
                    }
                } 
            }
        }catch(SocketException e) {
            System.out.println("Socket " + e.getMessage());
        }catch(IOException e) {
            System.out.println("IO " + e.getMessage());
        }finally {
            if(aSocket == null) {
                System.out.println("Erro");
            }
        }
    }
}