import java.util.Scanner;

public class RecieveChatThread extends Thread {


    private boolean stop = false;

    private final MessageReciever messageReciever;

    private static final int DEFAULT_SLEEP_MILLIS = 1000;

    private int sleepMillis = DEFAULT_SLEEP_MILLIS;

    private TerminateChat terminateChat = null;

    private Scanner scanner;

    public RecieveChatThread(MessageReciever messagerReciever, Scanner scanner) {
        validateMessageReciever(messagerReciever);
        this.messageReciever = messagerReciever;
        this.scanner = scanner;
    }

    private synchronized boolean running() {
        return !this.stop;
    }

    public synchronized void stopThis() {
        this.stop = true;
    }

    @Override
    public void run() {
        while (running()) {
            try {
                if (terminateChat.isStopped()) {
                    stopThis();
                    break;
                }
                if(messageReciever.getSocket()==null||messageReciever.getSocket().isClosed()){
                    terminateChat.execute();
                    messageReciever.close();
                    stopThis();
                    System.out.println("Connection lost");
                    break;
                }

                // Listening for new messages and adds them to the messageQue
                messageReciever.getMessage();

                if (!terminateChat.isStopped()) {
                    clearConsole();
                    System.out.println(messageReciever.printHeader());
                    System.out.println(messageReciever.printChat());
                    getNextLine("New Message: ");
                }
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Chat connection lost");
                messageReciever.close();
                stopThis();
            } finally {
                if (terminateChat.isStopped()) {
                    System.out.println("Chat receiver stopped");
                    getNextLine("Type 'exit' to stop chat: ");
                    messageReciever.close();
                    stopThis();
                }
            }
        }
    }

    private void getNextLine(String messageToUser) {
        System.out.print("\n");
        System.out.print(messageToUser);
    }

    /**
     * Sets the length in between every print done by run.
     *
     * @param sleepMillis the delay in milliseconds between every print to console
     */
    public void setSleepMillis(int sleepMillis) {
        this.sleepMillis = sleepMillis;
    }


    public boolean validateMessageReciever(MessageReciever messageReciever) {
        if (messageReciever == null) {
            throw new IllegalArgumentException("MessageReciever can not be null");
        }
        return true;
    }
    private void clearConsole() {
        for (int i = 0; i < 50; ++i) System.out.println();
    }

    public void setTerminateChat(TerminateChat terminateChat) {
        this.terminateChat = terminateChat;
    }
}
