package ChatHilos;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Scanner;

public class Chatconversacion {

    private static void displayMessage(String message) {
        System.out.println(message);
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        executorService.submit(() -> {
            try {
                Thread.sleep(2000);
                displayMessage("megm: Hi, there!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.submit(() -> {
            try {
                Thread.sleep(2000);
                displayMessage("Kathyo: Hi, Meg!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.submit(() -> {
            try {
                Thread.sleep(4000);
                displayMessage("megm: What are you doing?");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.submit(() -> {
            try {
                Thread.sleep(6000);
                displayMessage("kathyo: I'm sitting on my bed with my laptop. I'm doing my homework.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.submit(() -> {
            try {
                Thread.sleep(4000);
                displayMessage("megm: What are you working on?");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.submit(() -> {
            try {
                Thread.sleep(6000);
                displayMessage("kathyo: I'm writing an essay for Spanish class. :) Where are you?");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.submit(() -> {
            try {
                Thread.sleep(8000);
                displayMessage("megm: I'm in a café with my friend Carmen. I'm having coffee, and she's talking on the phone outside. How is your family?");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.submit(() -> {
            try {
                Thread.sleep(9000);
                displayMessage("kathyo: They're all fine! My father's watching a baseball game with his friends. My mother is out shopping.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.submit(() -> {
            try {
                Thread.sleep(3000);
                displayMessage("megm: Where's your brother?");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.submit(() -> {
            try {
                Thread.sleep(9000);
                displayMessage("kathyo: John's playing soccer in the park. Oh, wait. My phone is ringing. My mother's calling me. I have to go! Bye!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.submit(() -> {
            try {
                Thread.sleep(2000);
                displayMessage("megm: OK! Bye!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();

        try {
            executorService.awaitTermination(Long.MAX_VALUE, java.util.concurrent.TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Pregunta exerciseThread = new Pregunta();
        exerciseThread.startExercise("-----------------------------------------------------------------------------------------------\nRead the conversation. Who is doing these things? Complete the sentences.\n");
        exerciseThread.waitForAnswer("1. ___________ is writing an essay? ", "Kathyo");
        exerciseThread.evaluateAnswer("Kathyo");

        exerciseThread.startExercise("2. ___________ is having coffee? ");
        exerciseThread.waitForAnswer("2. Who is having coffee? ", "megm");
        exerciseThread.evaluateAnswer("Megm");

        exerciseThread.startExercise("3. ___________ is talking on the phone? ");
        exerciseThread.waitForAnswer("3. Who is talking on the phone? ", "Carmen");
        exerciseThread.evaluateAnswer("Carmen");

        exerciseThread.startExercise("4. ___________ is watching a baseball game? ");
        exerciseThread.waitForAnswer("4. Who is watching a baseball game? ", "Kathyo's father");
        exerciseThread.evaluateAnswer("Kathyo's father");

        exerciseThread.startExercise("5. ___________ is shopping? ");
        exerciseThread.waitForAnswer("5. Who is shopping? ", "Kathyo's mother");
        exerciseThread.evaluateAnswer("Kathyo's mother");

        exerciseThread.startExercise("6. ___________ is playing soccer? ");
        exerciseThread.waitForAnswer("6. Who is playing soccer? ", "John");
        exerciseThread.evaluateAnswer("John");
    }

    private static class Pregunta {
        private void startExercise(String exercise) {
            System.out.println(exercise);
            sleep();
        }

        private void waitForAnswer(String question, String expectedAnswer) {
            Scanner scanner = new Scanner(System.in);
            System.out.print(question);

            String answer = scanner.nextLine();
            while (!answer.equalsIgnoreCase(expectedAnswer)) {
                System.out.println("Incorrect answer. Try again.");
                System.out.print(question);
                answer = scanner.nextLine();
            }
        }

        private void evaluateAnswer(String person) {
            System.out.println("Correct! " + person + " is doing that.");
            System.out.println("Perfect!");
            System.out.println();
            sleep();
        }

        private void sleep() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
