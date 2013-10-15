package org.saJava.preferences;

import java.util.prefs.Preferences;

/**
 * Created with IntelliJ IDEA.
 * User: Goldberg
 * Date: 2/28/13
 * Time: 12:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserSettings {
    public Preferences prefs;
    public String pUname="uname",pPasswd="passwd",pCheckBox="check",pSession="session";

    public  void setPreference() {
        // This will define a node in which the preferences can be stored
        prefs = Preferences.userRoot().node(this.getClass().getName());

    }
}
