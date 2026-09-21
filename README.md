# Maze Game

This is a maze game that I made with Java and JavaFX for my OOP course.
The player must go from the start to the goal before the time is over.
There are 3 levels and every level uses a different algorithm to build the maze.

## How to play

- Move with **W A S D** or the **arrow keys**.
- Press **X** to use one star. It gives you 20 extra seconds.
- Reach the green cell to finish the level.
- If the time is over or an enemy catches you, the level starts again with a new maze.
- You can also click the **Restart Level** button at the top.

## Levels

| Level | Maze algorithm | Time  | Enemies           |
|-------|----------------|-------|-------------------|
| 1     | DFS            | 75 s  | none              |
| 2     | Prim           | 95 s  | 3 patrol enemies  |
| 3     | Aldous-Broder  | 120 s | 2 static enemies  |

## Items and enemies

- **Star** (gold): 7 stars in every level. You can use them for extra time, or keep them for a higher score.
- **Food** (orange): gives 10 extra seconds.
- **Bomb** (black): only in level 3. With a bomb you can destroy a static enemy.
- **Patrol enemy** (red square): moves every second between two cells. If it touches you, you lose.
- **Static enemy** (purple circle): stands on the shortest path. You need a bomb to pass it.

## Score

When you finish a level, the score is:

```
remaining seconds × 10 × stars you still have   (at least × 1)
```

The total score is the sum of all levels.

## Project structure

```
src/main/java/com/maze/
├── MainApp.java     # JavaFX window, keyboard and timer
├── controller/      # GameController: game rules and levels
├── model/           # Maze, Player, Position
├── entity/          # Star, Food, Bomb, PatrolEnemy, StaticEnemy
├── generation/      # DFS, Prim and Aldous-Broder maze generators
├── placer/          # puts items and enemies inside the maze
├── util/            # PathFinder: BFS shortest path
├── score/           # ScoreCalculator
├── exceptions/      # LethalCollisionException
└── view/            # MazeView: draws the maze on a Canvas
```

## How to run

You need **Java 17** or newer and **Maven**.

**From the terminal:**

```
mvn clean javafx:run
```

**From IntelliJ IDEA:** open the **Maven** panel on the right side, then go to
**Plugins → javafx → javafx:run** and double-click it.

## OOP ideas in this project

The project is built entirely around Object-Oriented Programming. It shows how
encapsulation, information hiding, inheritance, polymorphism, composition,
exception handling, Subtyping & Multityping and abstraction combine into an
architecture that is flexible, extensible and easy to maintain.
