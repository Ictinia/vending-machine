package com.techelevator;


import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.time.format.DateTimeFormatter;

public class VendingMachine {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE
    private static final Scanner userInput = new Scanner(System.in);

    private BigDecimal userBalance = new BigDecimal("0.00");
    private BigDecimal userChange = new BigDecimal("0.00");
    private BigDecimal amountSold = new BigDecimal("0.00");
    private Map<String, Item> itemIdMap = new TreeMap<>();
    private boolean play = true;


    private List<Item> itemInfo() {

        List<Item> itemInfo = new ArrayList<>();

        File inventory = new File("src\\main\\java\\com\\techelevator\\vendingmachine.csv");
        Scanner reader;

        try {
            reader = new Scanner(inventory);
        } catch (FileNotFoundException e) {
            System.out.println("Sowwy. nwothing there... :( uwu");
            throw new RuntimeException(e);
        }

        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split("\\n");
            for (String s : line) {
                String[] itemLine = s.split("\\|");
                Item itemObject = new Item(itemLine[0], itemLine[1], new BigDecimal(itemLine[2]), Item.ItemType.valueOf(itemLine[3]));
                itemInfo.add(itemObject);
            }
        }

        return itemInfo;
    }

    public void vendingMachine(){
        File salesReportDirectory = new File("src/main/java/com/techelevator");
        File salesReport;
        try {
            salesReport = File.createTempFile("SalesReport", ".csv", salesReportDirectory);
        } catch (IOException e) {
            System.out.println("Error! Probably wasn't you though");
            throw new RuntimeException(e);
        }

        for (Item item : itemInfo()) {
            itemIdMap.put(item.getPosition(), item);
        }

        while (play) {
            System.out.println("What would you like to do?");
            System.out.println("[1] Display Vending Machine Items");
            System.out.println("[2] Purchase");
            System.out.println("[3] Exit");
            System.out.println("[4] Print Sales Report");

            String userChoice = userInput.nextLine();

            switch (userChoice) {
                case "1":
                    // add Code for as quantity goes down color of quantity changes
                    System.out.println("---------------------------------------------------");
                    System.out.println(WHITE_BOLD_BRIGHT + "ID" + " Name                             " + "Price" + "  Quantity");
                    System.out.println(ANSI_RESET + "---------------------------------------------------");
                    for (Item item : itemIdMap.values()) {
                        System.out.println(item);
                    }
                    System.out.println(ANSI_RESET + " ");
                    break;
                case "2":
                    vendingMachineTransaction();
                    break;
                case "3":
                    System.out.println("Goodbye!");
                    play = false;
                    break;
                case "4":
                    try (PrintWriter dataOutput = new PrintWriter(salesReport)) {
                        for (Item item : itemIdMap.values()) {
                            dataOutput.printf("%s|%s\n", item.getName().stripTrailing(), (5 - item.getQuantity()));
                        }
                        dataOutput.printf("\n**TOTAL SALES** $%s\n", amountSold);
                    } catch (IOException e) {
                        System.out.println("Sorry, can't write to the file.");
                    }
                    System.out.println("Printing sales report.");
                    System.out.println("Goodbye!");
                    play = false;
                    break;
                default:
                    System.out.println("That's not a valid input.\n");
                    break;
            }
        }


    }

    private void vendingMachineTransaction(){
        File log = new File("src/main/java/com/techelevator/Log.txt");
        LocalDateTime dateTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

        boolean play = true;

        while (play) {
            System.out.println("Current Money Provided: $" + userBalance);
            System.out.println(" ");
            System.out.println("[1] Feed Money");
            System.out.println("[2] Select Product");
            System.out.println("[3] Finish Transaction");

            String userChoice = userInput.nextLine();

            switch (userChoice) {
                case "1":
                    acceptFunds(log, dateTime, formatter);
                    break;
                case "2":
                    boolean validItem = false;
                    while (!validItem) {
                        for (Item item : itemIdMap.values()) {
                            System.out.println(item);
                        }

                        try {
                            System.out.println("Enter item ID:" + ANSI_RESET);
                            String idChoice = userInput.nextLine();

                            String productId = itemIdMap.get(idChoice).getPosition();
                            String name = itemIdMap.get(idChoice).getName().stripTrailing();
                            BigDecimal price = itemIdMap.get(idChoice).getPrice();
                            Item.ItemType type = itemIdMap.get(idChoice).getItem();
                            int quantity = itemIdMap.get(idChoice).getQuantity();

                            if (itemIdMap.containsKey(idChoice) && userBalance.compareTo(price) >= 0) {
                                userBalance = userBalance.subtract(price);
                                if (quantity > 0) {
                                    itemIdMap.get(idChoice).setQuantity(quantity - 1); // we were overwriting the iteration with the file read
                                    amountSold = amountSold.add(itemIdMap.get(idChoice).getPrice());
                                } else {
                                    System.out.println("SOLD OUT");
                                    break;
                                }

                                try (PrintWriter dataOutput = new PrintWriter(new FileWriter(log, true))) {
                                    dataOutput.printf("%s %s %s $%s $%s\n", formatter.format(dateTime),  name, productId, price, userBalance);
                                } catch (FileNotFoundException e) {
                                    System.out.println("Sorry, can't write to the file.");
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                                System.out.printf("Dispensing: %s %s\n", name.stripTrailing(), price);
                                System.out.println("...");
                                TimeUnit.SECONDS.sleep(2);
                                System.out.println("...");
                                TimeUnit.SECONDS.sleep(2);
                                System.out.println("\033[3mGadunk\033[3m\n" + ANSI_RESET);
                                TimeUnit.SECONDS.sleep(2);
                                if (type.equals(Item.ItemType.CHIP)) {
                                    System.out.println("Crunch Crunch, Yum!\n");
                                    TimeUnit.SECONDS.sleep(2);
                                } else if (type.equals(Item.ItemType.CANDY)) {
                                    System.out.println("Munch Munch, Yum!\n");
                                    TimeUnit.SECONDS.sleep(2);
                                } else if (type.equals(Item.ItemType.DRINK)) {
                                    System.out.println("Glug Glug, Yum!\n");
                                    TimeUnit.SECONDS.sleep(2);
                                } else if (type.equals(Item.ItemType.GUM)) {
                                    System.out.println("Smack Smack, Yum!\n");
                                    TimeUnit.SECONDS.sleep(2);
                                }
                                validItem = true;
                            } else if (userBalance.compareTo(price) < 0) {
                                System.out.println("Insufficient balance.");
                                validItem = true;
                            } else {
                                System.out.println("Product not available.");
                                break;
                            }
                        } catch (NullPointerException | InterruptedException e) {
                            System.out.println("Not a valid input, please try again");
                            try {
                                TimeUnit.SECONDS.sleep(1);
                            } catch (InterruptedException ex) {
                                System.out.println("Error! Probably wasn't you.");
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                    break;
                case "3":
                    if (userBalance.compareTo(BigDecimal.ZERO) > 0) {
                        System.out.println("Dispensing leftover change.");
                        Money money = new Money();
                        Integer[] changeReturn = money.changeAlgo(userBalance);
                        System.out.printf("Quarters: %s Dimes: %s Nickels %s\n", changeReturn[0], changeReturn[1], changeReturn[2]);
                        System.out.println("Change received: $" + userBalance);
                        System.out.println("Goodbye!");
                        System.out.println(" ");
                        try (PrintWriter dataOutput = new PrintWriter(new FileWriter(log, true))) {
                            dataOutput.printf("%s GIVE CHANGE: $%s $%s\n", formatter.format(dateTime),userBalance , userBalance.subtract(userBalance));
                        } catch (IOException e) {
                            System.out.println("Sorry, can't write to the file.");
                        }
                        userBalance = new BigDecimal("0.00");
                    }

                    play = false;
                    break;
                default:
                    System.out.println("That's not a valid input.");
                    System.out.println(" ");
                    break;
            }
        }
    }

    private void acceptFunds(File log, LocalDateTime dateTime, DateTimeFormatter formatter) {
        boolean oneChoice = true;
        while (oneChoice) {

            System.out.println("How much would you like to add (in whole dollar amounts)");
            BigDecimal cashBig = null;
            String cashInput = userInput.nextLine();

            try {
                cashBig = new BigDecimal(cashInput);
                if (BigDecimal.ZERO.compareTo(cashBig.remainder(BigDecimal.ONE)) == 0) {
                    userBalance = userBalance.add(cashBig);
                } else {
                    System.out.println("That's not a valid input.");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("That's not a valid input.");
            }

            try (PrintWriter dataOutput = new PrintWriter(new FileWriter(log, true))) {
                dataOutput.printf("%s FEED MONEY: $%s.00 $%s\n", formatter.format(dateTime), cashBig, userBalance);
            } catch (IOException e) {
                System.out.println("Sorry, can't write to the file.");
            }
            oneChoice = false;
        }
    }
}
