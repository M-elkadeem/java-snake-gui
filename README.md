# Snake-Game

A classic Snake game written in Java with a Swing GUI. It adds player profiles with high scores, three kinds of food, and the option to save a game and continue it later.

## Features

- Swing GUI with a live score and snake length display
- Player profiles: enter a player ID, and returning players are greeted with their high score
- Three types of food:

  | Food | Colour | Effect |
  |------|--------|--------|
  | Normal | Green | Grows the snake, +1 point |
  | Golden | Orange | Grows the snake, +5 points |
  | Poison | Magenta | Shrinks the snake and halves your score |

- Save and load: save your current game when you exit and resume it the next time you log in with the same ID
- Anonymous players (no name entered) are never saved
- Pause, restart, and a game over screen
- Unit tests (JUnit)

## Controls

| Key | Action |
|-----|--------|
| `W` / `A` / `S` / `D` | Move up / left / down / right |
| `Space` | Pause or resume |
| `Enter` | Restart after game over |

The **File** menu has **New Game** and **Exit** (with an option to save).

## Project structure

| File | Purpose |
|------|---------|
| `Main.java` | Entry point: player login dialogs, window setup, loading a saved game |
| `Game.java` | Game loop (timer), key handling, collisions, pause/restart, save/load |
| `Board.java` | The game board: drawing, food placement, score handling |
| `snake.java`, `point.java`, `Direction.java` | Snake body, grid positions and movement directions |
| `Food.java`, `Normalfood.java`, `Goldenfood.java`, `Poisonfood.java` | Food hierarchy |
| `player.java`, `Playermanager.java` | Player data and high score storage in `players.txt` |
| `GameState.java`, `SaveLoadManager.java` | Serializable game state and save files (`savegame.dat`) |
| `Menu.java` | The File menu |
| `UnitTests.java` | JUnit tests |
| `Snake Game documentation .pdf` | Project documentation |

## Requirements

- JDK 11 or newer (the code uses `var`)
- JUnit (only needed to run `UnitTests.java`)

## Build and run

From the project root:

```bash
javac -d out src/*.java
java -cp out Main
```

`UnitTests.java` needs JUnit on the classpath, so if you compile with the command above, exclude it or add the JUnit jars. The easiest way is to open the project in IntelliJ IDEA and run `Main`.

Run the game from the project root so it can find `Snake image.png` (the window icon) and the `players.txt` and `savegame.dat` data files.

## How to play

1. Enter your player ID, then your name if you are new.
2. Steer the snake with `W`, `A`, `S`, `D` and eat food to score.
3. Avoid the walls and your own body.
4. Avoid the magenta poison food, which cuts your score in half.
5. Use **File > Exit** or close the window to save your progress.
