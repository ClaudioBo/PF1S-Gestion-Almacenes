import javax.swing.JOptionPane;

public class Main {
	
	/*
	 * Proyecto final 1er semestre
	 * Programacion I
	 * 12 de noviembre del 2019
	 */

	static Articulo[][] articulos;

	public static void main(String[] args) {

		// Creamos un vector bidimensional, con 4 filas y 30 columnas, osea '4
		// sucursales' y '30 articulos'

		articulos = new Articulo[4][30];

		boolean continuar = true;
		do {

			String mensaje = "--- Menu Principal ---" + "\n1. Alta en Catálogo de Articulos"
					+ "\n2. Alta de Existencia / Precio por sucursal" + "\n3. Actualización de Existencia / Precio"
					+ "\n4. Baja a articulo" + "\n5. Consulta total de articulos por sucursal"
					+ "\n6. Consulta total costos de inventario por articulo" + "\n7. Terminar";

			int opcion = 0;

			try {
				opcion = Integer
						.valueOf(JOptionPane.showInputDialog(null, mensaje, "", JOptionPane.INFORMATION_MESSAGE));
			} catch (Exception e) {
			}

			switch (opcion) {
			case 1:
				altaCatalogo();
				break;
			case 2:
				modificarArticulo(false);
				break;
			case 3:
				modificarArticulo(true);
				break;
			case 4:
				bajaArticulo();
				break;
			case 5:
				consultaArticulos();
				break;
			case 6:
				consultaTotal();
				break;
			case 7:
				continuar = false;
				break;
			default:
				JOptionPane.showMessageDialog(null, "Opcion invalida, ingrese una del 1 al 7", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		} while (continuar);
	}

	public static void altaCatalogo() {

		String descripcionArticulo = JOptionPane.showInputDialog(null, "Ingrese la descripcion del articulo", "",
				JOptionPane.INFORMATION_MESSAGE);

		if (descripcionArticulo.length() <= 0) {
			JOptionPane.showMessageDialog(null, "Escribe una descripcion, minimo 1 caracter", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		boolean hayLibre = false;

		int id = 0;

		// Comprobaremos aqui si ya existe
		for (int i = 0; i < articulos[0].length; i++) {
			if (articulos[0][i] != null) {
				if (articulos[0][i].getDescripcion().toLowerCase().equals(descripcionArticulo.toLowerCase())) {
					JOptionPane.showMessageDialog(null, "Este articulo ya existe, ingrese otro", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		}

		for (int i = 0; i < articulos[0].length; i++) {
			if (articulos[0][i] == null) {
				hayLibre = true;
				id = i;
				break;
			}
		}

		if (hayLibre) {

			for (int i = 0; i < articulos.length; i++) { // Agregamos el articulo en todas las sucursales
				Articulo art = new Articulo();

				art.setId(id);
				art.setDescripcion(descripcionArticulo);

				articulos[i][id] = art;
			}

			String mensaje = "El articulo fue ingresado con la identificacion '" + id + "'"
					+ "\n¿Desea continuar con otro articulo?";

			int opcion = JOptionPane.showConfirmDialog(null, mensaje, "", JOptionPane.YES_NO_OPTION);

			if (opcion == 0) {
				altaCatalogo();
			}

		} else {
			JOptionPane.showMessageDialog(null, "No hay mas espacio libre", "", JOptionPane.ERROR_MESSAGE);

		}

	}

	public static void modificarArticulo(boolean mostrarMensaje) {

		Articulo art = null;

		String descripcionArticulo = JOptionPane.showInputDialog(null, "Ingrese la descripcion del articulo", "",
				JOptionPane.INFORMATION_MESSAGE);

		int sucursal = 0;

		try {

			sucursal = Integer.valueOf(JOptionPane.showInputDialog(null, "Ingresa el numero de la sucursal (0-3)", "",
					JOptionPane.INFORMATION_MESSAGE));

			if (sucursal < 0 || sucursal > 3) {
				JOptionPane.showMessageDialog(null, "No existe la sucursal especificada, escribe una del 0 al 3", "",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Opcion invalida, porfavor ingresa un numero", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;

		}

		boolean existe = false;
		int id = 0;

		for (int i = 0; i < articulos[0].length; i++) {
			if (articulos[sucursal][i] != null) {
				if (articulos[sucursal][i].getDescripcion().toLowerCase().equals(descripcionArticulo.toLowerCase())) {
					art = articulos[sucursal][i];
					id = i;
					existe = true;
					break;
				}
			}
		}

		if (existe) {

			if (mostrarMensaje) {

				String artcantidad = "No especificado";
				String artprecio = "No especificado";

				if (art.getCantidad() != -1) {
					artcantidad = art.getCantidad() + "";
				}

				if (art.getCantidad() == 0) {
					artcantidad = "Agotado";
				}

				if (art.getPrecio() != -1) {
					artprecio = art.getPrecio() + "";
				}

				String mensaje = "Informacion del producto:" + "\n    Articulo: " + art.getDescripcion()
						+ "\n    Sucursal: " + sucursal + "\n    Cantidad: " + artcantidad + "\n    Precio: "
						+ artprecio;
				JOptionPane.showMessageDialog(null, mensaje, "Informacion", JOptionPane.INFORMATION_MESSAGE);
			}

			int cantidad = 0;
			double precio = 0;

			try {
				cantidad = Integer.valueOf(JOptionPane.showInputDialog(null, "Ingresa la cantidad del articulo", "",
						JOptionPane.INFORMATION_MESSAGE));
				precio = Double.valueOf(JOptionPane.showInputDialog(null, "Ingresa el precio del articulo", "",
						JOptionPane.INFORMATION_MESSAGE));

				if (cantidad < 0 || precio < 0.0) {
					JOptionPane.showMessageDialog(null, "Uno de los numero ingresados no puede ser negativo", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Opcion invalida, porfavor ingresa un numero valido", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			articulos[sucursal][id].setCantidad(cantidad);
			articulos[sucursal][id].setPrecio(precio);

			int opcion = JOptionPane.showConfirmDialog(null, "¿Desea asignar otro articulo?", "",
					JOptionPane.YES_NO_OPTION);
			if (opcion == 0) {
				modificarArticulo(mostrarMensaje);
			}

		} else {
			JOptionPane.showMessageDialog(null, "No existe el articulo especificado", "", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void bajaArticulo() {

		String descripcionArticulo = JOptionPane.showInputDialog(null, "Ingrese la descripcion del articulo", "",
				JOptionPane.INFORMATION_MESSAGE);

		Articulo art = null;
		int id = 0;

		for (int i = 0; i < articulos[0].length; i++) {
			if (articulos[0][i] != null) {
				if (articulos[0][i].getDescripcion().toLowerCase().equals(descripcionArticulo.toLowerCase())) {
					art = articulos[0][i];
					id = i;
					break;
				}
			}
		}

		if (art != null) {
			String mensaje = "Informacion del producto:" + "\n    Articulo: " + art.getDescripcion();

			for (int i = 0; i < articulos.length; i++) {
				String artcantidad = "No especificado";
				String artprecio = "No especificado";

				if (articulos[i][id].getCantidad() != -1) {
					artcantidad = articulos[i][id].getCantidad() + "";
				}

				if (articulos[i][id].getCantidad() == 0) {
					artcantidad = "Agotado";
				}

				if (articulos[i][id].getPrecio() != -1) {
					artprecio = articulos[i][id].getPrecio() + "";
				}

				mensaje += "\n    Sucursal: " + i + "\n    Cantidad: " + artcantidad + "\n    Precio: " + artprecio
						+ "\n";
			}
			mensaje += "\n¿Desea borrar este articulo?";

			int opcion = JOptionPane.showConfirmDialog(null, mensaje, "", JOptionPane.YES_NO_OPTION);
			if (opcion == 0) {

				// Se borra el articulo en todas las sucursales...
				for (int i = 0; i < articulos.length; i++) {
					articulos[i][id] = null;
				}
			}

			int opcion2 = JOptionPane.showConfirmDialog(null, "¿Desea bajar otro articulo?", "",
					JOptionPane.YES_NO_OPTION);
			if (opcion2 == 0) {
				bajaArticulo();
			}

		} else {
			JOptionPane.showMessageDialog(null, "No existe el articulo especificado", "", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void consultaArticulos() {

		// Creo una variable que guardara el mensaje que sera mostrado
		String mensaje = "Articulos disponibles en cada sucursal:\n";

		for (int i = 0; i < articulos.length; i++) { // Recorreremos todas las sucursales
			int sumaSucursal = 0; // Cada sucursal tendra X cantidad de articulos, aqui se iran sumando

			for (int j = 0; j < articulos[i].length; j++) { // Recorreremos todos los articulos de la sucursal
															// seleccionada
				if (articulos[i][j] != null) { // Si hay un articulo en esta posicion
					if (articulos[i][j].getCantidad() > 0) { // Preguntamos si la cantidad es mayor a 0
						sumaSucursal += articulos[i][j].getCantidad(); // Si es mayor a 0, sumamos los articulos
																		// disponibles a 'sumaSucursal'
					}
				}
			}

			// Despues de que se haya sumado los articulos disponibles
			// Agregamos un texto nuevo a la variable 'mensaje' que diga 'Sucursal X: Y
			// articulos'
			// Agregamos este mensaje con += envez de =, ya que si lo hacemos con =
			// Se borrarian los mensajes que se han asignado antes.
			mensaje += "\n  Sucursal " + i + ": " + sumaSucursal + " articulos";

		}

		JOptionPane.showMessageDialog(null, mensaje, "Consulta total", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void consultaTotal() {
		// Obtendremos primero el articulo dentro de la primera sucursal

		String mensaje = "Consulta total:\n";

//		Primero comprobaremos si hay articulos guardados en el almacen

		boolean estaVacio = true;

		for (int i = 0; i < articulos[0].length; i++) { // Pasare por todos los articulos
			if (articulos[0][i] != null) { // Compruebo si hay un articulo guardado
				estaVacio = false; // Si es asi, entonces ejecutaremos lo de abajo
				break;
			}
		}

		if (!estaVacio) {

			for (int i = 0; i < articulos[0].length; i++) { // Primero cada articulo
				if (articulos[0][i] != null) {
					String descripcion = articulos[0][i].getDescripcion();
					double costoInventario = 0;

					for (int j = 0; j < articulos.length; j++) { // Paso por todas las sucursales

						for (int z = 0; z < articulos.length; z++) { // Pregunto cada articulo de una sucursal, si es el
																		// mismo de arriba, le sumo
							if (articulos[j][i] != null) {
								if (articulos[j][i].getDescripcion().equals(descripcion)) {
									if (articulos[j][i].getCantidad() > 0 && articulos[j][i].getPrecio() > 0) {
										costoInventario += articulos[j][i].getPrecio() * articulos[j][i].getCantidad();
										break; // Ya que se sumo, paramos este ciclo, o se sumara 4 veces mas
									}
								}
							}
						}

					}

					mensaje += "\n  " + descripcion + ": $" + costoInventario + " pesos";
				}

			}

			JOptionPane.showMessageDialog(null, mensaje);
		} else {
			JOptionPane.showMessageDialog(null, "El almacen esta vacio.", "Error", JOptionPane.ERROR_MESSAGE);
		}

	}

}
