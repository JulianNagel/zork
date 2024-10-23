import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            Game game = StoryReader.readGame("src/main/resources/tutorial.yaml");
            StoryTeller storyteller = new StoryTeller(game);
            storyteller.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
