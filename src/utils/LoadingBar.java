package utils;

public interface LoadingBar {
    default void updateLoadingBar(int current, int total, String message) throws InterruptedException {
        int progressBarWidth = 50; // Adjust based on the width of your loading bar

        int progress = (int) ((double) current / total * 100);
        int progressChars = progressBarWidth * progress / 100;

        System.out.print("\r|"
                + "█".repeat(progressChars)
                + " ".repeat(progressBarWidth - progressChars)
                + "| " + progress + "% " + message);

        System.out.flush();

        if (progress == 100) {
            System.out.println(); // Force a new line after completion
        }
    }
}
