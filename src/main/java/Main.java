import java.io.InputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("tutorial.yaml");
            if (inputStream == null) {
                throw new IOException("YAML file not found.");
            }

            Game game = StoryReader.readGame(inputStream);
            StoryTeller storyteller = new StoryTeller(game);
            storyteller.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
