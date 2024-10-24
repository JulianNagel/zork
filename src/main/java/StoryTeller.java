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
        String verb = resolveVerb(parts[0]);
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


    private String resolveVerb(String inputVerb) {

        for (Map.Entry<String, Verb> entry : game.verbs.entrySet()) {
            Verb verb = entry.getValue();
            if (verb.synonyms.contains(inputVerb)) {
                return entry.getKey();
            }
        }
        return inputVerb;
    }

    private void performAction(Object action) {
        if (action instanceof Map) {
            Map<String, Object> actionMap = (Map<String, Object>) action;

            if (actionMap.containsKey("room")) {
                String nextRoom = (String) actionMap.get("room");
                currentRoom = game.rooms.get(nextRoom);
            }

            if (actionMap.containsKey("message")) {
                Console.print((String) actionMap.get("message"));
            }

            if (actionMap.containsKey("addState")) {
                states.add((String) actionMap.get("addState"));
            }
        }
    }



    private void handleError(String type) {
        Verb defaultVerb = game.verbs.get("default");
        if (defaultVerb != null && defaultVerb.errors.containsKey(type)) {
            Console.print(defaultVerb.errors.get(type));
        } else {
            Console.print("Unknown error occurred.");
        }
    }
}
