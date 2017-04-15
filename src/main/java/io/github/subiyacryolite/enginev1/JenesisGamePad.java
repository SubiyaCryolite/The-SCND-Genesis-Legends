package io.github.subiyacryolite.enginev1;
/**
 * Originally from JenesisGamePad.java
 * Andrew Davison, October 2006, ad@fivedots.coe.psu.ac.th
 * <p>
 * This controller supports a game pad with two
 * analog sticks with axes (x,y) and (z,rz), 12 buttons, a
 * D-Pad acting as a point-of-view (POV) hat, and a
 * single rumbler.
 * <p>
 * The sticks are assumed to be absolute and analog, while the
 * hat and buttons are absolute and digital.
 * The sticks and hat data are accessed as compass directions
 * (e.g. NW, NORTH). The compass constants are public so they can be
 * used in the rest of the application.
 * <p>
 * The buttons values (booleans) can be accessed individually, or
 * together in an array.
 * <p>
 * The rumbler can be switched on/off, and its current status retrieved.
 */

import com.scndgen.legends.LoginScreen;
import net.java.games.input.Component;
import net.java.games.input.Component.POV;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Rumbler;

public class JenesisGamePad {
    public static final int NUM_BUTTONS = 12;
    public static final int NUM_COMPASS_DIRS = 9;
    public static final int NW = 0;
    public static final int NORTH = 1;
    public static final int NE = 2;
    public static final int WEST = 3;
    public static final int NONE = 4;   // default value
    public static final int EAST = 5;
    public static final int SW = 6;
    public static final int SOUTH = 7;
    public static final int SE = 8;
    private static JenesisGamePad instance;
    public boolean rumblerOn = false, controllerFound = false;   // whether rumbler is on or off
    public int statusInt = 0;
    public String controllerName = "Basic";
    private int xAxis, yAxis, zAxis, rzAxis;
    private int povId;         // index for the POV hat
    private Controller controller;
    private Component[] components;  // holds the components
    private Rumbler[] rumblers;
    private int rumblerIdx;      // index for the rumbler being used
    private int buttons[];   // indices for the buttons
    private ControllerEnvironment controllerEnvironment;
    private Controller[] controllers;

    private JenesisGamePad() {
        try {
            if (LoginScreen.getInstance().controller) {
                controllerEnvironment = ControllerEnvironment.getDefaultEnvironment();
                controllers = controllerEnvironment.getControllers();
                if (controllers.length == 0) {
                    statusInt = 0;
                    controllerFound = false;
                } else {
                    controllerFound = true;
                    statusInt = 1;
                    System.out.printf("Num. controllers: %s\n", controllers.length);
                }
                if (controllerFound) {
                    controller = findGamePad(controllers);
                    controllerName = controller.getName();
                    System.out.printf("Game controller: %s, %s\n", controllerName, controller.getType());
                    findCompIndices(controller);
                    findRumblers(controller);
                }
            }
        } catch (Exception e) {
            //System.out.println("No JInput man");
            //statusInt=2;
            controllerFound = false;
        }
    } // end of JenesisGamePad()

    public static synchronized JenesisGamePad getInstance() {
        if (instance == null)
            instance = new JenesisGamePad();
        return instance;
    }

    /**
     * Search the array of controllers until a suitable game pad
     * controller is found (either of type GAMEPAD or STICK).
     *
     * @author Andrew Davison
     */
    private Controller findGamePad(Controller[] controllers) {
        Controller.Type type;
        int i = 0;
        while (i < controllers.length) {
            type = controllers[i].getType();
            if ((type == Controller.Type.GAMEPAD) || (type == Controller.Type.STICK)) {
                break;
            }
            i++;
        }
        if (i == controllers.length) {
            System.out.println("No game pad found");
            //System.exit(0);
        } else {
            System.out.println("Game pad index: " + i);
        }
        return controllers[i];
    }

