
import java.util.Random;

public class Ejercicio5 {
	
	public static void main(String[] args) {
		
		if(args.length != 2) {
			System.out.println("Número inválido de argumentos");
		}
		else {
			try {
				Supermoderno supermoderno = new Supermoderno(Integer.parseInt(args[0]));
				Cliente[] clientes = new Cliente[Integer.parseInt(args[1])];
				
				for(int i = 0; i < clientes.length; i++) {
					clientes[i] = new Cliente(supermoderno, i);
					clientes[i].start();
				}
				for(int i = 0; i < clientes.length; i++) {
					clientes[i].join();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				System.out.println("Argumentos inválidos.");
			}
		}
	}
}

class Supermoderno extends Supermercado {
	
	private boolean[] cajasLibres;

	public Supermoderno(int cajas) {
		super(cajas);
		cajasLibres = new boolean[cajas];
		for (int i = 0; i < cajas; i++) {
			cajasLibres[i] = true;
		}
	}
	
	@Override
	public void pasarPorCaja(int numCliente) {
		
		int numCaja;
		while((numCaja = cajaLibre()) == -1) {}
		
		System.out.println("El cliente " + numCliente + " está yendo a la caja " + numCaja);
		
		pasandoPorCaja(numCliente);
		pagando(numCliente);
		
		cajasLibres[numCaja] = true;
	}
	
	private synchronized int cajaLibre() {
		int caja = -1;
		int index = 0;
		while(caja == -1 && index < cajas.length) {
			if(cajasLibres[index]) {
				caja = index;
				cajasLibres[index] = false;
			}
			else index++;
		}
		return caja;
	}
	
}