package com.countdown;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class CountDown extends JFrame {

	private JPanel contentPane;
	private int pbValue;
	private int cdTimestamp;
	public JSpinner spinnerHours;
	public JSpinner spinnerMinutes;
	public JSpinner spinnerSecondes;
	public JProgressBar pbCountDown;
	public SimpleDateFormat format1;
	public JButton btnPause;
	public JButton btnStart;
	public JButton btnStop;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CountDown frame = new CountDown();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CountDown() {
		setResizable(false);
		setTitle("Compte à rebours");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 316);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTimeSelect = new JLabel("Sélectionner le temps du compte a rebours");
		lblTimeSelect.setHorizontalAlignment(SwingConstants.CENTER);
		lblTimeSelect.setForeground(new Color(0, 0, 255));
		lblTimeSelect.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTimeSelect.setBounds(10, 11, 414, 14);
		contentPane.add(lblTimeSelect);

		JLabel lblHours = new JLabel("Heures");
		lblHours.setForeground(new Color(0, 128, 0));
		lblHours.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblHours.setBounds(122, 36, 46, 14);
		contentPane.add(lblHours);

		spinnerHours = new JSpinner();
		spinnerHours.setModel(new SpinnerNumberModel(0, 0, 23, 1));
		spinnerHours.setFont(new Font("Tahoma", Font.PLAIN, 18));
		spinnerHours.setBounds(122, 50, 53, 28);
		contentPane.add(spinnerHours);

		spinnerMinutes = new JSpinner();
		spinnerMinutes.setModel(new SpinnerNumberModel(0, 0, 59, 1));
		spinnerMinutes.setFont(new Font("Tahoma", Font.PLAIN, 18));
		spinnerMinutes.setBounds(185, 50, 53, 28);
		contentPane.add(spinnerMinutes);

		spinnerSecondes = new JSpinner();
		spinnerSecondes.setModel(new SpinnerNumberModel(0, 0, 59, 1));
		spinnerSecondes.setFont(new Font("Tahoma", Font.PLAIN, 18));
		spinnerSecondes.setBounds(248, 50, 53, 28);
		contentPane.add(spinnerSecondes);

		JLabel lblMinutees = new JLabel("Minutes");
		lblMinutees.setForeground(new Color(0, 128, 0));
		lblMinutees.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMinutees.setBounds(185, 36, 46, 14);
		contentPane.add(lblMinutees);

		JLabel lblSecondes = new JLabel("Secondes");
		lblSecondes.setForeground(new Color(0, 128, 0));
		lblSecondes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSecondes.setBounds(248, 36, 71, 14);
		contentPane.add(lblSecondes);

		pbCountDown = new JProgressBar();
		pbCountDown.setForeground(new Color(0, 0, 0));
		pbCountDown.setStringPainted(true);
		pbCountDown.setString("00:00:00");
		pbCountDown.setToolTipText("");
		pbCountDown.setFont(new Font("Tahoma", Font.BOLD, 16));
		pbCountDown.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		pbCountDown.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		pbCountDown.setBounds(10, 105, 414, 28);
		contentPane.add(pbCountDown);

		btnStart = new JButton("Démarrer");
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnStart.setBounds(122, 144, 179, 33);
		contentPane.add(btnStart);

		btnPause = new JButton("Pause");
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPause.setEnabled(false);
				btnStart.setEnabled(true);
			}
		});
		btnPause.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPause.setBounds(122, 188, 179, 33);
		contentPane.add(btnPause);

		btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// On reinitialise tout
				terminer();
			}
		});
		btnStop.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnStop.setBounds(122, 232, 179, 33);
		contentPane.add(btnStop);

		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (btnPause.isEnabled() == false) {
					btnStart.setEnabled(false);
					btnPause.setEnabled(true);
					countDownTimer();
				} else {
					int heures = 0;
					int minutes = 0;
					int secondes = 0;
					if ((int) spinnerHours.getValue() == 0 && (int) spinnerMinutes.getValue() == 0
							&& (int) spinnerSecondes.getValue() == 0) {

						JOptionPane.showMessageDialog(null, "Sélectionner le temps du compte a rebours",
								"Avertissement", JOptionPane.INFORMATION_MESSAGE);
					} else {

						btnStart.setEnabled(false);
						btnStart.setText("Redémarrer");
						// On transforme le tout en secondes pour la bar de progression
						if ((int) spinnerHours.getValue() > 0) {
							heures = ((int) spinnerHours.getValue()) * 60 * 60;
						}
						if ((int) spinnerMinutes.getValue() > 0) {
							minutes = ((int) spinnerMinutes.getValue()) * 60;
						}
						if ((int) spinnerSecondes.getValue() > 0) {
							secondes = (int) spinnerSecondes.getValue();
						}
						cdTimestamp = heures + minutes + secondes;
						// On definie le maximum de la barre de progression
						pbCountDown.setMaximum(cdTimestamp);
						pbValue = cdTimestamp;
						countDownTimer();
					}
				}
			}
		});

	}

	public void countDownTimer() {
		// on creer un thread pour rafraichir les elements
		new Thread(new Runnable() {
			public void run() {

				while (pbValue >= 0) {
					if (btnPause.isEnabled() == false || btnStop.isEnabled() == false) {
						return;
					}

					int heures = pbValue / 3600;
					int minutes = (pbValue % 3600) / 60;
					int seconds = pbValue % 60;

					String timeString = String.format("%02d:%02d:%02d", heures, minutes, seconds);

					pbCountDown.setString(timeString);
					pbCountDown.setValue(pbValue);
					pbValue -= 1;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				terminer();
				JOptionPane.showMessageDialog(null, "Le compte a rebours est terminé !!", "Avertissement",
						JOptionPane.INFORMATION_MESSAGE);
			}
			// on lance le thread
		}).start();

	}

	public void terminer() {
		spinnerHours.setValue(0);
		spinnerMinutes.setValue(0);
		spinnerSecondes.setValue(0);
		pbCountDown.setString("00:00:00");
		pbCountDown.setValue(0);
		pbValue = 0;

		btnStart.setEnabled(true);
		btnStart.setText("Démarrer");
		btnPause.setEnabled(true);
		btnStop.setEnabled(true);
	}

}