    /**
     * Store the indices for the analogue sticks axes
     * (x,y) and (z,rz), POV hat, and
     * button components of the controller.
     *
     * @author Andrew Davison
     */
    private void findCompIndices(Controller controller) {
        components = controller.getComponents();
        if (components.length == 0) {
            System.out.println("No Components found");
        } else {
            System.out.println("Num. Components: " + components.length);
        }
        xAxis = findCompIndex(components, Component.Identifier.Axis.X, "x-axis");
        yAxis = findCompIndex(components, Component.Identifier.Axis.Y, "y-axis");
        zAxis = findCompIndex(components, Component.Identifier.Axis.Z, "z-axis");
        rzAxis = findCompIndex(components, Component.Identifier.Axis.RZ, "rz-axis");
        povId = findCompIndex(components, Component.Identifier.Axis.POV, "POV hat");
        findButtons(components);
    }

    /**
     * Search through components[] for id, returning the corresponding
     * array index, or -1
     *
     * @author Andrew Davison
     */
    private int findCompIndex(Component[] comps, Component.Identifier id, String nm) {
        Component c;
        for (int i = 0; i < comps.length; i++) {
            c = comps[i];
            if ((c.getIdentifier() == id) && !c.isRelative()) {
                System.out.println("Found " + c.getName() + "; index: " + i);
                return i;
            }
        }
        System.out.println("No " + nm + " component found");
        return -1;
    }

    /**
     * Search through components[] for NUM_BUTTONS buttons, storing
     * their indices in buttons[]. Ignore excessive buttons.
     * If there aren't enough buttons, then fill the empty spots in
     * buttons[] with -1's.
     */
    private void findButtons(Component[] components) {
        buttons = new int[NUM_BUTTONS];
        int numButtons = 0;
        Component component;
        for (int i = 0; i < components.length; i++) {
            component = components[i];
            if (isButton(component)) {    // deal with a button
                if (numButtons == NUM_BUTTONS) // already enough buttons
                {
                    System.out.println("Found an extra button; index: " + i + ". Ignoring it");
                } else {
                    buttons[numButtons] = i;  // store button index
                    System.out.println("Found " + component.getName() + "; index: " + i);
                    numButtons++;
                }
            }
        }

        // fill empty spots in buttons[] with -1's
        if (numButtons < NUM_BUTTONS) {
            System.out.println("Too few buttons (" + numButtons + "); expecting " + NUM_BUTTONS);
            while (numButtons < NUM_BUTTONS) {
                buttons[numButtons] = -1;
                numButtons++;
            }
        }
    }  // end of findButtons()

    /**
     * Return true if the component is a digital/absolute button, and
     * its identifier name ends with "Button" (i.e. the
     * identifier class is Component.Identifier.Button).
     */
    private boolean isButton(Component c) {
        if (!c.isAnalog() && !c.isRelative()) {    // digital and absolute
            String className = c.getIdentifier().getClass().getName();
            // System.out.println(c.getName() + " identifier: " + className);
            if (className.endsWith("Button")) {
                return true;
            }
        }
        return false;
    }  // end of isButton()

    /**
     * Find the rumblers. Use the last rumbler for making vibrations,
     * an arbitrary decision.
     */
    private void findRumblers(Controller controller) {
        // get the game pad's rumblers
        rumblers = controller.getRumblers();
        if (rumblers.length == 0) {
            System.out.println("No Rumblers found");
            rumblerIdx = -1;
        } else {
            System.out.println("Rumblers found: " + rumblers.length);
            rumblerIdx = rumblers.length - 1;    // use last rumbler
        }
    }  // end of findRumblers()

    // ----------------- polling and getting data ------------------

    /**
     * update the component values in the controller
     */
    public void poll() {
        if (controller != null)
            controller.poll();
    }

    /**
     * Return the (x,y) analog stick compass direction
     *
     * @return
     */
    public int getXYStickDir() {
        if ((xAxis == -1) || (yAxis == -1)) {
            System.out.println("(x,y) axis data unavailable");
            return NONE;
        } else {
            return getCompassDir(xAxis, yAxis);
        }
    } // end of getXYStickDir()

    public int getZRZStickDir() // return the (z,rz) analog stick compass direction
    {
        if ((zAxis == -1) || (rzAxis == -1)) {
            System.out.println("(z,rz) axis data unavailable");
            return NONE;
        } else {
            return getCompassDir(zAxis, rzAxis);
        }
    } // end of getXYStickDir()

