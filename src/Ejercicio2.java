import java.nio.IntBuffer;

class Ejercicio2 {
	
	public static void main(String[] args) {
		Check check = new Check();
		
		Lector lector = new Lector(check);
		Escritor escritor = new Escritor(check);
		
		escritor.start();
		lector.start();
		
		try {
			escritor.join();
			lector.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Check {

	private IntBuffer buffer;
	private Object[] objetoSincronizado;
	private int posicion, pasadaEscritor, pasadaLector;
	
	public Check() {
		buffer = IntBuffer.allocate(10000);
		objetoSincronizado = new Object[10000];
		posicion = 0;
		pasadaEscritor = 0;
		pasadaLector = 0;
	}
	
	public void leer() {
		
		boolean error = false;
		int index = 0;
		while(!error) {
			synchronized (objetoSincronizado[index]) {
				if(buffer.get(index) != pasadaLector) {
					error = true;
					System.out.println("Incorrecto");				
				}
				else {
					System.out.println("Correcto");
					if(++index == buffer.capacity()) {
						index = 0;
						pasadaLector++;
					};
				}
			}
			
		}
	}
	
	public void escribir() {
		int index = 0;
		while(true) {
			synchronized (objetoSincronizado[index]) {
				buffer.put(index, pasadaEscritor);
				if(++index == buffer.capacity()) {
					index = 0;
					pasadaEscritor++;
				};
			}
		}
	}
}

class Escritor extends Thread {
	
	private Check check;
	
	public Escritor(Check check) {
		this.check = check;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			check.escribir();
		}
	}
}

class Lector extends Thread {
	
	private Check check;
	
	public Lector(Check check) {
		this.check = check;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			check.leer();
		}
	}
}
