
import java.util.Random;

public class Ejercicio4 {
	
	public static void main(String[] args) {
		
		if(args.length != 2) {
			System.out.println("Número inválido de argumentos");
		}
		else {
			try {
				Supermercado supermercado = new Supermercado(Integer.parseInt(args[0]));
				Cliente[] clientes = new Cliente[Integer.parseInt(args[1])];
				
				for(int i = 0; i < clientes.length; i++) {
					clientes[i] = new Cliente(supermercado, i);
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

class Cliente extends Thread {
	
	private Supermercado supermercado;
	private int numCliente;
	
	public Cliente(Supermercado supermercado, int numCliente) {
		this.supermercado = supermercado;
		this.numCliente = numCliente;
	}
	
	@Override
	public void run() {
		comprar();
		hacerCola();
	}
	
	private void comprar() {
		try {
			Thread.sleep(new Random().nextInt(5000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void hacerCola() {
		supermercado.pasarPorCaja(numCliente);
	}
}

class Supermercado {

	protected Object[] cajas;
	
	public Supermercado(int cajas) {
		this.cajas = new Object[cajas];
		for(int i = 0 ; i < cajas; i++){
			this.cajas[i] = new Object();
		}
	}
	
	public void pasarPorCaja(int numCliente) {
		
		int numCaja = new Random().nextInt(cajas.length);
		System.out.println("El cliente " + numCliente + " está yendo a la caja " + numCaja);
		
		synchronized (cajas[numCaja]) {
			pasandoPorCaja(numCliente);
			pagando(numCliente);
		}
	}
	
	protected void pasandoPorCaja(int numCliente) {
		try {
			Thread.sleep(new Random().nextInt(5000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("El cliente " + numCliente + " está pasando por caja");
	}
	
	protected void pagando(int numCliente) {
		try {
			Thread.sleep(new Random().nextInt(5000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("El cliente " + numCliente + " ha pagado " + new Random().nextInt(1000) + "€");
	}
}