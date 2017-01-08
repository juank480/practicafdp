//García Fonseca, Juan José.
//Giralda Herguedas, Álvaro.
import java.util.Scanner;

public class juagarc {

	public static void main(String[] args) {
		//Menu principal
		int [][] tablero = new int [0][0];
		menu_p(tablero);
		
		
	}
	public static int datos(int tamaño_inf,int tamaño_sup){//como en la práctica solo se pide como dato de entrada tipo int he hecho una validación de datos que sirva para todo y coja todo tipo de errores ya sea de rango o tipo.
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		boolean c = false;
		int datos = 0;
		while(c==false){
			try{
				datos = in.nextInt();//coge este mismo valor todo el rato porque se almacena.
				while(datos<tamaño_inf||datos>tamaño_sup){	
					System.out.println("el número ha de estar entre " + tamaño_inf+ " y " + tamaño_sup);
					datos = in.nextInt();
				}
				c=true;
			}
			catch(java.util.InputMismatchException e){
				System.out.println("tipo de datos equivocado.");
				in.next();//vacia el valor erroneo
			}
		}return datos;
		
	}	
	public static void menu_p(int [][]tablero){
		System.out.println("MODO DE JUEGO:\n1. Modo continuo\n2. Modo progresivo\n3. Iniciar todas las estadísticas del juego\n0. salir");
		
		int opc_menup = datos(0,3);//opción elegida en el menu principal.
		switch(opc_menup){
		case 0 : System.out.println("¡Hasta la próxima!");
		break;
		case 1 : int n_colores = 2;
		int tamaño_t = 9;
		modo_continuo(tablero, n_colores,tamaño_t);
		break;
		case 2 :int pasos1,pasos2,pasos3,pasos4;
		pasos1=0;
		pasos2=0;
		pasos3=0;
		pasos4=0;
		int tamaño_sup=1;
		modo_progresivo(tablero, pasos1, pasos2, pasos3, pasos4, tamaño_sup);
		break;
		case 3 :/*iniciar estadisticas*/; 
		break;
		}
	}
	public static int [][] crear_tableroAleatorio(int [][] tablero,int tamaño,int colores){
		tamaño+=2;
		tablero = new int[tamaño][tamaño];//primero crea un tablero con el número de filas y columnas + 2 para el marco y lo rellena de 0.
			for(int x = 0;x<tablero.length;x++){
				for(int y = 0; y<tablero[x].length;y++){
					tablero[x][y]=0;
				}
			}
			for(int i = 1;i<tablero.length-1;i++){//deja el marco de 0 para que no se active nunca el movimiento y crea el tablero de dimension adecuada
				for(int j = 1; j<tablero[i].length-1;j++){
					tablero[i][j] = (int)Math.floor(Math.random()*(colores)+1);
				}
			}
			return tablero;
	}
	public static void imprimir_tablero(int [][]tablero){//imprime el tablero sin marco, es decir la parte jugable solo
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
		for(int i = 1; i<tablero.length-1;i++){
			for(int j = 1; j<tablero[i].length-1;j++){
				System.out.print(tablero[i][j] + " ");
			}
			System.out.println("");
		}
	}
	public static void modo_continuo(int [][] tablero, int n_colores,int tamaño_t){
		tablero=crear_tableroAleatorio(tablero, tamaño_t, n_colores);
		boolean comprobar = true;
		int pasos=0;
		String tam;
		if(tamaño_t==9){
			tam="Pequeño(9x9)";
		}else if(tamaño_t==11){
			tam="Mediano(11x11)";
		}else tam="Grande(15x15)";
		while(comprobar==true){
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\nModo Continuo");
			imprimir_tablero(tablero);
			System.out.println("\nSi desea cerrar el juego actual pulse 0.\nPasos: "+pasos+"\nNúmero de colores: "+n_colores+"\nTamaño de tablero: "+ tam);
			int entrada = datos(0, n_colores);
			if(entrada==0){
				comprobar = false;
			}else{
				golpe(tablero, n_colores,entrada);
				pasos++;
				comprobar=comprobar_tablero(tablero,comprobar);
				if(comprobar==false)System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\t----Enhorabuena, has completado el nivel en "+pasos+" pasos----\n\n");
			}
		}
		System.out.println("0. Volver al menu principal.\n1. Nuevo tablero con mismas caraterísticas.\n2. Cambiar tamaño tablero y nº de colores.");
		int select = datos(0, 3);
		switch(select){
			case 0 : menu_p(tablero);
			break;
			case 1 : modo_continuo(tablero, n_colores, tamaño_t);
			break;
			case 2 :
				System.out.println("Escriba un nº de colores deseado entre 2 y 6.");
				n_colores=datos(2, 6);
				System.out.println("1. Tamaño pequeño(9x9)\n2. Tamaño mediano(11x11)\n3. Tamaño grande(15x15)");
				int select2 = datos(1, 3);
				switch (select2){
					case 1:tamaño_t=9;
					break;
					case 2:tamaño_t=11;
					break;
					case 3:tamaño_t=15;
					break;
			}
			modo_continuo(tablero, n_colores, tamaño_t);
			break;
		}
	}
	public static void modo_progresivo(int [][]tablero,int pasos1,int pasos2,int pasos3,int pasos4,int tamaño_sup){
		System.out.println("\n0. Menu principal.\n1. Nivel 1, (resuelto en "+pasos1+" pasos)\n2. Nivel 2, (resuelto en "+pasos2+" pasos)\n3"+ ". Nivel 3, (resuelto en "+pasos3+" pasos)\n4. Nivel 4, (resuelto en "+pasos4+" pasos)\n ha desbloqueado hasta el nivel "+tamaño_sup);
		int select = datos(0, tamaño_sup);
		switch(select){
		case 0:menu_p(tablero);
			break;
		case 1://primer tablero predefinido
			tablero=crear_tableroAleatorio(tablero, 9, 1);
			tablero[1][1]=2;
			boolean comprobar = true;
			while(comprobar==true){
				imprimir_tablero(tablero);
				int entrada = datos(1, 2);
					golpe(tablero, 2,entrada);
					pasos1++;
					comprobar=comprobar_tablero(tablero,comprobar);
					if(comprobar==false){
						System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\t\t----Enhorabuena, has completado el nivel en "+pasos1+" pasos----\n\n");
					}
					
				}
				tamaño_sup++;
				System.out.println("Escriba 1 para pasar al siguiente nivel y 0 para volver al menu:");
				int sig_nivel = datos(0, 1);
				if(sig_nivel==0){
					modo_progresivo(tablero, pasos1, pasos2, pasos3, pasos4,tamaño_sup);
					break;
			}
		case 2://segundo tablero predefinido.
			tablero=crear_tableroAleatorio(tablero, 9, 1);
			tablero[1][1]=3;
			tablero[1][2]=2;
			tablero[2][1]=2;
			comprobar = true;
			while(comprobar==true){
				imprimir_tablero(tablero);
				int entrada = datos(1, 3);
					golpe(tablero, 2,entrada);
					pasos2++;
					comprobar=comprobar_tablero(tablero,comprobar);
					if(comprobar==false){System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\t\t----Enhorabuena, has completado el nivel en "+pasos2+" pasos----\n\n");
					}
			}
			tamaño_sup++;
			System.out.println("Escriba 1 para pasar al siguiente nivel y 0 para volver al menu:");
			sig_nivel = datos(0, 1);
			if(sig_nivel==0){
				modo_progresivo(tablero, pasos1, pasos2, pasos3, pasos4,tamaño_sup);
				break;
		
			}
		case 3://tercer tablero predefinido.
			tablero=crear_tableroAleatorio(tablero, 9, 1);
			for(int i = 5;i<tablero.length-1;i++){
				for(int j = 5; j<tablero.length-1;j++){
					tablero[i][j]=2;
				}
			}
			for(int i = 6;i<tablero.length-1;i++){
				for(int j = 6; j<tablero.length-1;j++){
					tablero[i][j]=3;
				}

			}
			tablero[7][7]=1;
			for(int i = 1;i<tablero.length-1;i++){
				for(int j = 1;j<tablero.length-1;j++){
					if(i+2+j==10){
						tablero[i][j]=3;
					}
				}
			}
			for(int i = 1;i<tablero.length-1;i++){
				for(int j = 1;j<tablero.length-1;j++){
					if(i+3+j==10){
						tablero[i][j]=2;
					}
				}
			}
			comprobar = true;
			while(comprobar==true){
				imprimir_tablero(tablero);
				int entrada = datos(1, 3);
					golpe(tablero, 2,entrada);
					pasos3++;
					comprobar=comprobar_tablero(tablero,comprobar);
					if(comprobar==false){System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\t\t----Enhorabuena, has completado el nivel en "+pasos3+" pasos----\n\n");
					}
			}
			tamaño_sup++;
			System.out.println("Escriba 1 para pasar al siguiente nivel y 0 para volver al menu:");
			sig_nivel = datos(0, 1);
			if(sig_nivel==0){
				modo_progresivo(tablero, pasos1, pasos2, pasos3, pasos4,tamaño_sup);
				break;
			}
		case 4:
			tablero=crear_tableroAleatorio(tablero, 9, 1);
			for(int i =3;i<tablero.length-1;i++){
				tablero[i][1]=3;
			}
			for(int j = 2;j<tablero.length-1;j++){
				tablero[1][j]=2;
				tablero[2][j]=2;
				tablero[4][j]=2;
				tablero[6][j]=2;
				tablero[8][j]=2;
			}
				comprobar = true;
				while(comprobar==true){
					imprimir_tablero(tablero);
					int entrada = datos(1, 3);
						golpe(tablero, 2,entrada);
						comprobar=comprobar_tablero(tablero,comprobar);
						if(comprobar==false){System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\t\t----Enhorabuena, has completado el nivel en "+pasos4+" pasos----\n\n");
						}
				}
				tamaño_sup++;
				System.out.println("Escriba 1 para pasar al siguiente nivel y 0 para volver al menu:");
				sig_nivel = datos(0, 1);
				if(sig_nivel==0){
					modo_progresivo(tablero, pasos1, pasos2, pasos3, pasos4,tamaño_sup);
					break;
			}else{
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nHas completado los 4 niveles.");
				modo_progresivo(tablero, pasos1, pasos2, pasos3, pasos4,tamaño_sup);
			}
		}
	}
	public static int[][] golpe(int [][]tablero, int n_colores,int entrada){//el gollpe se realiza de forma recursiva llamando sucesivamente al metodo siguiente casilla que va avanzando por el tablero.
		
		int aux = tablero[1][1];
		while(aux==entrada){//es una pequeña validación de datos para evitar un error de ejecución por como está hecho el golpe.
			System.out.println("El movimiento no se realiza debido a que el nº introducido es el mismo nº de la primera casilla.");
			entrada = datos(1,n_colores);
		}
		int i = 1; int j = 1;
		do{
		
			tablero[i][j]=entrada;
			tablero=siguiente_casilla(tablero, i, j, aux, entrada);
			
		}while(tablero[i+1][j]==aux||tablero[i-1][j]==aux||tablero[i][j-1]==aux||tablero[i][j+1]==aux);
		return tablero;
	}
	public static int[][] siguiente_casilla(int[][]tablero,int i, int j, int aux, int e){
		do{	
			if(tablero[i][j+1]==aux){
				j++;
				tablero[i][j]=e;
				tablero=siguiente_casilla(tablero, i, j, aux, e);
			}
			if(tablero[i+1][j]==aux){
				i++;
				tablero[i][j]=e;
				tablero=siguiente_casilla(tablero, i, j, aux, e);
			}
			if(tablero[i][j-1]==aux){
				j--;
				tablero[i][j]=e;
				tablero=siguiente_casilla(tablero, i, j, aux, e);
			}
			if(tablero[i-1][j]==aux){
				i--;
				tablero[i][j]=e;
				tablero=siguiente_casilla(tablero, i, j, aux, e);
			}
		}while(tablero[i+1][j]==aux||tablero[i-1][j]==aux||tablero[i][j-1]==aux||tablero[i][j+1]==aux);
		return tablero;
	}
	public static boolean comprobar_tablero(int[][]tablero,boolean comprobar){
		comprobar=false;
		for(int i = 1;i<tablero.length-1;i++){
			for(int j = 1;j<tablero.length-1;j++){
				if(tablero[1][1]!=tablero[i][j]){
					comprobar = true;
				}
			}
		}
		return comprobar;
	}
}
