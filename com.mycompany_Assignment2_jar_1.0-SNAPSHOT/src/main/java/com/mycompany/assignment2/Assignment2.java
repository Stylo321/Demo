


package com.mycompany.assignment2;

import java.io.File;
import javax.swing.JOptionPane;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Random;



public class Assignment2 {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellNumber;
    
        private static final String FILE_PATH = "messages.json";
    private static int totalMessages = 0;
    private static JSONArray sentMessages = new JSONArray();

    public String registerUser() {
        // First Name
        do {
            firstName = JOptionPane.showInputDialog("name:");
        } while (firstName == null || firstName.trim().isEmpty());

        // Last Name
        do {
            lastName = JOptionPane.showInputDialog("surname:");
        } while (lastName == null || lastName.trim().isEmpty());

        // Username with validation
        do {
            username = JOptionPane.showInputDialog("username:");
            if (!checkUserName()) {
                JOptionPane.showMessageDialog(null, "❌ Username is not correctly formatted.\n It must be 5 characters or less with an underscore.");
            }
        } while (!checkUserName());

        // Password with validation
        do {
            password = JOptionPane.showInputDialog("password:");
            if (!checkPasswordComplexity()) {
                JOptionPane.showMessageDialog(null, "❌ Password is not correctly formatted.\n (Min 8 characters with a capital letter, a number, and a special character.");
            }
        } while (!checkPasswordComplexity());

        // Cell number with validation
        do {
            cellNumber = JOptionPane.showInputDialog("Enter your cell number:");
            if (!checkCellPhoneNumber()) { 
                JOptionPane.showMessageDialog(null, "❌ Cell number is not correctly formatted.\nIt must start with +27 and be no more than 10 digits after that.");
            }
        } while (!checkCellPhoneNumber());

// After phone number validation
boolean otpVerified = verifyOTP(cellNumber);
while (!otpVerified) {
    cellNumber = JOptionPane.showInputDialog("Enter a new phone number (e.g. +2783123456):");
    if (!checkRecipientCell(cellNumber)) {
        JOptionPane.showMessageDialog(null, "❌ Invalid number. Must start with +27 and be ≤ 13 chars.");
        continue;
    }
    otpVerified = verifyOTP(cellNumber);
}


return "✅ User has been registered successfully!";

    }

    public boolean checkUserName() {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity() {
        if (password == null) return false;

        boolean length = password.length() >= 8;
        boolean capital = password.matches(".*[A-Z].*");
        boolean number = password.matches(".*\\d.*");
        boolean special = password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");

        return length && capital && number && special;
    }

    public boolean checkCellPhoneNumber() {
        return cellNumber != null && cellNumber.startsWith("+27") && cellNumber.length() <= 13;
    }

    public boolean loginUser(String inputUsername, String inputPassword) {
        return inputUsername.equals(username) && inputPassword.equals(password);
    }

    public String returnLoginStatus(boolean isLoginSuccessful) {
        if (isLoginSuccessful) {
            return "✅ Welcome " + firstName + " " + lastName + ", it is great to see you again!";
        } else {
            return "❌ Username or password is incorrect.";
        }
    }

    // MAIN METHOD TO RUN EVERYTHING
    public static void main(String[] args) {

    
    
        Assignment2 login = new Assignment2();
        JOptionPane.showMessageDialog(null, login.registerUser());

        boolean loggedIn = false;

        while (!loggedIn) {
            String inputUsername = JOptionPane.showInputDialog("Login - Enter your username:");
            String inputPassword = JOptionPane.showInputDialog("Login - Enter your password:");

            boolean loginResult = login.loginUser(inputUsername, inputPassword);
            JOptionPane.showMessageDialog(null, login.returnLoginStatus(loginResult));

            if (loginResult) {
                loggedIn = true; // Exit the loop
            } else {
                int retry = JOptionPane.showConfirmDialog(null,
                        "Do you want to try logging in again?",
                        "Login Failed",
                        JOptionPane.YES_NO_OPTION);

                if (retry == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(null, "👋 Exiting application. Goodbye!");
                    System.exit(0);
                }
            }
        }
     //Part ********************************************************************************

                int maxMessages = 0;
    while (true) {
        String input = JOptionPane.showInputDialog("How many messages do you want to enter?");
        try {
            maxMessages = Integer.parseInt(input);
            if (maxMessages > 0) break;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "❌ Please enter a valid number greater than 0.");
        }
    }

    int messageCount = 0;
        loadMessagesFromFile();

while (true) {
    String menu = "📋 Please select an option:\n";
    menu += "1. Send a message" + (messageCount >= maxMessages ? " (Limit reached)" : "") + "\n";
    menu += "2. Show recently sent messages (Coming Soon)\n";
    menu += "3. Quit";

    String option = JOptionPane.showInputDialog(menu);

    if (option == null) break;

    switch (option) {
        case "1":
            if (messageCount >= maxMessages) {
                JOptionPane.showMessageDialog(null, "⚠️ You have reached your message limit (" + maxMessages + ").");
            } else {
                sendMessage();
                messageCount++;
            }
            break;
        case "2":
            JOptionPane.showMessageDialog(null, "not available");
            break;
        case "3":
            JOptionPane.showMessageDialog(null, "👋 Goodbye!" );
            System.exit(0);
            break;
        default:
            JOptionPane.showMessageDialog(null, "❌ Invalid option. Please choose 1, 2 or 3.");
    }
}



        JOptionPane.showMessageDialog(null, printMessages() + "\n\nTotal messages sent/stored: " + returnTotalMessages());
    }

