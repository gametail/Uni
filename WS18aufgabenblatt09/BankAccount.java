package WS18aufgabenblatt09;
/*
Informatik 1 Winter 05.02.2018
Aufgabenblatt:    	09
Aufgabe:          	09.01
*/

//in der main Methode von Main.java wird diese Klasse demonstriert
public class BankAccount {
	
	private String name;
	private String lastName;
	private String accountNumber;
	private float balance = 0;
	private float dispo;
	
	public BankAccount(String name, String lastName, String accountNumber, float dispo) {
		/*Überprüft ob die Kontonummer 10 Stellen hat (ich weiß leider nicht wie man dann sagt das ein objekt nicht erzeugt werden soll [neue klasse für kontonummer?])
		if(accountNumber.length() > 10 ^ accountNumber.length() < 0) {
			System.out.println("Bitte geben sie eine gültige 10-stellige Kontonummer ein!");			
		}
		Überprüft ob die eingegebene Zahl positiv ist (genauso wie oben weiß ich nicht wie man die objekterzeugung abbricht bei diesem fall)
		else if(dispo < 0){
			System.out.println("Bitte geben sie eine gültigen Wert für den Dispokredit ein!");
		}
		else {
		*/
		this.accountNumber = accountNumber;
		this.dispo = dispo;
		this.lastName = lastName;
		this.name = name;
		System.out.println("Konto wurde erstellt.");
		//}
	}
	public void addBalance(float value) {
		if(value > 0) {
			balance += value;
			System.out.printf("%.2f", value);
			System.out.println("€ wurde/n eingezahlt.");
		}
		else {
			System.out.println("Bitte geben sie ein gültigen Wert ein!");
		}
	}
	public void takeBalance(float value) {
		if(value > 0) {
			if((balance - value) > -(dispo)) {
			balance -= value;
			System.out.printf("%.2f", value);
			System.out.println("€ wurde/n augezahlt.");
			}
			else {
				System.out.println("Sie haben nicht genügend Geld auf dem Konto, deshalb wurde ihre Transaktion storniert!");
			}
		}
		else {
			System.out.println("Bitte geben sie ein gültigen Wert ein!");
		}
	}
	public void printBankStatement(){
		System.out.println("________________________________________________");
		System.out.println("KONTOAUSZUG");
		System.out.println("________________________________________________");
		System.out.println("Name: " + lastName);
		System.out.println("Vorname: " + name);
		System.out.println("Kontonummer: " + accountNumber);
		System.out.print("Max. Dispo: ");
		System.out.printf("%.2f" , dispo);
		System.out.println("€.");
		System.out.println("________________________________________________");
		System.out.print("Aktueller Kontostand: ");
		System.out.printf("%.2f" , balance);
		System.out.println("€.");
		System.out.println("________________________________________________");
	}
}
