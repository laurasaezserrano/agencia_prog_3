package agencia_prog_3_GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout; 
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Frame; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.JDialog; 
import javax.swing.JProgressBar; 

import agencia_prog_3_thread.VentanaBuscandoExcursion;
import agencia_prog_3_thread.VentanaBuscandoVuelo;
import agencia_prog_3_thread.VentanaRuletaDeLaSuerte;

public class VentanaInicio extends JFrame{
	private static final long serialVersionUID = 1L;
	private JTextField titulo;
	private static final String MENSAJE_GANADOR = "¡Felicidades! Has ganado un 15% de descuento en tu próximo viaje.";

	public VentanaInicio(){
		JPanel mainpanel = new JPanel();
		JPanel panel1 = new JPanel(new GridLayout(2, 6, 40, 40));
		String [] nombresboton = {
			"Perfil",
			"Reservas",
			"Ofertas",
			"Vuelos",
			"Excursiones",
			"Contacto"
		};

		for (int i = 0; i < 6; i++) {
			int numero = i+1;
			JButton boton = new JButton(nombresboton [numero-1]);
			try {
				 BufferedImage originalImage = ImageIO.read(new File("resources/images/"+nombresboton[i]+".png"));

				 int anchoDeseado = 110;
				 int altoDeseado = 80;

				 Image scaledImage = originalImage.getScaledInstance(anchoDeseado, altoDeseado, Image.SCALE_SMOOTH);
				 ImageIcon iconoOriginal = new ImageIcon(scaledImage);

				if (originalImage != null) {
					ImageIcon originalIcon = new ImageIcon(originalImage);
					ImageIcon resizedIcon = new ImageIcon(originalIcon.getImage().getScaledInstance(90, 90, java.awt.Image.SCALE_SMOOTH));
					boton.setIcon(resizedIcon);

					boton.setHorizontalTextPosition(SwingConstants.CENTER);
					boton.setVerticalTextPosition(SwingConstants.BOTTOM);

					boton.setFont(new Font("SansSerif", Font.BOLD, 14));

				} else {
					System.err.println("No se pudo encontrar la imagen: " + nombresboton[i]);
				}
			} catch (Exception e) {
				System.err.println("Error al cargar la imagen " + nombresboton[i] + ": " + e.getMessage());
			}

			boton.setPreferredSize(new Dimension(220, 180));
			boton.setBackground(new Color(255, 255, 255));
			boton.setBorder(new LineBorder(Color.BLACK, 2));
			boton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String user = AlmacenajeSesion.getNombreUsuario();
					String pass = AlmacenajeSesion.getPassword();
					
					VentanaInicio.this.setVisible(false);

					if (nombresboton[numero-1].equals("Perfil")) {
						VentanaUser userInfo = new VentanaUser(user, pass, VentanaInicio.this);
						userInfo.setVisible(true);

					} else if (nombresboton[numero-1].equals("Reservas")) {
						VentanaReservas vReservas = new VentanaReservas();
						vReservas.setVisible(true);
						
					} else if (nombresboton[numero-1].equals("Ofertas")) {
						CuadriculaOfertas vOfertas = new CuadriculaOfertas();
						vOfertas.setVisible(true);
						
					} else if (nombresboton[numero-1].equals("Contacto")) {
						VentanaContacto ventanaContacto = new VentanaContacto(VentanaInicio.this);
						ventanaContacto.setVisible(true);
					
					} else if (nombresboton[numero-1].equals("Vuelos")) {
					 	abrirVueloHotelhilo();
						
					} else if (nombresboton[numero-1].equals("Excursiones")) {
						abrirExcursioneshilo();
					}

				}

			});
		panel1.add(boton);

		}

		mainpanel.add(panel1);
		add(mainpanel);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(new Color(50, 150, 200));
		
		JButton ruletaButton = new JButton();
		try {
			BufferedImage imagenRuleta = ImageIO.read(new File("resources/images/ruleta.png")); 
			Image scaledImageRuleta = imagenRuleta.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			ImageIcon iconoRuleta = new ImageIcon(scaledImageRuleta);
			ruletaButton.setIcon(iconoRuleta);
		} catch (Exception e) {
			System.err.println("Error al cargar la imagen ruleta.png: " + e.getMessage());
			ruletaButton.setText("Dados"); 
		}
		ruletaButton.setPreferredSize(new Dimension(60, 60));
		ruletaButton.setBackground(new Color(255, 255, 255));
		ruletaButton.setBorder(new LineBorder(Color.BLACK, 2));
		
		ruletaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				iniciarRuleta();
			}
		});
		
		bottomPanel.add(ruletaButton); 

		JButton menuButton = new JButton();
		try {
			BufferedImage imagenCerrar = ImageIO.read(new File("resources/images/cerrar.png"));
			Image scaledImageCerrar = imagenCerrar.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			ImageIcon iconoCerrar = new ImageIcon(scaledImageCerrar);
			menuButton.setIcon(iconoCerrar);
		} catch (Exception e) {
			System.err.println("Error al cargar la imagen cerrar.png: " + e.getMessage());
			menuButton.setText("Menú");
		}
		menuButton.setPreferredSize(new Dimension(60, 60));
		menuButton.setBackground(new Color(255, 255, 255));
		menuButton.setBorder(new LineBorder(Color.BLACK, 2));
		
		JPopupMenu popupMenu = new JPopupMenu();
		
		JMenuItem cerrarSesion = new JMenuItem("Cerrar sesión");
		cerrarSesion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AlmacenajeSesion.limpiarSesion();
				Ventana1Login ventanaLogin = new Ventana1Login();
				ventanaLogin.setVisible(true);
				VentanaInicio.this.dispose();
			}
		});
		
		JMenuItem cerrarApp = new JMenuItem("Cerrar aplicación");
		cerrarApp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		popupMenu.add(cerrarSesion);
		popupMenu.addSeparator();
		popupMenu.add(cerrarApp);
		
		menuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				popupMenu.show(menuButton, 0, menuButton.getHeight());
			}
		});
		
		bottomPanel.add(menuButton);
		add(bottomPanel, BorderLayout.SOUTH);
		
		setTitle("Bienvenid@ a SunnyAgencia");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(850, 550);
		setMaximumSize(getToolkit().getScreenSize());
		setResizable(false);
		setLocationRelativeTo(null);
		panel1.setBackground(new Color(50, 150, 200));
		mainpanel.setBackground(new Color(50, 150, 200));

		titulo = new JTextField();
		titulo.setText("Bienvenid@ a SunnyAgencia");
		titulo.setEditable(false);
		titulo.setHorizontalAlignment(JTextField.CENTER);
		titulo.setFont(new Font("Times new Roman", Font.BOLD, 30));
		titulo.setBackground(new Color(255, 255, 255));
		add(titulo, BorderLayout.NORTH);

	}

	private void iniciarRuleta() { 
		VentanaInicio.this.setVisible(false);
		
		VentanaRuletaDeLaSuerte dadosDialog = new VentanaRuletaDeLaSuerte(this);	
		
		final int[] resultados = new int[2];
		
	 	Thread hiloDado1 = new Thread(() -> {
	 	 	try {
	 	 		Thread.sleep(2000 + new Random().nextInt(2000)); 
				
				int resultadoDado = new Random().nextInt(6) + 1;
				resultados[0] = resultadoDado;
                
                SwingUtilities.invokeLater(() -> {
                    dadosDialog.actualizarDado(1, resultadoDado);
                });
	 	 	} catch (InterruptedException ex) {
	 	 		Thread.currentThread().interrupt();
	 	 	}
	 	});
        
	 	Thread hiloDado2 = new Thread(() -> {
	 	 	try {
	 	 		Thread.sleep(2000 + new Random().nextInt(2000)); 
				
				int resultadoDado = new Random().nextInt(6) + 1;
				resultados[1] = resultadoDado;
                
                SwingUtilities.invokeLater(() -> {
                    dadosDialog.actualizarDado(2, resultadoDado);
                });
	 	 	} catch (InterruptedException ex) {
	 	 		Thread.currentThread().interrupt();
	 	 	}
	 	});
        
        Thread hiloCoordinador = new Thread(() -> {
            hiloDado1.start();
            hiloDado2.start();
            
            try {
                hiloDado1.join();
                hiloDado2.join();
                
                Thread.sleep(2000); 

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

	 	 	int dado1 = resultados[0];
	 	 	int dado2 = resultados[1];
	 	 	int suma = dado1 + dado2;
	 	 	boolean haGanado = suma == 7;

	 	 	SwingUtilities.invokeLater(() -> {
	 	 		dadosDialog.cerrarVentana(); 
				
				String mensajeResultado = String.format("🎲 Dado 1: %d\n🎲 Dado 2: %d\nSuma Total: %d.\n\n", dado1, dado2, suma);

				if (haGanado) {
					JOptionPane.showMessageDialog(VentanaInicio.this, 
						mensajeResultado + MENSAJE_GANADOR, 
						"¡HAS GANADO (Suma 7)!", 
						JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(VentanaInicio.this, 
						mensajeResultado + "La suma no es 13. No has obtenido descuento. ¡Inténtalo de nuevo!", 
						"Inténtalo de nuevo", 
						JOptionPane.WARNING_MESSAGE);
				}
				
				VentanaInicio.this.setVisible(true);
	 	 	});

	 	});

	 	hiloCoordinador.start();
	 	dadosDialog.setVisible(true); 
	}


	private void abrirVueloHotelhilo() {
		VentanaInicio.this.setVisible(false);
		
		VentanaBuscandoVuelo dialog = new VentanaBuscandoVuelo(this);
	 	Thread hilo = new Thread(() -> {
	 	 	try {
	 	 		Thread.sleep(10000);
	 	 	} catch (InterruptedException ex) {
	 	 		ex.printStackTrace();
	 	 	}
	 	 	SwingUtilities.invokeLater(() -> {
	 	 		dialog.cerrarVentana();
	 	 		VentanaVueloYHotel v = new VentanaVueloYHotel();
	 	 		v.setVisible(true);
	 	 	});

	 	});

	 	hilo.start();
	 	dialog.setVisible(true);
	}

	private void abrirExcursioneshilo() {
		VentanaInicio.this.setVisible(false);
		
	 	VentanaBuscandoExcursion dialog = new VentanaBuscandoExcursion(this);
	 	Thread hilo = new Thread(() -> {
	 	 	try {
	 	 		Thread.sleep(6000);
	 	 	} catch (InterruptedException e) {}

	 	 	SwingUtilities.invokeLater(() -> {
	 	 		dialog.cerrar();
	 	 		new VentanaExcursiones().setVisible(true);
	 	 	});

	 	});

	 	hilo.start();
	 	pack();
	 	dialog.setVisible(true);
	}


	public static void main(String[] args) {
		VentanaInicio VentanaInicial = new VentanaInicio();
		VentanaInicial.setVisible(true);
	}
}