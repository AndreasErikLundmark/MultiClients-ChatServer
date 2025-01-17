import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Scanner;

public class SendChatThread extends Thread {

    private boolean stop = false;

    private final MessageSender messageSender;

    private static final int DEFAULT_SLEEP_MILLIS = 10;

    private final int sleepMillis = DEFAULT_SLEEP_MILLIS;

    private String message = null;

    private boolean sendMessage = false;

    private TerminateChat terminateChat = null;

    private final Scanner scanner;

    public SendChatThread(MessageSender messageSender, Scanner scanner) {
        validateMessageSender(messageSender);
        this.messageSender = messageSender;
        this.scanner = scanner;
    }

    private synchronized boolean running() {
        return !this.stop;
    }

    public void stopThis() {


        messageSender.close();
        this.stop = true;
        scanner.close();
    }

    @Override
    public void run() {
        System.out.print("New Message: ");
        while (running()) {
            try {
                String messageNew = "";
                if(terminateChat.isStopped()){

                }
//                System.out.print("New Message: ");
                messageNew = scanner.nextLine();
                clearConsole();/////////////////////////////////

                if (messageNew.equals("exit")) {
                    terminateChat.execute();
                    messageSender.close();
                    stopThis();
                    System.out.println("Chat end");
                    break;
                }
                if (messageSender.getSocket() == null || !messageSender.getSocket().isConnected()) {
                    terminateChat.execute();
                    messageSender.close();
                    stopThis();
                    System.out.println("Connection lost");
                }
                setMessage(messageSender.getAuthor() + messageNew);
                setSendMessage(true);

                if (sendMessage) {
                    if (validateMessage(message)) {
                        messageSender.sendMessage(message);
                        Thread.sleep(sleepMillis);
                        setMessage(null);
                        setSendMessage(false);

                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Could not send message");
            } finally {
                if (terminateChat.isStopped()) {
                    stopThis();
                }
            }
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private boolean validateMessage(String message) {
        if (message == null || message.isEmpty()) {
            setMessage("Message can not be empty");
            return false;
        }
        return true;
    }

    public void setSendMessage(boolean sendMessage) {
        this.sendMessage = sendMessage;
    }

    public void setTerminateChat(TerminateChat terminateChat) {
        this.terminateChat = terminateChat;
    }

    private boolean validateMessageSender(MessageSender messageSender) {
        if (messageSender == null) {
            throw new IllegalArgumentException("MessageSender can not be null");
        }
        return true;
    }

    public static void clearConsole() {
        for (int i = 0; i < 50; ++i) System.out.println();
    }
}
