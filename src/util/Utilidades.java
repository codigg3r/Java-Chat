package util;

import java.io.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Jordi
 */

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
public class Utilidades {

	protected static StringBuilder sb = new StringBuilder();

	private final static BufferedReader entradaConsola = new java.io.BufferedReader ( new java.io.InputStreamReader (System.in));

	//.........................................................................
	//.........................................................................
	public static void muestraMensajeC (String mensaje) {
		System.out.println (mensaje);
	}

	//.........................................................................
	//.........................................................................
	public static int leerEnteroC (String mensaje) {
		return Integer.parseInt( leerTextoC(mensaje));
	}

	//.........................................................................
	//.........................................................................
	public static double leerRealC (String mensaje) {
		return  Double.parseDouble( leerTextoC(mensaje));
	}

	//.........................................................................
	//.........................................................................
	public static String leerTextoC (String mensaje) {
		try {
			System.out.print(mensaje);
			return entradaConsola.readLine();
		} // ()
		catch (IOException ex) {
			return "";
		}
	} // ()

	//.........................................................................
	//.........................................................................
	public static String leerTextoG (String mensaje) {
		String leido = JOptionPane.showInputDialog(mensaje);
		if (leido == null) {
			return "";
		}
		return leido;
	} // ()

	//.........................................................................
	//.........................................................................
	public static int leerEnteroG (String mensaje) {
		int v = Integer.parseInt( leerTextoG(mensaje));
		return v;
	} // ()

	//.........................................................................
	//.........................................................................
	public static double leerRealG (String mensaje) {
		return  Double.parseDouble( leerTextoG(mensaje));
	} // ()

	//.........................................................................
	//.........................................................................
	public static void muestraMensajeG (String mensaje) {
		JOptionPane.showMessageDialog(null, mensaje);
	} // ()


		//.........................................................................
	//.........................................................................
	public static  void copiarBytes (byte[] destino, int inicioDestino, byte[] origen, int inicioOrigen, int longitud) {
		for (int i=0; i<=longitud-1; i++) {
			destino[inicioDestino+i] = origen[inicioOrigen+i];
		}
	} // ()

	//.........................................................................
	//.........................................................................
	public static byte[] juntarBytes (byte[] ... lista) {
		int totalBytes = 0;
		for (byte[] arr : lista) {
			totalBytes += arr.length;
		}

		byte[] result = new byte[totalBytes];
		int inicioDestino = 0;

		for (byte[] arr : lista) {
			copiarBytes(result, inicioDestino, arr, 0, arr.length);
			inicioDestino += arr.length;
		}


		return result;
	} // ()
	public static void guardarMensaje(String mensaje){
		sb.append(mensaje + "\n");

	}
	public static void guardarChat(){
		try {
			//OPEN
			FileOutputStream fos = new FileOutputStream ("chat.txt");
			PrintWriter salida = new PrintWriter(fos);

			//WRITE
			salida.println(sb.toString());
			//CLOSE

			salida.close();
			fos.close();

		} catch (FileNotFoundException err) {
			System.out.println("fichero no ha encontrado: " + err.getMessage());
		}catch (IOException err){
			System.out.println("Error entrada/salida: " + err.getMessage());
		}
	}

	public static ArrayList<Float> mathOp(String equ) {
		ArrayList<Float> resArray = new ArrayList<Float>();
		float firstVar = 0F;
		float secondVar = 0F;
		float resValue = 0;
		float resCode = 0;

		String op = String.valueOf(equ.charAt(0));
		try{
			firstVar = Float.parseFloat(equ.substring(equ.indexOf(" ")+1,
					equ.lastIndexOf(" ")));

			secondVar = Float.parseFloat(equ.substring(equ.lastIndexOf(" ")+1));


		}catch (NumberFormatException exception){
			// exeption
			resCode = 1;
		}catch (IndexOutOfBoundsException exception){
			// exeption
			resCode = 2;
		}

		try {
			switch (op){
				case "+":
					resValue = firstVar + secondVar;
					break;
				case "-":
					resValue = firstVar - secondVar;
					break;
				case "*":
					resValue = firstVar * secondVar ;
					break;
				case "/":
					resValue = firstVar / secondVar;
					break;

			}
		}catch (ArithmeticException e){
			// some aritmetic probles
			resCode = 3;
		}catch (NumberFormatException e){
			// type exception
			resCode = 1;
		}
		resArray.add(resValue);
		resArray.add(resCode);

		return resArray;
	}

	//public static byte[] trimByte(); // se puede hacer con Arrays.copyOf
} // class