    public static void sendMessage() {
        String messageID;
        do {
            messageID = JOptionPane.showInputDialog("Enter Message ID (max 10 chars):");
            if (!checkMessageID(messageID)) {
                JOptionPane.showMessageDialog(null, "❌ Message ID must not exceed 10 characters.");
            }
        } while (!checkMessageID(messageID));

        String recipient;
        do {
            recipient = JOptionPane.showInputDialog("Enter recipient number (must start with +27 and 13 chars max):");
            if (!checkRecipientCell(recipient)) {
                JOptionPane.showMessageDialog(null, "❌ Invalid recipient number. It must start with +27 and be max 13 characters.");
            }
        } while (!checkRecipientCell(recipient));

        String messageText = JOptionPane.showInputDialog("Enter your message:");
        String[] options = {"Send", "Store", "Discard"};
int choice = JOptionPane.showOptionDialog(
    null,
    "What would you like to do with the message?",
    "Choose Action",
    JOptionPane.DEFAULT_OPTION,
    JOptionPane.INFORMATION_MESSAGE,
    null,
    options,
    options[0]
);


        if (choice == 2) { // Discard
    JOptionPane.showMessageDialog(null, "🚫 Message discarded.");
}else {
            totalMessages++;
            String messageHash = createMessageHash(messageID, totalMessages, messageText);
            int encryptionKey = 5;
            String encryptedMsg = encrypt(messageText, encryptionKey);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = dtf.format(LocalDateTime.now());

            JSONObject messageObj = new JSONObject();
            messageObj.put("messageID", messageID);
            messageObj.put("recipient", recipient);
            messageObj.put("message", encryptedMsg);
            messageObj.put("encrypted", true);
            messageObj.put("hash", messageHash);
            messageObj.put("timestamp", timestamp);

            sentMessages.put(messageObj);

    if (choice == 0) {
        JOptionPane.showMessageDialog(null, "✅ Message sent!\nHash: " + messageHash);
    } else if (choice == 1) {
        saveMessagesToFile();
        JOptionPane.showMessageDialog(null, "📥 Message stored!\nHash: " + messageHash);
    }
}
    }
public static boolean verifyOTP(String cellNumber) {
    Random rand = new Random();

    while (true) {
        int otp = 10000 + rand.nextInt(90000); // Generate new OTP
        JOptionPane.showMessageDialog(null, "📨 OTP sent to: " + cellNumber + "\n(For testing: " + otp + ")");

        for (int i = 0; i < 3; i++) {
            String input = JOptionPane.showInputDialog("Enter the 5-digit OTP sent to " + cellNumber + ":");

            if (input == null) return false; // Cancelled
            if (input.equals(String.valueOf(otp))) return true;

            JOptionPane.showMessageDialog(null, "❌ Incorrect OTP. Attempts left: " + (2 - i));
        }

        // After 3 failed attempts, offer options
        String[] options = {"Exit App", "Resend OTP", "Change Number"};
        int choice = JOptionPane.showOptionDialog(
            null,
            "You entered the wrong OTP 3 times.\nWhat would you like to do?",
            "OTP Verification Failed",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.WARNING_MESSAGE,
            null,
            options,
            options[1]
        );

        if (choice == 0) { // Exit App
            JOptionPane.showMessageDialog(null, "👋 Exiting the application.");
            System.exit(0);
        } else if (choice == 2) { // Change number
            return false; // Signal that we need to re-enter phone number
        }
        // else choice == 1 → resend OTP loop restarts
    }

}


    public static boolean checkMessageID(String id) {
        return id != null && id.length() <= 10;
    }

    public static boolean checkRecipientCell(String cell) {
        return cell != null && cell.startsWith("+27") && cell.length() <= 13;
    }

    public static String createMessageHash(String id, int msgNum, String message) {
        String[] words = message.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
        String idStart = id.length() >= 2 ? id.substring(0, 2) : id;
        return (idStart + ":" + msgNum + ":" + firstWord + lastWord).toUpperCase();
    }

    public static void saveMessagesToFile() {
        JSONObject data = new JSONObject();
        data.put("messages", sentMessages);
        data.put("totalMessages", totalMessages);

        try (FileWriter file = new FileWriter(FILE_PATH)) {
            file.write(data.toString(4));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving messages: " + e.getMessage());
        }
    }

    public static void loadMessagesFromFile() {


        File file = new File(FILE_PATH);

        if (file.exists()) {
            try {
                String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
                JSONObject data = new JSONObject(content);
                sentMessages = data.getJSONArray("messages");
                totalMessages = data.getInt("totalMessages");
            } catch (IOException | JSONException e) {
                JOptionPane.showMessageDialog(null, "⚠️ Could not load previous messages.\nStarting fresh.");
                sentMessages = new JSONArray();
                totalMessages = 0;
            }
        }
    }

    public static String encrypt(String message, int key) {
        StringBuilder encrypted = new StringBuilder();
        for (char c : message.toCharArray()) {
            encrypted.append((char)(c ^ key));
        }
        return encrypted.toString();
    }

    public static String decrypt(String encrypted, int key) {
        return encrypt(encrypted, key); // XOR twice = original
    }

    public static String printMessages() {
        StringBuilder sb = new StringBuilder("📬 Sent Messages:\n");
        for (int i = 0; i < sentMessages.length(); i++) {
            JSONObject msg = sentMessages.getJSONObject(i);
            String msgText = msg.optBoolean("encrypted", false)
                ? decrypt(msg.getString("message"), 5)
                : msg.getString("message");

            sb.append((i + 1) + ". ID: " + msg.getString("messageID")
                    + ", Recipient: " + msg.getString("recipient")
                    + ", Hash: " + msg.getString("hash")
                    + ", Time: " + msg.getString("timestamp")
                    + ", Message: " + msgText + "\n");
        }
        return sb.toString();
    }

    public static int returnTotalMessages() {
        return totalMessages;
    }
}
