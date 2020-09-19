package WS18aufgabenblatt09;
/*
Informatik 1 Winter 05.02.2018
Aufgabenblatt:    	09
Aufgabe:          	09.01
*/
//in der main Methode von Main.java wird diese Klasse demonstriert
public class Car {
	
	private String brand;
	private String licenseId;
	private int fuelCapacity;
	private float fuel = 0;
	private float consumption;
	
	public Car(String brand, String licenseId, int fuelCapacity, float consumption) {
		this.brand = brand;
		this.licenseId = licenseId;
		this.fuelCapacity = fuelCapacity;
		this.consumption = consumption;
	}

	public void drive(float distance) {
		//Verbrauch auf 1 km
		float tmp = consumption/100f;
		//Verbrauch auf distance km
		tmp = tmp * distance;
		
		if((fuel - tmp) < 0) {
			System.out.println("Das Auto hat nicht genügend Sprit");
		}
		else {
			fuel -= tmp;
			System.out.println("Das Auto ist " + distance + " km gefahren");
		}
	
	}
	
	
	public void getFuel() {
		System.out.println("Der aktuelle Tankstand beträgt " + fuel + " Liter");
	}

	public void addFuel(float fuel) {
		if((this.fuel + fuel) > fuelCapacity) {
			System.out.println("Gewünschte Menge kann nicht getankt werden, da sie die maximale Tank Kapazität von " + fuelCapacity + " Liter überschreitet.");
		}
		else if(fuel < 0) {
			System.out.println("Falscher Wert");
		}
		else {
			this.fuel += fuel;
			System.out.println( fuel + " Liter getankt");
		}
	}

	public String toString() {
		return "Dieses Auto, der Marke " + brand + " fasst " + fuelCapacity +
				" Liter und hat einen durchschnittlichen Verbrauch von " + consumption + " l/100km und ist zugelassen auf " + licenseId + "." ;
		
	}
	
}
