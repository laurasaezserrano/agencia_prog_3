package es.deusto.ingenieria.prog3.checkin.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

import es.deusto.ingenieria.prog3.checkin.domain.Seat;
import es.deusto.ingenieria.prog3.checkin.domain.Seat.SeatClass;
import es.deusto.ingenieria.prog3.checkin.domain.SeatAllocator;

public class JDialogSeatAllocator extends JDialog {

	private static final long serialVersionUID = 1L;
	private SeatAllocator seatAllocator;
	private List<Seat> seatsProposed;
	
	private JComboBox<SeatClass> jComboSeatClass;
	private JSpinner jSpinnerPassengers;	
	private JButton jButtonCancel = new JButton("Cancel");
	private JButton jButtonFindSeats = new JButton("Find Seats");
	private JButton jButtonConfirm = new JButton("Confirm");
	private JLabel jLabelSeats = new JLabel("");
		
	//Se añade como parámetro un JTable para repintar la tabla de asientos.
	public JDialogSeatAllocator(SeatAllocator seatAllocator, JTable jTableSeats) {
		this.seatAllocator = seatAllocator;		
		
		jComboSeatClass = new JComboBox<>(new Vector<>(this.seatAllocator.getSeatClasses()));		
		jSpinnerPassengers = new JSpinner(new SpinnerNumberModel(1, 1, this.seatAllocator.getRowSize(), 1));				
		
		JPanel jPanelReservation = new JPanel();
		jPanelReservation.setBorder(new TitledBorder("Reservation details"));
		jPanelReservation.setLayout(new GridLayout(3, 1));
		
		JPanel jPanelSeatClass = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jPanelSeatClass.add(new JLabel("Select SeatClass:"));
		jPanelSeatClass.add(jComboSeatClass);
		
		JPanel jPanelPassengers = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jPanelPassengers.add(new JLabel("Number of passengers: "));
		jPanelPassengers.add(jSpinnerPassengers);

		jLabelSeats.setHorizontalAlignment(JLabel.CENTER);
		
		jPanelReservation.add(jPanelSeatClass);
		jPanelReservation.add(jPanelPassengers);
		jPanelReservation.add(jLabelSeats);
		
		JPanel jPanelButtons = new JPanel(new FlowLayout());
		jPanelButtons.add(jButtonCancel);
		jPanelButtons.add(jButtonFindSeats);
		jPanelButtons.add(jButtonConfirm);		
		jButtonConfirm.setEnabled(false);
		
		jButtonCancel.addActionListener((e) -> dispose());
		
		jButtonFindSeats.addActionListener((e) -> {
			SeatClass seatClass = (SeatClass) jComboSeatClass.getSelectedItem();
			int number = (int) jSpinnerPassengers.getValue();
					
			seatsProposed = seatAllocator.findSeats(seatClass, number);
			
			if (seatsProposed != null) {
				String auxText = "Proposed seats: ";

				for (Seat s : seatsProposed) {
					auxText += s.getRow() + s.getLetter().toString() + ", "; 
				}
				
				jLabelSeats.setText(auxText);
				jButtonConfirm.setEnabled(true);
			} else {
				jLabelSeats.setText(String.format("There are no %d adjacent seats in %s", number, seatClass));				
				jButtonConfirm.setEnabled(false);			
			}					
		});
		
		jButtonConfirm.addActionListener((e) -> {
			if (seatsProposed != null) {
				seatAllocator.confirmSeats(seatsProposed);
				
				//Se repinta la tabla de asientos
				jTableSeats.repaint();
			}
			
			dispose();
		});
				
		add(new JPanel(), BorderLayout.NORTH);
		add(new JPanel(), BorderLayout.EAST);
		add(new JPanel(), BorderLayout.WEST);
		add(jPanelReservation, BorderLayout.CENTER);
		add(jPanelButtons, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle("Random seats allocator");		
		setSize(360, 200);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}