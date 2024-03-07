package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;

public class VehicleDao {
	
	private static VehicleDao instance = null;
	private VehicleDao() {}
	public static VehicleDao getInstance() {
		if(instance == null) {
			instance = new VehicleDao();
		}
		return instance;
	}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
	private static final String COUNT_VEHICLES_QUERY = "SELECT COUNT(*) FROM Vehicle;" ;

	public long create(Vehicle vehicle) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(CREATE_VEHICLE_QUERY);

			ps.setString(1, vehicle.getConstructeur());
			ps.setString(2, vehicle.getModele());
			ps.setInt(3, vehicle.getNb_places());

			ps.execute();

			ResultSet resultSet = ps.getGeneratedKeys();

			if (resultSet.next()) {
				int id = resultSet.getInt(1) ;
				vehicle.setId(id);
				return id ;
			}

			ps.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException("Échec de la création du véhicule : " + vehicle.toString(), e);
		}
		return -1 ;
	}

	public long delete(Vehicle vehicle) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(DELETE_VEHICLE_QUERY);

			ps.setInt(1,vehicle.getId());

			return ps.executeUpdate() ;

		} catch (SQLException e) {
			throw new DaoException("Échec de la suppression du véhicule avec l'ID : " + vehicle.getId(), e);
		}
	}

	public Vehicle findById(int id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps =
					connection.prepareStatement(FIND_VEHICLE_QUERY);

			ps.setInt(1, id);

			//ps.execute();

			ResultSet resultSet = ps.executeQuery();

			if(resultSet.next()){
				String constructeur = resultSet.getString("constructeur");
				String modele = resultSet.getString("modele");
				Integer nb_places = resultSet.getInt("nb_places");
				return new Vehicle(id,constructeur,modele,nb_places);
			}

			ps.close();
			connection.close();

		} catch (SQLException e) {
			throw new DaoException();
		}
		return null;
	}

	public List<Vehicle> findAll() throws DaoException {
		try{
			System.out.println("lancement 3");
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_VEHICLES_QUERY);

			ResultSet resultSet = ps.executeQuery();

			List<Vehicle> listeVehicles = new ArrayList<>();
			while(resultSet.next()) {
				Integer id = resultSet.getInt("id");
				String constructeur = resultSet.getString("constructeur");
				String modele = resultSet.getString("modele");
				Integer nb_places = resultSet.getInt("nb_places");
				listeVehicles.add(new Vehicle(id,constructeur,modele,nb_places));
			}
			return listeVehicles ;

		} catch (SQLException e) {
			throw new DaoException("Échec de la récupération de la liste des véhicules", e);
		}
	}
	public int countVehicles() throws DaoException {
		int count = 0;
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(COUNT_VEHICLES_QUERY);

			ResultSet resultSet = ps.executeQuery();

			if (resultSet.next()) {
				count = resultSet.getInt(1);
			}

			resultSet.close();
			ps.close();
			connection.close();
		} catch (SQLException e) {
			throw new DaoException("Échec de la récupération du nombre de véhicules", e);
		}
		return count;
	}

}
