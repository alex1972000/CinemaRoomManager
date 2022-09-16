package cinema;

import java.util.Scanner;

public class Cinema {

    private static Scanner scanner;
    private static char[][] cinema;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seatsEachRow = scanner.nextInt();
        cinema = new char[rows][seatsEachRow];
        System.out.println("Total income:");
        System.out.println("$" + calculate(rows, seatsEachRow));
        fillRoom(cinema);
        printCinema(cinema);
        showMenu(cinema);
    }

    private static void showMenu(char[][] cinema) {
        boolean exit = false;
        while (!exit) {
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");
            int option = scanner.nextInt();
            System.out.println();
            switch (option) {
                case 1:
                    printCinema(cinema);
                    break;
                case 2:
                    buyTicket(cinema);
                    break;
                case 3:
                    showStatistics(cinema);
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid Operation Try again!!");
                    break;
            }
        }
    }

    private static void showStatistics(char[][] cinema) {
        int numberOfPurchasedTickets = getNumberOfPurchasedTickets(cinema);
        double percentage = getPercentage(cinema, getNumberOfPurchasedTickets(cinema));
        int currentIncome = getCurrentIncome(cinema);
        int totalIncome = calculate(cinema.length, cinema[0].length);
        System.out.println("Number of purchased tickets: " + numberOfPurchasedTickets);
        System.out.printf("Percentage: %.2f%%\n", percentage);
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome);
        System.out.println();
    }

    private static boolean isWrongInput(char[][] room, int rowNumber, int seatNumber) {
        return rowNumber < 0 || rowNumber > room.length - 1 || seatNumber < 0 || seatNumber > room[0].length - 1;
    }
    private static int getCurrentIncome(char[][] cinema) {
        int currentIncome = 0;
        for (int i = 0; i < cinema.length; i++) {
            for (int j = 0; j < cinema[0].length; j++) {
                if (cinema[i][j] == 'B') {
                    currentIncome += getTicketPrice(cinema, i, j);
                }
            }
        }
        return currentIncome;
    }
    private static double getPercentage(char[][] cinema, int numberOfPurchasedTickets) {
        double percentage = 0;
        int numberOfSeats = cinema.length * cinema[0].length;
        percentage =  (double) (numberOfPurchasedTickets * 100) / numberOfSeats;
        return percentage;

    }

    private static int getNumberOfPurchasedTickets(char[][] cinema) {
        int count = 0;
        for (char[] chars : cinema) {
            for (int j = 0; j < cinema[0].length; j++) {
                if (chars[j] == 'B') {
                    count++;
                }
            }
        }
        return count;
    }

    private static void buyTicket(char[][] cinema) {
        boolean success = false;
        while (!success) {
            System.out.println("Enter a row number:");
            int rowNumber = scanner.nextInt() - 1;
            System.out.println("Enter a seat number in that row:");
            int numberOfSeat = scanner.nextInt() - 1;
            if (isWrongInput(cinema, rowNumber, numberOfSeat)) {
                System.out.println("Wrong input!");
            }
            else if (cinema[rowNumber][numberOfSeat] != 'B') {
                order(cinema, rowNumber, numberOfSeat);
                System.out.println("Ticket price: $" + getTicketPrice(cinema, rowNumber, numberOfSeat));
                success = true;
            } else if (cinema[rowNumber][numberOfSeat] == 'B') {
                System.out.println("That ticket has already been purchased!");
            }
            printCinema(cinema);
        }
    }

    private static void order(char[][] cinema, int rowNumber, int numberOfSeat) {
        cinema[rowNumber][numberOfSeat] = 'B';
    }

    public static int getTicketPrice(char[][] cinema, int rowNumber, int seatNumber) {
        int row = cinema.length;
        int col = cinema[0].length;

        if (isLarger(row, col)) {
            int[][] prices = new int[row][col];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    prices[i][j] = 8;

                }
            }

            for (int i = 0; i < row / 2; i++) {
                for (int j = 0; j < col; j++) {
                    prices[i][j] = 10;
                }
            }
            return prices[rowNumber][seatNumber];
        }

        return 10;
    }

    private static void fillRoom(char[][] cinema) {
        for (int i = 0; i < cinema.length; i++) {
            for (int j = 0; j < cinema[0].length; j++) {
                cinema[i][j] = 'S';
            }
        }
    }

    private static void printCinema(char[][] cinema) {
        System.out.println("Cinema:");
        System.out.print("  ");
        for (int i = 0; i < cinema[0].length; i++) {
            System.out.print(i + 1 + " ");
        }
        System.out.println();
        for (int i = 0; i < cinema.length; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < cinema[0].length; j++) {
                System.out.print(cinema[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static int calculate(int rows, int seatsEachRow) {
        int totalIncome;
        if (isLarger(rows, seatsEachRow)) {
            int frontHalf = rows / 2;
            int backHalf = rows - frontHalf;
            totalIncome = frontHalf * seatsEachRow * 10 + backHalf * seatsEachRow * 8;
        } else {
            totalIncome = rows * seatsEachRow * 10;
        }
        return totalIncome;
    }

    public static boolean isLarger(int rows, int seatsEachRow) {
        return rows * seatsEachRow > 60;
    }
}