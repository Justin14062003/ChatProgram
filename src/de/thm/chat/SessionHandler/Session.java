package de.thm.chat.SessionHandler;

/**
 * Session handler for Chatprogramm
 * @author Justin Ulbert
 */
public class Session
{
    /* username */
    private String username;

    /* password */
    private String password;

    /* session */
    private boolean sessionActive;

    public Session(String username, String password)
    {
        this.username = username;
        this.password = password;
        this.sessionActive = true;

    }

    /**
     * Closes the current session
     * Sets all variables to null
     */
    public void CloseSession()
    {
        this.username = null;
        this.password = null;
        sessionActive = false;

        System.out.println("Erfolgreich abgemeldet!");
    }

    /**
     @return the username from the current session
     */
    public String getSessionUserName() {
        return this.username;
    }

    /**
     @return the password from the current session
     */
    public String getSessionPassword()
    {
        return this.password;
    }

    public boolean isSessionActive() {return sessionActive;}
}
