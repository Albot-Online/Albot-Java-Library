# Albot.Online Java Library

A simple library for communicating with the [Albot.Online](https://Albot.Online) client. 
This is great for getting you up and running fast, allowing you to focus more on the AI logic.
<br><br>
## Getting Started
This library is available as jars (library + javadoc). Download them from the [target folder of this repository](https://github.com/Albot-Online/Albot-Java-Library/tree/master/target).<br>
When the jars are downloaded, import them to your project. Preferably by using a sophisticated IDE such as IntelliJ: [How to add Jar to Dependencies in IntelliJ.](https://www.jetbrains.com/help/idea/library.html#add-library-to-module-dependencies)

## Example
Following is a short example of the Java Library being put to use on the [Snake](https://www.albot.online/snake/) game. 
For exact information of how to use the library see the [documentation Wiki](https://github.com/Albot-Online/Albot-Java-Library/wiki).

```java
import albot.Constants;
import albot.snake.*;

import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        SnakeGame game = new SnakeGame(); // Connects you to the client
        Random rand = new Random();

        while(game.waitForNextGameState() == BoardState.ongoing) { // Gets/Updates the board

            // Since this gives a class containing both playerMoves and enemyMoves, we specify playerMoves
            List<String> possibleMoves = game.getPossibleMoves(game.currentBoard).playerMoves;

            int randomIndex = rand.nextInt(possibleMoves.size());
            String randomMove = possibleMoves.get(randomIndex);

            game.makeMove(randomMove);
        }
    }
}
```
This bot will simply connect to the client, look at what moves it currently has available and pick one at random.
<br><br>


## Versioning

  0.2b0
  
## Authors

  Joey Öhman

## License

This project is licensed under the MIT License - see the [LICENSE.md](https://github.com/Albot-Online/Albot-Java-Library/blob/master/LICENSE) file for details
