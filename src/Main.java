
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
class Player {
    private int score;
    private int guess;
    final private String name;

    public int getGuess() {
        return guess;
    }

    public void setGuess(int guess) {
        this.guess = guess;
    }

    public String getName() {
        return name;
    }

    public Player(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


}

class GuessGame {
    final private int upperBound;
    final private int lowerBound;

    public GuessGame(int upperBound, int lowerBound) {
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
    }

    public int playGame(Player player, int attempts) throws InputMismatchException {
        System.out.println("Game Starts for" + player.getName());
        int randomNumber = generateRandomNumber(upperBound, lowerBound);
        System.out.println("I have selected a number, guess it?");
        int wrongGuess = 0;
        while (attempts != 0) {
            System.out.println("Enter your Number");
            int input = takeUserInput();
            if (lowerBound >= input || input >= upperBound) {
                throw new InputMismatchException("Number out of range");
            }
            if (input == randomNumber) {
                System.out.println("Correct Number Congratulation You won");
                break;
            }
            if (input < randomNumber) {
                System.out.println("To low");
            } else {
                System.out.println("To High");
            }
            attempts--;
            wrongGuess++;
        }
        if (attempts == 0)
            System.out.println("Attempts are over");
        return wrongGuess;
    }

    private int takeUserInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    private int generateRandomNumber(int upperBound, int lowerBound) {
        Random random = new Random();
        return random.nextInt(upperBound - lowerBound) + lowerBound;
    }


}

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Welcome to the guess the number Game");
            System.out.println("Tell me UpperBound");
            int upperBound = scanner.nextInt();
            System.out.println("Tell me lowerBound");
            int lowerBound = scanner.nextInt();
            if (lowerBound > upperBound) {
                throw new IllegalArgumentException("lower bound greater than upperBound");
            }
            System.out.println("Enter number of attempts");
            int attempts = scanner.nextInt();
            System.out.println("Enter number of Players ");
            int totalPlayers = scanner.nextInt();
            List<Player> players = new ArrayList<>();
            for (int i = 0; i < totalPlayers; i++) {
                System.out.println("Enter name of player" + (i + 1));
                String name = scanner.next();
                Player player = new Player(name);
                players.add(player);
                GuessGame guessGame = new GuessGame(upperBound, lowerBound);
                int wrongGuess = guessGame.playGame(player, attempts);
                player.setScore(attempts - wrongGuess);
                player.setGuess(wrongGuess);
                showResult(players);
            }
        } catch (InputMismatchException e) {
            System.out.println("Error " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid input - Please enter a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void showResult(List<Player> players) {
        for (Player player : players) {
            System.out.println("player" + player.getName() + "score" + " " + player.getGuess());
        }
        players.sort(Collections.reverseOrder(Comparator.comparingInt(Player::getScore)));
        System.out.println("Player" + players.getFirst().getName() + "has won the game");
    }

}
