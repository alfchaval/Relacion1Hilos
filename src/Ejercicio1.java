
class Ejercicio1 {
	
	public static void main(String[] args) {
		
		final int primeroEnSaludar = 2;
		
		Orden orden = new Orden(primeroEnSaludar);
		
		Hilo hilo1 = new Hilo(1, orden);
		Hilo hilo2 = new Hilo(2, orden);
		
		hilo1.start();
		hilo2.start();
		
		try {
			hilo1.join();
			hilo2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Hilo extends Thread {
	
	private int numero;
	private Orden orden;
	
	public Hilo(int numero, Orden orden) {
		this.numero = numero;
		this.orden = orden;
	}
	
	@Override
	public void run() {
		orden.saludar(numero);
	}
}

class Orden {
	
	private final int primeroEnSaludar;
	private boolean haSaludadoElPrimero;
	
	public Orden(int primeroEnSaludar) {
		this.primeroEnSaludar = primeroEnSaludar;
		this.haSaludadoElPrimero = false;
	}
	
	public synchronized void saludar(int numero) {
		
		while (!haSaludadoElPrimero) {
			if (numero != primeroEnSaludar) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Hola, soy el hilo número " + numero);
			}
			else {
				System.out.println("Hola, soy el hilo número " + numero);
				haSaludadoElPrimero = true;
				notify();
			}
		}
	}
	
}

