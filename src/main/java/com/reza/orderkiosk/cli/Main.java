package com.reza.orderkiosk.cli;

import com.reza.orderkiosk.interfaces.*;
import com.reza.orderkiosk.model.*;
import com.reza.orderkiosk.repo.*;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        CatalogRepository repo = new InMemoryCatalogRepository();
        Cart cart = new Cart();
        ReceiptService receipt = new ReceiptService(new FlatRateTaxCalculator(new BigDecimal("0.06")));
        ReceiptRepository fileRepo = new FileReceiptRepository(Paths.get("receipts"));
        ReceiptSaver saver = new ReceiptSaver(receipt, fileRepo);
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to Order Kiosk CLI");
        printHelp();

        while (true) {
            System.out.print("> ");
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split("\\s+");
            String cmd = parts[0].toLowerCase();
            try {
                switch (cmd) {
                    case "menu" -> repo.all().forEach(mi -> System.out.println(mi.getCategory()+" - "+mi.getName()+" "+mi.getPrice()));
                    case "menu.cat" -> {
                        // menu.cat COFFEE
                        Category c = Category.valueOf(parts[1].toUpperCase());
                        repo.byCategory(c).forEach(mi -> System.out.println(mi.getName()+" "+mi.getPrice()));
                    }
                    case "add" -> {
                        String name = parts[1];
                        int qty = Integer.parseInt(parts[2]);
                        MenuItem mi = repo.all().stream().filter(m -> m.getName().equalsIgnoreCase(name)).findFirst()
                                .orElseThrow(() -> new IllegalArgumentException("unknown item"));
                        cart.add(mi, qty);
                        System.out.println("Added " + qty + " x " + mi.getName());
                    }
                    case "receipt" -> receipt.render(cart).forEach(System.out::println);
                    case "save" -> {
                        Path p = saver.renderAndSave(cart);
                        System.out.println("Saved receipt at " + p.toAbsolutePath());
                    }
                    case "help" -> printHelp();
                    case "quit" -> {
                        System.out.println("Bye");
                        return;
                    }
                    default -> System.out.println("Unknown command, type help");
                }
            } catch (Exception e) {
                System.out.println("Error, " + e.getMessage());
            }
        }
    }

    private static void printHelp() {
        System.out.println("Commands, menu | menu.cat <Category> | add <Name> <Qty> | receipt | save | quit");
    }
}
