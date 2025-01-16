import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageReciever {

    private final static String FAILED_SENDER = "MessageSender is not connected";

    private final static String CHAT_HEADER = "<--------Chat--------> ";

    private Socket socket;

    private BufferedReader in;

    private MessageQue messageQue;

    private MessageSender messageSender;

    private TerminateChat terminateChat = null;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");

    public MessageReciever(MessageSender messageSender, TerminateChat terminateChat) throws IOException {
        if (validateMessageSender(messageSender)) {
            in = new BufferedReader(new InputStreamReader(messageSender.getSocket().getInputStream()));
            messageQue = new MessageQue();
            this.messageSender = messageSender;
            this.socket = messageSender.getSocket();
            this.terminateChat = terminateChat;
        } else {
            System.out.println(FAILED_SENDER);
        }
    }

    /**
     * Listening for new messages and adds them to the messageQue
     * @return String
     */
    public String getMessage() {
        LocalDateTime dateTime = LocalDateTime.now();
        StringBuilder sb = new StringBuilder();
        String result = "";
        try {
            result = in.readLine();
            if (result==null||result.isEmpty()){
                lostConnectionHandler();
                return "exit";
            }

        } catch (IOException e) {
            e.getMessage();
            System.out.println("Connection lost");
            return "Connection lost";
        }
        sb.append(dtf.format(dateTime));
        sb.append(" - ");
        sb.append(result);
        messageQue.addMsg(sb.toString());
        return sb.toString();
    }

    public String printHeader() {
        return CHAT_HEADER;
    }

    public String printChat() {
        return messageQue.toString();
    }

    public boolean validateMessageSender(MessageSender messageSender) {
        if (messageSender == null || messageSender.getSocket() == null) {
            return false;
        }
        return messageSender.getSocket().isConnected();
    }

    public Socket getSocket() {
        return socket;
    }

    private void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void lostConnectionHandler(){
        terminateChat.execute();
        close();
        System.out.println("Connection lost");
    }
//        clearConsole();
//        StringBuilder sb = new StringBuilder("Connection lost");
//        close();



    private void clearConsole() {
        for (int i = 0; i < 50; ++i) System.out.println();
    }

    public void close() {
        try {
            in.close();
            messageSender.close();
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
