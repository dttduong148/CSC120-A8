import java.util.ArrayList;
import java.lang.RuntimeException;

public class Pokemon implements Contract
{
    private String skin = "Chansey";
    private ArrayList<String> inventory;
    private static int cordX = 0;
    private static int cordY = 0;
    private static int speed = 10;
    private ArrayList<String> history;
    private String direction = "";

    private Pokemon(String name) {
        this.inventory = new ArrayList<String>();
        this.history = new ArrayList<String>();

    } 

    /**
     * Capture a Pokemon or a skin item.
     * @param item name of a Pokemon or a skin item 
     */
    public void grab(String item) {
        if (this.inventory.contains(item)) {
            throw new RuntimeException(item + " is already in your inventory.");
        }
        this.inventory.add(item);
        System.out.println("Congratulations! You have captured a " + item + "!");
        this.history.add("grab");
    };


    /**
     * Release a captured Pokemon.
     * @param item name of a Pokemon
     * @return name of item
     */
    public String drop(String item) {
        if (!this.inventory.contains(item)) {
            throw new RuntimeException(item + " is not in your inventory.");
        }
        this.inventory.remove(item);
        System.out.println("You have released a " + item + ".");
        this.history.add("drop");
        return item;
    };


    /**
     * Examine if player has captured a Pokemon or a skin item before.
     * @param item name of a Pokemon or a skin item 
     */
    public void examine(String item) {
        if (this.inventory.contains(item)) {
            System.out.println("You have captured a " + item + "in your inventory.");
        }
       System.out.println("You have not captured any " + item + ".");
       this.history.add("examine");
    };


    /**
     * Change player's skin.
     * @param item name of a skin item 
     */
    public void use(String item) {
        if (!this.inventory.contains(item)) {
            throw new RuntimeException(item + " is not in your inventory.");
        };
        this.skin = item;
        System.out.println("You are now a" + this.skin + "!");
        history.add("examine");
    };

    /**
     * Move player's character. Return true if player moved forward. Return false if player moved backward.
     * @param direction "a" for backward, "d" for forward
     */
    public boolean walk(String direction) {
        this.direction = direction;
        this.history.add("walk");
        if (this.direction.equals("a")) {
            cordX -= speed;
            System.out.println("You are at coordinates: (" + cordX +", " + cordY + ")");
            return true;
        }
        if (this.direction.equals("d")) {
            cordX += speed;
            System.out.println("You are at coordinates: (" + cordX +", " + cordY + ")");
            return false;
        }
        return false;
    };

    
    /**
     * Move player to specific coordinates, i.e. fly. Return false if coordinate is out of range.
     * @param x x coordinate
     * @param y y coordinate 
     */
    public boolean fly(int x, int y) {
        cordX = x;
        cordY = y;
        System.out.println("WEEEEEEEEE");
        cordY = 0;
        System.out.println("Landed!");
        System.out.println("You are at coordinates: (" + cordX +", " + cordY + ")");
        this.history.add("fly");
        return (-500 < cordX && cordX < 500 && -500 < cordY && cordY < 500);
    };

    /**
     * Shrink player. Move half as fast each time method is called. 
     * @return current speed
     */
    public Number shrink() {
        speed /= 2;
        if (speed < 1) {
            throw new RuntimeException("Cannot shrink any further.");
        }
        this.history.add("shrink");
        return speed;
    };

    /**
     * Grow player. Move twice as fast each time method is called. 
     * @return current speed
     */
    public Number grow() {
        speed *= 2;
        if (speed > 50) {
            throw new RuntimeException("Cannot grow any further.");
        }
        this.history.add("grow");
        return speed;
    };

    /**
     * Pause game.
     */
    public void rest() {
        cordY = 0;
        System.out.println("You are at coordinates: (" + cordX +", " + cordY + ")");
        speed = 0;
        System.out.println("Game paused.");

    };

    /**
    * Undo previous action.
    */
    public void undo() {
        speed = 0;
        int i = this.history.size();
        if (this.history.get(i - 1).equals("grab")) {
            inventory.remove(inventory.size() - 1);
            this.history.remove(history.get(i - 1));
        }

        if (this.history.get(i - 1).equals("drop")) {
            this.history.remove(history.get(i - 1));
            throw new RuntimeException("Cannot undo selling an item.");
            
        }

        if (this.history.get(i - 1).equals("examine")) {
            this.history.remove(history.get(i - 1));
            throw new RuntimeException("Cannot undo examining an item.");
        }

        if (this.history.get(i - 1).equals("use")) {
            this.history.remove(history.get(i - 1));
            this.skin = "Chansey";
            System.out.println("You're using the default skin: " + this.skin + ".");
        }

        if (this.history.get(i - 1).equals("walk")) {
            if (this.direction.equals("a")) {
                cordX += speed;
            }
            if (this.direction.equals("d")) {
                cordX -= speed;
            }
            this.history.remove(history.get(i - 1));
        }

        if (this.history.get(i - 1).equals("fly")) {
            this.history.remove(history.get(i - 1));
            throw new RuntimeException("Cannot undo flying. You are already on the ground.");
        }

        if (this.history.get(i - 1).equals("shrink")) {
            this.history.remove(history.get(i - 1));
            speed *= 2;
            if (speed > 50) {
                throw new RuntimeException("Action invalid.");
            }
        }

        if (this.history.get(i - 1).equals("grow")) {
            this.history.remove(history.get(i - 1));
            speed /= 2;
            if (speed < 1) {
                throw new RuntimeException("Action invalid.");
            }

        }
    };

    public static void main(String[] args) {
        Pokemon angela = new Pokemon("Angela");

        angela.walk("d");
        angela.walk("d");
        angela.walk("a");
        angela.fly(50, 50);
        angela.examine("Mewtwo");
        angela.grab("skirt");

        angela.grow();
        angela.grow();
        angela.grow();



    }
    
}
