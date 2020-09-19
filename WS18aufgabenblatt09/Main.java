package WS18aufgabenblatt09;
/*
Informatik 1 Winter 05.02.2018
Aufgabenblatt:    	09
Aufgabe:          	09.01
*/
import java.io.Console;

public class Main {

	public static void main(String[] args) {
		Console console = System.console();
		
		Car car1 = new Car("VW", "GL-ZS-97", 60, 5f);
		
		System.out.println(car1.toString());
		car1.getFuel();
		/*Tank füllen über maximale Kapazität
		car1.addFuel(100);
		*/
		car1.addFuel(60);
		car1.getFuel();
		car1.drive(255.7f);
		car1.getFuel();
		car1.drive(1100);
		System.out.println();
		
		BankAccount konto = new BankAccount("Zafer", "-----", "1234567890", 10000f);
		System.out.println();
		
		konto.addBalance(2000000f);
		konto.takeBalance(150.25f);
		System.out.println();

		konto.printBankStatement();
	}

}
