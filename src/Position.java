public enum Position {
	       TOP_LEFT(0), TOP(1), TOP_RIGHT(2), 
	       LEFT(3), RIGHT(4),
	       BOTTOM_LEFT(5), BOTTOM(6), BOTTOM_RIGHT(7);
		
		public int valor;
		Position(int valor) {
			this.valor = valor;
		}
		
		public int getValor(){
			return valor;
		}
	} 