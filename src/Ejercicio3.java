
public class Ejercicio3 {

	public static void main(String[] args) {
		
		final int numRelevistas = 4;
		
		Relevos relevo = new Relevos(numRelevistas);
		Relevista[] relevistas = new Relevista[numRelevistas];
		
		for (int i = 0; i < relevistas.length; i++) {
			relevistas[i] = new Relevista(relevo, i);
			relevistas[i].start();
		}
		
		relevo.comenzarCarrera();
		
		for (int i = 0; i < relevistas.length; i++) {
			try {
				relevistas[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
	}
}

class Relevos {

	private boolean carreraComenzada;
	private int numRelevistas, relevosCompletados;
	
	public Relevos(int numRelevistas) {
		this.carreraComenzada = false;
		this.numRelevistas = numRelevistas;
		this.relevosCompletados = 0;
	}
	
	public void comenzarCarrera() {
		this.carreraComenzada = true;
	}
	
	public synchronized void cogerRelevo(int numero) {
		while(!carreraComenzada) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("El relevista " + numero + " está corriendo");
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(++relevosCompletados < numRelevistas) {
			System.out.println("El relevista " + numero + " ha pasado el testigo");
			notify();
		}
		else {
			System.out.println("El relevista " + numero + " ha llegado a la meta, la carrera ha terminado");
		}
	}
}

class Relevista extends Thread {
	
	private Relevos carrera;
	private int numero;
	
	public Relevista(Relevos carrera, int numero) {
		this.carrera = carrera;
		this.numero = numero;
	}
	
	@Override
	public void run() {
		carrera.cogerRelevo(numero);
	}
}