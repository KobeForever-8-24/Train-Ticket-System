// --== CS400 File Header Information ==--
// Name: Deming Xu
// Email: dxu227@wisc.edu
// Team: CG
// Role: Back End Developer 1
// TA: Yeping Wang
// Lecturer: Gary Dahl
// Notes to Grader: None

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Class that handles log in authentication and register
 *
 * @author Deming Xu
 */
public class LogInAndRegister {
    private HashTableMap<String, User> userTable;       // Hash table for user

    /**
     * Password encryption. Add every char in the String by 8
     *
     * @param password
     * @return encrypted password
     */
    private String encryption(String password){
        // convert String to char array
        char[] chars = password.toCharArray();
        // for every char, add 8 to encrypt
        for(int i=0;i<chars.length;i++){
            chars[i] += 8;
        }
        return String.valueOf(chars);
    }

    /**
     * default constructor
     */
    public LogInAndRegister() {
        userTable = new HashTableMap<>();
        FileReader fileReader;      // File reader object
        String os = System.getProperty("os.name");  // get the current operation system
        String filePath = "user_info.txt";      // File path, linux' file path by default

        // If the current operation system is Windows, change the file path for windows. Same for MacOS
        if (os.toLowerCase().contains("win")) {
            filePath = "src\\user_info.txt";
        } else if (os.toLowerCase().contains("mac")) {
            filePath = "src/user_info.txt";
        }
        BufferedReader bufferedReader = null;
        try {
            // Get ready for file reader and buffered reader
            fileReader = new FileReader(filePath);
            bufferedReader = new BufferedReader(fileReader);

            // Scan file line by line
            String line = bufferedReader.readLine();
            while (line != null && !line.equals("")) {
                String[] userInfo = line.split(",");
                userTable.put(userInfo[0], new User(userInfo[0], userInfo[1], Boolean.parseBoolean(userInfo[2])));
                line = bufferedReader.readLine();
            }
        }catch (Exception ignored){
        }finally {
            try{
                if(bufferedReader!=null)
                    bufferedReader.close();
            }catch(Exception ignored){ }
        }
    }

    /**
     * log in authentication
     *
     * @param username
     * @param password
     * @return true if authentication granted, false if authencation failed
     */
    public boolean logIn(String username, String password){
        // check whether username exists in the database
        if(!isUserExist(username)){
            return false;
        }
        String encryptPasswd = encryption(password);        // encrypt the password
        // check whether the encrypted password math the one in the hashtable
        return userTable.get(username).getPasswordEncrypted().equals(encryptPasswd);
    }

    /**
     * Register helper
     *
     * @param username
     * @param password
     * @param isAdmin
     * @return true of registration success, false if username exists
     */
    public boolean addUser(String username, String password, boolean isAdmin){
        // determine whether username exists in the hash table
        if(!userTable.containsKey(username)) {
            User user = new User(username, encryption(password), isAdmin);      // create a user object
            userTable.put(username, user);          // add user object to the hashtable and return true

            String os = System.getProperty("os.name");  // get the current operation system
            String filePath = "user_info.txt";      // File path, linux' file path by default

            // If the current operation system is Windows, change the file path for windows. Same for MacOS
            if (os.toLowerCase().contains("win")) {
                filePath = "src\\user_info.txt";
            } else if (os.toLowerCase().contains("mac")) {
                filePath = "src/user_info.txt";
            }
            BufferedWriter bw = null;
            // Write the user to a binary file
            try {
                FileWriter fw = new FileWriter(filePath, true);
                bw = new BufferedWriter(fw);
                // Create a String object that contains user info
                String output = username + "," + encryption(password) + "," + isAdmin + "\n";
                // Write to external file
                bw.write(output);
            }catch (Exception ignored){}
            finally {
                try{
                    if(bw!=null)
                        bw.close();
                }catch(Exception ignored){ }
            }
            return true;
        }else{
            return false;
        }
    }

    /**
     * check whether username exists
     *
     * @param username
     * @return true if username exists, false if not
     */
    public boolean isUserExist(String username){
        return userTable.containsKey(username);
    }

    /**
     * check whether user is an administrator
     *
     * @param username
     * @return true if the user is admin, false if not
     */
    public boolean isAdmin(String username){
        return userTable.get(username).isAdmin();
    }

    /**
     * clear the user database
     */
    public void clear(){
        userTable = new HashTableMap<>();

        String os = System.getProperty("os.name");  // get the current operation system
        String filePath = "user_info.txt";      // File path, linux' file path by default

        // If the current operation system is Windows, change the file path for windows. Same for MacOS
        if (os.toLowerCase().contains("win")) {
            filePath = "src\\user_info.txt";
        } else if (os.toLowerCase().contains("mac")) {
            filePath = "src/user_info.txt";
        }
        BufferedWriter bw = null;
        // Write the user to a binary file
        try {
            FileWriter fw = new FileWriter(filePath);
            bw = new BufferedWriter(fw);
            // Create a String object that contains ""
            String output = "";
            // Write to external file
            bw.write(output);
        }catch (Exception ignored){}
        finally {
            try{
                if(bw!=null)
                    bw.close();
            }catch(Exception ignored){ }
        }
    }
}
