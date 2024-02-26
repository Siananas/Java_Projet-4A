package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;

public class ReservationDao {

	private static ReservationDao instance = null;
	private ReservationDao() {}
	public static ReservationDao getInstance() {
		if(instance == null) {
			instance = new ReservationDao();
		}
		return instance;
	}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATION = "SELECT id_client, vehicle_id, debut, fin FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
		
	public long create(Reservation reservation) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(CREATE_RESERVATION_QUERY);

			ps.setInt(1, reservation.getClient_id());
			ps.setInt(2, reservation.getVehicule_id());
			ps.setDate(3,Date.valueOf(reservation.getDebut()));
			ps.setDate(4,Date.valueOf(reservation.getFin()));

			ps.execute();

			ResultSet resultSet = ps.getGeneratedKeys();

			if (resultSet.next()) {
				int id = resultSet.getInt(1) ;
				reservation.setId(id);
				return id ;
			}

			ps.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException();
		}
		return -1 ;
	}
	
	public long delete(Reservation reservation) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(DELETE_RESERVATION_QUERY);

			ps.setInt(1,reservation.getId());

			return ps.executeUpdate() ;

		} catch (SQLException e){
			throw new DaoException();
		}
	}

	public Reservation findReservationById(int reservationId) throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATION);

			ps.setInt(1, reservationId);

			ResultSet resultSet = ps.executeQuery();

			if (resultSet.next()) {
				Integer Client_id = resultSet.getInt("client_id");
				Integer Vehicule_id = resultSet.getInt("vehicle_id");
				LocalDate debut = resultSet.getDate("debut").toLocalDate();
				LocalDate fin = resultSet.getDate("fin").toLocalDate();
				 return new Reservation(reservationId, Client_id, Vehicule_id, debut, fin) ;
			}
			return null ;

		} catch (SQLException e){
			throw new DaoException() ;
		}
	}

	
	public List<Reservation> findResaByClientId(int clientId) throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);

			ps.setInt(1, clientId);

			ResultSet resultSet = ps.executeQuery();

			List<Reservation> listeReservations = new ArrayList<>();

			while(resultSet.next()) {
				Integer id = resultSet.getInt("id");
				Integer Vehicule_id = resultSet.getInt("vehicle_id");
				LocalDate debut = resultSet.getDate("debut").toLocalDate();
				LocalDate fin = resultSet.getDate("fin").toLocalDate();
				listeReservations.add(new Reservation(id, clientId, Vehicule_id, debut, fin)) ;
			}
			return listeReservations ;

		} catch (SQLException e){
			throw new DaoException() ;
		}
	}

	public List<Reservation> findResaByVehicleId(int vehicleId) throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);

			ps.setInt(1, vehicleId);

			ResultSet resultSet = ps.executeQuery();

			List<Reservation> listeReservations = new ArrayList<>();

			while(resultSet.next()) {
				Integer id = resultSet.getInt("id");
				Integer Client_id = resultSet.getInt("client_id");
				LocalDate debut = resultSet.getDate("debut").toLocalDate();
				LocalDate fin = resultSet.getDate("fin").toLocalDate();
				listeReservations.add(new Reservation(id, Client_id, vehicleId, debut, fin)) ;
			}
			return listeReservations ;

		} catch (SQLException e){
			throw new DaoException() ;
		}
	}

	public List<Reservation> findAll() throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_QUERY);

			ResultSet resultSet = ps.executeQuery();

			List<Reservation> listeReservations = new ArrayList<>();

			while(resultSet.next()) {
				Integer id = resultSet.getInt("id");
				Integer Client_id = resultSet.getInt("client_id");
				Integer Vehicule_id = resultSet.getInt("vehicle_id");
				LocalDate debut = resultSet.getDate("debut").toLocalDate();
				LocalDate fin = resultSet.getDate("fin").toLocalDate();
				listeReservations.add(new Reservation(id, Client_id, Vehicule_id, debut, fin)) ;
			}
			return listeReservations ;

		} catch (SQLException e){
			throw new DaoException() ;
		}
	}
}