    private int getCompassDir(int xA, int yA) // Return the axes as a single compass value
    {
        float xCoord = components[xA].getPollData();
        float yCoord = components[yA].getPollData();
        // System.out.println("(x,y): (" + xCoord + "," + yCoord + ")");

        int xc = Math.round(xCoord);
        int yc = Math.round(yCoord);
        // System.out.println("Rounded (x,y): (" + xc + "," + yc + ")");
        if ((yc == -1) && (xc == -1)) // (y,x)
        {
            return NW;
        } else if ((yc == -1) && (xc == 0)) {
            return NORTH;
        } else if ((yc == -1) && (xc == 1)) {
            return NE;
        } else if ((yc == 0) && (xc == -1)) {
            return WEST;
        } else if ((yc == 0) && (xc == 0)) {
            return NONE;
        } else if ((yc == 0) && (xc == 1)) {
            return EAST;
        } else if ((yc == 1) && (xc == -1)) {
            return SW;
        } else if ((yc == 1) && (xc == 0)) {
            return SOUTH;
        } else if ((yc == 1) && (xc == 1)) {
            return SE;
        } else {
            System.out.println("Unknown (x,y): (" + xc + "," + yc + ")");
            return NONE;
        }
    }  // end of getCompassDir()

    public int getHatDir() // Return the POV hat's direction as a compass direction
    {
        if (povId == -1) {
            System.out.println("POV hat data unavailable");
            return NONE;
        } else {
            float povDir = components[povId].getPollData();
            if (povDir == POV.CENTER) //	0.0f
            {
                return NONE;
            } else if (povDir == POV.DOWN) // 0.75f
            {
                return SOUTH;
            } else if (povDir == POV.DOWN_LEFT) // 0.875f
            {
                return SW;
            } else if (povDir == POV.DOWN_RIGHT) // 0.625f
            {
                return SE;
            } else if (povDir == POV.LEFT) // 1.0f
            {
                return WEST;
            } else if (povDir == POV.RIGHT) // 0.5f
            {
                return EAST;
            } else if (povDir == POV.UP) // 0.25f
            {
                return NORTH;
            } else if (povDir == POV.UP_LEFT) // 0.125f
            {
                return NW;
            } else if (povDir == POV.UP_RIGHT) // 0.375f
            {
                return NE;
            } else { // assume center
                System.out.println("POV hat value out of range: " + povDir);
                return NONE;
            }
        }
    }  // end of getHatDir()

    public boolean[] getButtons() /* Return all the buttons in a single array. Each button value is
    a boolean. */ {
        boolean[] buttons = new boolean[NUM_BUTTONS];
        float value;
        for (int i = 0; i < NUM_BUTTONS; i++) {
            value = components[this.buttons[i]].getPollData();
            buttons[i] = ((value == 0.0f) ? false : true);
        }
        return buttons;
    }  // end of getButtons()

    public boolean isButtonPressed(int pos) /* Return the button value (a boolean) for button number 'pos'.
    pos is in the range 1-NUM_BUTTONS to match the game pad
    button labels.
     */ {
        if ((pos < 1) || (pos > NUM_BUTTONS)) {
            System.out.println("Button position out of range (1-"
                    + NUM_BUTTONS + "): " + pos);
            return false;
        }

        if (buttons[pos - 1] == -1) // no button found at that pos
        {
            return false;
        }

        float value = components[buttons[pos - 1]].getPollData();
        // array range is 0-NUM_BUTTONS-1
        return ((value == 0.0f) ? false : true);
    } // end of isButtonPressed()

    // ------------------- trigger a rumbler -------------------
    public void setRumbler(boolean switchOn, float power) // turn the rumbler on or off
    {
        if (controllerFound) {
            if (rumblerIdx != -1) {
                if (switchOn) {
                    rumblers[rumblerIdx].rumble(power);  // almost full on for last rumbler
                } else // switch off
                {
                    rumblers[rumblerIdx].rumble(power);
                }
                rumblerOn = switchOn;    // record rumbler's new status
            }
        }
    }  // end of setRumbler()

    public boolean isRumblerOn() {
        return rumblerOn;
    }
} // end of JenesisGamePad class

