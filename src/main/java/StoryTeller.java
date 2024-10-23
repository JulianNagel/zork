import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class StoryTeller {
    private Game game;
    private Room currentRoom;
    private Set<String> states = new HashSet<>();

    public StoryTeller(Game game) {
        this.game = game;
        this.currentRoom = game.rooms.get(game.startRoom);
    }

    public void play() {
        Console.print(game.description);
        while (true) {
            Console.print(currentRoom.description);
            String input = Console.readCommand();
            processCommand(input);
        }
    }

    private void processCommand(String input) {
        String[] parts = input.split(" ", 2);
        String verb = parts[0];
        String object = parts.length > 1 ? parts[1] : "";

        if (currentRoom.verbs.containsKey(verb)) {
            Map<String, Object> actions = currentRoom.verbs.get(verb);
            if (actions.containsKey(object)) {
                performAction(actions.get(object));
            } else {
                handleError("object");
            }
        } else {
            handleError("verb");
        }
    }

    private void performAction(Object action) {
        if (action instanceof String) {
            Console.print((String) action);
        } else if (action instanceof Map) {
            Map<String, Object> actionMap = (Map<String, Object>) action;
            if (actionMap.containsKey("message")) {
                Console.print((String) actionMap.get("message"));
            }
            if (actionMap.containsKey("room")) {
                currentRoom = game.rooms.get(actionMap.get("room"));
            }
            if (actionMap.containsKey("addState")) {
                states.add((String) actionMap.get("addState"));
            }
        }
    }

    private void handleError(String type) {
        Console.print(game.verbs.get("default").errors.get(type));
    }
}
