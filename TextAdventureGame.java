import java.util.Scanner;

public class TextAdventureGame {

    private static Scanner scanner = new Scanner(System.in);
    private static String playerName;
    private static int playerHealth = 100;

    public static void main(String[] args) {
        System.out.println("Welcome to the Text Adventure Game!");
        System.out.print("Enter your name: ");
        playerName = scanner.nextLine();

        System.out.println("\nHello, " + playerName + "! Let's start the game.\n");

        // Start the game in the starting room
        playGame();
        
        System.out.println("Thanks for playing the Text Adventure Game!");
        scanner.close();
    }

    private static void playGame() {
        Room currentRoom = createWorld(); // Start in the starting room

        while (true) {
            System.out.println("--------------------------------------------------");
            System.out.println("You are in the " + currentRoom.getName());
            System.out.println(currentRoom.getDescription());

            if (currentRoom.getItem() != null) {
                System.out.println("You found an item: " + currentRoom.getItem());
                System.out.println("Do you want to pick it up? (yes/no)");

                String choice = scanner.nextLine().toLowerCase();
                if (choice.equals("yes")) {
                    System.out.println("You picked up the " + currentRoom.getItem());
                    // Add the item to player's inventory or do something with it
                    currentRoom.setItem(null); // Remove the item from the room
                } else {
                    System.out.println("You left the " + currentRoom.getItem() + " behind.");
                }
            }

            if (currentRoom.isEndRoom()) {
                System.out.println("Congratulations! You reached the end of the game.");
                break;
            }

            System.out.println("What do you want to do?");
            System.out.println("1. Move to another room");
            System.out.println("2. Check player status");
            System.out.println("3. Quit game");

            int choice = getIntInput(1, 3);

            if (choice == 1) {
                currentRoom = movePlayer(currentRoom);
                if (currentRoom == null) {
                    System.out.println("Thanks for playing!");
                    break;
                }
            } else if (choice == 2) {
                System.out.println("Player Status:");
                System.out.println("Name: " + playerName);
                System.out.println("Health: " + playerHealth);
            } else if (choice == 3) {
                System.out.println("Thanks for playing!");
                break;
            }
        }
    }

    private static Room createWorld() {
        // Define rooms
        Room startingRoom = new Room("Starting Room", "You are at the beginning of the adventure.");
        Room cave = new Room("Dark Cave", "A dark and spooky cave. You hear bats squeaking.");
        Room forest = new Room("Enchanted Forest", "A lush forest with ancient trees and strange sounds.");
        Room treasureRoom = new Room("Treasure Room", "You found the treasure! You won the game.");
        
        // Set room connections
        startingRoom.setExits(null, cave, null, null);
        cave.setExits(null, forest, null, startingRoom);
        forest.setExits(null, null, treasureRoom, cave);
        
        // Place items in rooms
        startingRoom.setItem("Health Potion");
        forest.setItem("Sword");

        return startingRoom; // Start in the starting room
    }

    private static int getIntInput(int min, int max) {
        int choice;
        while (true) {
            System.out.print("Enter your choice: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character
                if (choice >= min && choice <= max) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
            }
        }
        return choice;
    }

    private static Room movePlayer(Room currentRoom) {
        System.out.println("Which direction do you want to go?");
        System.out.println("1. North");
        System.out.println("2. East");
        System.out.println("3. South");
        System.out.println("4. West");

        int choice = getIntInput(1, 4);

        switch (choice) {
            case 1:
                return currentRoom.getNorth();
            case 2:
                return currentRoom.getEast();
            case 3:
                return currentRoom.getSouth();
            case 4:
                return currentRoom.getWest();
            default:
                return null;
        }
    }
}

class Room {
    private String name;
    private String description;
    private String item;
    private boolean endRoom;
    private Room north;
    private Room east;
    private Room south;
    private Room west;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        this.item = null;
        this.endRoom = false;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public boolean isEndRoom() {
        return endRoom;
    }

    public void setEndRoom(boolean endRoom) {
        this.endRoom = endRoom;
    }

    public Room getNorth() {
        return north;
    }

    public void setNorth(Room north) {
        this.north = north;
    }

    public Room getEast() {
        return east;
    }

    public void setEast(Room east) {
        this.east = east;
    }

    public Room getSouth() {
        return south;
    }

    public void setSouth(Room south) {
        this.south = south;
    }

    public Room getWest() {
        return west;
    }

    public void setWest(Room west) {
        this.west = west;
    }

    public void setExits(Room north, Room east, Room south, Room west) {
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
    }
}
